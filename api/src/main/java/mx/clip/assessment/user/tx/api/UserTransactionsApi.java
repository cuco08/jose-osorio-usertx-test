package mx.clip.assessment.user.tx.api;

import mx.clip.assessment.user.tx.api.model.AddUserTransactionRequest;
import mx.clip.assessment.user.tx.api.model.GetAllUserTransactionsResponse;
import mx.clip.assessment.user.tx.api.model.GetUserTransactionRequest;
import mx.clip.assessment.user.tx.api.model.SumUpUserTransactionsResponse;
import mx.clip.assessment.user.tx.api.model.UserIdentifierRequest;
import mx.clip.assessment.user.tx.api.model.UserTransactionResponse;
import mx.clip.assessment.user.tx.api.model.GetUserTransactionsReportResponse;

public interface UserTransactionsApi {

    UserTransactionResponse addUserTransaction(AddUserTransactionRequest request);

    UserTransactionResponse getUserTransaction(GetUserTransactionRequest request);

    GetAllUserTransactionsResponse getAllUserTransactions(UserIdentifierRequest request);

    SumUpUserTransactionsResponse sumUpUserTransactions(UserIdentifierRequest request);

    GetUserTransactionsReportResponse getUserTransactionsReport(UserIdentifierRequest request);

    UserTransactionResponse getRandomUserTransaction();
}
