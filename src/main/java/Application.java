import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/***
 *@Author : liuyitao
 *@CreateDate :9:44 PM 1/28/2018
 *@DESC :springboot main class
 *
 *
 *@Modify:
 ***/

@MapperScan(basePackages = "com.liuyitao.dao")
@SpringBootApplication(scanBasePackages ="com.liuyitao")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }

}
