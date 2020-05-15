package com.rhribeiro25.bestTrip.repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.rhribeiro25.bestTrip.model.RouteModel;

/**
 * @author Renan Ribeiro
 * @date 11/05/2020.
 */

@Repository
public class RouteRepository {

	private static File fileRoutes;

	public void saveFileRoutes(File file) {
		fileRoutes = file;
	}

	public boolean saveNewRoute(String route) throws IOException {
		Writer writer = null;
		try {
			if (fileRoutes != null) {
				writer = new BufferedWriter(new FileWriter(fileRoutes, true));
				writer.write("\n" + route);
			} else {
				throw new IOException("Please start the system in shell, using (best-trip fileName)!");
			}
		} finally {
			if (writer != null)
				writer.close();
		}
		return true;
	}

	public List<RouteModel> findAllRoutes() throws IOException {
		List<RouteModel> routes = new ArrayList<>();
		BufferedReader br = null;
		try {
			if (fileRoutes != null) {
				br = new BufferedReader(new FileReader(fileRoutes));
				String line;
				RouteModel routeAux;
				while ((line = br.readLine()) != null) {
					String[] attributes = line.split(",");
					routeAux = new RouteModel(attributes[0].trim(), attributes[1].trim(),
							Double.parseDouble(attributes[2].trim()));
					routes.add(routeAux);
				}
			} else {
				throw new IOException("Please start the system in shell, using (best-trip fileName)!");
			}
		} finally {
			if (br != null)
				br.close();
		}
		return routes;
	}

}
