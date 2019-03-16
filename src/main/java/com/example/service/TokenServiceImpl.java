package com.example.service;

import com.alibaba.fastjson.JSONObject;
import com.example.model.Result;
import com.example.util.WeChatContant;
import com.example.util.WeChatUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @Author Yincn
 * @Date 2019/3/15 19:59
 */
@Service
public class TokenServiceImpl implements TokenService {

    private static Logger log = LoggerFactory.getLogger(TokenServiceImpl.class);

    private static final String TOKEN_GET_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + WeChatContant.APP_ID  + "&secret=" + WeChatContant.APP_SECRET;

    @Override
    public Result get() {
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
