package com.lp.adapter.runner;

import com.lp.adapter.core.ThreadManagement;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 应用程序启动后，创建第一个线程和队列通道。
 * 
 * @author luo2p
 */
@Component
@Order(value = 1)
public class InitAppRunner implements ApplicationRunner {

	@Override
	public void run(ApplicationArguments args) throws Exception {

		ThreadManagement.addNewThread();
	}

}
