package cc.nanjo.common.bilibili.black;

import cc.nanjo.common.bilibili.black.entity.BiliUpBlack;
import cc.nanjo.common.bilibili.black.service.BiliUpBlackService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author xw
 * @date 2020/3/4/004 19:58
 */
@Controller
@RequestMapping("bilibili/black")
@Slf4j
public class UpBlack {

    @Autowired
    private BiliUpBlackService biliUpBlackService;

    @RequestMapping("json/{user}")
    @ResponseBody
    @CrossOrigin
    public List<String> json(@PathVariable String user, HttpServletResponse response){
        return biliUpBlackService.getUpNames(user);
    }

    @RequestMapping("index")
    @BiliAno
    public String black(HttpServletRequest request){
        return "bilibili/blackList";
    }

    @RequestMapping("list")
    @ResponseBody
    @BiliAno
    public Object list(HttpServletRequest request, int pageNum, int pageSize){
        return biliUpBlackService.selectBlackListJson(pageNum, pageSize);
    }

    @RequestMapping("delete/{id}")
    @BiliAno
    public String delete(HttpServletRequest request, @PathVariable String id){
        biliUpBlackService.remove(new LambdaQueryWrapper<BiliUpBlack>().eq(BiliUpBlack::getId, id));
        return "redirect:/bilibili/black/index";
    }

    @RequestMapping("save")
    @BiliAno
    public String save(HttpServletRequest request, String blackName){
        biliUpBlackService.save(new BiliUpBlack(null, "xw", blackName));
        return "redirect:/bilibili/black/index";
    }

}
