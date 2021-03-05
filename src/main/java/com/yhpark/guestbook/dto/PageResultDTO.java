package com.yhpark.guestbook.dto;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
public class PageResultDTO <DTO, EN>{

    private List<DTO> dtoList;

    // 받은 페이지 목록을 (함수)를 사용해서 각 엔티티를 dto로 변환한 dto 목록을 만들어서 변수에 저장.
    public PageResultDTO(Page<EN> result, Function<EN, DTO> fn) {
        dtoList = result.stream().map(fn).collect(Collectors.toList());
    }

}
