package com.lp.adapter.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lp.adapter.utils.StringUtil;

public class KeyAndQueueMapping {

	private static Logger logger = LoggerFactory.getLogger(KeyAndQueueMapping.class);

	// 主要映射关系
	private static Map<String, String> keyAndQueueMap = new HashMap<String, String>();

	// 该映射主要便于后续计数处理
	private static Map<String, List<String>> queueAndKeyListMap = new HashMap<String, List<String>>();

	/**
	 * 添加队列名和关键字列表映射
	 * 
	 * @param queueName 队列名称
	 */
	public synchronized static void addInitialMapping(String queueName) {
		
		List<String> list = new ArrayList<String>();
		queueAndKeyListMap.put(queueName, list);
	}

	/**
	 * 自动选择关键字对应的队列通道，
	 * 保存关键字和队列通道映射。
	 * 保存成功后返回队列通道名称
	 * 
	 * @param key 关键字
	 * @return 队列通道名称
	 */
	public synchronized static String save(String key) {

		// 已经完成关键字和队列通道的情况
		if (keyAndQueueMap.containsKey(key)) {

			return keyAndQueueMap.get(key);

		} else {

			// 没有映射关系的情况为关键字分配队列通道
			Map<String, Integer> compareMap = new HashMap<String, Integer>();

			// 获取关键字数量最少的队列通道
			for (String queue : queueAndKeyListMap.keySet()) {

				compareMap.put(queue, queueAndKeyListMap.get(queue).size());
			}

			Entry<String, Integer> minEntry = Collections.min(compareMap.entrySet(), new Comparator<Entry<String, Integer>>() {

				public int compare(Entry<String, Integer> e1, Entry<String, Integer> e2) {

					return e1.getValue().compareTo(e2.getValue());
				}
			});

			logger.info("Queue name: " + minEntry.getKey() + ", Key list size: " + minEntry.getValue());

			// 保存关键字和队列通道名称的映射数据
			keyAndQueueMap.put(key, minEntry.getKey());

			// 保存队列通道名称和关键字列的映射数据
			queueAndKeyListMap.get(minEntry.getKey()).add(key);

			return minEntry.getKey();
		}
	}

	/**
	 * 判断是否存在指定的关键字映射
	 * 
	 * @param key 关键字
	 * @return 存在的情况返回true，否则返回false
	 */
	public synchronized static boolean contianesKey(String key) {
		
		if (StringUtil.isEmpty(key)) {
			
			return false;

		} else {

			return keyAndQueueMap.containsKey(key.trim());
		}
	}

	/**
	 * 通过关键字在隐射数据中获取线程名称
	 * 
	 * @param key 关键字
	 * @return
	 */
	public synchronized static String getQueueName(String key) {
		
		if (StringUtil.isEmpty(key)) {

			return null;
		}

		if (keyAndQueueMap.containsKey(key.trim())) {

			return keyAndQueueMap.get(key.trim());

		} else {

			return null;
		}
	}

	/**
	 * 将关键字从源队列移动到目标队列
	 * 
	 * @param sourceQueueName 源队列名称
	 * @param targetQueueName 目标队列名称
	 */
	public synchronized static void move(String sourceQueueName, String targetQueueName) {

		// 移动前后队列名相同的情况下不做处理
		if (sourceQueueName.equals(targetQueueName)) {

			return;

		} else {

			// 遍历关键字和队列映射关系，找出所有迁移源为sourceQueueName的机号，并将迁移源队列该为目标队列
			for (String key : keyAndQueueMap.keySet()) {

				String queueName = keyAndQueueMap.get(key);

				if (queueName.equals(sourceQueueName)) {

					keyAndQueueMap.put(key, targetQueueName);
				}
			}
		}
	}

	/**
	 * 获取映射关系数量
	 * 
	 * @return 映射关系数量
	 */
	public synchronized static int getMapSize() {
		
		return keyAndQueueMap.size();
	}

}
