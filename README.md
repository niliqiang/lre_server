# lre_server
  
## 简介
基于Spring Boot 2 + Thymeleaf + Layui + Spring Security + Mybatis + MySQL的语种识别系统业务处理服务器端。
  
## 项目演示
- ~~演示地址：[182.92.82.54:8102](http://182.92.82.54:8102)~~
- ~~账号密码：guest/123456~~
- 带宽不太给力，请见谅
- **2022年更新**：由于阿里云服务器到期，演示地址已失效
  
## 代码结构
```bash
├─main
│  ├─java
│  │  └─com
│  │      └─lre_server
│  │            ├─LreServerApplication.java 项目启动类
│  │            ├─common      公共资源，如文件上传配置类、Spring Security配置类、通用工具类以及WebSocket配置等
│  │            ├─controller  Controler层
│  │            ├─entity      实体类
│  │            ├─dao         DAO层
│  │            ├─service     Service层
│  │            │  └─impl     Service层实现
│  └─resources
│      ├─application.properties  通用配置文件
│      ├─generator               Mybatis-Generator配置文件
│      ├─mapper                  Mybatis XML文件
│      ├─static                  静态文件
│      │  ├─css                  通用css文件
│      │  ├─images               静态图片
│      │  ├─js                   通用js文件
│      │  ├─layui                layui库
│      │  └─layui-ext            layui插件库
│      └─templates               项目页面目录
│          ├─client              设备管理
│          ├─file                文件管理
│          ├─role                角色管理
│          ├─session             会话管理
│          ├─user                用户管理
```

## 其他说明
此工程是语种识别系统的server端实现  
语种识别系统的语种识别算法实现见：[kaldi_lre](https://github.com/niliqiang/kaldi_lre)  
语种识别系统的client端实现由于可能涉及到商业秘密，暂不开源，如需要参考请单独联系  
基于此语种识别系统完成的硕士毕业论文：[倪立强. 基于i-vector的语种识别系统设计与实现[D].北京邮电大学,2021.](https://kns.cnki.net/kcms/detail/detail.aspx?dbcode=CMFD&dbname=CMFD202201&filename=1021130386.nh&uniplatform=NZKPT&v=Oxe1lP-f9RxxsTkUI8AR0V4ktJenmmobzK4lmEXds_M3LM8hpOrMBLWa2R_zkNGe)  
