package cc.nanjo.common.fate;

import cc.nanjo.common.fate.table.entity.NServent;
import cc.nanjo.common.fate.table.service.NServentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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

}
