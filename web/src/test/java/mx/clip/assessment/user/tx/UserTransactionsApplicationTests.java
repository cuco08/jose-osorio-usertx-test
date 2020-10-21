package mx.clip.assessment.user.tx;

import com.fasterxml.jackson.databind.ObjectMapper;

import mx.clip.assessment.user.tx.api.model.AddUserTransactionRequest;
import mx.clip.assessment.user.tx.api.model.GetAllUserTransactionsResponse;
import mx.clip.assessment.user.tx.api.model.GetUserTransactionsReportResponse;
import mx.clip.assessment.user.tx.api.model.SumUpUserTransactionsResponse;
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
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserTransactionsApplicationTests {

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
		request.setDate("2019-12-31");

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

		String transactionId = allUserTransactionsResponse.getUserTransactions().get(0).getTransactionId();
		mockMvc.perform(get("/clip/v1/user/{userId}/transaction/{transactionId}", userId, transactionId)
				.contentType("application/json"))
				.andExpect(status().isOk());

		MvcResult result = mockMvc.perform(get("/clip/v1/user/{userId}/transaction", userId)
				.contentType("application/json"))
				.andExpect(status().isOk())
				.andReturn();
		GetAllUserTransactionsResponse response =
				objectMapper.readValue(result.getResponse().getContentAsString(), GetAllUserTransactionsResponse.class);
		assertThat(response).isNotNull();
		assertThat(response.getUserTransactions()).isNotEmpty();

		result = mockMvc.perform(get("/clip/v1/user/{userId}/transaction/sum", userId)
				.contentType("application/json"))
				.andExpect(status().isOk())
				.andReturn();
		SumUpUserTransactionsResponse sumResponse =
				objectMapper.readValue(result.getResponse().getContentAsString(), SumUpUserTransactionsResponse.class);
		assertThat(sumResponse).isNotNull();
		assertThat(sumResponse.getSum()).isEqualTo("88.5");

		result = mockMvc.perform(get("/clip/v1/user/{userId}/transaction/report", userId)
				.contentType("application/json"))
				.andExpect(status().isOk())
				.andReturn();
		GetUserTransactionsReportResponse reportResponse =
				objectMapper.readValue(result.getResponse().getContentAsString(), GetUserTransactionsReportResponse.class);
		assertThat(reportResponse).isNotNull();
		assertThat(reportResponse.getWeeklyReports()).isNotEmpty();
		assertThat(reportResponse.getWeeklyReports().get(0).getStartWeek()).isEqualTo("2019-12-27 Friday");
		assertThat(reportResponse.getWeeklyReports().get(0).getFinishWeek()).isEqualTo("2019-12-31 Tuesday");
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

	@Test
	public void testGetRandomTransaction() throws Exception {
		mockMvc.perform(get("/clip/v1/user/transaction/random")
				.contentType("application/json"))
				.andExpect(status().is5xxServerError());
	}
}
