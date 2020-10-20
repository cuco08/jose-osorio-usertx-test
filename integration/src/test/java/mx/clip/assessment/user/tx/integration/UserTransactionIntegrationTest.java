package mx.clip.assessment.user.tx.integration;

import com.fasterxml.jackson.databind.ObjectMapper;

import mx.clip.assessment.user.tx.api.model.AddUserTransactionRequest;
import mx.clip.assessment.user.tx.api.model.GetAllUserTransactionsResponse;
import mx.clip.assessment.user.tx.api.model.UserIdentifierRequest;
import mx.clip.assessment.user.tx.service.UserTransactionService;
import mx.clip.assessment.user.tx.service.builder.UserIdentifierRequestBuilder;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserTransactionIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserTransactionService userTransactionsApi;

    @Test
    public void testAddUserTransactions() throws Exception {
        AddUserTransactionRequest request = new AddUserTransactionRequest();
        String userId = "000";
        request.setUserId(userId);
        request.setAmount(88.50);
        request.setDescription("Dummy");
        request.setDate("2020-10-19");

        mockMvc.perform(post("/clip/v1/user/transaction")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        UserIdentifierRequest userIdentifierRequest = new UserIdentifierRequestBuilder().withUserId(userId).build();

        GetAllUserTransactionsResponse allUserTransactionsResponse =
                userTransactionsApi.getAllUserTransactions(userIdentifierRequest);

        assertThat(allUserTransactionsResponse).isNotNull();
        assertThat(allUserTransactionsResponse.getUserTransactions()).isNotEmpty();
        assertThat(allUserTransactionsResponse.getUserTransactions().size()).isEqualTo(1);
    }

    @Test
    public void testAddUserTransactions_BadRequest() throws Exception {
        AddUserTransactionRequest request = new AddUserTransactionRequest();
        String userId = "000";
        request.setUserId(userId);
        request.setAmount(88.50);
        request.setDescription("Dummy");
        request.setDate("20201009");

        mockMvc.perform(post("/clip/v1/user/transaction")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}
