package com.breeze.guli.service.edu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breeze.guli.service.edu.entity.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.breeze.guli.service.edu.vo.TeacherQueryVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author breeze
 * @since 2019-11-20
 */
public interface TeacherService extends IService<Teacher> {

    /**
     * 分页查询教师信息
     * @param teacherPage
     * @param teacherQueryVo
     * @return
     */
    IPage<Teacher> selectPage(Page<Teacher> teacherPage, TeacherQueryVo teacherQueryVo);

    List<Map<String, Object>> selectNameListByKey(String key);
}
