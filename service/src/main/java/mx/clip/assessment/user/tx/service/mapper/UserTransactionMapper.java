package mx.clip.assessment.user.tx.service.mapper;

import mx.clip.assessment.user.tx.api.model.UserTransaction;
import org.springframework.beans.BeanUtils;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class UserTransactionMapper {

    private SimpleDateFormat simpleDateFormat;


    public UserTransactionMapper(String dateFormatPattern) {
        simpleDateFormat = new SimpleDateFormat(dateFormatPattern);
    }

    public UserTransaction map(mx.clip.assessment.user.tx.dao.entities.UserTransaction userTransactionEntity) {
        final UserTransaction userTransaction = new UserTransaction();

        BeanUtils.copyProperties(userTransactionEntity, userTransaction);

        final LocalDateTime localDateTime = userTransactionEntity.getDate();

        String dateStr = simpleDateFormat.format(Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()));
        userTransaction.setDate(dateStr);

        return userTransaction;
    }
}
