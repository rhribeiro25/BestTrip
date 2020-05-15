package com.rhribeiro25.bestTrip.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.rhribeiro25.bestTrip.service.RouteService;

/**
 * @author Renan Ribeiro
 * @date 11/05/2020.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(properties = { InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
		ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false" })
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class RouteControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private RouteService routeService;

	private static String URI;;

	@BeforeClass
	public static void setUp() {
		URI = "http://localhost:8080/routes";
	}

	@Test
	public void findBestPriceStatusCode200() throws Exception {
		BDDMockito.given(this.routeService.findBestPrice("GRU-CDG"))
				.willReturn("best route: GRU - BRC - SCL - ORL - CDG > $40");
		mvc.perform(MockMvcRequestBuilders.get(URI + "/GRU-CDG").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	public void findBestPriceStatusCode404() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get(URI + "/TEST-TEST").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	@Test
	public void saveNewFileStatusCode400() throws Exception {
		mvc.perform(MockMvcRequestBuilders.post(URI).content("{}").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).characterEncoding("utf-8")).andExpect(status().isBadRequest());
	}
}
