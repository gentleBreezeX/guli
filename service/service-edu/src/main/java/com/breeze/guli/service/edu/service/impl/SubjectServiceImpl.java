package com.breeze.guli.service.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.breeze.guli.common.base.util.ExcelImportUtil;
import com.breeze.guli.service.edu.entity.Subject;
import com.breeze.guli.service.edu.mapper.SubjectMapper;
import com.breeze.guli.service.edu.service.SubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breeze.guli.service.edu.vo.SubjectVO;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.management.Query;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author breeze
 * @since 2019-11-20
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void batchImport(InputStream inputStream) throws Exception {

        //创建工具类对象
        ExcelImportUtil excel = new ExcelImportUtil(inputStream);

        //获取工作表
        HSSFSheet sheet = excel.getSheet();

        for (Row rowData : sheet) {
            //标题行
            if (rowData.getRowNum() == 0) {
                continue;
            }

            //获取一级分类
            Cell levelOneCell = rowData.getCell(0);
            String levelOneValue = excel.getCellValue(levelOneCell).trim();
            //表格不能为null，里面数据不能为空串
            if (levelOneCell == null || StringUtils.isEmpty(levelOneValue)) {
                continue;
            }

            //获取二级分类
            Cell levelTwoCell = rowData.getCell(1);
            String levelTwoValue = excel.getCellValue(levelTwoCell).trim();
            //表格不能为null，里面数据不能为空串
            if (levelTwoCell == null || StringUtils.isEmpty(levelTwoValue)) {
                continue;
            }

            //判断一级分类是否重复
            Subject subjectOne = this.getByTitle(levelOneValue);
            //父id
            String parentId = null;
            //不重复则添加
            if (subjectOne == null) {
                //将一级分裂存入数据库
                Subject subjectLevelOne = new Subject();
                subjectLevelOne.setTitle(levelOneValue);
                this.baseMapper.insert(subjectLevelOne);
                parentId = subjectLevelOne.getId();
            } else {
                parentId = subjectOne.getId();
            }

            //判断二级分类是否重复
            Subject subjectTwo = this.getSubByTitle(levelTwoValue, parentId);
            //不重复则添加
            if (subjectTwo == null) {
                //将一级分裂存入数据库
                Subject subjectLevelTwo = new Subject();
                subjectLevelTwo.setTitle(levelTwoValue);
                subjectLevelTwo.setParentId(parentId);

                this.baseMapper.insert(subjectLevelTwo);
            }

        }

    }

    @Override
    public List<SubjectVO> nestedList() {

        //获取所有的分类数据，(减少数据库查询)
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("sort", "id");
        List<Subject> subjects = this.baseMapper.selectList(wrapper);

        //创建返回结果集合
        List<SubjectVO> subjectVOList = new ArrayList<>();
        //创建二级菜单列表
        List<SubjectVO> levelTwo = new ArrayList<>();

        //遍历所有的分类数据
        for (Subject subject : subjects) {
            SubjectVO subjectVO = new SubjectVO();
            //判断是否是父类菜单
            if ("0".equals(subject.getParentId())) {
                BeanUtils.copyProperties(subject, subjectVO);
                subjectVOList.add(subjectVO);
            } else {
                //不是父菜单存储在二级菜单集合中
                BeanUtils.copyProperties(subject, subjectVO);
                levelTwo.add(subjectVO);
            }
        }

        //遍历二级菜单集合
        subjectVOList = subjectVOList.stream().map(subjectVo -> {

            //一级菜单的id及父id
            String parentId = subjectVo.getId();
            List<SubjectVO> children = new ArrayList<>();

            for (SubjectVO subject : levelTwo) {
                SubjectVO subjectVO = new SubjectVO();
                if (parentId.equals(subject.getParentId())) {
                    BeanUtils.copyProperties(subject, subjectVO);
                    children.add(subjectVO);
                }
            }
            subjectVo.setChildren(children);
            return subjectVo;
        }).collect(Collectors.toList());

        return subjectVOList;
    }

    /**
     * 根据分类名查询这一级别分类是否存在
     * @param title
     * @return
     */
    private Subject getByTitle(String title) {
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", title);
        wrapper.eq("parent_id", "0");

        return this.baseMapper.selectOne(wrapper);
    }

    /**
     * 根据分类名和父Id查询这一级别分类是否存在
     * @param title
     * @param parentId
     * @return
     */
    private Subject getSubByTitle(String title, String parentId) {
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", title);
        wrapper.eq("parent_id", parentId);

        return this.baseMapper.selectOne(wrapper);
    }
}
