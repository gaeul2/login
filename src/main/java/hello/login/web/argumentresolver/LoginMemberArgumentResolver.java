package hello.login.web.argumentresolver;

import hello.login.domain.member.Member;
import hello.login.web.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        log.info("supportsParameter 실행");

        //hasParameterAnnotation는 컨트롤러 호출전 컨트롤러의 파라미터에  @Login이 있는지 확인
        boolean hasLoginAnnotation = parameter.hasParameterAnnotation(Login.class);

        //Member클래스가 할당가능하냐? 어디에? 컨트롤러 파라미터의 파라미터 타입으로! 어디에?
        //HomeController의  public String homeLoginV3ArgumentResolver(@Login {Member}여기에 loginMember, Model model){
        boolean hasMemberType = Member.class.isAssignableFrom(parameter.getParameterType());

        return hasLoginAnnotation && hasMemberType;
    }

    //supportsParameter가 true면 실행되고, false면 실행안됨.
    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        log.info("resolveArgument 실행");
        HttpServletRequest request = (HttpServletRequest) nativeWebRequest.getNativeRequest();
        HttpSession session = request.getSession(false);//새로생성할거 아니니깐 false 로
        if (session == null){
            return null; //loginMember가 null이 되는것.
        }

        return session.getAttribute(SessionConst.LOGIN_MEMBER);
    }
}
