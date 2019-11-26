package org.lanqiao.servlet;

import org.dom4j.DocumentException;
import org.lanqiao.bean.TextMessage;
import org.lanqiao.utils.CheckSignature;
import org.lanqiao.utils.MessageUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

@WebServlet("/WeixinServlet")
public class WeixinServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String signature = req.getParameter("signature");
        String timestamp = req.getParameter("timestamp");
        String nonce = req.getParameter("nonce");
        String echostr = req.getParameter("echostr");

        //将token、timestamp、nonce三个参数进行字典序排序
        String token = "loveling";

        String signature1 = CheckSignature.checkSignature(token, timestamp, nonce);

        if(signature1.equals(signature)){
            resp.getWriter().print(echostr);
        }


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");

        resp.setContentType("text/html;charset=utf-8");

        PrintWriter out = resp.getWriter();
        try {
            //将微信传过来的xml转成map
            Map<String, String> map = MessageUtils.xml2Map(req);

            System.out.println(map);
            String toUserName = map.get("ToUserName");
            String fromUserName = map.get("FromUserName");
            String msgType = map.get("MsgType");

            String massage = null;
            if(MessageUtils.MESSAGE_TEXT.equals(msgType)){
                //回复1
                String content = map.get("Content");
                if("1".equals(content)){
                    massage = MessageUtils.getMessage(fromUserName,toUserName,"维鲁斯,小黄毛,卡莎,小炮,女警");
                }else if("2".equals(content)){
                    massage = MessageUtils.getMessage(fromUserName,toUserName,"妖姬,托儿索,儿童劫,球女,雷电法王");
                }else if("3".equals(content)){
                    //回复图片消息
                    massage = MessageUtils.initImageMessage(fromUserName,toUserName);
                }
            }else if(MessageUtils.MESSAGE_EVENT.equals(msgType)){
                String event = map.get("Event");
                if(MessageUtils.MESSAGE_EVENT_SUBSCRIBE.equals(event)){
                    //给微信回复消息
                    massage = MessageUtils.getMessage(fromUserName,toUserName,MessageUtils.subscribeText());
                }else if(MessageUtils.MESSAGE_EVENT_CLICK.equalsIgnoreCase(event)){
                    //点击菜单
                    String  eventKey = map.get("EventKey");
                    if(eventKey.equals("11")){
                        //点击菜单一
                        massage = MessageUtils.getMessage(fromUserName,toUserName,"菜单一被点击");
                    }
                }else if(MessageUtils.MESSAGE_EVENT_SCANCODE_PUSH.equals(event)){
                    String  eventKey = map.get("EventKey");
                    System.out.println(eventKey);
                    if(eventKey.equals("31")){
                        //点击子菜单一
                        massage = MessageUtils.getMessage(fromUserName,toUserName,"我太难了");
                        System.out.println(massage);
                    }
                }
            }
            out.print(massage);
        } catch (DocumentException e) {
            e.printStackTrace();
        }finally {
            out.close();
        }
    }
}
