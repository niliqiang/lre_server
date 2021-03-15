package com.lre_server.common.websocket;


import com.lre_server.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @ClassName: WebSocketServer
 * @Author: niliqiang
 * @Date: 2019/8/24
 * @Description: TODO
 */
@ServerEndpoint("/websocket/{userName}")
@Component
public class WebSocketServer {
    private static Logger logger= LoggerFactory.getLogger(WebSocketServer.class);
    // 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;
    // concurrent包的线程安全HashMap，用来存放每个客户端对应的WebSocket对象。
    private static ConcurrentHashMap<String, CopyOnWriteArraySet<WebSocketServer>> webSocketHashMap = new ConcurrentHashMap<>();
    // 与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    private String userName = null;

    // 因为websocket是多实例单线程的，而websocket中的对象在@Autowried时，只有整个项目启动时会注入
    // 之后新的websocket实例都不会再次注入，故websocket上@Autowried的bean是会为null的
    // 手动注入
    private static UserService userService;
    @Autowired
    public void setUserService(UserService userService) {
        WebSocketServer.userService = userService;
    }

    /**
     * 连接建立成功调用的方法*/
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "userName") String userName) {
        this.session = session;
        this.userName = userName;
        // 判断当前用户是否为管理员
        String roleName = userService.getUserRoleName(userName);
        if (roleName.equals("ADMIN")) {
            // 注册时对用户名做了校验，不会与"#ADMIN"重复
            userName = "#ADMIN";
        }
        // 用户不是初次建立连接时，向Vector中添加元素
        if (webSocketHashMap.containsKey(userName)) {
            webSocketHashMap.get(userName).add(this);
        } else {
            CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<>();
            webSocketSet.add(this);
            webSocketHashMap.put(userName, webSocketSet);
        }
        addOnlineCount();           //在线数加1
        logger.info("用户" + this.userName + "连接！当前在线连接数为" + getOnlineCount());
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        // 用户连接数不止一个时，移除对应元素
        String userName = this.userName;
        if (!webSocketHashMap.containsKey(userName)) {
            userName = "#ADMIN";
        }
        if (webSocketHashMap.get(userName).size() > 1) {
            webSocketHashMap.get(userName).remove(this);
        } else {
            webSocketHashMap.remove(userName);
        }
        subOnlineCount();                   //在线数减1
        logger.info("有一连接关闭！当前在线连接数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息*/
    @OnMessage
    public void onMessage(String message, Session session) {
        logger.info("收到来自客户端的信息:" + message);
    }

    /**
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        logger.error("WebSocket发生错误");
        error.printStackTrace();
    }

    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    /**
     * 发送信息给指定ID用户和管理员用户
     * @param userName
     * @param message
     * @throws IOException
     */
    public static void sendToUser(String userName, String message) throws IOException  {
        logger.info("发送消息给用户" + userName + "，推送内容:" + message);
        for (String key : new String[]{userName, "#ADMIN"}) {
            if (webSocketHashMap.containsKey(key)) {
                for (WebSocketServer item : webSocketHashMap.get(key)) {
                    try {
                        item.sendMessage(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    /**
     * 群发自定义消息
     * */
    public static void sendToAll(String message) throws IOException {
        logger.info("群发消息，推送内容:" + message);
        for (String userName : webSocketHashMap.keySet())
            for (WebSocketServer item : webSocketHashMap.get(userName)) {
                try {
                    item.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }
}
