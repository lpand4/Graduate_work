package ru.pugovkinv.commissioningofventilationsystems;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@EntityScan("ru.pugovkinv.commissioningofventilationsystems")
public class CommissioningOfVentilationSystemsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommissioningOfVentilationSystemsApplication.class, args);
	}

}
