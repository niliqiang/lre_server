package com.lre_server.common.tools;

import lombok.Data;

/**
 * @ClassName: JsonResult
 * @Author: niliqiang
 * @Date: 2021/2/6
 * @Description: TODO
 */
@Data
public class JsonResult {
    private Object data;
    private int code;
    private String msg;

    /**
     * 若没有数据返回，默认状态码为SUCCESS，提示信息为：操作成功
     */
    public JsonResult() {
        this.data = null;
        this.code = ResponseCode.SUCCESS.getCode();
        this.msg = ResponseCode.SUCCESS.getMsg();
    }

    /**
     * 有数据返回，默认状态码为SUCCESS，提示信息为：操作成功
     */
    public JsonResult(Object data) {
        this.data = data;
        this.code = ResponseCode.SUCCESS.getCode();
        this.msg = ResponseCode.SUCCESS.getMsg();
    }

    /**
     * 没有数据返回，人为指定状态码和提示信息
     */
    public JsonResult(int code, String msg) {
        this.data = null;
        this.code = code;
        this.msg = msg;
    }

    /**
     * 有数据返回，人为指定数据、状态码和提示信息
     */
    public JsonResult(Object data, int code, String msg) {
        this.data = data;
        this.code = code;
        this.msg = msg;
    }

    /**
     * 操作成功 data为null
     * @return
     */
    public static JsonResult success() {
        return new JsonResult();
    }

    /**
     * 操作成功 data不为null
     * @return
     */
    public static JsonResult success(Object data) {
        return new JsonResult(data);
    }

    /**
     * 操作成功 data为null 自定义msg
     * @return
     */
    public static JsonResult success(String msg) {
        return new JsonResult(ResponseCode.SUCCESS.getCode(), msg);
    }

    /**
     * 操作失败 data为null code和msg不为null
     * @return
     */
    public static JsonResult fail(String msg) {
        return new JsonResult(ResponseCode.OPERATION_ERROR.getCode(), msg);
    }

    /**
     * 自定义返回
     * @param responseCode
     * @return
     */
    public static JsonResult getResult(ResponseCode responseCode) {
        return new JsonResult(responseCode.getCode(), responseCode.getMsg());
    }

    /**
     * 自定义返回
     * @param data
     * @param code
     * @param msg
     * @return
     */
    public static JsonResult getResult(Object data, int code, String msg) {
        return new JsonResult(data, code, msg);
    }
}
