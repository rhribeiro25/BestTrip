package com.rhribeiro25.bestTrip.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rhribeiro25.bestTrip.model.RouteModel;
import com.rhribeiro25.bestTrip.service.RouteService;

/**
 * @author Renan Ribeiro
 * @date 11/05/2020.
 */

@RestController
@RequestMapping("/routes")
public class RouteController {

	@Autowired
	private RouteService routeService;

	@GetMapping("/{from-to}")
	public ResponseEntity<String> findBestPrice(@PathVariable("from-to") String from_to) throws IOException {
		try {
			String result = routeService.findBestPrice(from_to);
			if (result == null) {
				result = "Connection not found!";
				return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
			} else
				return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping
	public ResponseEntity<String> saveRoute(@RequestBody RouteModel route) throws IOException {
		try {
			boolean isSave = routeService.saveNewRoute(route);
			if (isSave)
				return new ResponseEntity<>("Successful to save route!", HttpStatus.CREATED);
			else
				return new ResponseEntity<>("Failed to save route!", HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
