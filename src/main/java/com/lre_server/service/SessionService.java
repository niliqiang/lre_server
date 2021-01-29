package com.lre_server.service;

import com.github.pagehelper.PageInfo;
import com.lre_server.entity.SessionInfo;

/**
 * @InterfaceName: SessionService
 * @Author: niliqiang
 * @Date: 2021/1/29
 * @Description: TODO
 */
public interface SessionService {
    PageInfo<SessionInfo> querySessionList(int page, int size);
}
