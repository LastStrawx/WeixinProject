package org.lanqiao.bean;
/*
     <ToUserName><![CDATA[toUser]]></ToUserName>
  <FromUserName><![CDATA[fromUser]]></FromUserName>
  <CreateTime>12345678</CreateTime>
  <MsgType><![CDATA[text]]></MsgType>
  <Content><![CDATA[你好]]></Content>

 */
public class TextMessage extends BaseMessage{

    private String Content;//内容

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
