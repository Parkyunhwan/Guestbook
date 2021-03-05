package com.yhpark.guestbook.service;

import com.yhpark.guestbook.dto.GuestbookDTO;
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
}