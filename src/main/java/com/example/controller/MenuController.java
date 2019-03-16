package com.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.model.Menu;
import com.example.model.Result;
import com.example.service.MenuService;
import com.example.util.WeChatUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Yincn
 * @Date 2019/3/15 18:49
 */
@RequestMapping("/menu")
@RestController
public class MenuController {

    @Autowired
    private MenuService menuService;

    private static Logger log = LoggerFactory.getLogger(MenuController.class);

    // 菜单查询（POST） 限10000（次/天）
    public static String menu_get_url = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";

    // 菜单删除（POST） 限1000（次/天）
    public static String menu_delete_url = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";

    /**
     * 查询菜单
     *
     * @param accessToken 有效的access_token
     * @return
     */
    public JSONObject getMenu(String accessToken) {

        // 拼装创建菜单的url
        String url = menu_get_url.replace("ACCESS_TOKEN", accessToken);
        // 调用接口查询菜单
        JSONObject jsonObject = WeChatUtil.httpRequest(url, "GET", null);

        return jsonObject;
    }

    /**
     * 创建菜单/替换旧菜单
     */
    @RequestMapping("/create")
    public Result create() {
        //第一栏菜单
        Menu menu1=new Menu();
        menu1.setId("1");
        menu1.setName("第一栏");
        menu1.setType("click");
        menu1.setKey("1");

        Menu menu11=new Menu();
        menu11.setId("11");
        menu11.setName("第一栏的第一个");
        menu11.setType("click");
        menu11.setKey("11");

        Menu menu12=new Menu();
        menu12.setId("12");
        menu12.setName("第一栏的第二个");
        menu12.setType("click");
        menu12.setKey("12");

        //第二栏
        Menu menu2=new Menu();
        menu2.setId("2");
        menu2.setName("第二栏");
        menu2.setType("click");
        menu2.setKey("2");

        Menu menu21=new Menu();
        menu21.setId("21");
        menu21.setName("第二栏的第一个");
        menu21.setType("click");
        menu21.setKey("21");

        Menu menu3=new Menu();
        menu3.setId("3");
        menu3.setName("第三栏");
        menu3.setType("view");
        menu3.setUrl("http://www.baidu.com");

        //最外一层大括号
        Map<String, Object> wechatMenuMap = new HashMap<>();

        //包装button的List
        List<Map<String, Object>> wechatMenuMapList = new ArrayList<Map<String, Object>>();

        //包装第一栏
        Map<String, Object> menuMap1 = new HashMap<String, Object>();
        Map<String, Object> menuMap11 = new HashMap<String, Object>();
        Map<String, Object> menuMap12 = new HashMap<String, Object>();

        List<Map<String, Object>> subMenuMapList1 = new ArrayList<Map<String, Object>>();

        //第一栏第一个
        menuMap11.put("name",menu11.getName());
        menuMap11.put("type",menu11.getType());
        menuMap11.put("key",menu11.getKey());
        subMenuMapList1.add(menuMap11);

        //第二栏第二个
        menuMap12.put("name",menu12.getName());
        menuMap12.put("type",menu12.getType());
        menuMap12.put("key",menu12.getKey());
        subMenuMapList1.add(menuMap12);

        menuMap1.put("name",menu1.getName());
        menuMap1.put("sub_button",subMenuMapList1);

        //包装第二栏
        Map<String, Object> menuMap2 = new HashMap<String, Object>();
        Map<String, Object> menuMap21 = new HashMap<String, Object>();
        List<Map<String, Object>> subMenuMapList2 = new ArrayList<Map<String, Object>>();

        //第二栏第一个
        menuMap21.put("name",menu21.getName());
        menuMap21.put("type",menu21.getType());
        menuMap21.put("key",menu21.getKey());
        subMenuMapList2.add(menuMap21);

        menuMap2.put("name",menu2.getName());
        menuMap2.put("sub_button",subMenuMapList2);

        //包装第三栏
        Map<String, Object> menuMap3 = new HashMap<String, Object>();
        List<Map<String, Object>> subMenuMapList3 = new ArrayList<Map<String, Object>>();

        menuMap3.put("name",menu3.getName());
        menuMap3.put("type",menu3.getType());
        menuMap3.put("url",menu3.getUrl());
        menuMap3.put("sub_button",subMenuMapList3);


        wechatMenuMapList.add(menuMap1);
        wechatMenuMapList.add(menuMap2);
        wechatMenuMapList.add(menuMap3);

        wechatMenuMap.put("button",wechatMenuMapList);

        return menuService.createMenu(wechatMenuMap);
    }

    /**
     * 删除菜单
     *
     * @param accessToken 有效的access_token
     * @return 0表示成功，其他值表示失败
     */
    public int deleteMenu(String accessToken) {
        int result = 0;

//        // 拼装创建菜单的url
//        String url = menu_delete_url.replace("ACCESS_TOKEN", accessToken);
//
//        // 调用接口创建菜单
//        JSONObject jsonObject = WeixinUtil.httpRequest(url, "POST", null);
//
//        if (null != jsonObject) {
//            if (0 != jsonObject.getInt("errcode")) {
//                result = jsonObject.getInt("errcode");
//                log.error("删除菜单失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
//            }
//        }
        return result;
    }
}
