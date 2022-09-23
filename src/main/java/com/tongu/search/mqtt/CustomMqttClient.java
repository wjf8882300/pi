package com.tongu.search.mqtt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;

import java.util.List;

/**
 * mqtt链接对象
 * @author ：wangjf
 * @date ：2020/8/25 11:50
 * @description：provider-com
 * @version: v1.1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomMqttClient {

    private IMqttAsyncClient client;
    private MqttConnectOptions mqttConnectOptions;
    private List<String> topics;
}
