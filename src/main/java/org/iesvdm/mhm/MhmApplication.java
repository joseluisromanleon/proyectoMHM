package org.iesvdm.mhm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@ComponentScan(basePackages = "org.iesvdm.mhm.domain")
public class MhmApplication {

    public static void main(String[] args) {
        SpringApplication.run(MhmApplication.class, args);
    }

}
