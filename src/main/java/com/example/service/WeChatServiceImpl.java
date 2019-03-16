package com.example.service;

/**
 * @Author Yincn
 * @Date 2019/3/15 10:47
 */

import com.example.model.ArticleItem;
import com.example.util.WeChatContant;
import com.example.util.WeChatUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 核心服务类
 */
@Service
public class WeChatServiceImpl implements WeChatService{

    private final Logger log = LoggerFactory.getLogger(WeChatServiceImpl.class);

    @Override
    public String processRequest(HttpServletRequest request) {
        // xml格式的消息数据
        String respXml = null;
        // 默认返回的文本消息内容
        String respContent;
        try {
            // 调用parseXml方法解析请求消息
            Map<String,String> requestMap = WeChatUtil.parseXml(request);
            // 消息类型
            String msgType = (String) requestMap.get(WeChatContant.MsgType);
            log.info("接收请求：{}，消息类型：{}", requestMap, msgType);
            String mes = null;
            // 文本消息
            if (msgType.equals(WeChatContant.REQ_MESSAGE_TYPE_TEXT)) {
                mes = requestMap.get(WeChatContant.Content).toString();
                if (StringUtils.isEmpty(mes)) {
                    mes = "请输入内容！";
                } else {
                    if(mes.equals("001")){
                        List<ArticleItem> items = new ArrayList<>();
                        ArticleItem item = new ArticleItem();
                        item.setTitle("照片墙");
                        item.setDescription("阿狸照片墙");
                        item.setPicUrl("http://changhaiwx.pagekite.me/photo-wall/a/iali11.jpg");
                        item.setUrl("http://changhaiwx.pagekite.me/page/photowall");
                        items.add(item);

                        item = new ArticleItem();
                        item.setTitle("哈哈");
                        item.setDescription("一张照片");
                        item.setPicUrl("http://changhaiwx.pagekite.me/images/me.jpg");
                        item.setUrl("http://changhaiwx.pagekite.me/page/index");
                        items.add(item);

                        item = new ArticleItem();
                        item.setTitle("小游戏2048");
                        item.setDescription("小游戏2048");
                        item.setPicUrl("http://changhaiwx.pagekite.me/images/2048.jpg");
                        item.setUrl("http://changhaiwx.pagekite.me/page/game2048");
                        items.add(item);

                        item = new ArticleItem();
                        item.setTitle("百度");
                        item.setDescription("百度一下");
                        item.setPicUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505100912368&di=69c2ba796aa2afd9a4608e213bf695fb&imgtype=0&src=http%3A%2F%2Ftx.haiqq.com%2Fuploads%2Fallimg%2F170510%2F0634355517-9.jpg");
                        item.setUrl("http://www.baidu.com");
                        items.add(item);

                        respXml = WeChatUtil.sendArticleMsg(requestMap, items);
                    }else if("我的信息".equals(mes)){
                        Map<String, String> userInfo = null;
                        //userInfo = getUserInfo(requestMap.get(WeChatContant.FromUserName));
                        System.out.println(userInfo.toString());
                        String nickname = userInfo.get("nickname");
                        String city = userInfo.get("city");
                        String province = userInfo.get("province");
                        String country = userInfo.get("country");
                        String headimgurl = userInfo.get("headimgurl");
                        List<ArticleItem> items = new ArrayList<>();
                        ArticleItem item = new ArticleItem();
                        item.setTitle("你的信息");
                        item.setDescription("昵称:"+nickname+"  地址:"+country+" "+province+" "+city);
                        item.setPicUrl(headimgurl);
                        item.setUrl("http://www.baidu.com");
                        items.add(item);

                        respXml = WeChatUtil.sendArticleMsg(requestMap, items);
                    }

                }
            }
            // 图片消息
            else if (msgType.equals(WeChatContant.REQ_MESSAGE_TYPE_IMAGE)) {
                respContent = "您发送的是图片消息！";
                respXml = WeChatUtil.sendTextMsg(requestMap, respContent);
            }
            // 语音消息
            else if (msgType.equals(WeChatContant.REQ_MESSAGE_TYPE_VOICE)) {
                respContent = "您发送的是语音消息！";
                respXml = WeChatUtil.sendTextMsg(requestMap, respContent);
            }
            // 视频消息
            else if (msgType.equals(WeChatContant.REQ_MESSAGE_TYPE_VIDEO)) {
                respContent = "您发送的是视频消息！";
                respXml = WeChatUtil.sendTextMsg(requestMap, respContent);
            }
            // 地理位置消息
            else if (msgType.equals(WeChatContant.REQ_MESSAGE_TYPE_LOCATION)) {
                respContent = "您发送的是地理位置消息！";
                respXml = WeChatUtil.sendTextMsg(requestMap, respContent);
            }
            // 链接消息
            else if (msgType.equals(WeChatContant.REQ_MESSAGE_TYPE_LINK)) {
                respContent = "您发送的是链接消息！";
                respXml = WeChatUtil.sendTextMsg(requestMap, respContent);
            }
            // 事件推送
            else if (msgType.equals(WeChatContant.REQ_MESSAGE_TYPE_EVENT)) {
                log.info("事件推送：{}", requestMap);

                // 事件类型
                String eventType = (String) requestMap.get(WeChatContant.Event);
                // 关注
                if (eventType.equals(WeChatContant.EVENT_TYPE_SUBSCRIBE)) {
                    log.info("用户 {} 关注", requestMap.get("FromUserName"));
                    respContent = "您好，欢迎您关注传宁测试号！";
                    respXml = WeChatUtil.sendTextMsg(requestMap, respContent);
                }
                // 取消关注
                else if (eventType.equals(WeChatContant.EVENT_TYPE_UNSUBSCRIBE)) {
                    log.info("用户 {} 取消了关注", requestMap.get("FromUserName"));
                    return null;
                }
                // 扫描带参数二维码
                else if (eventType.equals(WeChatContant.EVENT_TYPE_SCAN)) {
                    // TODO 处理扫描带参数二维码事件
                    log.info("用户 {} 进行扫码", requestMap.get("FromUserName"));
                    return null;
                }
                // 上报地理位置
                else if (eventType.equals(WeChatContant.EVENT_TYPE_LOCATION)) {
                    // TODO 处理上报地理位置事件
                }
                // 自定义菜单
                else if (eventType.equals(WeChatContant.EVENT_TYPE_CLICK)) {
                    // TODO 处理菜单点击事件
                }
            }
            if(respXml == null) {
                respXml = WeChatUtil.sendTextMsg(requestMap, mes == null ? "不知道你在干嘛" : "你发送的内容是：" + mes);
            }
            System.out.println(respXml);
            return respXml;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";

    }
}
