package org.example.phonelocatebackend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@MapperScan("org.example.phonelocatebackend.mapper")
public class PhonelocateBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(PhonelocateBackendApplication.class, args);
    }

}
