package mx.clip.assessment.user.tx.dao;

import mx.clip.assessment.user.tx.dao.entities.UserTransaction;

import mx.clip.assessment.user.tx.dao.util.DaoTestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class UserTransactionRepositoryTest {

    @Autowired
    private UserTransactionRepository repository;

    @Test
    public void shouldFindAllUserTransactions() {
        Iterable<UserTransaction> userTxns = repository.findAll();

        assertThat(userTxns).isNotEmpty();
    }

    @Test
    public void shouldFindNewUserTransactionByUserId() {
        final UserTransaction tx = repository.save(DaoTestData.getUserTransaction(12.25, "Next tx #n"));

        assertThat(tx).isNotNull();
        assertThat(tx.getAmount()).isEqualTo(12.25);
        assertThat(tx.getDescription()).isEqualTo("Next tx #n");

        final String userId = tx.getUserId();
        final List<UserTransaction> userTxns = repository.findByUserId(userId);

        assertThat(userTxns).isNotEmpty();
        assertThat(userTxns.size()).isEqualTo(1);
        assertThat(userTxns.get(0).getTransactionId()).isEqualTo(tx.getTransactionId());
    }

    @Test
    public void shouldFindNewUserTransactionByTransactionId() {
        final UserTransaction tx = repository.save(DaoTestData.getUserTransaction(8.35, "Next tx #n"));

        assertThat(tx).isNotNull();
        assertThat(tx.getAmount()).isEqualTo(8.35);
        assertThat(tx.getDescription()).isEqualTo("Next tx #n");

        final String txnId = tx.getTransactionId();
        final List<UserTransaction> userTxns = repository.findByTransactionId(txnId);

        assertThat(userTxns).isNotEmpty();
        assertThat(userTxns.size()).isEqualTo(1);
        assertThat(userTxns.get(0).getUserId()).isEqualTo(tx.getUserId());
    }

    @Test
    public void shouldNotFindUserTransactionsByUserId() {

        assertThat(repository.findByUserId(UUID.randomUUID().toString())).isEmpty();
    }

    @Test
    public void shouldNotFindUserTransactionsByTransactionId() {

        assertThat(repository.findByTransactionId(UUID.randomUUID().toString())).isEmpty();
    }

    @Test
    public void shouldFindManyUserTransactionByUserId() {
        UserTransaction testUserTx = DaoTestData.getUserTransaction(8.35, "Next tx #n1");
        final UserTransaction savedUserTx = repository.save(testUserTx);

        testUserTx = DaoTestData.getUserTransaction(2.35, "Next tx #n2");
        testUserTx.setUserId(savedUserTx.getUserId());
        repository.save(testUserTx);

        testUserTx = DaoTestData.getUserTransaction(3.35, "Next tx #n3");
        testUserTx.setUserId(savedUserTx.getUserId());
        repository.save(testUserTx);

        testUserTx = DaoTestData.getUserTransaction(4.35, "Next tx #n4");
        testUserTx.setUserId(savedUserTx.getUserId());
        repository.save(testUserTx);

        final List<UserTransaction> userTxns = repository.findByUserId(savedUserTx.getUserId());

        assertThat(userTxns).isNotEmpty();
        assertThat(userTxns.size()).isEqualTo(4);
        assertThat(userTxns.get(0).getId()).isNotEqualTo(userTxns.get(1).getId());
        assertThat(userTxns.get(1).getId()).isNotEqualTo(userTxns.get(2).getId());
        assertThat(userTxns.get(2).getId()).isNotEqualTo(userTxns.get(3).getId());
    }
}
