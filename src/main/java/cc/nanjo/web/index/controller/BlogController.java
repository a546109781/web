package cc.nanjo.web.index.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: xw
 * 2019-4-2 15:00
 */
@Slf4j
//@Controller
public class BlogController {

    @RequestMapping("/")
    public String index(Model model) {
        return "main/blog/index";
    }

    @RequestMapping("/whisper")
    public String whisper(Model model) {
        return "main/blog/whisper";
    }

    @RequestMapping("/leacots")
    public String leacots(Model model) {
        return "main/blog/leacots";
    }

    @RequestMapping("/album")
    public String album(Model model) {
        return "main/blog/album";
    }

    @RequestMapping("/about")
    public String about(Model model) {
        return "main/blog/about";
    }

}
