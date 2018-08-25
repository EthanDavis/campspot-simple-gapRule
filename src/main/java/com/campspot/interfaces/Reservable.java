package com.campspot.interfaces;

import java.util.List;

import com.campspot.models.Campsite;
import com.campspot.models.Park;

public interface Reservable {

	public  List<Campsite> getAvailableCampSites(Park park, int gapSize);
	
}
