package mx.clip.assessment.user.tx.dao;

import mx.clip.assessment.user.tx.dao.util.DaoTestData;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class UserTransactionRepositoryApplicationTest {

    public static void main(String[] args) {
        SpringApplication.run(UserTransactionRepositoryApplicationTest.class, args);
    }

    @Bean
    public CommandLineRunner demo(UserTransactionRepository repository) {
        return (args) -> {
            repository.save(DaoTestData.getUserTransaction(23.50, "Dummy tx #1"));
            repository.save(DaoTestData.getUserTransaction(15.45, "Dummy tx #2"));
        };
    }
}
