package ru.mkonovalov.jurdoc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.mkonovalov.jurdoc.payload.Test;

@EnableJpaRepositories
@SpringBootApplication
public class JurdocApplication {

    public static void main(String[] args) {
        SpringApplication.run(JurdocApplication.class, args);

        Test test = new Test();

        test = test.id(1L)
                .name("test name");


    }

}
