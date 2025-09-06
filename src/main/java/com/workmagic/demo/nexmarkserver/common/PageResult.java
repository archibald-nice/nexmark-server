package com.workmagic.demo.nexmarkserver.common;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PageResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long current;

    private Long size;

    private Long total;

    private Long pages;

    private List<T> records;

    public PageResult() {}

    public PageResult(Long current, Long size, Long total, Long pages, List<T> records) {
        this.current = current;
        this.size = size;
        this.total = total;
        this.pages = pages;
        this.records = records;
    }

    public static <T> PageResult<T> build(IPage<T> page) {
        return new PageResult<>(
            page.getCurrent(),
            page.getSize(),
            page.getTotal(),
            page.getPages(),
            page.getRecords()
        );
    }

}