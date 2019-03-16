package com.example.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @Author Yincn
 * @Date 2019/3/15 20:15
 */
public class MenuUtil {
    // 菜单创建（POST） 限1000（次/天）
    public static String MENU_CREATE_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + WeChatContant.TOKEN;

    private static Logger log = LoggerFactory.getLogger(MenuUtil.class);


    public static JSONObject create(Map<String, Object> menu) {
        // 将菜单对象转换成json字符串
        String jsonMenu = JSON.toJSONString(menu);
        log.info("准备创建菜单：{}", jsonMenu);
        // 调用接口创建菜单
        return WeChatUtil.httpRequest(MENU_CREATE_URL, "POST", jsonMenu);
    }
}
