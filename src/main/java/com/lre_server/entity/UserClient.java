package com.lre_server.entity;

import java.util.Date;

public class UserClient {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column lre_server..user_client.client_id
     *
     * @mbg.generated
     */
    private String clientId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column lre_server..user_client.user_id
     *
     * @mbg.generated
     */
    private Integer userId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column lre_server..user_client.create_time
     *
     * @mbg.generated
     */
    private Date createTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column lre_server..user_client.client_id
     *
     * @return the value of lre_server..user_client.client_id
     *
     * @mbg.generated
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column lre_server..user_client.client_id
     *
     * @param clientId the value for lre_server..user_client.client_id
     *
     * @mbg.generated
     */
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column lre_server..user_client.user_id
     *
     * @return the value of lre_server..user_client.user_id
     *
     * @mbg.generated
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column lre_server..user_client.user_id
     *
     * @param userId the value for lre_server..user_client.user_id
     *
     * @mbg.generated
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column lre_server..user_client.create_time
     *
     * @return the value of lre_server..user_client.create_time
     *
     * @mbg.generated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column lre_server..user_client.create_time
     *
     * @param createTime the value for lre_server..user_client.create_time
     *
     * @mbg.generated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}