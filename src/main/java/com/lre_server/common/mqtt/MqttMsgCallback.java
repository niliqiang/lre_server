package com.lre_server.common.mqtt;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lre_server.common.tools.JsonResult;
import com.lre_server.service.SessionService;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
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
    @Autowired
    private SessionService sessionService;

    static String connectAckStr = "{\"msgId\":20, \"sessionId\":\"\", \"state\":{\"acknowledge\":{\"connection\":\"\"}}}";

    @Override
    public void connectionLost(Throwable throwable) {
        // 连接丢失后，一般在这里面进行重连
        logger.info("连接断开，准备重新连接...");
        mqttConfig.getMqttMsgClient();
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        // subscribe后得到的消息会执行到这里面
        logger.info("接收消息主题:" + topic);
        logger.info("接收消息Qos:" + message.getQos());
        String strMsg = new String(message.getPayload());
        logger.info("接收消息内容:" + strMsg);
        JSONObject jMsg = JSONObject.parseObject(strMsg);
        Integer msgId = jMsg.getInteger("msgId");
        switch (msgId / 10) {
            // TOPIC_PUB_CONNECTION
            case 1: {
                String clientId = jMsg.getString("clientId");
                String clientName = jMsg.getJSONObject("state").getJSONObject("reported").getString("connection");
                JsonResult jRes = sessionService.addSession(clientId);
                if (jRes.getCode() == 0) {
                    MqttMsgClient mqttMsgClient =  MqttMsgClient.getInstance();
                    JSONObject jResData = (JSONObject) JSONObject.toJSON(jRes.getData());
                    String partTopic = jResData.getString("userName") + "/" + clientName;
                    // 根据用户名和设备名订阅主题
                    String topicDirectiveAck = "lre/"+ partTopic + "/directive/acknowledge";
                    mqttMsgClient.subscribe(topicDirectiveAck, 0);
                    String topicEvent = "lre/"+ partTopic + "/event";
                    mqttMsgClient.subscribe(topicEvent, 0);
                    // 更新ConnectAck消息并推送
                    JSONObject jConnectAck = JSON.parseObject(connectAckStr);
                    jConnectAck.put("sessionId", jResData.getString("sessionId"));
                    jConnectAck.getJSONObject("state").getJSONObject("acknowledge").put("connection", clientName);
                    String topicConnectAck = "lre/" + partTopic + "/connection/acknowledge";
                    // 由于publish函数中有阻塞过程，需要开启新线程执行
                    new Thread() {
                        public void run() {
                            mqttMsgClient.publish(topicConnectAck, jConnectAck.toString(), 0);
                        }
                    }.start();
                } else {
                    logger.info("设备不存在");
                }
                break;
            }
            // TOPIC_PUB_DIRECTIVE_ACK
            case 4: {
                logger.info("接收终端指令响应:" + jMsg.getJSONObject("state").getJSONObject("acknowledge").getString("directive"));
                break;
            }
            // TOPIC_PUB_EVENT
            case 5: {
                switch (msgId % 10) {
                    // WakeUp
                    case 0: {
                        logger.info("接收终端事件上报:" + jMsg.getJSONObject("state").getJSONObject("reported").getString("event"));
                        break;
                    }
                    // StartCapture
                    case 1: {
                        logger.info("接收终端事件上报:" + jMsg.getJSONObject("state").getJSONObject("reported").getString("event"));
                        break;
                    }
                    // StartRecognize
                    case 2: {
                        logger.info("接收终端事件上报:" + jMsg.getJSONObject("state").getJSONObject("reported").getString("event"));
                        break;
                    }
                    // CompleteTask
                    case 3: {
                        logger.info("接收终端事件上报:" + jMsg.getJSONObject("state").getJSONObject("reported").getString("event"));
                        break;
                    }
                    // WakeUpTimeOut
                    case 4: {
                        logger.info("接收终端事件上报:" + jMsg.getJSONObject("state").getJSONObject("reported").getString("event"));
                        break;
                    }
                    // StopCapture
                    case 5: {
                        logger.info("接收终端事件上报:" + jMsg.getJSONObject("state").getJSONObject("reported").getString("event"));
                        break;
                    }
                    // NeedMoreSpeech
                    case 6: {
                        logger.info("接收终端事件上报:" + jMsg.getJSONObject("state").getJSONObject("reported").getString("event"));
                        break;
                    }
                    // ExpectLRAgain
                    case 7: {
                        logger.info("接收终端事件上报:" + jMsg.getJSONObject("state").getJSONObject("reported").getString("event"));
                        break;
                    }
                    default: {
                        logger.info("msgId 指示的消息不匹配");
                        break;
                    }
                }
            }
            default: {
                logger.info("msgId 指示的主题不匹配");
                break;
            }
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        System.out.println("deliveryComplete---------" + token.isComplete());
    }
}
