package ro.gcloud.mycinema;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MycinemaApplication {

    public static void main(String[] args) {
        SpringApplication.run(MycinemaApplication.class, args);
    }

}
