package org.lanqiao.utils;

import com.thoughtworks.xstream.XStream;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.lanqiao.bean.Image;
import org.lanqiao.bean.ImageMessage;
import org.lanqiao.bean.TextMessage;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class MessageUtils {
    public static final String MESSAGE_TEXT = "text";
    public static final String MESSAGE_EVENT = "event";
    public static final String MESSAGE_EVENT_SUBSCRIBE = "subscribe";
    public static final String MESSAGE_IMAGE = "image";
    //public static final String MESSAGE_INEWS = "news";
    //public static final String MESSAGE_MUSIC = "music";
    public static final String MESSAGE_EVENT_CLICK= "click";
    public static final String MESSAGE_EVENT_VIEW= "view";
    public static final String MESSAGE_EVENT_SCANCODE_PUSH= "scancode_push";
    public static final String MESSAGE_EVENT_LOCATION_SELECT= "location_select";
    public static final String MESSAGE_EVENT_LOCATION= "location";

    public static Map<String , String> xml2Map(HttpServletRequest request) throws IOException, DocumentException {
        Map<String , String> map = new HashMap<>();
        ServletInputStream inputStream = request.getInputStream();
        //创建saxreader对象解析xml
        SAXReader reader = new SAXReader();
        //读取xml文件
        Document document = reader.read(inputStream);
        //获取根节点---xml
        Element root = document.getRootElement();
        //获取根节点里的子节点
        List<Element> elements = root.elements();

        for(Element e : elements){
            map.put(e.getName() , e.getText());
        }
        return map;
    }

    public static String text2XML(TextMessage message){
        XStream xStream = new XStream();
        xStream.alias("xml",TextMessage.class);
        return xStream.toXML(message);
    }

    /*
    订阅是回复用户的内容
     */
    public static String subscribeText(){
        StringBuilder sb = new StringBuilder();
        sb.append("欢迎来到召唤师峡谷!\n");
        sb.append("1.查看射手英雄\n");
        sb.append("2.查看法师英雄\n");
        sb.append("3.查看打野英雄\n");
        sb.append("4.查看上单英雄\n");
        sb.append("5.查看辅助英雄\n");

        return sb.toString();
    }

    public static String getMessage(String fromUserName , String toUserName , String content){
        TextMessage message = new TextMessage();

        message.setToUserName(fromUserName);
        message.setFromUserName(toUserName);
        message.setCreateTime(new Date().getTime()+"");
        message.setMsgType(MessageUtils.MESSAGE_TEXT);
        message.setContent(content);

        return MessageUtils.text2XML(message);
    }

    //qX31vWCSLgrGq4ftqZ6B95KoYata2pLziceI8vrb7h95JR7LRTkqJkHmDIqL0IYQ

    /*
    将图片消息转成xml
     */
    public static String image2XML(ImageMessage message){
        XStream xStream = new XStream();
        xStream.alias("xml",ImageMessage.class);
        xStream.alias("Image", Image.class);

        return xStream.toXML(message);
    }

    public static String initImageMessage(String fromUserName , String toUserName){
        Image image = new Image();
        image.setMediaId("qX31vWCSLgrGq4ftqZ6B95KoYata2pLziceI8vrb7h95JR7LRTkqJkHmDIqL0IYQ");

        ImageMessage message = new ImageMessage();
        message.setFromUserName(toUserName);
        message.setToUserName(fromUserName);
        message.setImage(image);
        message.setCreateTime(new Date().getTime()+"");
        message.setMsgType(MESSAGE_IMAGE);

        return image2XML(message);
    }


    public static Map<String, String> xmlToMap(String strXML) throws Exception {
        try {
            Map<String, String> data = new HashMap<String, String>();
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilderFactory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            documentBuilderFactory.setFeature("http://xml.org/sax/features/external-general-entities", false);
            documentBuilderFactory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            documentBuilderFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            documentBuilderFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            documentBuilderFactory.setXIncludeAware(false);
            documentBuilderFactory.setExpandEntityReferences(false);
            DocumentBuilder documentBuilder= documentBuilderFactory.newDocumentBuilder();
//DocumentBuilder documentBuilder = WXPayXmlUtil.newDocumentBuilder();
            InputStream stream = new ByteArrayInputStream(strXML.getBytes("UTF-8"));
            org.w3c.dom.Document doc = documentBuilder.parse(stream);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getDocumentElement().getChildNodes();
            for (int idx = 0; idx < nodeList.getLength(); ++idx) {
                Node node = nodeList.item(idx);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    org.w3c.dom.Element element = (org.w3c.dom.Element) node;
                    data.put(element.getNodeName(), element.getTextContent());
                }
            }
            try {
                stream.close();
            } catch (Exception ex) {
// do nothing
            }
            return data;
        } catch (Exception ex) {
            throw ex;
        }
    }
}
