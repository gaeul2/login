package hello.login.web.filter;

import hello.login.web.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//필터만들어주고 꼭 등록해줘야 쓸수 있다. 여기서는 WebConfig 에서 등록
@Slf4j
public class LoginCheckFilter implements Filter {
    //화이트리스트 선언
    private static final String[] whitelist = {"/", "/members/add", "/login", "/logout", "/css/*"};
    //Filter 자체의 init과 destroy는 public default 선언되어있어서 구현하지 않아도 됨
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();

        HttpServletResponse httpResponse = (HttpServletResponse) response;
        //체크로직
        try{
            log.info("인증 체크 필터 시작 {}", requestURI);

            if (isLoginCheckPath(requestURI)){//로그인 체크해야하는 URI면
                log.info("인증 체크 로직 실행 {}", requestURI);
                HttpSession session = httpRequest.getSession(false);
                if  (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null){
                    log.info("미인증 사용자 요청 {}", requestURI);
                    httpResponse.sendRedirect("/login?redirectURL=" + requestURI);
                    return;//다음필터나 서블릿 호출안하겠다는것.
                }
            }

            chain.doFilter(request, response);//꼭 filter 할일 끝나면 이거호출하기
        } catch (Exception e){
            throw e; //예외 로깅 가능하지만, 톰캣까지 예외를 보내주어야 함.
        } finally {
            log.info("인증 체크 필터 종료 {}", requestURI);
        }
    }

    /**
     * 화이트 리스트의 경우 인증체크 X
     */
    private boolean isLoginCheckPath(String requestURI) {
        //whitelist에 없으면 false 반환해서 거기에 부정붙였으니 return값은 true -> LoginCheckPath가 되는것임.
        return !PatternMatchUtils.simpleMatch(whitelist, requestURI);
    }

}
