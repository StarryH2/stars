package cn.hewei.stars;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "cn.hewei.stars.mapper")
public class StarsApplication {

    public static void main(String[] args) {
        SpringApplication.run(StarsApplication.class, args);
    }

}
