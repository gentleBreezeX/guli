package com.breeze.guli.service.edu.service;

import com.breeze.guli.service.edu.entity.Subject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.breeze.guli.service.edu.vo.SubjectVO;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author breeze
 * @since 2019-11-20
 */
public interface SubjectService extends IService<Subject> {

    void batchImport(InputStream inputStream) throws Exception;

    List<SubjectVO> nestedList();
}
