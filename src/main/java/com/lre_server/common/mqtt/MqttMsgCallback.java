package com.lre_server.common.mqtt;

import com.alibaba.fastjson.JSONObject;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ClassName: MqttMessageCallback
 * @Author: niliqiang
 * @Date: 2021/3/15
 * @Description: TODO
 */
@Component
public class MqttMsgCallback implements MqttCallback {
    private static Logger logger= LoggerFactory.getLogger(MqttMsgClient.class);

    @Autowired
    private MqttConfig mqttConfig;

    @Override
    public void connectionLost(Throwable throwable) {
        // 连接丢失后，一般在这里面进行重连
        logger.info("连接断开，准备重新连接...");
        mqttConfig.getMqttMsgClient();
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        // subscribe后得到的消息会执行到这里面
        System.out.println("接收消息主题:" + topic);
        System.out.println("接收消息Qos:" + message.getQos());
        String strMsg = new String(message.getPayload());
        System.out.println("接收消息内容:" + strMsg);
        JSONObject jMsg = JSONObject.parseObject(strMsg);
        Integer msgId = jMsg.getInteger("msgId");
        switch (msgId / 10) {
            case 1: {
                logger.info(jMsg.getJSONObject("state").getJSONObject("reported").getString("connection"));
            }
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        System.out.println("deliveryComplete---------" + token.isComplete());
    }
}
