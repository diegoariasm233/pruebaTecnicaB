package co.com.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class PricesApplication {

    public static void main(String[] args) {
        SpringApplication.run(PricesApplication.class, args);
    }

}
