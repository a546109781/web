package cc.nanjo.common.fate.calendar.util;

import cc.nanjo.common.fate.calendar.entity.IcsVevent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.MethodUtils;

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
@Slf4j
public class Calendar {

    private static String templateVevent = "BEGIN:VEVENT\n" +
            "UID:${uid}\n" +
            "DTSTART;TZID=Asia/Shanghai:${startTime}\n" +
            "DTEND;TZID=Asia/Shanghai:${endTime}\n" +
            "LAST-MODIFIED:${modifiTime}\n" +
            "LOCATION:${location}\n" +
            "SEQUENCE:${sequence}\n" +
            "SUMMARY:${summary}\n" +
            "URL:${url}\n" +
            "END:VEVENT\n";

    private static String templateIcs = "BEGIN:VCALENDAR\n" +
            "VERSION:2.0\n" +
            "X-WR-CALNAME:命运-冠位指定\n" +
            "X-APPLE-CALENDAR-COLOR:#34aadc\n" +
            "${EVENTS}" +
            "END:VCALENDAR";


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


    public static String builderIcs(List<IcsVevent> icsVevents) {
        StringBuilder stringBuilder = new StringBuilder();
        for (IcsVevent icsVevent : icsVevents) {
            String icsStr = "";
            Field[] fields = icsVevent.getClass().getDeclaredFields();
            for (Field field : fields) {
                try {
                    String result = MethodUtils.invokeMethod(icsVevent, "get" + upcaseFrist(field.getName())).toString();
                    String target = "${" + field.getName() + "}";
                    if ("".equals(icsStr)) {
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

}
