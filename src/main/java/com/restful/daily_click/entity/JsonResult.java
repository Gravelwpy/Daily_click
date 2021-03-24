package com.restful.daily_click.entity;


public class JsonResult<T> {
    private int error_code;
    private String msg;
    private T data;

    public JsonResult() {
        this.error_code = 0;
        this.msg = "success";
    }

    public JsonResult(T data) {
        this.data = data;
        this.error_code = 0;
        this.msg = "success";
    }

    public JsonResult(int error_code, String msg) {
        this.error_code = error_code;
        this.msg = msg;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
