package cc.nanjo.common.error;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: xw
 * 2019-3-29 11:03
 */
@Controller
public class ErrorController {

    @RequestMapping("error-404")
    public String toPage404() {
        return "error/404";
    }

    @RequestMapping("error-400")
    public String toPage400() {
        return "error/400";
    }

    @RequestMapping("error-500")
    public String toPage500() {
        return "error/500";
    }

}
