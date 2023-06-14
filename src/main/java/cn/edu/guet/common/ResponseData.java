package cn.edu.guet.common;

import java.io.Serializable;

/**
 * @Author liwei
 * @Date 2023/5/21 10:38
 * @Version 1.0
 */

public class ResponseData<T> implements Serializable {
    private static final long serialVersionUID = -8782333365744933352L;
    private int code = 200;
    private String message = "";
    private T data;

    public static <T> ResponseData<T> ok() {
        return new ResponseData();
    }

    public static <T> ResponseData<T> ok(T data) {
        return new ResponseData(data);
    }

    public static <T> ResponseData<T> fail() {
        return new ResponseData((String) null, ResponseRecode.PARAM_ERROR_CODE.getRecode());
    }

    public static <T> ResponseData<T> fail(String message) {
        return new ResponseData(message, ResponseRecode.PARAM_ERROR_CODE.getRecode());
    }

    public static <T> ResponseData<T> fail(String message, int code) {
        return new ResponseData(message, code);
    }

    public static <T> ResponseData<T> failByParam(String message) {
        return new ResponseData(message, ResponseRecode.PARAM_ERROR_CODE.getRecode());
    }

    public ResponseData(T data) {
        this.data = data;
    }

    public ResponseData(String message) {
        this.message = message;
    }

    public ResponseData(String message, int code) {
        this.message = message;
        this.code = code;
    }

    public ResponseData() {
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
