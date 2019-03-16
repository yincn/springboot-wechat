package com.example.controller;

import com.example.service.WeChatService;
import com.example.util.CheckUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Author Yincn
 * @Date 2019/3/14 17:24
 */
@RestController
public class WeChatController {

    @Autowired
    private WeChatService weChatService;

    private final Logger log = LoggerFactory.getLogger(WeChatController.class);

    /**
     * 处理微信服务器发来的get请求，进行签名的验证
     * 步骤:
     *      1.将参与微信加密签名的三个参数(timestamp、nonce、token)按照字典序排序并组合在一起形成一个数组
     *      2.将数组里所有参数拼接成一个字符串，进行 sha1 加密
     *      3.加密完会生成一个 singnatrue，和微信发送过来的 singnatrue 进行对比
     *          1).如果相同，表示消息来自于微信服务器，并返回传递过来的 echostr 值
     *          2).如果不同，表示非法请求，返回 error
     * @param request
     * @param response
     */
    @GetMapping("/wechat")
    public void login(HttpServletRequest request, HttpServletResponse response) {
        String signature = request.getParameter("signature");   // 微信加密签名
        String timestamp = request.getParameter("timestamp");   // 时间戳
        String nonce = request.getParameter("nonce");           // 随机数
        String echostr = request.getParameter("echostr");       // 随机字符串
        log.info("登录接口被调用，接收参数 signature:{},timestamp:{},nonce:{},echostr:{}", signature, timestamp, nonce, echostr);
        PrintWriter out = null;
        try {
            out = response.getWriter();
            if(CheckUtil.checkSignature(signature, timestamp, nonce)){
                System.out.println("检验成功");
                out.write(echostr);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            out.close();
        }
    }

    /**
     * 此处是处理微信服务器的消息转发的
     */
    @PostMapping("/wechat")
    public String processMsg(HttpServletRequest request) {
        // 调用核心服务类接收处理请求
        return weChatService.processRequest(request);
    }
}
