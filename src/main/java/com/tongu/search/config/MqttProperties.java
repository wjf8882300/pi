package com.tongu.search.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@Data
@ConfigurationProperties(prefix = "mqtt")
public class MqttProperties {

	private Map<String, Mqtt> clientList;

	@Data
	public static class Mqtt {
		private String connectionString;

		private String username;

		private String password;

		private Producer producer;

		@Data
		public final static class Producer {
			private String clientId;
			private String topic;
		}
	}


}