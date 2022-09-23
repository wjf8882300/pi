package com.tongu.search.mqtt;

import com.tongu.search.config.MqttProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.eclipse.paho.client.mqttv3.*;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;
import java.util.Map;

/**
 * mqtt抽象类
 * @author ：wangjf
 * @date ：2020/8/25 12:07
 * @description：provider-com
 * @version: v1.1.0
 */
@Slf4j
abstract public class AbstractMqttSender{
    private static final String WILL_TOPIC = "willTopic";
    private static final byte[] WILL_DATA = "offline".getBytes();

    public static final long DEFAULT_COMPLETION_TIMEOUT = 30000L;

    /**
     * 获取mqtt配置
     * @return
     */
    abstract public Map<String, MqttProperties.Mqtt> getMap();

    /**
     * 获取客户端列表
     * @return
     */
    abstract public List<CustomMqttClient> getClients();

    public void sendMessage(String topic, String message) {
        for (CustomMqttClient client : getClients()) {
            MqttMessage mqttMessage = new MqttMessage();
            mqttMessage.setQos(1);
            mqttMessage.setRetained(false);
            mqttMessage.setPayload(message.getBytes());
            try {
                client.getClient().publish(topic, mqttMessage);
            } catch (MqttException e) {
                log.error("mqtt:{}, 发送消息失败", client.getClient().getServerURI(), e);
            }
        }
    }

    @PostConstruct
    public void start() {
        Map<String, MqttProperties.Mqtt> map = getMap();
        if(MapUtils.isEmpty(map)) {
            return;
        }
        map.entrySet().stream().forEach((e)->{
            MqttProperties.Mqtt mqttProperties = e.getValue();
            MqttConnectOptions mqttConnectOptions=new MqttConnectOptions();
            mqttConnectOptions.setUserName(mqttProperties.getUsername());
            mqttConnectOptions.setPassword(mqttProperties.getPassword().toCharArray());
            mqttConnectOptions.setServerURIs(new String[]{mqttProperties.getConnectionString()});
            mqttConnectOptions.setKeepAliveInterval(20);
            mqttConnectOptions.setConnectionTimeout(10);
            mqttConnectOptions.setCleanSession(true);
            mqttConnectOptions.setWill(WILL_TOPIC, WILL_DATA, 2, false);
            try {
                IMqttAsyncClient client = new MqttAsyncClient(mqttProperties.getConnectionString(), mqttProperties.getProducer().getClientId());
                CustomMqttClient customMqttClient = new CustomMqttClient(client, mqttConnectOptions, null);
                customMqttClient.getClient().connect(customMqttClient.getMqttConnectOptions()).waitForCompletion(DEFAULT_COMPLETION_TIMEOUT);
                getClients().add(customMqttClient);
                log.info("***** connect to {} successfully *****", customMqttClient.getMqttConnectOptions().getServerURIs());
            } catch (MqttException e1) {
                log.error("mqtt:{}, 建立连接失败",mqttConnectOptions.getServerURIs(), e);
            }
        });
    }

    @PreDestroy
    public void doStop() {
        try {
            for (CustomMqttClient client : getClients()) {
                client.getClient().disconnect().waitForCompletion(DEFAULT_COMPLETION_TIMEOUT);
                client.getClient().close();
            }
        } catch (MqttException e) {
            log.error("Failed to disconnect", e);
        }
    }
}
