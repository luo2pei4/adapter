package com.lp.adapter.core;

import com.alibaba.fastjson.JSONObject;
import com.lp.adapter.utils.StringUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

public class QueueInformation {

	private static Map<String, LinkedBlockingQueue<JSONObject>> queueMap = new HashMap<>();

	/**
	 * 新增队列
	 * 使用LinkedBlockingQueue队列，限定了队列最大长度为10000，
	 * 避免数据写入过多造成的内存溢出异常
	 * 
	 * @param queueName 队列名称
	 */
	public synchronized static void addNewQueue(String queueName) {

		if (!StringUtil.isEmpty(queueName)) {

			queueMap.put(queueName.trim(), new LinkedBlockingQueue<>(10000));
		}
	}

	/**
	 * 删除指定队列
	 * 
	 * @param queueName 队列名称
	 */
	public synchronized static void deleteQueue(String queueName) {

		if (!StringUtil.isEmpty(queueName)) {

			queueMap.remove(queueName.trim());
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
	public synchronized static LinkedBlockingQueue<JSONObject> getQueue(String queueName) {

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
	 * @throws InterruptedException 线程中断异常
	 */
	public synchronized static void add(String queueName, JSONObject jsonObject) throws InterruptedException {

		if (!StringUtil.isEmpty(queueName)) {

			// put方法具有阻塞效果，当无法向队列中写入时，线程将等待
			queueMap.get(queueName).put(jsonObject);
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
