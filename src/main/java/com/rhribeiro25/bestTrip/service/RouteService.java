package com.rhribeiro25.bestTrip.service;

import java.io.IOException;

import com.rhribeiro25.bestTrip.model.RouteModel;

/**
 * @author Renan Ribeiro
 * @date 11/05/2020.
 */
public interface RouteService {

	public void saveFileRoutes(String fileName);

	public String findBestPrice(String from_to) throws IOException;

	public boolean saveNewRoute(RouteModel route) throws IOException;

}
