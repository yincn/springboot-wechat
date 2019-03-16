package com.example.service;

import com.alibaba.fastjson.JSONObject;
import com.example.model.Result;
import com.example.util.MenuUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Author Yincn
 * @Date 2019/3/15 19:29
 */
@Service
public class MenuServiceImpl implements MenuService {

    private static Logger log = LoggerFactory.getLogger(MenuServiceImpl.class);

    @Override
    public Result createMenu(Map<String, Object> menu) {
        Result result = new Result();
        boolean success = false;
        int count = 0;

        JSONObject obj = MenuUtil.create(menu);
        log.info("调用创建菜单接口，返回：{}", obj);
        if (obj == null) {
            result.setCode(1);
            result.setMsg("调用微信创建菜单接口异常！返回 null");
            log.error(result.getMsg());

            return result;
        }

        int code = obj.getIntValue("errcode");
        if (code != 0) {
            result.setCode(obj.getIntValue("errcode"));
            result.setMsg(obj.getString("errmsg"));
            log.error(result.getMsg());
        }

        return result;
    }
}
