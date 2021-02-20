package com.lre_server.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @ClassName: StatsInfoEntity
 * @Author: niliqiang
 * @Date: 2021/2/20
 * @Description: 用于定义统计信息的StatsInfoEntity
 */
@Data
public class StatsInfoEntity {

    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date createDay;

    private Integer dayCount;
}
