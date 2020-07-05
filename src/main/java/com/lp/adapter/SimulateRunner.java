package com.lp.adapter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.lp.adapter.core.KeyAndQueueMapping;
import com.lp.adapter.core.QueueInformation;
import com.lp.adapter.utils.StringUtil;

@Component
@Order(value = 2)
public class SimulateRunner implements ApplicationRunner {

	@Override
	public void run(ApplicationArguments args) throws Exception {

		System.out.println("-----------------------------------------------------------");
		System.out.println("初始化模拟器");

		try {

			System.out.println("程序暂停，做线程增加操作。");
			Thread.sleep(30000);

			File file = new File("E:\\Temp\\adsb-output.txt");
			BufferedReader br = new BufferedReader(new FileReader(file));

			String line = null;

			while ((line = br.readLine()) != null) {

				JSONObject jsonObject = JSONObject.parseObject(line);
				String craftNo = jsonObject.getString("an");
				String queueName = null;

				if (!StringUtil.isEmpty(craftNo)) {

					queueName = KeyAndQueueMapping.save(craftNo);
				}

				if (!StringUtil.isEmpty(queueName)) {

					QueueInformation.add(queueName, jsonObject);
				}
			}

			br.close();
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}

}
