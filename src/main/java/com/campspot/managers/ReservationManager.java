package com.campspot.managers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.joda.time.Interval;

import com.campspot.interfaces.Reservable;
import com.campspot.models.Campsite;
import com.campspot.models.Park;
import com.campspot.models.Reservation;
import com.campspot.models.Search;

public class ReservationManager implements Reservable {

	public List<Campsite> getAvailableCampSites(Park park, int gapSize) {

		List<Campsite> availableCampsite = new ArrayList<>();

		Interval searchInterval = new Interval(park.getSearchDates().getStartDate(),
				park.getSearchDates().getEndDate());

		for (Campsite currentCampsite : park.getCampsites()) {
			List<Reservation> reservations = currentCampsite.getReservations(park).stream().filter(res -> willOverLap(
					park.getSearchDates(), res)
					|| (checkGapRule(searchInterval, new Interval(res.getStartDate(), res.getEndDate()), gapSize)))
					.collect(Collectors.toList());

			if (reservations.size() == 0) {
				availableCampsite.add(currentCampsite);
			}
		}
		return availableCampsite;
	}

	private boolean willOverLap(Search search, Reservation res) {
		Interval currentSearchInterval = new Interval(search.getStartDate(), search.getEndDate());
		Interval resInterval = new Interval(res.getStartDate(), res.getEndDate());
		return currentSearchInterval.overlaps(resInterval) || (isBefore(currentSearchInterval, resInterval)
				&& search.getEndDate().isEqual(res.getStartDate())
				|| isAfter(currentSearchInterval, resInterval) && search.getStartDate().isEqual(res.getEndDate()));
	}

	private boolean isBefore(Interval searchInterval, Interval intervalToCheck) {
		return searchInterval.isAfter(intervalToCheck);
	}

	private boolean isAfter(Interval searchInterval, Interval intervalToCheck) {
		return searchInterval.isBefore(intervalToCheck);
	}

	private boolean checkGapRule(Interval searchInterval, Interval intervalToCheck, int gapSize) {
		int gap = searchInterval.gap(intervalToCheck).toPeriod().getDays() - 1;
		return gap != 0 && gap <= gapSize;
	}

}
