package com.rhribeiro25.bestTrip.controller;

import java.io.IOException;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import com.rhribeiro25.bestTrip.service.RouteService;

/**
 * @author Renan Ribeiro
 * @date 11/05/2020.
 */
@ShellComponent
public class shellController {

	@Autowired
	private RouteService routeService;

	@ShellMethod("Insert routes from-to")
	public void bestTrip(String fileName) throws IOException {
		routeService.saveFileRoutes(fileName);
		while (true) {
			Scanner scanner = new Scanner(System.in);
			try {
				System.out.print("please enter the route: ");
				String from_to = scanner.nextLine();
				String bestRoute = routeService.findBestPrice(from_to);
				if (bestRoute != null)
					System.out.println(bestRoute);
				else
					System.out.println("Route not found!");
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

}
