package com.breeze.guli.service.edu.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breeze.guli.common.base.result.R;
import com.breeze.guli.service.edu.entity.Teacher;
import com.breeze.guli.service.edu.service.TeacherService;
import com.breeze.guli.service.edu.vo.TeacherQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author breeze
 * @since 2019-11-20
 */
@CrossOrigin
@Api(tags = "讲师管理")
@RestController
@RequestMapping("/admin/edu/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @ApiOperation(value="所有讲师列表")
    @GetMapping("list")
    public R listAll(){
        List<Teacher> list = teacherService.list(null);
        return R.ok().data("items", list).message("获取讲师列表成功");
    }

    @ApiOperation(value = "根据左关键字查询讲师名列表")
    @GetMapping("list/name/{key}")
    public R selectNameListByKey(
            @ApiParam(name = "key", value = "查询关键字", required = true)
            @PathVariable String key){

        List<Map<String, Object>> nameList = teacherService.selectNameListByKey(key);

        return R.ok().data("nameList", nameList);
    }

    @ApiOperation(value = "根据ids删除讲师")
    @DeleteMapping("remove/ids")
    public R removeByIds(
            @ApiParam(name = "ids", value = "讲师id集合", required = true)
            @RequestBody List<String> ids){
        boolean result = this.teacherService.removeByIds(ids);
        return R.ok().message("删除讲师成功");
    }

    @ApiOperation(value = "根据ID删除讲师", notes = "根据ID删除讲师")
    @DeleteMapping("remove/{id}")
    public R removeById(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id){
        this.teacherService.removeById(id);
        return R.ok().message("删除讲师成功");
    }

    @ApiOperation(value = "分页讲师列表")
    @GetMapping("{page}/{limit}")
    public R index(
            @ApiParam(name = "page", value = "当前页", required = true)
            @PathVariable("page")Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable("limit")Long limit,

            @ApiParam(name = "teacherQueryVo", value = "查询条件对象")
            TeacherQueryVo teacherQueryVo){

        Page<Teacher> teacherPage = new Page<>(page, limit);
        IPage<Teacher> pageModel = this.teacherService.selectPage(teacherPage, teacherQueryVo);

        List<Teacher> records = pageModel.getRecords();
        long total = pageModel.getTotal();

        return R.ok().data("total", total).data("rows", records);
    }

    @ApiOperation("新增讲师")
    @PostMapping("save")
    public R save(
            @ApiParam(name = "teacher", value = "讲师对象", required = true)
            @RequestBody Teacher teacher){

        boolean b = this.teacherService.save(teacher);

        return R.ok();
    }

    @ApiOperation("根据id获取讲师")
    @GetMapping("get/{id}")
    public R get(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable("id")String id){
        Teacher teacher = this.teacherService.getById(id);
        return R.ok().data("items", teacher);
    }

    @ApiOperation(value = "修改讲师")
    @PutMapping("update")
    public R updateById(
            @ApiParam(name = "teacher", value = "讲师对象", required = true)
            @RequestBody Teacher teacher) {
        teacherService.updateById(teacher);
        return R.ok();
    }
}

