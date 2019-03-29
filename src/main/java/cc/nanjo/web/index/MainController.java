package cc.nanjo.web.index;

import cc.nanjo.web.base.BaseController;
import cc.nanjo.web.table.entity.NPost;
import cc.nanjo.web.table.service.NPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: xw
 * 2019-3-28 16:31
 */
@Controller
public class MainController extends BaseController {

    @Autowired
    private NPostService nPostService;


    @RequestMapping("/")
    public String main(Model model) {
        NPost nPost = nPostService.getById(1L);
        return "main/index";
    }

    @RequestMapping("/page/{pageNum}")
    public String page(Model model, @RequestParam("pageNum") Integer pageNum) {
        NPost nPost = nPostService.getById(1L);
        return "main/index";
    }

    @RequestMapping("/post/{postId}")
    public String p(Model model, @RequestParam("postId") Long postId) {
        NPost nPost = nPostService.getById(postId);
        model.addAttribute("post", nPost);
        return "main/post";
    }

}
