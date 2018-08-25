package com.campspot.models;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Reservation {

	@JsonProperty("campsiteId")
	private int campsiteId;
	@JsonProperty("startDate")
	private DateTime startDate;
	@JsonProperty("endDate")
	private DateTime endDate;

	public int getCampsiteId() {
		return campsiteId;
	}

	public void setCampsiteId(int campsiteId) {
		this.campsiteId = campsiteId;
	}

	public DateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(DateTime startDate) {
		this.startDate = startDate;
	}

	public DateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(DateTime endDate) {
		this.endDate = endDate;
	}
}
