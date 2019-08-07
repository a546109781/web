package cc.nanjo.common.fate.calendar;

import cc.nanjo.common.fate.calendar.entity.IcsContent;
import cc.nanjo.common.fate.calendar.service.IcsContentService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: xw
 * 2019/7/26/026 21:48
 */
@Controller
@RequestMapping("calendar")
public class PublicRequest {


    @Autowired
    private IcsContentService icsContentService;


    @RequestMapping("/bgo")
    @ResponseBody
    public String calendarBgo() {
        IcsContent icsContent = icsContentService.getOne(new QueryWrapper<IcsContent>().orderBy(true, false, "create_time"));
        return icsContent.getContent();
    }


}
