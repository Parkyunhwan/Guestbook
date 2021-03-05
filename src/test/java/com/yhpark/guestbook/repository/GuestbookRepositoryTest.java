package com.yhpark.guestbook.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.yhpark.guestbook.entity.Guestbook;
import com.yhpark.guestbook.entity.QGuestbook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GuestbookRepositoryTest {

    @Autowired
    private GuestbookRepository guestbookRepository;

    @Test
    public void insertdummies() throws Exception {
        //given 300개의 더미 데이터
        IntStream.rangeClosed(1, 300).forEach(i -> {
            Guestbook guestbook = Guestbook.builder()
                    .title("Title...." + i)
                    .content("Content..." + i)
                    .writer("user" + (i % 10))
                    .build();
            System.out.println(guestbookRepository.save(guestbook));
        });
       //when

        //then
    }

    @Test
    public void updateTest() throws Exception {
        //given
        Optional<Guestbook> result = guestbookRepository.findById(300L);
        //when
        if (result.isPresent()) {
            Guestbook guestbook = result.get();

            // title and content 변경
            guestbook.changeTitle("Changed Title....");
            guestbook.changeContent("Changed Content....");

            // 변경 후 저장
           guestbookRepository.save(guestbook);
        }
        //then
    }

    @Test
    public void testQuery1() throws Exception {
        //given
        // 0 - 9 페이지, gno로 정
        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());
        //when
        //findAll -> JpaRepositroy => 쿼리dsl 적용하기위해 레포의 QuerydslPredicateExecutor를 사용

        //Predicate (import 시 주의)
        QGuestbook qGuestbook = QGuestbook.guestbook;

        // 엔티티 변수를 조합해서 만들어보자

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        // title like %1% 을 코드로 만든다고 생각하면 된다.
        BooleanExpression expression = qGuestbook.title.contains("1");

        // 빌더에 코드 추가
        booleanBuilder.and(expression);

        // 쿼리 실행
        Page<Guestbook> result = guestbookRepository.findAll(booleanBuilder, pageable);

        //then
        result.forEach(guestbook -> {
            System.out.println(guestbook);
        });
    }

    @Test
    public void testQuery2() throws Exception {
        //given
        // 0 - 9 페이지, gno로 정
        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());
        //when
        //findAll -> JpaRepositroy => 쿼리dsl 적용하기위해 레포의 QuerydslPredicateExecutor를 사용

        //Predicate (import 시 주의)
        QGuestbook qGuestbook = QGuestbook.guestbook;

        // 엔티티 변수를 조합해서 만들어보자

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        // title like %1% 을 코드로 만든다고 생각하면 된다.
        BooleanExpression exTitle = qGuestbook.title.contains("1");
        BooleanExpression exTitleContent = exTitle.or(qGuestbook.content.contains("1"));

        booleanBuilder.and(exTitleContent);

        // gt -> greater than
        booleanBuilder.and(qGuestbook.gno.gt(0L));

        // 쿼리 실행
        Page<Guestbook> result = guestbookRepository.findAll(booleanBuilder, pageable);

        //then
        result.stream().forEach(guestbook -> {
            System.out.println(guestbook);
        });
    }
}