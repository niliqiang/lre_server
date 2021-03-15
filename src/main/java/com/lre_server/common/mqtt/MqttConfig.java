package com.lre_server.common.mqtt;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName: MqttConfig
 * @Author: niliqiang
 * @Date: 2021/3/15
 * @Description: TODO
 */
@Configuration
@Data
public class MqttConfig {
    @Value("${spring.mqtt.url}")
    private  String brokerUrl;

    @Value("${spring.mqtt.client.id}")
    private String clientId;

    @Value("${spring.mqtt.default.topic}")
    private String defaultTopic;

    @Value("${spring.mqtt.timeout}")
    private Integer timeout;

    @Value("${spring.mqtt.keepalive}")
    private Integer keepAlive;

    @Value(("${spring.mqtt.cleanSession}"))
    private Boolean cleanSession;

    @Bean
    public void getMqttMsgClient() {
        MqttMsgClient mqttMsgClient = MqttMsgClient.getInstance();
        mqttMsgClient.connectBroker();
        // TOPIC_PUB_CONNECTION
        mqttMsgClient.subscribe("lre/client/connection", 0);
    }
}
