package cc.nanjo.web.base;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: xw
 * 2019-3-29 10:48
 */
@Slf4j
@Controller
public class BaseController {


    @ModelAttribute
    public HttpServletRequest modelAttribute(HttpServletRequest request, HttpServletResponse response) {


        return request;
    }

}