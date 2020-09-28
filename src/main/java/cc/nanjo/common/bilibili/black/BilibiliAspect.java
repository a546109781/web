package cc.nanjo.common.bilibili.black;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author xw
 * @date 2020/3/4/004 21:20
 */
@Aspect
@Component
@Slf4j
public class BilibiliAspect {

    @Pointcut("@annotation(cc.nanjo.common.bilibili.black.BiliAno)")
    public void asp(){

    }

    @Before("asp()")
    public Object doBefore(JoinPoint joinPoint) throws Exception{
        Object[] args = joinPoint.getArgs();
        HttpServletRequest request = (HttpServletRequest) args[0];
        HttpSession session = request.getSession();
        Object isLogin = session.getAttribute("isLogin");
        if(isLogin == null){
            HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
            response.sendRedirect("/login?redirect=" + request.getRequestURI());
        }
        return null;
    }

}
