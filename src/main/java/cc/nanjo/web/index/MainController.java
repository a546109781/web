package cc.nanjo.web.index;

import cc.nanjo.web.base.BaseController;
import cc.nanjo.web.table.entity.NPost;
import cc.nanjo.web.table.service.NPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: xw
 * 2019-3-28 16:31
 */
@Controller
public class MainController extends BaseController {

    @Autowired
    private NPostService nPostService;


    @RequestMapping("/")
    public String main(HttpServletRequest request) {
        NPost nPost = nPostService.getById(1L);
        return "main/index";
    }

    @RequestMapping("/p/{postId}")
    public String p(HttpServletRequest request, @RequestParam("postId") Long postId) {
        NPost nPost = nPostService.getById(postId);

        return "main/post";
    }

}
