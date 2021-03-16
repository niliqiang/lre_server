package com.lre_server.dao;

import com.lre_server.entity.SessionInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface SessionInfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lre_server..session_info
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String sessionId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lre_server..session_info
     *
     * @mbg.generated
     */
    int insert(SessionInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lre_server..session_info
     *
     * @mbg.generated
     */
    SessionInfo selectByPrimaryKey(String sessionId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lre_server..session_info
     *
     * @mbg.generated
     */
    List<SessionInfo> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lre_server..session_info
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(SessionInfo record);

    List<SessionInfo> selectSessionList(@Param(value = "sessionInfo") SessionInfo sessionInfo, @Param(value = "userId") Integer userId);

    /**
     * 根据sessionId更新会话信息和语种识别结果
     * @param record
     * @return
     */
    int updateSessionInfo(SessionInfo record);
}