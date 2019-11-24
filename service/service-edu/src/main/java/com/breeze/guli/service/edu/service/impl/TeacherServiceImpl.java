package com.breeze.guli.service.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breeze.guli.service.edu.entity.Teacher;
import com.breeze.guli.service.edu.mapper.TeacherMapper;
import com.breeze.guli.service.edu.service.TeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breeze.guli.service.edu.vo.TeacherQueryVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author breeze
 * @since 2019-11-20
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {

    @Override
    public IPage<Teacher> selectPage(Page<Teacher> teacherPage, TeacherQueryVo teacherQueryVo) {

        QueryWrapper<Teacher> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("sort");

        if (teacherQueryVo == null) {
            return this.baseMapper.selectPage(teacherPage, wrapper);
        }

        String joinDateBegin = teacherQueryVo.getJoinDateBegin();
        String joinDateEnd = teacherQueryVo.getJoinDateEnd();
        Integer level = teacherQueryVo.getLevel();
        String name = teacherQueryVo.getName();

        if (level != null) {
            wrapper.eq("level", level);
        }

        if (!StringUtils.isEmpty(name)) {
            wrapper.eq("name", name);
        }

        if (StringUtils.isNotBlank(joinDateBegin)) {
            wrapper.ge("join_date", joinDateBegin);
        }

        if (StringUtils.isNotBlank(joinDateEnd)) {
            wrapper.le("join_date", joinDateEnd);
        }

        return this.baseMapper.selectPage(teacherPage, wrapper);
    }

    @Override
    public List<Map<String, Object>> selectNameListByKey(String key) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("name");
        queryWrapper.likeRight("name", key);

        List<Map<String, Object>> list = baseMapper.selectMaps(queryWrapper);//返回值是Map列表
        return list;
    }
}
