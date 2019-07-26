package cc.nanjo.web;

import cc.nanjo.common.fate.calendar.BgoNewsExecute;
import cc.nanjo.common.fate.table.entity.NServent;
import cc.nanjo.common.fate.table.service.NServentService;
import cc.nanjo.common.util.okhttp.OkHttpUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hankcs.hanlp.HanLP;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.env.SystemEnvironmentPropertySourceEnvironmentPostProcessor;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class WebApplicationTests {

    @Autowired
    private BgoNewsExecute bgoNewsExecute;

    @Test
    public void testBgo(){
        bgoNewsExecute.getBgoNewList();
    }

}
