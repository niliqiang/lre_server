package com.lre_server.service;

import com.github.pagehelper.PageInfo;
import com.lre_server.common.tools.JsonResult;
import com.lre_server.entity.SessionInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @InterfaceName: SessionService
 * @Author: niliqiang
 * @Date: 2021/1/29
 * @Description: TODO
 */
public interface SessionService {
    /**
     * 查询会话列表
     * @param sessionInfo
     * @return
     */
    PageInfo<SessionInfo> querySessionList(SessionInfo sessionInfo, HttpServletRequest request);

    /**
     * 删除会话
     * @param sessionIds
     * @return
     */
    JsonResult deleteSession(List<String> sessionIds);
}
