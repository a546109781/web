package cc.nanjo.common.security.controller;

import cc.nanjo.common.security.entity.Msg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: xw
 * 2019-5-10 10:15
 */
@Slf4j
//@Controller
public class HomeController {

    @RequestMapping("/")
    public String index(Model model) {
        Msg msg = new Msg("测试标题", "测试内容", "欢迎来到HOME页面,您拥有 ROLE_HOME 权限");
        model.addAttribute("msg", msg);
        return "home";
    }


    @RequestMapping("/admin")
    @ResponseBody
    public String hello() {
        return "hello admin";
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @ResponseBody
    public String getList() {
        return "hello getList";
    }


    @RequestMapping(value = "/user", method = RequestMethod.POST)
    @ResponseBody
    public String save() {
        return "hello save";
    }


    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    @ResponseBody
    public String update() {
        return "hello update";
    }
}
