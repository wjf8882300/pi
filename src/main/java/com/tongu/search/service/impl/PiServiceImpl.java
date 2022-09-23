package com.tongu.search.service.impl;

import com.tongu.search.mqtt.MqttClientSender;
import com.tongu.search.service.PiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PiServiceImpl implements PiService {

    @Autowired
    private MqttClientSender mqttSender;

    @Override
    public void send(String type) {
        mqttSender.sendMessage("pi/cmd", type);
    }
}
