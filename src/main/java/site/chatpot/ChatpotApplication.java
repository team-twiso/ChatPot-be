package site.chatpot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ChatpotApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatpotApplication.class, args);
    }

}
