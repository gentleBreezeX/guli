package com.breeze.guli.service.oss.controller;

import com.breeze.guli.common.base.result.R;
import com.breeze.guli.common.base.result.ResultCodeEnum;
import com.breeze.guli.common.base.util.ExceptionUtils;
import com.breeze.guli.service.base.exception.OnlineEduException;
import com.breeze.guli.service.oss.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author breeze
 * @date 2019/11/25
 */
@Api(tags = "阿里云文件管理")
@CrossOrigin //跨域
@RestController
@RequestMapping("/admin/oss/file")
@Slf4j
public class FileController {

    @Autowired
    private FileService fileService;

    @ApiOperation(value = "文件上传")
    @PostMapping("upload")
    public R upload(
            @ApiParam(name = "module", value = "模块名-文件夹名", required = true)
            @RequestParam("module") String module,

            @ApiParam(name = "file", value = "上传的文件", required = true)
            @RequestParam("file") MultipartFile file){

        try {
            InputStream inputStream = file.getInputStream();
            String originalFilename = file.getOriginalFilename();

            String uploadUrl = this.fileService.upload(inputStream, module, originalFilename);
            return R.ok().message("文件上传成功").data("url", uploadUrl);
        } catch (Exception e) {
            log.error(ExceptionUtils.getMessage(e));
            throw new OnlineEduException(ResultCodeEnum.FILE_UPLOAD_ERROR);
        }
    }

    @ApiOperation(value = "文件删除")
    @DeleteMapping("remove")
    public R removeFile(
            @ApiParam(name = "url", value = "要删除的文件路径", required = true)
            @RequestBody String url) {

        try {
            fileService.removeFile(url);
            return R.ok().message("文件刪除成功");
        } catch (Exception e) {
            //添加异常跟踪信息
            log.error(ExceptionUtils.getMessage(e));
            throw new OnlineEduException(ResultCodeEnum.FILE_DELETE_ERROR);
        }
    }

}
