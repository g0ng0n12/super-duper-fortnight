package com.playtomic.tests.wallet.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


public class WalletApplicationIT extends BaseApiApplication {


	@Autowired
	private MockMvc mvc;

	@Test
	public void emptyTest() {
	}
}

