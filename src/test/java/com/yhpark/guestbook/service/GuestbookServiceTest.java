package com.yhpark.guestbook.service;

import com.yhpark.guestbook.dto.GuestbookDTO;
import com.yhpark.guestbook.dto.PageRequestDTO;
import com.yhpark.guestbook.dto.PageResultDTO;
import com.yhpark.guestbook.entity.Guestbook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class GuestbookServiceTest {
    @Autowired
    GuestbookService guestbookService;

    @Test
    public void testRegister() throws Exception {
        //given
        GuestbookDTO guestbookDTO = GuestbookDTO.builder()
                .title("title")
                .content("CONTENT")
                .writer("yunhwan")
                .build();

        // db에 저장하는 명령어!
        System.out.println(guestbookService.register(guestbookDTO));
        //when

        //then
    }

    @Test
    public void testList() throws Exception {
        //given
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(1).size(10).build();

        //when
        // pk를 기준으로 정렬해서 조회!
        PageResultDTO<GuestbookDTO, Guestbook> resultDTO = guestbookService.getList(pageRequestDTO);
        //then
        for (GuestbookDTO guestbookDTO : resultDTO.getDtoList()) {
            System.out.println(guestbookDTO);
        }
    }
}