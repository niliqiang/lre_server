package com.lre_server.common.tools;


public enum ResponseCode {

	SUCCESS(0, "操作成功"),
	SYSTEM_BUSY(500001, "系统繁忙，请稍候再试"),
	OPERATION_ERROR(500002, "操作失败"),
	TOKEN_ERROR(401001, "登录凭证已过期，请重新登录"),
	USER_LOCK(401002, "该用户未认证，请联系运营人员"),
	PASSWORD_ERROR(401003, "用户名或密码错误"),
	OLD_PASSWORD_ERROR(401004, "旧密码不正确");

    // 成员变量
    private int code;
    private String msg;

    // 构造方法
    private ResponseCode(int code, String msg) {
        this.setCode(code);
		this.setMsg(msg);
    }

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
