package com.devpass.global.common.dto;

import com.devpass.global.payload.apicode.ErrorStatus;
import com.devpass.global.payload.error.exception.GeneralException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

public class PageRequestDTO {
    private static final int DEFAULT_SIZE = 8;
    private static final int MAX_SIZE = 50;

    private int page = 1;
    private int size = 8;
    private Sort.Direction direction = Direction.DESC;

    public void setPage(int page) {
        this.page = page <= 0 ? 1 : page;
    }

    public void setSize(int size) {
        this.size = size > MAX_SIZE ? DEFAULT_SIZE : size;
    }

    public void setDirection(Sort.Direction direction) {
        this.direction = direction;
    }

    public PageRequest of() {
        if (size <= 0) {
            throw new GeneralException(ErrorStatus.BAD_REQUEST);
        }
        return PageRequest.of(page - 1, size, direction, "createdAt");
    }

}
