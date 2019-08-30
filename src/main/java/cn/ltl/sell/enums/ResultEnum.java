package cn.ltl.sell.enums;

public enum ResultEnum {
    PRODUCT_NOT_FOUND(10, "商品不存在"),
    PRODUCT_STOCK_ERROR(11, "商品库存不正确"),
    ORDER_NOT_EXIST(12, "订单不存在"),
    ORDERDETAIL_NOT_EXIST(13, "订单不存在"),
    ORDER_STATUS_ERROR(14, "订单状态不正确"),
    ORDER_UPDATE_FAIL(15, "订单更新失败"),
    PAY_STATUS_ERROR(16, "订单状态不正确"),
    PARAM_ERROR(17, "参数错误"),
    CART_EMPTY(18, "购物车为空"),
    ORDER_OWNER_ERROR(19, "订单openid不一致"),
    WECHAT_AUTHORIZE_FAIL(20, "微信授权失败"),
    PRODUCT_STATUS_ERROR(20, "商品状态不正确");


    private Integer code;
    private String message;

    ResultEnum(Integer code, String message) {
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
