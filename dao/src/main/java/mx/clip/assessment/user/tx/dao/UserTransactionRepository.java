package mx.clip.assessment.user.tx.dao;

import mx.clip.assessment.user.tx.dao.entities.UserTransaction;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTransactionRepository extends CrudRepository<UserTransaction, Long> {

    List<UserTransaction> findByUserId(String userId);

    List<UserTransaction> findByTransactionId(String transactionId);
}
