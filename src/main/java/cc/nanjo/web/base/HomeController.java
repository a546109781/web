package cc.nanjo.web.base;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author xw
 * @date 2020/3/10/010 17:21
 */
@Controller
@Slf4j
@PropertySource("classpath:config.properties")
public class HomeController {

    @Value("${resourcesPath}")
    private String resourcesPath;

    @RequestMapping("/")
    public String index(){
        return "redirect:/home";
    }

    @RequestMapping("/home")
    public String home(){
        return "nav/home2";
    }

    @RequestMapping("/home/form")
    public String homeForm(){
        return "nav/form";
    }


}
