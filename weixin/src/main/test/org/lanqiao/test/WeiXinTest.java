package org.lanqiao.test;

import org.junit.Test;
import org.lanqiao.bean.AccessToken;
import org.lanqiao.utils.MessageUtils;
import org.lanqiao.utils.NetUtils;

import java.io.IOException;
import java.util.Map;

public class WeiXinTest {
    @Test
    public void test1(){
        AccessToken token = NetUtils.getAccessToken();
        System.out.println(token.getAccess_token());
        System.out.println(token.getExpires_in());
    }

    @Test
    public void test2() throws IOException {
        NetUtils.createMenu(NetUtils.getAccessToken());
    }

    @Test
    public void test3() throws IOException {
        String menuInfo = NetUtils.getMenuInfo(NetUtils.getAccessToken());
        System.out.println(menuInfo);
    }

    @Test
    public void test4() throws IOException {
        NetUtils.deleteMenu(NetUtils.getAccessToken());

    }

    @Test
    public void test5() throws IOException {
        String filePath = "d:/sao.jpg";

        String image = NetUtils.uploadFile(filePath, NetUtils.getAccessToken(), "image");

        System.out.println(image);

    }

    @Test
    public void test6() throws IOException {
        String media = "sKYi_at97x004VSD2LU04fQGOzJv0MEZYLLe6dAaDErfraalgyYSD-A2hgk-e2rK";
        NetUtils.getMedia(NetUtils.getAccessToken(),media);

    }

    @Test
    public void test7() throws IOException {
        String content = "hello";

        System.out.println(NetUtils.getAutoChat(content));
    }


}
