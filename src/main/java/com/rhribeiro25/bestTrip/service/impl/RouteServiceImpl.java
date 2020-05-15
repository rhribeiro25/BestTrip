package com.rhribeiro25.bestTrip.service.impl;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.rhribeiro25.bestTrip.model.RouteModel;
import com.rhribeiro25.bestTrip.repository.RouteRepository;
import com.rhribeiro25.bestTrip.service.RouteService;

/**
 * @author Renan Ribeiro
 * @date 11/05/2020.
 */

@Service
public class RouteServiceImpl implements RouteService {

	@Value("${files.path}")
	private String filesPath;

	@Autowired
	private RouteRepository routeRepository;

	private List<RouteModel> routesList;
	private Map<Double, List<RouteModel>> routesMap;

	@Override
	public void saveFileRoutes(String fileName) {
		File file = new File(filesPath + fileName);
		routeRepository.saveFileRoutes(file);
	}

	@Override
	public boolean saveNewRoute(RouteModel route) throws IOException {
		DecimalFormat df = new DecimalFormat("#,##0");
		df.setMaximumFractionDigits(0);
		String routeString = route.getFrom() + "," + route.getTo() + "," + df.format(route.getPrice());
		return routeRepository.saveNewRoute(routeString);
	}

	@Override
	public String findBestPrice(String from_to) throws IOException {
		DecimalFormat df = new DecimalFormat("#,##0");
		df.setMaximumFractionDigits(0);
		String[] fromToVector = from_to.split("-");
		String from = fromToVector[0];
		String to = fromToVector[1];
		List<RouteModel> routesFrom = new ArrayList<>();
		List<RouteModel> routesTo = new ArrayList<>();
		routesMap = new HashMap<>();
		routesList = routeRepository.findAllRoutes();
		for (RouteModel r : routesList) {
			if (r.getFrom().equalsIgnoreCase(from)) {
				routesFrom.add(r);
			}
			if (r.getTo().equalsIgnoreCase(to)) {
				routesTo.add(r);
			}
		}
		for (RouteModel routeFrom : routesFrom) {
			for (RouteModel routeTo : routesTo) {
				findRoutesByFromTo(routeFrom, routeTo);
			}
		}
		if (!routesMap.isEmpty()) {
			Double bestPrice = 1000000000.1;
			for (Double key : routesMap.keySet()) {
				if (bestPrice > key) {
					bestPrice = key;
				}
			}
			List<RouteModel> routesBestPrice = routesMap.get(bestPrice);
			StringBuilder result = new StringBuilder("best route: ");
			result.append(routesBestPrice.get(0).getFrom());
			for (RouteModel r : routesBestPrice) {
				result.append(" - " + r.getTo());
			}
			result.append(" > $" + df.format(bestPrice));
			return result.toString();
		}
		return null;
	}

	// Checking available flight connections
	private void findRoutesByFromTo(RouteModel routeFrom, RouteModel routeTo) {
		boolean exists = false;
		List<RouteModel> traceRoute;

		// 0 connection
		if (routeFrom.getTo().equalsIgnoreCase(routeTo.getTo())
				&& routeFrom.getFrom().equalsIgnoreCase(routeTo.getFrom())) {
			Double price = routeFrom.getPrice();
			traceRoute = Arrays.asList(routeFrom);
			if (!routesMap.isEmpty()) {
				for (Double key : routesMap.keySet()) {
					if ((price.compareTo(key) == 0) && traceRoute.size() > routesMap.get(key).size()) {
						exists = true;
						break;
					}
				}
				if (!exists)
					routesMap.put(price, traceRoute);
			} else
				routesMap.put(price, traceRoute);
		}

		// 1 connection
		if (routeFrom.getTo().equalsIgnoreCase(routeTo.getFrom())
				&& routeTo.getFrom().equalsIgnoreCase(routeFrom.getTo())) {
			Double price = routeFrom.getPrice() + routeTo.getPrice();
			traceRoute = Arrays.asList(routeFrom, routeTo);
			if (!routesMap.isEmpty()) {
				for (Double key : routesMap.keySet()) {
					if ((price.compareTo(key) == 0) && traceRoute.size() > routesMap.get(key).size()) {
						exists = true;
						break;
					}
				}
				if (!exists)
					routesMap.put(price, traceRoute);
			} else
				routesMap.put(price, traceRoute);
		}

		// 2 connections
		for (RouteModel r1 : routesList) {
			if (routeFrom.getTo().equalsIgnoreCase(r1.getFrom()) && r1.getTo().equalsIgnoreCase(routeTo.getFrom())) {
				Double price = routeFrom.getPrice() + r1.getPrice() + routeTo.getPrice();
				traceRoute = Arrays.asList(routeFrom, r1, routeTo);
				if (!routesMap.isEmpty()) {
					for (Double key : routesMap.keySet()) {
						if ((price.compareTo(key) == 0) && traceRoute.size() > routesMap.get(key).size()) {
							exists = true;
							break;
						}
					}
					if (!exists)
						routesMap.put(price, traceRoute);
				} else
					routesMap.put(price, traceRoute);
			} else if (routeFrom.getTo().equalsIgnoreCase(r1.getFrom())) {

				// 3 connections
				for (RouteModel r2 : routesList) {
					if (r1.getTo().equalsIgnoreCase(r2.getFrom()) && r2.getTo().equalsIgnoreCase(routeTo.getFrom())) {
						Double price = routeFrom.getPrice() + r1.getPrice() + r2.getPrice() + routeTo.getPrice();
						traceRoute = Arrays.asList(routeFrom, r1, r2, routeTo);
						if (!routesMap.isEmpty()) {
							for (Double key : routesMap.keySet()) {
								if ((price.compareTo(key) == 0) && traceRoute.size() > routesMap.get(key).size()) {
									exists = true;
									break;
								}
							}
							if (!exists)
								routesMap.put(price, traceRoute);
						} else
							routesMap.put(price, traceRoute);
					} else if (r1.getTo().equalsIgnoreCase(r2.getFrom())) {

						// 4 connections
						for (RouteModel r3 : routesList) {
							if (r2.getTo().equalsIgnoreCase(r3.getFrom())
									&& r3.getTo().equalsIgnoreCase(routeTo.getFrom())) {
								Double price = routeFrom.getPrice() + r1.getPrice() + r2.getPrice() + r3.getPrice()
										+ routeTo.getPrice();
								traceRoute = Arrays.asList(routeFrom, r1, r2, r3, routeTo);
								if (!routesMap.isEmpty()) {
									for (Double key : routesMap.keySet()) {
										if ((price.compareTo(key) == 0)
												&& traceRoute.size() > routesMap.get(key).size()) {
											exists = true;
											break;
										}
									}
									if (!exists)
										routesMap.put(price, traceRoute);
								} else
									routesMap.put(price, traceRoute);
							} else if (r2.getTo().equalsIgnoreCase(r3.getFrom())) {

								// 5 connections
								for (RouteModel r4 : routesList) {
									if (r3.getTo().equalsIgnoreCase(r4.getFrom())
											&& r4.getTo().equalsIgnoreCase(routeTo.getFrom())) {
										Double price = routeFrom.getPrice() + r1.getPrice() + r2.getPrice()
												+ r3.getPrice() + r4.getPrice() + routeTo.getPrice();
										traceRoute = Arrays.asList(routeFrom, r1, r2, r3, r4, routeTo);
										if (!routesMap.isEmpty()) {
											for (Double key : routesMap.keySet()) {
												if ((price.compareTo(key) == 0)
														&& traceRoute.size() > routesMap.get(key).size()) {
													exists = true;
													break;
												}
											}
											if (!exists)
												routesMap.put(price, traceRoute);
										} else
											routesMap.put(price, traceRoute);
									}
								}
							}
						}
					}
				}
			}
		}
	}
}
