package cn.ltl.sell.handler;

import cn.ltl.sell.VO.ResultVO;
import cn.ltl.sell.exception.SellException;
import cn.ltl.sell.util.ResultVOUtil;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class SellExceptionHandler {

    /**
     * 处理全局异常
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(SellException.class)
    public ResultVO handlerSellException(SellException e) {
        return ResultVOUtil.error(e.getCode(), e.getMessage());
    }
}
