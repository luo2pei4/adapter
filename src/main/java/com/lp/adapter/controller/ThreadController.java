package com.lp.adapter.controller;

import com.lp.adapter.core.KeyAndQueueMapping;
import com.lp.adapter.core.ThreadManagement;
import com.lp.adapter.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.lp.adapter.core.QueueInformation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

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

	@RequestMapping(value = "/start", method = RequestMethod.POST)
	public JSONObject start(@RequestBody JSONObject jsonObject) {

		Integer threadNum = jsonObject.getInteger("threadnum");

		if (threadNum != null && threadNum > 0) {

			for (int i = 0; i < threadNum; i++) {

				ThreadManagement.addNewThread();
			}
		}

		try {

			File file = new File("E:\\Temp\\adsb-output.txt");
			BufferedReader br = new BufferedReader(new FileReader(file));

			String line;

			while ((line = br.readLine()) != null) {

				JSONObject jsonObj = JSONObject.parseObject(line);
				String craftNo = jsonObj.getString("an");
				String queueName = null;

				if (!StringUtil.isEmpty(craftNo)) {

					queueName = KeyAndQueueMapping.save(craftNo);
				}

				if (!StringUtil.isEmpty(queueName)) {

					QueueInformation.add(queueName, jsonObj);
				}
			}

			br.close();

		} catch (Exception e) {

			e.printStackTrace();
		}

		return new JSONObject();
	}
}
