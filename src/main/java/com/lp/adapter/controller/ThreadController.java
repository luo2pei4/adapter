package com.lp.adapter.controller;

import com.lp.adapter.core.ThreadManagement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.lp.adapter.core.QueueInformation;

/**
 * 解析应用注册API
 * 
 * @author luo2p
 */
@RestController
@RequestMapping("/adapter")
public class ThreadController {

	private static Logger logger = LoggerFactory.getLogger(ThreadController.class);

	/**
	 * 新增业务线程
	 *
	 * @return 新增线程结果消息
	 */
	@RequestMapping(value = "/addthread", method = RequestMethod.GET)
	public JSONObject addThread() {

		logger.info("收到新增处理线程请求.");

		Thread businessThread = ThreadManagement.addNewThread();

		JSONObject threadJsonObject = new JSONObject();
		threadJsonObject.put("newThreadId", businessThread.getId());
		threadJsonObject.put("newThreadName", businessThread.getName());
		threadJsonObject.put("newThreadStatus", "OK");

		JSONObject threadGroupJsonObject = new JSONObject();
		threadGroupJsonObject.put("currentActiveThreadCount", ThreadManagement.getCurrentActiveThreadCount());
		threadGroupJsonObject.put("currentQueueCount", QueueInformation.getQueueCount());
		threadJsonObject.put("threadGroupInfo", threadGroupJsonObject);

		return threadJsonObject;
	}

}
