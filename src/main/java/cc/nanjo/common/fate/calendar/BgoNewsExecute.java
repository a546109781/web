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

    private static String bgoRegWordEvent1 = "◆活动时间◆";
    private static String bgoRegWordEvent2 = "◆公开时间◆";
    private static String bgoRegWordServer = "【维护时间】";
    private static String pageSize = "100";
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

    public String getBgoNewsDetail(BgoNews bgoNews) throws Exception {
        String url = "http://api.biligame.com/news/" + bgoNews.getId() + ".action";
        String result = OkHttpUtils.builder().get(url, header);
        //log.info("发送请求获取文章详细内容--> url = [{}]", url);
        JSONObject jsonObject = JSONObject.parseObject(result);
        JSONObject data = jsonObject.getJSONObject("data");
        String content = data.getString("content");
        content = Jsoup.parse(content.replace("&nbsp;", "")).body().text();
        /**
         * ◆活动时间◆ 2019年7月19日（周五）维护后~8月2日（周五）13:59为止 ◆活动概要◆ 限时活动「唠唠叨叨帝都圣杯奇谭」举办！ 和「坂本龙马」一起体验发生于帝都的唠唠叨叨活动第3弹的故事吧！ 在本次活动中，推进主线关卡，活动限定从者「★4（SR）坂本龙马」会限时加入。 推进主线关卡至尾声结束，即可让「★4（SR）坂本龙马」正式加入！ ◆活动参加条件◆ 仅限已通关「终局特异点」的御主参加 ※无需通关亚种特异点（Ⅰ至Ⅳ）以及第二部的「序」。 ※限定从者「★4（SR）坂本龙马」将在通关活动后期开放的尾声时正式加入。 在活动开始初期尾声不会立即开放，此时即使通关所有已开放的主线关卡「★4（SR）坂本龙马」也不会正式加入，敬请注意。 尾声开放时间 2019年7月26日（周五）17:00 在限时活动「唠唠叨叨帝都圣杯奇谭」中，达成「收集一定数量以上的特定道具」、「打败特定的敌人」等各种条件，即可触发包含豪华奖励的任务！ 此外，随着任务的不断达成，还会有新的关卡和任务开放。 ※达成条件后不会自动获得奖励，需要手动领取，敬请注意。 ※若没有领取任务奖励，则有可能会出现新任务及新地点无法开放的情况，敬请注意。 ◆有效率的攻略方法·其1◆ 对象从者在「唠唠叨叨帝都圣杯奇谭」的关卡中，将被付与提升攻击威力的效果！ 强化对象从者来完成活动挑战吧！ ※各对象从者的加成数值存在不同。 【对象从者】 职阶 稀有度 从者名 Saber ★★★★★ 冲田总司 Archer ★★★★ 卫宫 ★★★★ 织田信长 Lancer ★★★★ 李书文 Rider ★★★★ 坂本龙马 ★★★ 美杜莎 Caster ★★★ 美狄亚 Assassin ★★★★★ 谜之女主角X ★★★ 冈田以藏 Berserker ★★★★★ 谜之女主角X〔Alter〕 ★★★★★ 土方岁三 ★★★★ 茶茶 Alterego ★★★★★ 冲田总司〔Alter〕 ◆有效率的攻略方法·其2◆ 完成任务需要打败一定数量的特定敌人。装备在活动道具兑换中获得的活动限定概念礼装「白衣水手小姐」，即可提升对应敌人的追加出现率。 ※在各个关卡中，即使敌人的追加出现率显示为超过100%的数值，实际的出现率仍为100%，敬请注意。 ※追加出现率提升的〔迷你信系〕敌人包括「巨大信」等活动限定敌人。 ◆有效率的攻略方法·其3◆ 装备圣晶石召唤中被推荐召唤的限定概念礼装「帝都圣杯战争」、「坂本侦探事务所」、「研磨锐牙之暗剑」，即可提升限时活动「唠唠叨叨帝都圣杯奇谭」中的专用道具「青蛙香炉」、「青蛙根附」、「青蛙手帕」的掉落获得数。 ※各关卡中的道具掉落率并不是100%，敬请注意。 ◆灵基再临◆ 使用仅可在活动期间通过「活动报酬」获得的「海援队旗」对「★4（SR）坂本龙马」进行4次灵基再临后，「★4（SR）坂本龙马」的圣肖像将发生变化！ ※「★4（SR）坂本龙马」完成灵基再临时不会有战斗形象变化，敬请注意。 ◆来入手活动限定概念礼装EXP卡吧！◆ ◆兑换方法◆ 兑换期：7月19日（周五）维护后~8月9日（周五）13:59为止 ※「青蛙香炉」、「青蛙根附」、「青蛙手帕」将在兑换期结束后被删除。 可通过点击主界面右上角的「活动报酬」按钮进入「兑换活动道具」界面，使用活动专用道具兑换以下道具。 ◆青蛙香炉可兑换的道具◆ ◆青蛙根附可兑换的道具◆ ◆青蛙手帕可兑换的道具◆ 面对已完成限时活动「唠唠叨叨帝都圣杯奇谭」所有任务的御主，将开放高难度的「挑战关卡」。 「挑战关卡」在通关后不会消失，可以反复挑战。御主们可以尝试不同的从者与概念礼装组合，进行多次挑战。 ※关卡通关奖励、战利品、御主经验、魔术礼装经验、羁绊点数都仅可在首次通关时获得。 ◆挑战关卡开放时间◆ 2019年7月26日（周五）17:00~ ◆挑战关卡参加条件◆ 仅限已完成限时活动「唠唠叨叨帝都圣杯奇谭」所有任务的御主参加 ◆挑战关卡初次通关奖励◆ 传承结晶 1个 在限时活动「唠唠叨叨帝都圣杯奇谭」的活动时间内，个人空间将限时变更为特别样式！ ◆时间◆ 2019年8月2日13:59为止
         */
        String type = "0";

        if (bgoNews.getTitle().indexOf("预告") != -1 || bgoNews.getTitle().indexOf("每日替换") != -1) {
            return type;
        }

        if (content.indexOf(bgoRegWordServer) != -1) {
            log.info("维护公告: url = [{}], content = {}", detailUrl + bgoNews.getId(), content);
            type = "1";

        }
        if (content.indexOf(bgoRegWordEvent1) != -1) {
            log.info("活动公告: url = [{}], content = {}", detailUrl + bgoNews.getId(), content);
            type = "2";
            content = content.substring(content.lastIndexOf(bgoRegWordEvent1), content.length() - 1).replace(bgoRegWordEvent1, "").replaceAll(" ", "");
            if (content.indexOf("为止") != -1) {
                content = content.substring(0, content.indexOf("为止"));
            } else {
                int first = 0;
                char[] chars = content.toCharArray();
                for (int i = 0; i < chars.length; i++) {
                    char aChar = chars[i];
                    if (aChar == '◆' || aChar == '*' || aChar == '※') {
                        first = i;
                        break;
                    }
                }
                content = content.substring(0, first);
            }
            content = content.replaceAll("（.*?）|\\(.*?\\)", "");
            content = content.replaceAll("年|月|日", "-").replaceAll("维护后", "19:00").trim();
            String[] split = null;
            if (content.indexOf("~") != -1) {
                split = content.split("~");
            } else if (content.indexOf("～") != -1) {
                split = content.split("～");
            }
            String start = split[0];
            String end = split[1];
            Date startDate = DateUtils.parseDate(start, "yyyy-M-dd-HH:mm");
            Date endDate = DateUtils.parseDate(end, "yyyy-M-dd-HH:mm", "M-dd-HH:mm");
            endDate.setYear(startDate.getYear());
            endDate.setSeconds(59);
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
        }
        return type;
    }

    public static void main(String[] args) {
        String str = " 2019年7月12日（周五）0:00~7月26日（周五）13:59 ◆参加条件◆ 所有已通关「特异点F 燃烧污染都市 冬木 第3节」的御主 ※若已获得魔术礼装「2004年的碎片」，则「魔术礼装任务·2004年的碎片」不会出现。 ※魔术礼装「2004年的碎片」获得关卡无剧情内容，敬请注意";
        //2019-5-31-19:00~2019-6-17-13:59◆活动概要◆限时Fate/Apocrypha×Fate/GrandOrder特别活动「Apocrypha/InheritanceofGlory」举办！在梦的引导下，于非梦的世界中，取得再现的圣杯战争的胜利吧！本次活动中，随着主线任务的进行，将会发生敌方阵营的从者陆续复数出现的击退战。击退战自5-31-19:00起，至6-7-期间，将分为五个阶段出现。所有御主们协力，击退敌方从者，将主线任务推进至最后，便能获得活动限定从者「★4齐格」。◆活动参加条件◆仅限完成通关「第一特异点邪龙百-战争奥尔良」的御主参加◆登场敌方阵营从者一览◆◆击退战时间表◆敌方阵营从者复数出现的击退战，将从5-31-19:00开始，至6-7-，分为五个阶段发生。五个阶段的击退战全部结束之后，将不会再有新的击退战发生，敬请注意。※击退战是全体御主们共同削减敌方阵营的「剩余战斗数」的战斗。※1次战斗胜利之后，「剩余战斗数」便将减少1。※「剩余战斗数」为0时击退战即结束，直到下个击退战发生
        //2019-4-30-19:00～5-14-13:59※不包括已经通关的自由关卡。※不包括强化关卡、幕间物语关卡、每-任务关卡。在现已永久举办的AP消耗量减半的状态下，将再次追加AP消耗量减半，也就是说，第1部主线关卡（特异点F至终局特异点
        if (str.indexOf("◆") != -1) {
            str = str.substring(0, str.indexOf("◆"));
        }
        if (str.indexOf("*") != -1) {
            str = str.substring(0, str.indexOf("*"));
        }
        System.out.println(str);
    }

    private static boolean isNum(String charid) {
        for (char c : charid.toCharArray()) {
            if (c == '.') {
                continue;
            }
            if (c < 48 || c > 57) {
                return false;
            }
        }
        return true;
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

}
