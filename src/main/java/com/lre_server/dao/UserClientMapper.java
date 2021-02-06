package com.lre_server.dao;

import com.lre_server.entity.UserClient;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface UserClientMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lre_server..user_client
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String clientId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lre_server..user_client
     *
     * @mbg.generated
     */
    int insert(UserClient record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lre_server..user_client
     *
     * @mbg.generated
     */
    UserClient selectByPrimaryKey(String clientId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lre_server..user_client
     *
     * @mbg.generated
     */
    List<UserClient> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lre_server..user_client
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(UserClient record);

    /**
     * 查询用户的设备列表
     * @return
     */
    List<UserClient> userClientList();

    /**
     * 根据设备名称查询设备
     * @param clientName
     * @return
     */
    UserClient selectByClientName(String clientName);
}