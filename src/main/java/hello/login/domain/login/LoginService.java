package hello.login.domain.login;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final MemberRepository memberRepository;

    /**
     * @return이 null이면 로그인 실패
     */
    public Member login(String loginId, String password){
//        Optional<Member> findMemberOptional = memberRepository.findByLoginId(loginId);
//        Member findMember = findMemberOptional.get(); //Optional일때 .get()으로 꺼낼수 있는데 얘는 꺼낸값이 없으면 예외가 터짐
//        if(findMember.getPassword().equals(password)){
//            return findMember;
//        } else {
//            return null;
//        }

        //위에꺼를 한줄로 만든게 이거.
        return memberRepository.findByLoginId(loginId).filter(m -> m.getPassword().equals(password))//같은가.
                .orElse(null); //orElse는 그렇지 않으면
    }
}
