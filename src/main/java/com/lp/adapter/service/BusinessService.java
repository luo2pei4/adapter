package com.lp.adapter.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.lp.adapter.entity.AdsbEntity;
import com.lp.adapter.entity.FlightInfoEntity;
import com.lp.adapter.core.QueueInformation;
import com.lp.adapter.repository.AdsbRepository;
import com.lp.adapter.repository.FlightInfoRepository;
import com.lp.adapter.utils.DateUtil;
import com.lp.adapter.utils.StringUtil;

@Service(value = "businessService")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class BusinessService extends Thread {

	private static Logger logger = LoggerFactory.getLogger(BusinessService.class);

	@Autowired
	private AdsbRepository adsbRepository;

	@Autowired
	private FlightInfoRepository flightInfoRepository;
	
	private String queueName;

	public void setQueueName(String queueName) {

		this.queueName = queueName;
	}

	@Override
	public void run() {

		Queue<JSONObject> queue = QueueInformation.getQueue(queueName);

		// 从队列通道中获取数据并打印
		while (true) {

			try {

				if (queue != null) {

					if (!queue.isEmpty()) {

						JSONObject jsonObject = queue.poll();
						AdsbEntity adsbEntity = new AdsbEntity();
						adsbEntity.setArr(jsonObject.getString("arr"));
						adsbEntity.setDep(jsonObject.getString("dep"));
						adsbEntity.setFi(jsonObject.getString("fi"));
						adsbEntity.setIcao24(jsonObject.getString("icao24"));
						adsbEntity.setAn(jsonObject.getString("an"));
						adsbEntity.setAlt(jsonObject.getString("alt"));
						adsbEntity.setLon(jsonObject.getString("lon"));
						adsbEntity.setCas(jsonObject.getString("cas"));
						adsbEntity.setLat(jsonObject.getString("lat"));

						if (StringUtil.isEmpty(jsonObject.getString("vec"))) {

							adsbEntity.setVec(BigDecimal.valueOf(0.0));

						} else {

							adsbEntity.setVec(jsonObject.getBigDecimal("vec"));
						}

						adsbEntity.setSsr(jsonObject.getString("ssr"));
						adsbEntity.setType(jsonObject.getString("type"));
						adsbEntity.setSendTime(DateUtil.getLocalDateTime(jsonObject.getString("sendTime"), "yyyyMMddHHmmss"));
						adsbEntity.setThreadid(queueName);
						adsbEntity.setCreatetime(LocalDateTime.now());
						adsbRepository.saveAndFlush(adsbEntity);

						FlightInfoEntity flightInfoEntity = flightInfoRepository.searchEntityByAn(jsonObject.getString("an"));

						if (flightInfoEntity == null) {

							flightInfoEntity = new FlightInfoEntity();
							flightInfoEntity.setAn(jsonObject.getString("an"));
						}

						flightInfoEntity.setArr(jsonObject.getString("arr"));
						flightInfoEntity.setDep(jsonObject.getString("dep"));
						flightInfoEntity.setFi(jsonObject.getString("fi"));
						flightInfoEntity.setIcao24(jsonObject.getString("icao24"));
						flightInfoEntity.setAlt(jsonObject.getString("alt"));
						flightInfoEntity.setLon(jsonObject.getString("lon"));
						flightInfoEntity.setCas(jsonObject.getString("cas"));
						flightInfoEntity.setLat(jsonObject.getString("lat"));

						if (StringUtil.isEmpty(jsonObject.getString("vec"))) {

							adsbEntity.setVec(BigDecimal.valueOf(0.0));

						} else {

							adsbEntity.setVec(jsonObject.getBigDecimal("vec"));
						}

						flightInfoEntity.setSsr(jsonObject.getString("ssr"));
						flightInfoEntity.setType(jsonObject.getString("type"));
						flightInfoEntity.setSendTime(DateUtil.getLocalDateTime(jsonObject.getString("sendTime"), "yyyyMMddHHmmss"));
						flightInfoEntity.setThreadid(queueName);
						flightInfoEntity.setCreatetime(LocalDateTime.now());

						flightInfoRepository.saveAndFlush(flightInfoEntity);

					} else {

						sleep(1000);
					}
				}

			} catch (Exception e) {

				logger.error("处理异常", e);
			}
		}
	}

}
