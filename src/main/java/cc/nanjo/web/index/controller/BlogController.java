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
        return "BgoNewsExecute/blog/index";
    }

    @RequestMapping("/whisper")
    public String whisper(Model model) {
        return "BgoNewsExecute/blog/whisper";
    }

    @RequestMapping("/leacots")
    public String leacots(Model model) {
        return "BgoNewsExecute/blog/leacots";
    }

    @RequestMapping("/album")
    public String album(Model model) {
        return "BgoNewsExecute/blog/album";
    }

    @RequestMapping("/about")
    public String about(Model model) {
        return "BgoNewsExecute/blog/about";
    }

}
