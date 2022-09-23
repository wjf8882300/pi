package com.tongu.search.mqtt;

import com.tongu.search.config.MqttProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 发送MQTT
 * @author ：wangjf
 * @date ：2020/8/25 12:30
 * @description：provider-com
 * @version: v1.1.0
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(MqttProperties.class)
public class MqttClientSender extends AbstractMqttSender {

	@Autowired
	private MqttProperties mqttProperties;

	private static List<CustomMqttClient> clients = new ArrayList<>();

	@Override
	public Map<String, MqttProperties.Mqtt> getMap() {
		return mqttProperties.getClientList();
	}

	@Override
	public List<CustomMqttClient> getClients() {
		return clients;
	}
}
