package com.yhpark.guestbook.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.yhpark.guestbook.dto.GuestbookDTO;
import com.yhpark.guestbook.dto.PageRequestDTO;
import com.yhpark.guestbook.dto.PageResultDTO;
import com.yhpark.guestbook.entity.Guestbook;
import com.yhpark.guestbook.entity.QGuestbook;
import com.yhpark.guestbook.repository.GuestbookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor
public class GuestbookServiceImpl implements GuestbookService{

    @Autowired
    private final GuestbookRepository guestbookRepository;

    @Override
    public Long register(GuestbookDTO dto) {
        log.info("DTO------------------------");
        log.info(dto);

        Guestbook entity = dtoToEntity(dto);

        log.info(entity);

        guestbookRepository.save(entity);
        return entity.getGno();
    }

    @Override
    public PageResultDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO requestDTO) {

        // 컨트롤러에서 넘어온 dto에서 정렬 방향 선택해서 pageable형태로 변환
        Pageable pageable = requestDTO.getPageable(Sort.by("gno").descending());
        //=> 실제 조회 시 사용하는 pageable 객체..

        // 검색 조건이 적용된 BooleanBuilder 생성
        BooleanBuilder booleanBuilder = getSearch(requestDTO);

        // 파라미터로 넘어줄 객체 생성...
        Page<Guestbook> result = guestbookRepository.findAll(booleanBuilder, pageable);
        Function<Guestbook, GuestbookDTO> fn = (entity -> entityToDto(entity));

        // 리스트 형태의 dto를 반환함!
        return new PageResultDTO<>(result, fn);
    }

    @Override
    public GuestbookDTO read(long gno) {

        Optional<Guestbook> result = guestbookRepository.findById(gno);

        return result.map(this::entityToDto).orElse(null);
    }

    @Override
    public void modify(GuestbookDTO guestbookDTO) {
        Optional<Guestbook> result = guestbookRepository.findById(guestbookDTO.getGno());

        if (result.isPresent()) {

            Guestbook entity = result.get();

            entity.changeTitle(guestbookDTO.getTitle());
            entity.changeContent(guestbookDTO.getContent());

            guestbookRepository.save(entity);
        }
    }

    @Override
    public void remove(Long gno) {
        guestbookRepository.deleteById(gno);
    }

    /**
     * 검색 조건 처리용 메서드
     */
    private BooleanBuilder getSearch(PageRequestDTO requestDTO) {
        String type = requestDTO.getType();
        String keyword = requestDTO.getKeyword();

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        QGuestbook qGuestbook = QGuestbook.guestbook; // static variable

        BooleanExpression booleanExpression = qGuestbook.gno.gt(0); // gno > 0 조건
        booleanBuilder.and(booleanExpression);

        if (type == null || type.trim().length() == 0) {
            return booleanBuilder;
        }

        BooleanBuilder conditionBuilder = new BooleanBuilder();

        // title, content, writer에 키워드가 존재하는 지 검사!
        if (type.contains("t")) {
            conditionBuilder.or(qGuestbook.title.contains(keyword));
        }
        if (type.contains("c")) {
            conditionBuilder.or(qGuestbook.content.contains(keyword));
        }
        if (type.contains("w")) {
            conditionBuilder.or(qGuestbook.writer.contains(keyword));
        }

        booleanBuilder.and(conditionBuilder);

        return booleanBuilder;
    }
}
