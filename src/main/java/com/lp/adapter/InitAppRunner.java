package com.lp.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.lp.adapter.core.KeyAndQueueMapping;
import com.lp.adapter.core.QueueInformation;
import com.lp.adapter.core.ThreadGroupInformation;
import com.lp.adapter.service.BusinessService;
import com.lp.adapter.utils.SpringUtil;
import com.lp.adapter.utils.StringUtil;

/**
 * 应用程序启动后，创建线程和队列通道。
 * 
 * @author luo2p
 */
@Component
@Order(value = 1)
public class InitAppRunner implements ApplicationRunner {

	private static Logger logger = LoggerFactory.getLogger(InitAppRunner.class);
	
	@Override
	public void run(ApplicationArguments args) throws Exception {

		logger.info("初始化处理线程.");

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
	}

}
