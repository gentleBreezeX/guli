package com.breeze.guli.service.edu.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author breeze
 * @date 2019/11/26
 */
@Data
public class SubjectVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String parentId;

    private String title;

    private Integer sort;

    private List<SubjectVO> children = new ArrayList<>();
}
