package com.yhpark.guestbook.service;

import com.yhpark.guestbook.dto.GuestbookDTO;
import com.yhpark.guestbook.dto.PageRequestDTO;
import com.yhpark.guestbook.dto.PageResultDTO;
import com.yhpark.guestbook.entity.Guestbook;

public interface GuestbookService {

    // 고객의 요구사항들..
    // 새로운 방명록 등록
    Long register(GuestbookDTO dto);

    // 고객의 요구사항 -> Requestdto로 주면 resultDTO(dto리스트 형태)로 받아오길 원함.
    PageResultDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO requestDTO);

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

    // 엔티티 -> dto 변환.
    default GuestbookDTO entityToDto(Guestbook entity){
        GuestbookDTO dto = GuestbookDTO.builder()
                .gno(entity.getGno())
                .title(entity.getTitle())
                .content(entity.getContent())
                .writer(entity.getWriter())
                .regDate(entity.getRegDate())
                .modDate(entity.getModDate())
                .build();
        return dto;
    }
}
