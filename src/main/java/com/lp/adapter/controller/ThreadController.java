package com.lp.adapter.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.lp.adapter.core.KeyAndQueueMapping;
import com.lp.adapter.core.QueueInformation;
import com.lp.adapter.core.ThreadGroupInformation;
import com.lp.adapter.service.BusinessService;
import com.lp.adapter.utils.SpringUtil;
import com.lp.adapter.utils.StringUtil;

/**
 * 解析应用注册API
 * 
 * @author luo2p
 */
@RestController
@RequestMapping("/adapter")
public class ThreadController {

	private static Logger logger = LoggerFactory.getLogger(ThreadController.class);

	@RequestMapping(value = "/addthread", method = RequestMethod.GET)
	public JSONObject addThread() {

		logger.info("收到新增处理线程请求.");

		// 生成唯一标识符。用uuid作为线程和内部队列通道的名称
		String uuid = StringUtil.getUuid();

		logger.info("生成线程名称......" + uuid);

		// 添加队列通道
		QueueInformation.addNewQueue(uuid);

		logger.info("添加队列通道......success");
		
		// 添加初始映射
		KeyAndQueueMapping.addInitialMapping(uuid);

		logger.info("添加初始映射......success");
		
		// 获取新的业务对象实例
		BusinessService businessService = (BusinessService)SpringUtil.getBean("businessService");
		businessService.setQueueName(uuid);

		logger.info("新建线程实例......success");

		// 实例化线程，并将线程加入线程组中。传入参数包括线程组对象，业务对象和作为线程名称的uuid
		Thread businessThread = new Thread(ThreadGroupInformation.getThreadGroup(), businessService);
		
		// 启动线程
		businessThread.start();

		logger.info("启动新建线程......success");

		JSONObject threadJsonObject = new JSONObject();
		threadJsonObject.put("newThreadId", businessThread.getId());
		threadJsonObject.put("newThreadName", uuid);
		threadJsonObject.put("newThreadStatus", "OK");

		JSONObject threadGroupJsonObject = new JSONObject();
		threadGroupJsonObject.put("currentActiveThreadCount", ThreadGroupInformation.getCurrentActiveThreadCount());
		threadGroupJsonObject.put("currentQueueCount", QueueInformation.getQueueCount());
		threadJsonObject.put("threadGroupInfo", threadGroupJsonObject);

		return threadJsonObject;
	}
}
