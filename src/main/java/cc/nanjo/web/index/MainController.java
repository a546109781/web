package cc.nanjo.web.index;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: xw
 * 2019-3-28 16:31
 */
@Controller
public class MainController {


    @RequestMapping("/")
    public String main(HttpServletRequest httpServletRequest) {

        return "hello world";
    }

}
