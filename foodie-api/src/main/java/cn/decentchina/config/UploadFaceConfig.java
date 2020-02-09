package cn.decentchina.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author jiangyu
 * @date 2020/1/30
 */
@Component
@PropertySource("classpath:upload-config.properties")
@ConfigurationProperties(prefix = "file")
@Data
public class UploadFaceConfig {

    /**
     * 上传路径
     */
    private String uploadUrl;
    /**
     * 查看图片路径
     */
    private String serverImgUrl;

}
