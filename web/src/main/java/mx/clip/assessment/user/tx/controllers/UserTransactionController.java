package mx.clip.assessment.user.tx.controllers;

import io.swagger.annotations.ApiParam;

import mx.clip.assessment.user.tx.api.model.AddUserTransactionRequest;
import mx.clip.assessment.user.tx.api.model.GetAllUserTransactionsResponse;
import mx.clip.assessment.user.tx.api.model.GetUserTransactionRequest;
import mx.clip.assessment.user.tx.api.model.GetUserTransactionsReportResponse;
import mx.clip.assessment.user.tx.api.model.SumUpUserTransactionsResponse;
import mx.clip.assessment.user.tx.api.model.UserIdentifierRequest;
import mx.clip.assessment.user.tx.api.model.UserTransactionResponse;
import mx.clip.assessment.user.tx.service.UserTransactionService;

import mx.clip.assessment.user.tx.service.builder.GetUserTransactionRequestBuilder;
import mx.clip.assessment.user.tx.service.builder.UserIdentifierRequestBuilder;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(
        value = {"clip/v1"},
        produces = {"application/json"},
        consumes = {"application/json"}
)
public class UserTransactionController {

    private UserTransactionService userTransactionService;

    public UserTransactionController(UserTransactionService userTransactionService) {
        this.userTransactionService = userTransactionService;
    }

    @RequestMapping(
        value = {"/user/transaction"},
        method = {RequestMethod.POST}
    )
    public ResponseEntity<UserTransactionResponse> addTransaction(@ApiParam(required = true)
                                                                  @Valid
                                                                  @RequestBody AddUserTransactionRequest request) {
        return new ResponseEntity<>(userTransactionService.addUserTransaction(request),
                HttpStatus.OK);
    }

    @RequestMapping(
        value = {"/user/{userId}/transaction/{transactionId}"},
        method = {RequestMethod.GET}
    )
    public ResponseEntity<UserTransactionResponse> getTransaction(@PathVariable("userId") String userId,
                                                                  @PathVariable("transactionId") String transactionId) {
        final GetUserTransactionRequest request = new GetUserTransactionRequestBuilder()
                .withUserId(userId)
                .withTransactionId(transactionId)
                .build();

        return new ResponseEntity<>(userTransactionService.getUserTransaction(request),
                HttpStatus.OK);
    }

    @RequestMapping(
        value = {"/user/{userId}/transaction"},
        method = {RequestMethod.GET}
    )
    public ResponseEntity<GetAllUserTransactionsResponse> getAllTransactions(@PathVariable("userId") String userId) {
        final UserIdentifierRequest request = new UserIdentifierRequestBuilder()
                .withUserId(userId)
                .build();

        return new ResponseEntity<>(userTransactionService.getAllUserTransactions(request),
                HttpStatus.OK);
    }

    @RequestMapping(
        value = {"/user/{userId}/transaction/sum"},
        method = {RequestMethod.GET}
    )
    public ResponseEntity<SumUpUserTransactionsResponse> sumAllTransactions(@PathVariable("userId") String userId) {
        final UserIdentifierRequest request = new UserIdentifierRequestBuilder()
                .withUserId(userId)
                .build();

        return new ResponseEntity<>(userTransactionService.sumUpUserTransactions(request),
                HttpStatus.OK);
    }

    @RequestMapping(
            value = {"/user/{userId}/transaction/report"},
            method = {RequestMethod.GET}
    )
    public ResponseEntity<GetUserTransactionsReportResponse> getTransactionsReport(@PathVariable("userId") String userId) {
        final UserIdentifierRequest request = new UserIdentifierRequestBuilder()
                .withUserId(userId)
                .build();

        return new ResponseEntity<>(userTransactionService.getUserTransactionsReport(request),
                HttpStatus.OK);
    }
}
