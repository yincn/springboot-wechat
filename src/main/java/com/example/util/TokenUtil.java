package com.example.util;

import com.alibaba.fastjson.JSONObject;
import com.example.model.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author Yincn
 * @Date 2019/3/15 20:11
 */
public class TokenUtil {

    private static final String TOKEN_GET_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + WeChatContant.APP_ID  + "&secret=" + WeChatContant.APP_SECRET;

    private static Logger log = LoggerFactory.getLogger(TokenUtil.class);

    public static Result get() {
        Result result = new Result();

        JSONObject obj = WeChatUtil.httpRequest(TOKEN_GET_URL, "GET", null);
        log.info("请求 Token 时返回：{}", obj);
        if (obj == null) {
            result.setCode(0);
            result.setMsg("请求Token时返回空！");
            return result;
        }

        String token = obj.getString("access_token");
        log.info("返回新 Token：{}", token);
        WeChatContant.TOKEN = token;

        return result;
    }
}
