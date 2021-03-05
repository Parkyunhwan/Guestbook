package com.yhpark.guestbook.service;

import com.yhpark.guestbook.dto.GuestbookDTO;
import com.yhpark.guestbook.entity.Guestbook;

public interface GuestbookService {
    // 새로운 방명록 등록
    Long register(GuestbookDTO dto);

    // 인터페이스가 실제 내용을 가지는 코드를 가질 수 있게 됨 (java 8) dto -> entity
    default Guestbook dtoToEntity(GuestbookDTO dto) {
        Guestbook entity = Guestbook.builder()
                .gno(dto.getGno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(dto.getWriter())
                .build();
        return entity;
    }
}
