package com.Management.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Objects;

@Getter
@Setter
public class PaginationAndSortingDto {

    private Integer pageNo = 0;

    private Integer pageSize = 10;

    private Sort.Direction sortDir = Sort.Direction.ASC;

    private String sortByColumn ;

    public Pageable getPage(PaginationAndSortingDto dto){

        Integer page = Objects.nonNull(dto.getPageNo()) ? dto.getPageNo() : this.getPageNo();
        Integer size = Objects.nonNull(dto.getPageSize()) ? dto.getPageSize() : this.getPageSize();
        Sort.Direction sortDir = Objects.nonNull(dto.getSortDir())? dto.getSortDir() : this.getSortDir();
        String sortByColumn = Objects.nonNull(dto.getSortByColumn())? dto.getSortByColumn() : this.getSortByColumn();

        return PageRequest.of(page, size);
    }

}
