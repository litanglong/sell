package cn.ltl.sell.enums;

public enum OrderStatusEnum {
    NEW(0, "新下单"),
    FINISH(1, "完结"),
    CANCEL(2 ,"取消");

    private Integer code;
    private String message;

    OrderStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
