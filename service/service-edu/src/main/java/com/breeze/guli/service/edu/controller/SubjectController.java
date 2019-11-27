package com.breeze.guli.service.edu.controller;


import com.breeze.guli.common.base.result.R;
import com.breeze.guli.common.base.result.ResultCodeEnum;
import com.breeze.guli.common.base.util.ExceptionUtils;
import com.breeze.guli.service.base.exception.OnlineEduException;
import com.breeze.guli.service.edu.service.SubjectService;
import com.breeze.guli.service.edu.vo.SubjectVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author breeze
 * @since 2019-11-20
 */
@CrossOrigin
@Api(tags="课程类别管理")
@RestController
@RequestMapping("/admin/edu/subject")
@Slf4j
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @ApiOperation(value= "Excel批量导入课程类别数据")
    @PostMapping("import")
    public R batchImport(
            @ApiParam(name = "file", value = "excel文件", required = true)
            @RequestParam("file") MultipartFile file){

        try {
            InputStream inputStream = file.getInputStream();
            this.subjectService.batchImport(inputStream);
            return R.ok().message("批量导入成功");
        } catch (Exception e) {
            log.error(ExceptionUtils.getMessage(e));
            throw new OnlineEduException(ResultCodeEnum.EXCEL_DATA_IMPORT_ERROR);
        }
    }

    @ApiOperation("嵌套数据列表-课程类别菜单tree")
    @GetMapping("nested-list")
    public R nestedList() {
        List<SubjectVO> subjectVOS = this.subjectService.nestedList();
        return R.ok().data("items", subjectVOS);
    }
}

