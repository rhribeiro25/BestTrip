package com.rhribeiro25.bestTrip.model;
/**
 * @author Renan Ribeiro
 * @date 11/05/2020.
 */
public class RouteModel {
	private String from;
	private String to;
	private Double price;

	public RouteModel() {}

	public RouteModel(String from, String to, Double price) {
		super();
		this.from = from;
		this.to = to;
		this.price = price;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

}
