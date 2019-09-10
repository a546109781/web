package cc.nanjo.common.fate.calendar;

import cc.nanjo.common.fate.calendar.entity.BgoNews;
import cc.nanjo.common.fate.calendar.entity.IcsVevent;
import cc.nanjo.common.fate.calendar.service.BgoNewsService;
import cc.nanjo.common.fate.calendar.service.IcsVeventService;
import cc.nanjo.common.util.StringUtil;
import cc.nanjo.common.util.okhttp.OkHttpUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hankcs.hanlp.HanLP;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.jsoup.Jsoup;
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
public class BgoNewsExecute {

    private static String[] bgoRegWordEvent = {"◆活动时间◆", "◆活动举办时间◆"};
    private static String[] bgoRegWordServer = {"【维护时间】"};
    private static String pageSize = "20";
    private static String detailUrl = "http://fgo.biligame.com/h5/news.html#detailId=";
    private static String bgoListUrl = "http://api.biligame.com/news/list.action?";


    private static Map<String, String> header = new HashMap<>();

    static {
        header.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36");
        header.put("x-requested-with", "XMLHttpRequest");
    }

    @Autowired
    private BgoNewsService bgoNewsService;
    @Autowired
    private IcsVeventService icsVeventService;


    public void getBgoNewList() {
        Map<String, Object> param = new TreeMap<>();
        param.put("gameExtensionId", "45");
        param.put("positionId", "2");
        param.put("typeId", "");
        param.put("pageNum", "1");
        param.put("pageSize", pageSize);
        String paramStr = getMap2UrlParams(param);
        String result = OkHttpUtils.builder().get(bgoListUrl + paramStr, header);
        if (result == null) {
            log.info("返回数据失败, end execute. ");
            return;
        }
        log.info("start action. url --> [{}] , --> param --> [{}]", bgoListUrl, param);
        JSONObject jsonObject = JSONObject.parseObject(result);
        JSONArray data = jsonObject.getJSONArray("data");
        log.info("end action. result num --> [{}]", data.size());

        log.info("analyzing data...");
        for (Object datum : data) {
            JSONObject news = JSONObject.parseObject(datum.toString());
            String id = news.getString("id");
            BgoNews bgoNews = bgoNewsService.getById(id);
            if (bgoNews != null) {
                continue;
            }
            String content = news.getString("content");
            String title = news.getString("title");
            String createTime = news.getString("createTime");
            String modifyTime = news.getString("modifyTime");
            bgoNews = new BgoNews();
            bgoNews.setId(id);
            bgoNews.setCreateTime(parseLocalTime(createTime, "yyyy-MM-dd HH:mm:ss"));
            bgoNews.setModifyTime(parseLocalTime(modifyTime, "yyyy-MM-dd HH:mm:ss"));
            bgoNews.setTitle(unescapeJava(title));
            bgoNews.setContent(unescapeJava(content));
            try {
                String type = getBgoNewsDetail(bgoNews);
                bgoNews.setType(type);
                bgoNewsService.saveOrUpdate(bgoNews);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private String getBgoNewsDetail(BgoNews bgoNews) throws Exception {
        String url = "http://api.biligame.com/news/" + bgoNews.getId() + ".action";
        String result = OkHttpUtils.builder().get(url, header);
        //log.info("发送请求获取文章详细内容--> url = [{}]", url);
        JSONObject jsonObject = JSONObject.parseObject(result);
        JSONObject data = jsonObject.getJSONObject("data");
        String content = data.getString("content");
        content = Jsoup.parse(content.replace("&nbsp;", "").replace("：", ":")).body().text();

        String type = "0";

        if (bgoNews.getTitle().indexOf("预告") != -1 || bgoNews.getTitle().indexOf("日替") != -1 || bgoNews.getTitle().indexOf("幕间") != -1 || bgoNews.getTitle().indexOf("强化") != -1) {
            return type;
        }
        String server = isServer(content);
        if (StringUtils.isNotBlank(server)) {
            log.info("维护公告: title = [{}], url = [{}], content = {}", bgoNews.getTitle(), detailUrl + bgoNews.getId(), content);
            content = content.substring(content.indexOf(server), content.length() - 1).replaceFirst(server, "").replaceAll(" ", "");
            type = "1";
        }
        String event = isEvent(content);
        if (StringUtils.isNotBlank(event)) {
            log.info("活动公告: title = [{}], url = [{}], content = {}", bgoNews.getTitle(), detailUrl + bgoNews.getId(), content);
            content = content.substring(content.indexOf(event), content.length() - 1).replaceFirst(event, "").replaceAll(" ", "");
            type = "2";
        }

        if ("0".equals(type)) {
            return type;
        }

        /**
         * 我们将于以下时间，对游戏服务器实施临时维护， 在维护期间，各位御主将暂时无法进行游戏。 【维护时间】 2019年7月19日14:00～ 7月19日19:00（预计） 【维护内容】 1.开启「唠唠叨叨帝都圣杯奇谭」限时活动 2.开启「唠唠叨叨帝都圣杯奇谭推荐召唤（每日替换）」限时卡池 3.修正，优化多处游戏内文本 【补偿内容】 圣晶石5个 【补偿对象】 2019年7月19日22:00之前已在游戏中创建角色的所有御主 【补偿方式】 发送至御主的礼物盒 【发放时间】 2019年7月19日22:00 注意事项: -请您在维护开始前，退出游戏并确认关闭游戏进程，以避免维护结束后影响游戏更新 -维护结束后，可能会需要下载更新文件，这可能会导致游戏出现运行不顺畅的现象 -维护结束后，建议在游戏设置界面，重新进行游戏资源的下载 -建议在Wi-Fi环境下进行游戏的资源下载
         */

        int first = 0;
        char[] chars = content.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char aChar = chars[i];
            if (aChar == '◆' || aChar == '*' || aChar == '※' || aChar == '为' || aChar == '【' || aChar == '在' || aChar == '期' || aChar == '供') {
                first = i;
                break;
            }
        }
        content = content.substring(0, first);
        content = content.replaceAll("（.*?）|\\(.*?\\)", "");
        content = content.replaceAll("年|月|日", "-").replaceAll("维护后", "19:00").trim();
        String[] split = null;
        if (content.indexOf("~") != -1) {
            split = content.split("~");
        } else if (content.indexOf("～") != -1) {
            split = content.split("～");
        }
        if (split.length <= 1) {
            return "0";
        }
        String start = setNum(split[0]);
        String end = setNum(split[1]);

        Date startDate = DateUtils.parseDate(start, "yyyy-MM-dd-HH:mm", "MM-dd-HH:mm");
        Date endDate = DateUtils.parseDate(end, "yyyy-MM-dd-HH:mm", "MM-dd-HH:mm");
        endDate.setYear(startDate.getYear());
        if ("2".equals(type)) {
            endDate.setSeconds(59);
        }
        LocalDateTime startLocalDate = LocalDateTime.ofInstant(startDate.toInstant(), ZoneId.systemDefault());
        LocalDateTime endLocalDate = LocalDateTime.ofInstant(endDate.toInstant(), ZoneId.systemDefault());

        IcsVevent icsVevent = new IcsVevent();
        icsVevent.setUid(UUID.randomUUID().toString());
        icsVevent.setType(type);
        icsVevent.setBid(bgoNews.getId());
        icsVevent.setStartTime(getTimeStringwithT(startDate));
        icsVevent.setEndTime(getTimeStringwithT(endDate));
        icsVevent.setStartDate(startLocalDate);
        icsVevent.setEndDate(endLocalDate);
        icsVevent.setModifiTime(getTimeStringwithTZ(Date.from(bgoNews.getModifyTime().atZone(ZoneId.systemDefault()).toInstant())));
        icsVevent.setLocation("");
        icsVevent.setSequence("0");
        icsVevent.setSummary(bgoNews.getTitle());
        icsVevent.setUrl(detailUrl + bgoNews.getId());
        icsVevent.setCreateDate(LocalDateTime.now());
        icsVeventService.save(icsVevent);

        return type;
    }


    private static String getMap2UrlParams(Map<String, Object> map) {
        if (map == null || map.isEmpty()) {
            return "";
        }
        List<String> params = new ArrayList<>();
        map.forEach((k, v) -> {
            params.add(k + "=" + v);
        });
        return String.join("&", params);
    }

    private static String unescapeJava(String str) {
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

    private LocalDateTime parseLocalTime(String date, String pattern) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(date, df);
    }

    private static String getTimeStringwithT(Date date) {
        return new SimpleDateFormat("yyyyMMdd'T'HHmmss").format(date);
    }

    private static String getTimeStringwithTZ(Date date) {
        return new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'").format(date);
    }

    private String setNum(String str) {
        StringBuilder stringBuilder = new StringBuilder();
        String[] split = str.split("-");
        for (String s : split) {
            if (s.length() == 1) {
                s = "0" + s;
            }
            stringBuilder.append(s).append("-");
        }
        return stringBuilder.toString().substring(0, stringBuilder.toString().length() - 1);
    }

    private String isServer(String content) {
        for (String s : bgoRegWordServer) {
            if (content.indexOf(s) != -1) {
                return s;
            }
        }
        return "";
    }

    private String isEvent(String content) {
        for (String s : bgoRegWordEvent) {
            if (content.indexOf(s) != -1) {
                return s;
            }
        }
        return "";
    }


}
