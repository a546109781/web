package cc.nanjo.common.fate.calendar;

import cc.nanjo.common.fate.calendar.entity.IcsContent;
import cc.nanjo.common.fate.calendar.service.IcsContentService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: xw
 * 2019/7/26/026 21:48
 */
@Controller
@RequestMapping("calendar")
@Slf4j
public class PublicRequest {


    @Autowired
    private IcsContentService icsContentService;


    @RequestMapping("/bgo")
    @ResponseBody
    public String calendarBgo(HttpServletRequest request) {
        log.info("[bgo] request header {}", JSONObject.toJSONString(getHeadersInfo(request)));
        IcsContent icsContent = icsContentService.selectNewIcs();
        return icsContent.getContent();
    }


    private Map<String, String> getHeadersInfo(HttpServletRequest request) {
        Map<String, String> map = new HashMap<String, String>();
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }
        return map;
    }


}
