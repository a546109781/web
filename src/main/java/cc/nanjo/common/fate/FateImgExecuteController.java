package cc.nanjo.common.fate;

import cc.nanjo.common.fate.table.entity.NServent;
import cc.nanjo.common.fate.table.service.NServentService;
import cc.nanjo.common.fate.utils.ImgUtils;
import cc.nanjo.common.fate.vo.SubmitReq;
import cc.nanjo.common.fate.vo.Submits;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author: xw
 * 2019-4-1 11:33
 */
@Slf4j
@Controller
@RequestMapping("/public")
public class FateImgExecuteController {

    @Autowired
    private NServentService nServentService;

    @RequestMapping("/fate-grand-order/i-q")
    public String toImgPage(Model model) {
        List<NServent> list = nServentService.list();
        model.addAttribute("list", list);
        return "fate/fgo/img";
    }

    @RequestMapping("/fate-grand-order/submit")
    @ResponseBody
    public String submit(@RequestBody SubmitReq submitReq, HttpServletRequest request) {
        List<Submits> submits = submitReq.getSubmits();
        ImgUtils imgUtils = new ImgUtils(submits);
        try {
            imgUtils.getImg();
        } catch (Exception e) {

        }
        return "fate/fgo/img";
    }

}
