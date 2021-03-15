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
//        connectBroker();
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

    public void publish(String topic, String pushMessage, int qos) {
        MqttTopic mTopic = mqttClient.getTopic(topic);
        MqttMessage message = new MqttMessage();
        message.setPayload(pushMessage.getBytes());
        message.setQos(qos);
        if (null == mTopic) {
            logger.error("topic not exist");
        }
        MqttDeliveryToken mqttDeliveryToken;
        try {
            mqttDeliveryToken = mTopic.publish(message);
            mqttDeliveryToken.waitForCompletion();
        } catch (MqttPersistenceException e) {
            e.printStackTrace();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    /**
     * 订阅某个主题
     *
     * @param topic 主题
     * @param qos   连接方式
     */
    public void subscribe(String topic, int qos) {
        logger.info("订阅主题" + topic);
        try {
            mqttClient.subscribe(topic, qos);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
