package cc.nanjo.web.index.controller;

import cc.nanjo.web.index.dto.PostContentDto;
import cc.nanjo.web.index.dto.PostListDto;
import cc.nanjo.web.index.service.MainService;
import cc.nanjo.web.table.service.NPostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @Author: xw
 * 2019-3-28 16:31
 */
@Slf4j
//@Controller
public class MainController {

    @Autowired
    private MainService mainService;

    @RequestMapping("/")
    public String main(Model model) {
        List<PostListDto> postList = mainService.getPostList(1, 10);
        model.addAttribute("postList", postList);
        return "BgoNewsExecute/index";
    }

    @RequestMapping("page/{pageNum}")
    public String page(Model model, @PathVariable Integer pageNum) {
        List<PostListDto> postList = mainService.getPostList(pageNum, 10);
        model.addAttribute("postList", postList);
        return "BgoNewsExecute/index";
    }

    @RequestMapping(value = "post/{postId}", method = RequestMethod.GET)
    public String post(Model model, @PathVariable Long postId) {
        log.info("进入帖子详情页面");
        PostContentDto postContentDto = mainService.getPostContentByPostId(postId);
        model.addAttribute("post", postContentDto);
        return "BgoNewsExecute/post";
    }

}
