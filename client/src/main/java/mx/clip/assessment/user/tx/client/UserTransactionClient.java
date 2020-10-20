package mx.clip.assessment.user.tx.client;

import lombok.extern.slf4j.Slf4j;

import mx.clip.assessment.user.tx.api.UserTransactionsApi;
import mx.clip.assessment.user.tx.api.model.AddUserTransactionRequest;
import mx.clip.assessment.user.tx.api.model.GetAllUserTransactionsResponse;
import mx.clip.assessment.user.tx.api.model.GetUserTransactionRequest;
import mx.clip.assessment.user.tx.api.model.GetUserTransactionsReportResponse;
import mx.clip.assessment.user.tx.api.model.SumUpUserTransactionsResponse;
import mx.clip.assessment.user.tx.api.model.UserIdentifierRequest;
import mx.clip.assessment.user.tx.api.model.UserTransactionResponse;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;

import java.net.URI;

@Slf4j
public class UserTransactionClient extends AbstractClient implements UserTransactionsApi {

    private String httpUrl;

    public UserTransactionClient(HttpClient httpClient, URI endPoint) {
        super(httpClient);
        this.httpUrl = configureHttpUrl(endPoint).get();
    }

    @Override
    public UserTransactionResponse addUserTransaction(AddUserTransactionRequest request) {
        HttpPost httpRequest = new HttpPost(this.httpUrl + "/user/transaction");

        return executeRequest(httpRequest, UserTransactionResponse.class, request);
    }

    @Override
    public UserTransactionResponse getUserTransaction(GetUserTransactionRequest request) {
        HttpGet httpRequest = new HttpGet(this.httpUrl
                + "/user/"
                + request.getUserId()
                + "/transaction/"
                + request.getTransactionId());

        return executeRequest(httpRequest, UserTransactionResponse.class);
    }

    @Override
    public GetAllUserTransactionsResponse getAllUserTransactions(UserIdentifierRequest request) {
        HttpGet httpRequest = new HttpGet(this.httpUrl
                + "/user/"
                + request.getUserId()
                + "/transaction");

        return executeRequest(httpRequest, GetAllUserTransactionsResponse.class);
    }

    @Override
    public SumUpUserTransactionsResponse sumUpUserTransactions(UserIdentifierRequest request) {
        HttpGet httpRequest = new HttpGet(this.httpUrl
                + "/user/"
                + request.getUserId()
                + "/transaction/sum");

        return executeRequest(httpRequest, SumUpUserTransactionsResponse.class);
    }

    @Override
    public GetUserTransactionsReportResponse getUserTransactionsReport(UserIdentifierRequest request) {
        HttpGet httpRequest = new HttpGet(this.httpUrl
                + "/user/"
                + request.getUserId()
                + "/transaction/report");

        return executeRequest(httpRequest, GetUserTransactionsReportResponse.class);
    }

    @Override
    public UserTransactionResponse getRandomUserTransaction() {
        HttpGet httpRequest = new HttpGet(this.httpUrl + "/user/transaction/random");

        return executeRequest(httpRequest, UserTransactionResponse.class);
    }

    @Override
    public String getBasePath() {
        return "/clip/v1";
    }
}
