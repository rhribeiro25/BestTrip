package com.rhribeiro25.bestTrip.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.rhribeiro25.bestTrip.model.RouteModel;

/**
 * @author Renan Ribeiro
 * @date 11/05/2020.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(properties = { InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
		ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false" })
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class RouteServiceTest {

	@MockBean
	private RouteService routeService;

	@Test
	public void findBestPriceTest() throws IOException {
		BDDMockito.given(this.routeService.findBestPrice("BRC-ORL")).willReturn("best route: BRC - ORL > $30");

		String route = this.routeService.findBestPrice("BRC-ORL");
		assertEquals("best route: BRC - ORL > $30", route);
	}

	@Test
	public void saveNewRouteTest() throws IOException {
		RouteModel route = new RouteModel("BRC", "CDG", 85.0);
		BDDMockito.given(this.routeService.saveNewRoute(route)).willReturn(true);

		boolean isSaved = this.routeService.saveNewRoute(route);
		assertTrue(isSaved);
	}

}
