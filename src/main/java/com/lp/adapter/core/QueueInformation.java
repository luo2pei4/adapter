package com.lp.adapter.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.alibaba.fastjson.JSONObject;
import com.lp.adapter.utils.StringUtil;

public class QueueInformation {

	private static Map<String, Queue<JSONObject>> queueMap = new HashMap<String, Queue<JSONObject>>();

	/**
	 * 新增队列
	 * 
	 * @param queueName 队列名称
	 */
	public synchronized static void addNewQueue(String queueName) {

		if (!StringUtil.isEmpty(queueName)) {

			queueMap.put(queueName.trim(), new ConcurrentLinkedQueue<JSONObject>());
		}
	}

	/**
	 * 删除指定队列
	 * 
	 * @param queueName 队列名称
	 */
	public synchronized static void deleteQueue(String queueName) {

		if (!StringUtil.isEmpty(queueName)) {

			if (queueMap.containsKey(queueName.trim())) {

				queueMap.remove(queueName.trim());
			}
		}
	}

	/**
	 * 获取指定队列的长度
	 * 
	 * @param queueName 队列名称
	 * @return 指定队列的长度
	 */
	public synchronized static int getQueueLength(String queueName) {

		if (!StringUtil.isEmpty(queueName)) {

			return queueMap.get(queueName.trim()).size();

		} else {

			return 0;
		}
	}

	/**
	 * 获取当前队列数量
	 * 
	 * @return 当前队列数量
	 */
	public synchronized static int getQueueCount() {

		return queueMap.size();
	}

	/**
	 * 获取指定队列
	 * 
	 * @param queueName 队列名称
	 * @return 指定队列
	 */
	public synchronized static Queue<JSONObject> getQueue(String queueName) {

		if (!StringUtil.isEmpty(queueName)) {

			if (queueMap.containsKey(queueName)) {

				return queueMap.get(queueName);
			}
		}

		return null;
	}

	/**
	 * 向指定队列添加消息
	 * 
	 * @param queueName 队列名称
	 * @param jsonObject json对象
	 */
	public synchronized static void add(String queueName, JSONObject jsonObject) {

		if (!StringUtil.isEmpty(queueName)) {

			queueMap.get(queueName).add(jsonObject);
		}
	}
	
	public static String getOnlyOneQueueName() {

		String queueName = null;

		for (String key : queueMap.keySet()) {
			
			queueName = key;
		}
		
		return queueName;
	}
}
