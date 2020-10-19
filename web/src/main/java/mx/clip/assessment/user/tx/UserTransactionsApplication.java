package mx.clip.assessment.user.tx;

import mx.clip.assessment.user.tx.dao.UserTransactionRepository;
import mx.clip.assessment.user.tx.data.UserTransactionData;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class UserTransactionsApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserTransactionsApplication.class, args);
	}

    @Bean
    public CommandLineRunner commandLineRunner(UserTransactionRepository repository) {
        return args -> {

			repository.save(UserTransactionData.createUserTransaction("01",
					LocalDateTime.of(2019, 12, 9, 12, 00)));
			repository.save(UserTransactionData.createUserTransaction("01",
					LocalDateTime.of(2019, 11, 29, 13, 00)));
			repository.save(UserTransactionData.createUserTransaction("01",
					LocalDateTime.of(2019, 11, 29, 12, 00)));
			repository.save(UserTransactionData.createUserTransaction("01",
					LocalDateTime.of(2019, 12, 5, 11, 00)));
			repository.save(UserTransactionData.createUserTransaction("01",
					LocalDateTime.of(2019, 12, 5, 12, 00)));
			repository.save(UserTransactionData.createUserTransaction("01",
					LocalDateTime.of(2019, 11, 19, 12, 00)));
			repository.save(UserTransactionData.createUserTransaction("01",
					LocalDateTime.of(2019, 11, 10, 12, 00)));
			repository.save(UserTransactionData.createUserTransaction("01",
					LocalDateTime.of(2019, 11, 8, 12, 00)));
			repository.save(UserTransactionData.createUserTransaction("01",
					LocalDateTime.of(2019, 12, 1, 12, 00)));
			repository.save(UserTransactionData.createUserTransaction("01",
					LocalDateTime.of(2019, 12, 1, 13, 00)));
			repository.save(UserTransactionData.createUserTransaction("01",
					LocalDateTime.of(2019, 12, 2, 12, 00)));
			repository.save(UserTransactionData.createUserTransaction("01",
					LocalDateTime.of(2019, 11, 24, 12, 00)));
			repository.save(UserTransactionData.createUserTransaction("01",
					LocalDateTime.of(2019, 11, 24, 13, 00)));
			repository.save(UserTransactionData.createUserTransaction("01",
					LocalDateTime.of(2019, 12, 6, 12, 00)));
			repository.save(UserTransactionData.createUserTransaction("01",
					LocalDateTime.of(2019, 11, 26, 12, 00)));
			repository.save(UserTransactionData.createUserTransaction("01",
					LocalDateTime.of(2019, 11, 28, 12, 00)));
			repository.save(UserTransactionData.createUserTransaction("01",
					LocalDateTime.of(2019, 12, 5, 13, 00)));
			repository.save(UserTransactionData.createUserTransaction("01",
					LocalDateTime.of(2019, 12, 6, 13, 00)));
			repository.save(UserTransactionData.createUserTransaction("01",
					LocalDateTime.of(2019, 11, 30, 12, 00)));
        };
    }
}
