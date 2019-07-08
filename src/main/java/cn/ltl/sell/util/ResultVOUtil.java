package cn.ltl.sell.util;

import cn.ltl.sell.VO.ResultVO;

public class ResultVOUtil {

    public static ResultVO success (Object data) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMsg("成功");
        resultVO.setData(data);
        return resultVO;
    }

    public static ResultVO success () {
        ResultVO resultVO = success(null);
        return resultVO;
    }

    public static ResultVO error (String msg) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(1);
        resultVO.setMsg(msg);
        return resultVO;
    }
}
