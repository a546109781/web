package cc.nanjo.common.konachan;

import cc.nanjo.common.bilibili.black.BiliAno;
import cc.nanjo.common.util.okhttp.OkHttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author xw
 * @date 2020/3/16/016 15:44
 */
@Controller
@Slf4j
public class KonachanController {

    private final static String KONACHAN_URL = "http://konachan.com/post?page=#page#&tags=#tags#";


    @RequestMapping("konachan")
    @BiliAno
    public String konachan(HttpServletRequest httpServletRequest) {
        return "konachan/base";
    }

    @PostMapping("konachan/download")
    @BiliAno
    @ResponseBody
    public KonachanResult download(HttpServletRequest request, String tag, String pages, String pointer) {

        StringBuilder sb = new StringBuilder();
        KonachanResult konachanResult = checkParam(tag, pages);
        if (!konachanResult.getSuccess()) {
            return konachanResult;
        }

        Map<String, String> params = new HashMap<>();
        params.put("tags", tag);
        params.put("page", pages);
        String url = replaceUrl(KONACHAN_URL, params);
        String page = OkHttpUtils.builder().get(url);
        Document pageDoc = Jsoup.parse(page);
        Element postListPosts = pageDoc.getElementById("post-list-posts");
        if (postListPosts == null) {
            konachanResult.setSuccess(false);
            konachanResult.setMsg("此标签没有检索到有效数据！");
            return konachanResult;
        }
        Element paginator = pageDoc.getElementById("paginator");
        Elements next_page = paginator.getElementsByClass("next_page");
        Element first = next_page.first();
        String lastPage = first.before("a").text();
        Integer lastPageInt = Integer.valueOf(lastPage);
        sb.append("当前标签查询到").append(lastPageInt).append("页数据...\r\n");
        Integer pageInt = Integer.valueOf(pages);
        if(pageInt > lastPageInt){
            pageInt = lastPageInt;
        }




        return konachanResult;
    }


    private static String replaceUrl(String url, Map<String, String> params) {
        Set<String> keySet = params.keySet();
        for (String key : keySet) {
            url = url.replace("#" + key + "#", params.get(key));
        }
        return url;
    }


    private static KonachanResult checkParam(String tag, String page) {
        if (StringUtils.isBlank(tag) || StringUtils.isBlank(page)) {
            KonachanResult konachanResult = new KonachanResult();
            konachanResult.setSuccess(false);
            konachanResult.setMsg("参数不能为空");
            return konachanResult;
        }
        if (!StringUtils.isNumeric(page)) {
            KonachanResult konachanResult = new KonachanResult();
            konachanResult.setSuccess(false);
            konachanResult.setMsg("页数输入有误");
            return konachanResult;
        }
        KonachanResult konachanResult = new KonachanResult();
        konachanResult.setSuccess(true);
        return konachanResult;
    }

}
