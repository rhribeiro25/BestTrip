package com.rhribeiro25.bestTrip.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
public class RouteRepositoryTest {

	@MockBean
	private RouteRepository routeRepository;

	@Test
	public void findAllTest() throws IOException {
		List<RouteModel> routes = new ArrayList<>();
		routes.add(new RouteModel("teste", "teste", 80.4));
		routes.add(new RouteModel("teste2", "teste2", 81.4));
		routes.add(new RouteModel("teste3", "teste3", 82.4));
		BDDMockito.given(this.routeRepository.findAllRoutes()).willReturn(routes);
		List<RouteModel> routesList = this.routeRepository.findAllRoutes();
		assertEquals(routes, routesList);
	}

	@Test
	public void saveNewRouteTest() throws IOException {
		BDDMockito.given(this.routeRepository.saveNewRoute("BRC,CDG,85")).willReturn(true);

		boolean isSaved = this.routeRepository.saveNewRoute("BRC,CDG,85");
		assertTrue(isSaved);
	}
}
