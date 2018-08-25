package com.campspot.models;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Campsite {
	@JsonProperty("id")
	private int id;
	@JsonProperty("name")
	private String name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public List<Reservation> getReservations(Park campSite) {
		return Arrays.stream(campSite.getReservations()).filter(res -> res.getCampsiteId() == id)
				.collect(Collectors.toList());
	}

}
