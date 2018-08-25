package com.campspot.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Park {
	@JsonProperty("search")
	private Search searchDates;
	@JsonProperty("campsites")
	private Campsite[] campsites;
	@JsonProperty("reservations")
	private Reservation[] reservations;

	public void setReservations(Reservation[]  reservations) {
		this.reservations = reservations;
	}

	public void setSearchDates(Search searchDates) {
		this.searchDates = searchDates;
	}

	public void setCampsites(Campsite[] campsites) {
		this.campsites = campsites;
	}

	public Reservation[] getReservations() {
		return reservations;
	}

	public Search getSearchDates() {
		return searchDates;
	}

	public Campsite[] getCampsites() {
		return campsites;
	}
}
