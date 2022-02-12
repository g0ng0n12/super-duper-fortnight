package com.playtomic.tests.wallet;

import com.playtomic.tests.wallet.model.Wallet;
import com.playtomic.tests.wallet.repository.WalletRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

@SpringBootApplication
public class WalletApplication {

	public static void main(String[] args) {
		SpringApplication.run(WalletApplication.class, args);
	}

	@Bean
	public CommandLineRunner demoData(WalletRepository walletRepository) {
		return args -> {
			Wallet wallet = new Wallet();
			wallet.setBalance(new BigDecimal(100.0));

			walletRepository.save(wallet);
		};

	}
}
