package com.onezero.ozerp.appbase.error.exception;

public class OzErpRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 8090212152789128834L;
    private String pointer;
    private String parameter;
    private Integer code;

    public OzErpRuntimeException() {
        super();
    }

    public OzErpRuntimeException(String message) {
        super(message);
    }

    public OzErpRuntimeException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public OzErpRuntimeException(String message, Integer code, String pointer, String parameter) {
        super(message);
        this.code = code;
        this.pointer = pointer;
        this.parameter = parameter;
    }

    public OzErpRuntimeException(Exception e) {
        super(e.getMessage(), e);
    }

    public String getPointer() {
        return pointer;
    }

    public void setPointer(String pointer) {
        this.pointer = pointer;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
