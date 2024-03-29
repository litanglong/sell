package cn.ltl.sell.enums;

public enum PayStatusEnum implements CodeEnum {
    WAIT(0, "等待支付"),
    SUCCESS(1, "完结"),
    CANCEL(2 ,"取消");

    private Integer code;
    private String message;

    PayStatusEnum(Integer code, String message) {
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
