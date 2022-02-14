package com.playtomic.tests.wallet.integration;

import com.playtomic.tests.wallet.api.requests.WalletTopUpHttpRequest;
import com.playtomic.tests.wallet.api.responses.WalletHttpResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class WalletApplicationIT extends BaseApiApplication {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	private final RestTemplate patchRestTemplate  = restTemplate();



	@Test
	public void walletHappyPath() throws Exception {

		String hostUrl = "http://localhost:" + port + "/api/v1/wallets/";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);


		// Creating a wallet
		ResponseEntity<WalletHttpResponse> createWalletResponse =
				this.restTemplate.postForEntity(hostUrl,null, WalletHttpResponse.class);

		assertThat(createWalletResponse.getStatusCode(), equalTo(HttpStatus.OK));
		WalletHttpResponse createWalletHttpResponse = createWalletResponse.getBody();

		// TopUp a Wallet with 100

		String topUpJsonBodyRequest = "{\"creditCardNumber\":\"123456789\",\"amount\":100}";
		HttpEntity httpTopUpRequestEntity = new HttpEntity(topUpJsonBodyRequest, headers);


		ResponseEntity topUpWalletResponse =
				this.patchRestTemplate.exchange(hostUrl + "1" + "/topup", HttpMethod.PATCH, httpTopUpRequestEntity, WalletHttpResponse.class);

		assertThat(topUpWalletResponse, notNullValue());
		WalletHttpResponse topUpWalletHttpResponseBody = (WalletHttpResponse) topUpWalletResponse.getBody();
		assertThat(topUpWalletHttpResponseBody, notNullValue());
		assertThat(topUpWalletHttpResponseBody.getBalance(), notNullValue());
		assertEquals(100,topUpWalletHttpResponseBody.getBalance().intValue());

		// Spend Test
		String spendJsonBodyRequest = "{\n" +
				"    \"type\": \"SPEND\",\n" +
				"    \"amount\": 20,\n" +
				"    \"product\": \"renting\"\n" +
				"}";

		HttpEntity httpSpendRequestEntity = new HttpEntity(spendJsonBodyRequest, headers);

		ResponseEntity spendUpWalletResponse =
				this.patchRestTemplate.exchange(hostUrl + "1" + "/transactions", HttpMethod.PATCH, httpSpendRequestEntity, WalletHttpResponse.class);

		assertThat(spendUpWalletResponse, notNullValue());
		WalletHttpResponse spendUpWalletResponseBody = (WalletHttpResponse) spendUpWalletResponse.getBody();

		assertThat(spendUpWalletResponseBody, notNullValue());
		assertThat(spendUpWalletResponseBody.getBalance(), notNullValue());
		assertEquals(80,spendUpWalletResponseBody.getBalance().intValue());


		// Getting a wallet with 1 Transaction that is the TopUP Transaction
		ResponseEntity<WalletHttpResponse> getWalletResponse =
				this.restTemplate.getForEntity(hostUrl + topUpWalletHttpResponseBody.getId(), WalletHttpResponse.class);

		assertThat(getWalletResponse.getStatusCode(), equalTo(HttpStatus.OK));
		WalletHttpResponse getWalletHttpResponseBody = getWalletResponse.getBody();
		assertEquals(2,getWalletHttpResponseBody.getTransactions().size());

	}

}

