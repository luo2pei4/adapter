package com.lp.adapter.core;

import com.lp.adapter.service.BusinessService;
import com.lp.adapter.utils.SpringUtil;
import com.lp.adapter.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 线程管理操作类
 *
 * @author luopei
 * @version 1.0
 */
public class ThreadManagement {

    private static Logger logger = LoggerFactory.getLogger(ThreadManagement.class);

    private static ThreadGroup threadGroup = new ThreadGroup("businessThreadGroup");

    // 获取当前线程数量
    public static int getCurrentActiveThreadCount() {

        return threadGroup.activeCount();
    }

    public static Thread addNewThread() {

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
        BusinessService businessService = (BusinessService) SpringUtil.getBean("businessService");
        businessService.setQueueName(uuid);

        logger.info("新建线程实例......success");

        // 实例化线程，并将线程加入线程组中。传入参数包括线程组对象，业务对象和作为线程名称的uuid
        Thread businessThread = new Thread(threadGroup, businessService);

        // 用队列通道名称作为线程的名称
        businessThread.setName(uuid);

        // 启动线程
        businessThread.start();

        logger.info("启动新建线程......success");

        return businessThread;
    }

}
