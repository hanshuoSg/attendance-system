package com.example.attendance;

public class Result<T> {
    private Integer code;    // 状态码：200成功，400失败等
    private String message;  // 提示信息
    private T data;          // 返回的数据

    // 构造方法
    public Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    // 成功方法（带数据）
    public static <T> Result<T> success(T data) {
        return new Result<>(200, "success", data);
    }

    // 成功方法（只返回成功消息）
    public static <T> Result<T> success(String message, T data) {
        return new Result<>(200, message, data);
    }

    // 失败方法
    public static <T> Result<T> error(String message) {
        return new Result<>(400, message, null);
    }

    // getter和setter方法
    public Integer getCode() { return code; }
    public void setCode(Integer code) { this.code = code; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public T getData() { return data; }
    public void setData(T data) { this.data = data; }
}