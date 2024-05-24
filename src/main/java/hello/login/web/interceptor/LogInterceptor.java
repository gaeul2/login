package hello.login.web.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Slf4j
public class LogInterceptor implements HandlerInterceptor {

    public static final String LOG_ID = "logId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        String uuid = UUID.randomUUID().toString();

        request.setAttribute(LOG_ID, uuid);
        //handler가 여기서는 컨트롤러임. 어떤 컨트롤러 호출되는지 알수있음
        /*
        * @RequestMapping : HandlerMethod
        * 정적 리소스 : ResourceHttpRequestHandler
        * */
        if (handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) handler;// 호출할 컨트롤러 메서드의 모든 정보가 포함되어 있음.
        }

        log.info("REQEUST [{}][{}][{}]", uuid, requestURI, handler);
        return true; // return true로 하면 핸들러 어댑터가 핸들러 호출하여 핸들로(컨트롤러)로 감. false면 여기서 끝남
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("postHandle [{}]", modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String requestURI = request.getRequestURI();
        String logId = (String) request.getAttribute(LOG_ID);
        log.info("RESPONSE [{}][{}][{}]", logId, requestURI, handler);
        //ex는 예외가 있을때만 값이 넘어옴.
        if(ex != null){
            //오류는 오류찍을때 그냥 이렇게 넣으면 됨
            log.error("afterCompletion error!", ex);
        }

    }
}
