package com.lre_server.common.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @ClassName: FileUploadProperties
 * @Author: niliqiang
 * @Date: 2021/2/11
 * @Description: TODO
 */
@Component
@Data
public class FileUploadProperties {
    @Value("${file.upload.path}")
    private String path;

    @Value("${file.download.url}")
    private String url;

}
