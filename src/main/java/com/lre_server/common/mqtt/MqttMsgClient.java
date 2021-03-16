package com.lre_server.common.mqtt;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ClassName: MqttClient
 * @Author: niliqiang
 * @Date: 2021/3/15
 * @Description: TODO
 */
@Component
public class MqttMsgClient {
    private static Logger logger= LoggerFactory.getLogger(MqttMsgClient.class);

    private static MqttMsgCallback mqttMsgCallback;
    @Autowired
    public void setMqttMsgCallback(MqttMsgCallback mqttMsgCallback) {
        MqttMsgClient.mqttMsgCallback = mqttMsgCallback;
    }

    private static MqttConfig mqttConfig;
    @Autowired
    public void setMqttConfig(MqttConfig mqttConfig) {
        MqttMsgClient.mqttConfig = mqttConfig;
    }

    private MqttClient mqttClient;

    // 利用静态内部类实现单例
    private static class MqttMsgClientInstance {
        private static MqttMsgClient instance = new MqttMsgClient();
    }

    private MqttMsgClient() {
    }

    public static MqttMsgClient getInstance() {
        return MqttMsgClientInstance.instance;
    }

    public void connectBroker() {
        try {
            mqttClient = new MqttClient(mqttConfig.getBrokerUrl(), mqttConfig.getClientId(), new MemoryPersistence());
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setKeepAliveInterval(mqttConfig.getKeepAlive());
            connOpts.setCleanSession(mqttConfig.getCleanSession());
            connOpts.setConnectionTimeout(mqttConfig.getTimeout());
            try {
                mqttClient.setCallback(mqttMsgCallback);
                mqttClient.connect(connOpts);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 主题消息推送
     * @param topic
     * @param pushMessage
     * @param qos
     */
    public void publish(String topic, String pushMessage, int qos) {
        MqttTopic mTopic = mqttClient.getTopic(topic);
        MqttMessage mMessage = new MqttMessage();
        mMessage.setPayload(pushMessage.getBytes());
        mMessage.setQos(qos);
        try {
            MqttDeliveryToken mqttDeliveryToken = mTopic.publish(mMessage);
            mqttDeliveryToken.waitForCompletion();
            logger.info("主题消息推送成功:" + topic);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    /**
     * 主题订阅
     * @param topic
     * @param qos
     */
    public void subscribe(String topic, int qos) {
        try {
            mqttClient.subscribe(topic, qos);
            logger.info("主题订阅成功:" + topic);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
