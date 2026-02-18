package ctw.screenscoreapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class ScreenScoreApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScreenScoreApiApplication.class, args);
    }

}
