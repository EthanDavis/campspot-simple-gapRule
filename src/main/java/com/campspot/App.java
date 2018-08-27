package com.campspot;

import java.io.File;
import java.io.IOException;

import com.campspot.managers.ReservationManager;
import com.campspot.models.Park;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;

public class App {
	static ReservationManager reservationManager = new ReservationManager();

	public static void main(String[] args) {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JodaModule());

		Park park;
		try {
			int gapRule = (args.length == 2 && args[1] != null) ? Integer.parseInt(args[1]) : 1;
			park = objectMapper.readValue(new File(args[0]), Park.class);
			reservationManager.getAvailableCampSites(park, gapRule)
			.forEach(campsite ->System.out.println(campsite.getName()));
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
