package com.lre_server.entity;

import java.util.Date;

public class FileInfo extends BaseEntity{
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column lre_server..file_info.file_id
     *
     * @mbg.generated
     */
    private Integer fileId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column lre_server..file_info.user_id
     *
     * @mbg.generated
     */
    private Integer userId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column lre_server..file_info.file_name
     *
     * @mbg.generated
     */
    private String fileName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column lre_server..file_info.lre_result
     *
     * @mbg.generated
     */
    private Byte lreResult;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column lre_server..file_info.status
     *
     * @mbg.generated
     */
    private Byte status;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column lre_server..file_info.create_time
     *
     * @mbg.generated
     */
    private Date createTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column lre_server..file_info.file_id
     *
     * @return the value of lre_server..file_info.file_id
     *
     * @mbg.generated
     */
    public Integer getFileId() {
        return fileId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column lre_server..file_info.file_id
     *
     * @param fileId the value for lre_server..file_info.file_id
     *
     * @mbg.generated
     */
    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column lre_server..file_info.user_id
     *
     * @return the value of lre_server..file_info.user_id
     *
     * @mbg.generated
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column lre_server..file_info.user_id
     *
     * @param userId the value for lre_server..file_info.user_id
     *
     * @mbg.generated
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column lre_server..file_info.file_name
     *
     * @return the value of lre_server..file_info.file_name
     *
     * @mbg.generated
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column lre_server..file_info.file_name
     *
     * @param fileName the value for lre_server..file_info.file_name
     *
     * @mbg.generated
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column lre_server..file_info.lre_result
     *
     * @return the value of lre_server..file_info.lre_result
     *
     * @mbg.generated
     */
    public Byte getLreResult() {
        return lreResult;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column lre_server..file_info.lre_result
     *
     * @param lreResult the value for lre_server..file_info.lre_result
     *
     * @mbg.generated
     */
    public void setLreResult(Byte lreResult) {
        this.lreResult = lreResult;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column lre_server..file_info.status
     *
     * @return the value of lre_server..file_info.status
     *
     * @mbg.generated
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column lre_server..file_info.status
     *
     * @param status the value for lre_server..file_info.status
     *
     * @mbg.generated
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column lre_server..file_info.create_time
     *
     * @return the value of lre_server..file_info.create_time
     *
     * @mbg.generated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column lre_server..file_info.create_time
     *
     * @param createTime the value for lre_server..file_info.create_time
     *
     * @mbg.generated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}