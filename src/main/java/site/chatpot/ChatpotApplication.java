package site.chatpot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import site.chatpot.config.S3Properties;

@EnableJpaAuditing
@SpringBootApplication
@EnableConfigurationProperties(S3Properties.class)
public class ChatpotApplication {
    public static void main(String[] args) {
        SpringApplication.run(ChatpotApplication.class, args);
    }
}
