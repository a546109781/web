package cc.nanjo.fgo;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.assertj.core.util.DateUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author xw
 * @date 2019/7/26 10:29
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class Calendar {

    String templateVevent = "BEGIN:VEVENT\n" +
            "UID:${uid}\n" +
            "DTSTART;TZID=Asia/Shanghai:${startTime}\n" +
            "DTEND;TZID=Asia/Shanghai:${endTime}\n" +
            "LAST-MODIFIED:${modifiTime}\n" +
            "LOCATION:${location}\n" +
            "SEQUENCE:${sequence}\n" +
            "SUMMARY:${summary}\n" +
            "URL:${url}\n" +
            "END:VEVENT\n";

    String templateIcs = "BEGIN:VCALENDAR\n" +
            "VERSION:2.0\n" +
            "X-WR-CALNAME:命运-冠位指定\n" +
            "X-APPLE-CALENDAR-COLOR:#34aadc\n" +
            "${EVENTS}" +
            "END:VCALENDAR";


    @Data
    class IcsVevent {

        private String uid;
        private String startTime;
        private String endTime;
        private String modifiTime;
        private String location;
        private String sequence;
        private String summary;
        private String url;

        private Date createDate;
        private Date startDate;
        private Date endDate;

    }


    /**
     * BEGIN:VEVENT
     * UID:00EDCAEF-ED38-42F9-B204-8CA108C1DD6A
     * DTSTART;TZID=Asia/Shanghai:20170715T140000
     * DTEND;TZID=Asia/Shanghai:20170715T170000
     * LAST-MODIFIED:20170713T040441Z
     * LOCATION:
     * SEQUENCE:0
     * SUMMARY:临时维护
     * URL:http://fgo.biligame.com/h5/news.html#detailId=1634
     * END:VEVENT
     */

    @Test
    public void testIcs() throws Exception {

        List<IcsVevent> icsVevents = new ArrayList<>();

        Date startDate = str2Date("2018-01-01 17:00:00");
        Date endDate = str2Date("2018-01-15 14:00:00");

        IcsVevent icsVevent = new IcsVevent();
        icsVevent.setUid(UUID.randomUUID().toString());
        icsVevent.setStartDate(startDate);
        icsVevent.setStartTime(getTimeStringwithT(startDate));
        icsVevent.setEndDate(endDate);
        icsVevent.setEndTime(getTimeStringwithT(endDate));
        icsVevent.setLocation("");
        icsVevent.setSequence("0");
        icsVevent.setModifiTime(getTimeStringwithTZ(DateUtil.now()));
        icsVevent.setSummary("延长道具兑换「魔法少女纪行~Prisma·Corps~」");
        icsVevent.setUrl("http://fgo.biligame.com/h5/news.html#detailId=1634");
        icsVevent.setCreateDate(DateUtil.now());

        icsVevents.add(icsVevent);

        startDate = str2Date("2018-01-15 17:00:00");
        endDate = str2Date("2018-01-30 14:00:00");

        IcsVevent icsVeventTwo = new IcsVevent();
        icsVeventTwo.setUid(UUID.randomUUID().toString());
        icsVeventTwo.setStartDate(startDate);
        icsVeventTwo.setStartTime(getTimeStringwithT(startDate));
        icsVeventTwo.setEndDate(endDate);
        icsVeventTwo.setEndTime(getTimeStringwithT(endDate));
        icsVeventTwo.setLocation("");
        icsVeventTwo.setSequence("0");
        icsVeventTwo.setModifiTime(getTimeStringwithTZ(DateUtil.now()));
        icsVeventTwo.setSummary("限时活动·推荐召唤「复刻：大约一周的圣诞Alter小姐 轻量版」");
        icsVeventTwo.setUrl("http://fgo.biligame.com/h5/news.html#detailId=1634");
        icsVeventTwo.setCreateDate(DateUtil.now());
        icsVevents.add(icsVeventTwo);
        String s = this.builderIcs(icsVevents);
        System.out.println(s);

    }


    public String builderIcs(List<IcsVevent> icsVevents) {
        StringBuilder stringBuilder = new StringBuilder();
        for (IcsVevent icsVevent : icsVevents) {
            String icsStr = "";
            Field[] fields = icsVevent.getClass().getDeclaredFields();
            for (Field field : fields) {
                try {
                    String result = MethodUtils.invokeMethod(icsVevent, "get" + upcaseFrist(field.getName())).toString();
                    String target = "${" + field.getName() + "}";
                    if("".equals(icsStr)){
                        icsStr = templateVevent.replace(target, result);
                    } else {
                        icsStr = icsStr.replace(target, result);
                    }
                } catch (Exception e) {

                }
            }
            stringBuilder.append(icsStr);
        }
        return templateIcs.replace("${EVENTS}", stringBuilder.toString());
    }

    private static String upcaseFrist(String str) {
        char[] ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] = (char) (ch[0] - 32);
        }
        return new String(ch);
    }

    public static String getTimeStringwithT(Date date) {
        return new SimpleDateFormat("yyyyMMdd'T'HHmmss").format(date);
    }

    public static String getTimeStringwithTZ(Date date) {
        return new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'").format(date);
    }

    public static Date str2Date(String date) throws Exception {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
    }


}
