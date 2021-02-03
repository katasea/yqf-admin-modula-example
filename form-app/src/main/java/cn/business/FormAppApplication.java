package cn.business;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@ServletComponentScan
@EnableSwagger2
@MapperScan(basePackages = "cn.business.main.mapper")
@EnableTransactionManagement
public class FormAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(FormAppApplication.class, args);
	}

}
