package cc.nanjo.bilibili;

import cc.nanjo.common.util.okhttp.OkHttpUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: xw
 * 2019/4/14/014 14:10
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BilibiliTest {

    private final static String BASEURL = "https://account.bilibili.com/answer/base#/";


    @Test
    public void testBibiliQuestion(){

        String s = OkHttpUtils.builder().get(BASEURL);
        System.out.println(s);


    }



}
