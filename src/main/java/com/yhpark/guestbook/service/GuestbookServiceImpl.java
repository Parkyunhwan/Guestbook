package com.yhpark.guestbook.service;

import com.yhpark.guestbook.dto.GuestbookDTO;
import com.yhpark.guestbook.dto.PageRequestDTO;
import com.yhpark.guestbook.dto.PageResultDTO;
import com.yhpark.guestbook.entity.Guestbook;
import com.yhpark.guestbook.repository.GuestbookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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

        // 파라미터로 넘어줄 객체 생성...
        Page<Guestbook> result = guestbookRepository.findAll(pageable);
        Function<Guestbook, GuestbookDTO> fn = (entity -> entityToDto(entity));

        // 리스트 형태의 dto를 반환함!
        return new PageResultDTO<>(result, fn);
    }
}
