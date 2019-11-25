package com.breeze.guli.service.oss.service;


import java.io.InputStream;

/**
 * @author breeze
 * @date 2019/11/25
 */
public interface FileService {

    String upload(InputStream inputStream, String module, String originalFilename);

    void removeFile(String url);
}
