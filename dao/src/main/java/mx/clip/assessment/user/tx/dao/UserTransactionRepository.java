package mx.clip.assessment.user.tx.dao;

import mx.clip.assessment.user.tx.dao.entities.UserTransaction;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserTransactionRepository extends CrudRepository<UserTransaction, Long> {

    List<UserTransaction> findByUserId(String userId);

    List<UserTransaction> findByTransactionId(String transactionId);
}
