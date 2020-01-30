package cn.decentchina;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author jiangyu
 * @date 2020/1/7
 */
@SpringBootApplication
@MapperScan(basePackages = "cn.decentchina.mapper")
@ComponentScan(basePackages = {"cn.decentchina", "org.n3r.idworker"})
@EnableScheduling
public class ApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }
}
