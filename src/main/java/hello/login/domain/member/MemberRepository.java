package hello.login.domain.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.*;

@Slf4j
@Repository
public class MemberRepository {

    private static Map<Long, Member> store = new HashMap<>(); //static 사용
    private static long sequence = 0L; //static 사용

    public Member save(Member member){
        member.setId(++sequence);
        log.info("save: member={}", member);
        store.put(member.getId(), member);
        return member;
    }

    public Member findById(long id){
        return store.get(id);
    }

//    public Member findByLoginId(String loginId){
//        List<Member> all = findAll();
//
//        for (Member m : all) {
//            if (m.getLoginId().equals(loginId)){
//                return m;
//            }
//        }
//        return null;
//    }

    public Optional<Member> findByLoginId(String loginId){
//        List<Member> all = findAll();
//
//        for (Member m : all) {
//            if (m.getLoginId().equals(loginId)){
//                return Optional.of(m);
//            }
//        }
//        return Optional.empty();

        return findAll().stream() //stream()쓰면 반복문돌게됨
                .filter(m -> m.getLoginId().equals(loginId))//filter를 쓰면 ()안의 조건에 만족하는 애만 다음단계로 넘어감
                .findFirst();
    }

    public List<Member> findAll(){
        return new ArrayList<>(store.values());
    }

    public void clearStore(){
        store.clear();
    }
}
