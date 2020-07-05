package com.lp.adapter.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 解析程序注册信息监听任务
 * 每隔十秒钟扫描一次注册表，如果有新增则注册信息，则开通消息队列，并将消息发送到该队列。
 * 
 * @author luo2p
 *
 */
@Component
public class RegisterTask {

//	private static int appCounter = 0;

	@Scheduled(fixedRate = 10000)
	public void monitor() {

//		int counter = Register.getParseAppInfoMap().size();
//
//		if (counter != appCounter) {
//			
//			List<ParseAppInfoBean> inactiveList = Register.getInactiveThread();
//
//			for (ParseAppInfoBean parseAppInfoBean : inactiveList) {
//				
//				// 获取注册应用的应用名称
//				String appName = parseAppInfoBean.getAppName();
//				
//				// 获取新的对象实例
//				RabbitmqService rabbitmqService = (RabbitmqService)SpringUtil.getBean("rabbitmqService");
//				
//				// 向对象实例中设置队列信息，包括routingKey和appName，用于创建消息队列
//				rabbitmqService.setQueueConfig(appName, appName);
//
//				// 实例化线程，并将线程加入线程组中。
//				Thread parseAppThread = new Thread(ThreadGroupManager.getThreadGroup(), rabbitmqService, appName);
//				parseAppThread.start();
//				
//				// 设置线程状态和线程ID
//				Register.setThreadInfo(appName, parseAppThread.getId(), "Active");
//			}
//
//			// 完成各种操作后，记录最新注册数
//			appCounter = counter;
//		}
	}
}
