package cc.nanjo.common.fate.calendar;

import cc.nanjo.common.fate.calendar.entity.IcsContent;
import cc.nanjo.common.fate.calendar.entity.IcsVevent;
import cc.nanjo.common.fate.calendar.service.IcsContentService;
import cc.nanjo.common.fate.calendar.service.IcsVeventService;
import cc.nanjo.common.fate.calendar.util.Calendar;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author: xw
 * 2019/7/26/026 21:09
 */
@Component
@Slf4j
public class BgoNewToCalendarTask {

    @Autowired
    private BgoNewsExecute bgoNewsExecute;
    @Autowired
    private IcsVeventService icsVeventService;
    @Autowired
    private IcsContentService icsContentService;


    @Scheduled(cron = "0 30/59 0/1 * * ? ")
    public void taskListBgoNews() {
        bgoNewsExecute.getBgoNewList();
    }

    @Scheduled(cron = "0 35/59 0/1 * * ? ")
    public void executeIcs() {
        List<IcsVevent> icsVevents = icsVeventService.list(new QueryWrapper<IcsVevent>().ge("end_date", LocalDateTime.now()).orderBy(true, true, "end_date"));
        String content = Calendar.builderIcs(icsVevents);
        IcsContent icsContent = new IcsContent();
        icsContent.setContent(content);
        icsContent.setCreateTime(LocalDateTime.now());
        icsContentService.save(icsContent);
    }

}
