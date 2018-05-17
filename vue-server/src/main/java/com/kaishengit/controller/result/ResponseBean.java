package com.kaishengit.controller.result;

public class ResponseBean {

    private String status;
    private String message;
    private Object result;

    public static ResponseBean success() {
        ResponseBean responseBean = new ResponseBean();
        responseBean.setStatus("success");
        return responseBean;
    }

    public static ResponseBean success(Object object) {
        ResponseBean responseBean = new ResponseBean();
        responseBean.setStatus("success");
        responseBean.setResult(object);
        return responseBean;
    }

    public static ResponseBean error(String message) {
        ResponseBean responseBean = new ResponseBean();
        responseBean.setStatus("error");
        responseBean.setMessage(message);
        return responseBean;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
