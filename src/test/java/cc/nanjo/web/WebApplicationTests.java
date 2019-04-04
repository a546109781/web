package cc.nanjo.web;

import cc.nanjo.common.fate.table.entity.NServent;
import cc.nanjo.common.fate.table.service.NServentService;
import cc.nanjo.common.util.okhttp.OkHttpUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class WebApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Autowired
    private NServentService nServentService;

    @Test
    public void testServent() throws Exception {
        int pn = 241 / 16;
        List<NServent> nServents = new ArrayList<>();
        for (int i = 1; i <= pn; i++) {
            log.info("开始执行第{}页数据抓取...", i);
            String url = "https://fgo.umowang.com/servant/ajax?card=&wd=&ids=&sort=12777&o=asc&pn=" + i;
            String urlServant = "https://fgo.umowang.com/servant/";
            Map<String, String> header = new HashMap<>();
            header.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36");
            header.put("x-requested-with", "XMLHttpRequest");
            String result = OkHttpUtils.builder().get(url, header);
            JSONObject retJson = JSONObject.parseObject(result);
            //log.info("返回数据:{}", retJson.toJSONString());
            JSONArray data = retJson.getJSONArray("data");
            for (Object obj : data) {
                try {
                    JSONObject servent = JSONObject.parseObject(obj.toString());
                    Integer id = servent.getInteger("id");

                    String resultServant = OkHttpUtils.builder().get(urlServant + id, header);
                    int index = resultServant.indexOf("女性</td>");
                    index = index == -1 ? resultServant.indexOf("男性</td>") : index;
                    if (index == -1) {
                        resultServant = "3";
                    } else {
                        resultServant = resultServant.substring(index, resultServant.length() - 1);
                        resultServant = resultServant.substring(0, 2);
                        resultServant = "女性".equals(resultServant) ? "1" : "2";
                    }
                    log.info("开始抓取从者性别，当前从者：{}，url-id：{}, 抓取结果：{}", servent.getString("name"), id, resultServant);
                    NServent nServent = nServentService.getById(servent.getInteger("charid"));
                    nServent.setSex(Integer.valueOf(resultServant));
                    nServentService.updateById(nServent);
                } catch (Exception e) {
                    //System.out.println(obj.toString());
                    e.printStackTrace();
                }
            }
        }
    }

    private boolean isNum(String charid) {
        for (char c : charid.toCharArray()) {
            if (c < 48 || c > 57) {
                return false;
            }
        }
        return true;
    }

}
