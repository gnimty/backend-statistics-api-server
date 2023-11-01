package onlysolorank.apiserver;

import java.util.TimeZone;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class ApiServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiServerApplication.class, args);
    }

    @PostConstruct
    void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
    }

}
