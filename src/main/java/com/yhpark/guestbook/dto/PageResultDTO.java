package com.yhpark.guestbook.dto;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class PageResultDTO <DTO, EN>{

    private List<DTO> dtoList;

    // 총 페이지 번호
    private int totalPage;

    // 현재 페이지 번호
    private int page;

    // 목록 사이즈
    private int size;

    // 시작 페이지 번호, 끝 페이지 번호
    private int start, end;

    // 이전, 다음 페이지 유무
    private boolean prev, next;

    //페이지 번호, 목록
    private List<Integer> pageList;

    // 받은 페이지 목록을 (함수)를 사용해서 각 엔티티를 dto로 변환한 dto 목록을 만들어서 변수에 저장.
    public PageResultDTO(Page<EN> result, Function<EN, DTO> fn) {
        dtoList = result.stream().map(fn).collect(Collectors.toList());

        this.totalPage = result.getTotalPages();

        makePageList(result.getPageable());
    }

    private void makePageList(Pageable pageable) {

        // index start 0 -> but, page start 1
        this.page = pageable.getPageNumber() + 1;
        this.size = pageable.getPageSize();

        int tempEnd = (int) (Math.ceil(page / 10.0) * 10);
        start = tempEnd - 9;

        prev = start > 1; // 1보다 크면 이전 버튼 있도록 불린 설정

        end = totalPage > tempEnd ? tempEnd : totalPage;

        next = totalPage > end; // 아직 남은 페이지가 있으면 뒷방향 버튼 on

        // 시작페이지와 끝 페이지 번호로 리스트를 만들어 반환
        this.pageList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
    }

}
