package com.example.demo;


import io.r2dbc.spi.ConnectionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.connection.R2dbcTransactionManager;
import org.springframework.transaction.ReactiveTransactionManager;
import org.springframework.transaction.reactive.TransactionalOperator;


@SpringBootApplication(scanBasePackages = { "com.example.demo"})
public class DemoApplication {

	@Bean
	TransactionalOperator transactionalOperator(ReactiveTransactionManager rtm) {
		return TransactionalOperator.create(rtm);
	}

	@Bean
	ReactiveTransactionManager r2dbcTransactionManager(ConnectionFactory cf) {
		return new R2dbcTransactionManager(cf);
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}


