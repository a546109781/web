package cc.nanjo.web.base;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xw
 * @date 2020/3/4/004 21:38
 */
@Controller
@Slf4j
@PropertySource("classpath:config.properties")
public class LoginController {

    @Value("${username}")
    private String myUsername;
    @Value("${password}")
    private String myPassword;

    @GetMapping("login")
    public String loginIndex(Model model) {
        return "/login/index";
    }

    @PostMapping("login")
    public String login(HttpServletRequest request, String username, String password, String redirect) {
        log.info("{}, {}", username, password);
        if (username.trim().equals(myUsername) && password.trim().equals(myPassword)) {
            request.getSession().setAttribute("isLogin", true);
            return "redirect:" + redirect;
        }
        return "/login/index";
    }

}
