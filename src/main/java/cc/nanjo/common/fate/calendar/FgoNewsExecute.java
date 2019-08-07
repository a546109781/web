package cc.nanjo.common.fate.calendar;

import cc.nanjo.common.fate.calendar.entity.BgoNews;
import cc.nanjo.common.fate.calendar.entity.IcsVevent;
import cc.nanjo.common.fate.calendar.service.BgoNewsService;
import cc.nanjo.common.fate.calendar.service.IcsVeventService;
import cc.nanjo.common.util.okhttp.OkHttpUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hankcs.hanlp.HanLP;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author xw
 * @date 2019/7/26 9:57
 */
@Slf4j
@Service
public class FgoNewsExecute {

    private static int page = 3;
    private static String baseUrl = "https://news.fate-go.jp";
    private static String maintenanceUrl = "https://news.fate-go.jp/maintenance/";

    private static Map<String, String> header = new HashMap<>();

    static {
        header.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36");
        header.put("x-requested-with", "XMLHttpRequest");
    }


    public void getMaintenance() {
        String result = OkHttpUtils.builder().get(maintenanceUrl, header);
        Document document = Jsoup.parse(result);

        Elements listNews = document.select(".list_news li");
        for (Element element : listNews) {
            String title = element.select(".title").text();
            String url = element.select("a").attr("href");
            String detail = OkHttpUtils.builder().get(baseUrl + url, header);
            detail = Jsoup.parse(detail.replace("&nbsp;", "")).body().text();
            detail = detail.substring(detail.indexOf("日時"), detail.length() - 1).replaceAll("（.*?）|\\(.*?\\)", "");
            char[] chars = detail.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                if (chars[i] == ' ' && (chars[i - 1] >= 48 && chars[i - 1] <= 57)) {
                    detail = detail.substring(0, i).replaceAll("日時", "").replaceAll(" ", "");
                    break;
                }
            }
            detail = detail.replaceAll("年|月|日", "-");
            String[] split = detail.split("～");

            String start = fixStr(split[0]);
            String end = fixDate(start, fixStr(split[1]));


        }
    }

    public void getNews() {
        String type = "1";
        String listUrl;
        for (int i = page; i >= 1; i--) {
            if (i != 1) {
                listUrl = baseUrl + "/page/" + i;
            } else {
                listUrl = baseUrl;
            }
            String result = OkHttpUtils.builder().get(listUrl, header);
            Document document = Jsoup.parse(result);
            Elements listNews = document.select(".list_news li");
            for (Element element : listNews) {
                String title = element.select(".title").text();
                if (title.indexOf("【期間限定】") != -1 && title.indexOf("開催") != -1) {
                    String url = element.select("a").attr("href");
                    String detail = OkHttpUtils.builder().get(baseUrl + url, header);
                    Document detailDoc = Jsoup.parse(detail);
                    String detailTitle = detailDoc.select(".title").text();
                    String text = detailDoc.select(".article span.em01").first().text();
                }
            }
        }
    }

    public static void main(String[] args) {

    }


    public static String getMap2UrlParams(Map<String, Object> map) {
        if (map == null || map.isEmpty()) {
            return "";
        }
        List<String> params = new ArrayList<>();
        map.forEach((k, v) -> {
            params.add(k + "=" + v);
        });
        return String.join("&", params);
    }

    public static String unescapeJava(String str) {
        String trim = StringUtils.trim(str);
        return trim.replaceAll("\\r|\\n|\\t|\\\\r|\\\\n|\\\\t|\\\\\\r|\\\\\\n|\\\\\\t", "")
                .replaceAll("\"|\\\"|\\\\\"", "\"").replace("&nbsp;", "")
                .replaceAll("　", "");
    }

    private static List<String> getSplitWords(String sentence) {
        // 去除掉html标签
        sentence = Jsoup.parse(sentence.replace("&nbsp;", "")).body().text();
        // 标点符号会被单独分为一个Term，去除之
        return HanLP.segment(sentence).stream().map(a -> a.word).filter(s -> !"`~!@#$^&*()=|{}':;',\\[\\].<>/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？ ".contains(s)).collect(Collectors.toList());
    }

    public LocalDateTime parseLocalTime(String date, String pattern) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(date, df);
    }

    public static String getTimeStringwithT(Date date) {
        return new SimpleDateFormat("yyyyMMdd'T'HHmmss").format(date);
    }

    public static String getTimeStringwithTZ(Date date) {
        return new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'").format(date);
    }

    public static String fixDate(String start, String end) {
        StringBuilder stringBuilder = new StringBuilder();
        char[] startChars = start.toCharArray();
        char[] endChars = end.toCharArray();
        for (int i = 0; i < startChars.length; i++) {
            if ((i + end.length()) >= startChars.length) {
                stringBuilder.append(endChars[(i + end.length()) - startChars.length]);
            } else {
                stringBuilder.append(startChars[i]);
            }
        }
        return stringBuilder.toString();
    }

    public static String fixStr(String str) {
        String[] split = str.split("-");
        for (int i = 0; i < split.length; i++) {
            if (split[i].length() <= 1 || (i == (split.length - 1) && split[i].length() <= 4)) {
                split[i] = "0" + split[i];
            }
        }
        return String.join("-", split);
    }

}
