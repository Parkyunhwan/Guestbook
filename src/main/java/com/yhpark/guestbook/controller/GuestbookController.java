package com.yhpark.guestbook.controller;

import com.yhpark.guestbook.dto.GuestbookDTO;
import com.yhpark.guestbook.dto.PageRequestDTO;
import com.yhpark.guestbook.service.GuestbookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/guestbook")
@Log4j2
@RequiredArgsConstructor
public class GuestbookController {

    private final GuestbookService service;

//    @GetMapping({"/", "/list"}) // layout test
//    public String list() {
//        log.info("list................");
//        return "/guestbook/list";
//    }

    @GetMapping("/list")
    public void list(@ModelAttribute("pageRequestDTO") PageRequestDTO pageRequestDTO,
                     Model model) {
        log.info("list..........." + pageRequestDTO);

        // requestDTO를 넘겨주고 resultDTO를 받아올 수 있다. (조회)
        model.addAttribute("result", service.getList(pageRequestDTO));
    }

    @GetMapping("/register")
    public void register() {
        log.info("register get....");
    }

    @PostMapping("/register")
    public String registerPost(GuestbookDTO dto, RedirectAttributes redirectAttributes) {
        log.info("dto..." + dto);

        Long gno = service.register(dto);

        // 단 한 번만 데이터를 전달하는 용
        redirectAttributes.addFlashAttribute("msg", gno);

        return "redirect:/guestbook/list";
    }
}
 