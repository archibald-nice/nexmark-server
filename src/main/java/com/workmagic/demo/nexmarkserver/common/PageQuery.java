package com.workmagic.demo.nexmarkserver.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class PageQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long current = 1L;

    private Long size = 10L;

    public PageQuery() {}

    public PageQuery(Long current, Long size) {
        this.current = current;
        this.size = size;
    }

}