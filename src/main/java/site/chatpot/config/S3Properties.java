package site.chatpot.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "spring.cloud.aws.s3")
@Setter
public class S3Properties {
    private String bucket;
    private String folder;
}

