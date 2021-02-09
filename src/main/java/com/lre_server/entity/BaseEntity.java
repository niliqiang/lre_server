package com.lre_server.entity;

import lombok.Data;

/**
 * @ClassName: BaseEntity
 * @Author: niliqiang
 * @Date: 2021/2/9
 * @Description: 用于页面查询的BaseEntity
 */
@Data
public class BaseEntity {

    private int page = 1;

    private int limit = 10;

    private String startTime;

    private String endTime;

}
