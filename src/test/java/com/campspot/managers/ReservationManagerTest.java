package com.campspot.managers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import com.campspot.models.Campsite;
import com.campspot.models.Park;
import com.campspot.models.Reservation;
import com.campspot.models.Search;

public class ReservationManagerTest {

	ReservationManager target;
	Park testPark;

	Search searchDates;

	@Before
	public void setUp() {
		target = new ReservationManager();
		testPark = new Park();
		searchDates = new Search();
		searchDates.setStartDate(new DateTime("2018-02-04"));
		searchDates.setEndDate(new DateTime("2018-02-06"));

		testPark.setSearchDates(searchDates);
	}

	@Test
	public void should_return_all_available_campsites_if_search_does_not_break_any_rules() {
		List<Campsite> result;
		testPark.setCampsites(createCampsites(1));
		testPark.setReservations(createReservations(new DateTime("2018-02-01"), new DateTime("2018-02-03"), 0, 8, 2));

		result = target.getAvailableCampSites(testPark, 1);

		assertThat(result.size(), equalTo(1));
		assertThat(result.get(0).getName(), equalTo(testPark.getCampsites()[0].getName()));
	}
	
	@Test
	public void should_return_empty_list_if_gap_rule_is_broken_by_a_reservation_after_the_search_dates() {
		List<Campsite> result;
		testPark.setCampsites(createCampsites(1));
		testPark.setReservations(createReservations(new DateTime("2018-02-01"), new DateTime("2018-02-03"), 0, 7, 2));

		result = target.getAvailableCampSites(testPark, 1);

		assertThat(result.size(), equalTo(0));
	}
	
	@Test
	public void should_return_campsites_that_have_no_reservations() {
		List<Campsite> result;
		testPark.setCampsites(createCampsites(2));
		testPark.setReservations(createReservations(new DateTime("2018-02-01"), new DateTime("2018-02-05"), 0, 8, 2));

		result = target.getAvailableCampSites(testPark, 1);

		assertThat(result.size(), equalTo(1));
		assertThat(result.get(0).getName(), equalTo(testPark.getCampsites()[1].getName()));
	}
	
	@Test
	public void should_return_empty_list_if_search_overLaps_reservation() {
		List<Campsite> result;
		testPark.setCampsites(createCampsites(1));
		testPark.setReservations(createReservations(new DateTime("2018-02-04"), new DateTime("2018-02-06"), 0, 0, 1));

		result = target.getAvailableCampSites(testPark, 1);

		assertThat(result.size(), equalTo(0));
	}
	
	@Test
	public void should_return_empty_list_if_search_overLaps_reservation_at_the_beginning() {
		List<Campsite> result;
		testPark.setCampsites(createCampsites(1));
		testPark.setReservations(createReservations(new DateTime("2018-02-02"), new DateTime("2018-02-05"), 0, 0, 1));

		result = target.getAvailableCampSites(testPark, 1);

		assertThat(result.size(), equalTo(0));
	}
	
	@Test
	public void should_return_empty_list_if_search_overLaps_reservation_at_the_end() {
		List<Campsite> result;
		testPark.setCampsites(createCampsites(1));
		testPark.setReservations(createReservations(new DateTime("2018-02-05"), new DateTime("2018-02-08"), 0, 0, 1));

		result = target.getAvailableCampSites(testPark, 1);

		assertThat(result.size(), equalTo(0));
	}
	
	@Test
	public void should_return_empty_list_if_gapRule_is_set_to_2_day() {
		List<Campsite> result;
		testPark.setCampsites(createCampsites(1));
		testPark.setReservations(createReservations(new DateTime("2018-02-01"), new DateTime("2018-02-03"), 0, 8, 2));

		result = target.getAvailableCampSites(testPark, 2);

		assertThat(result.size(), equalTo(0));
	}

	
	@Test
	public void should_return_empty_list_if_gapRule_is_set_to_3_day() {
		List<Campsite> result;
		testPark.setCampsites(createCampsites(1));
		testPark.setReservations(createReservations(new DateTime("2018-02-01"), new DateTime("2018-02-03"), 0, 9, 2));

		result = target.getAvailableCampSites(testPark, 3);

		assertThat(result.size(), equalTo(0));
	}
	
	@Test
	public void should_return_empty_list_if_gapRule_is_set_to_3_day_and_gap_is_1() {
		List<Campsite> result;
		testPark.setCampsites(createCampsites(1));
		testPark.setReservations(createReservations(new DateTime("2018-02-01"), new DateTime("2018-02-03"), 0, 7, 2));

		result = target.getAvailableCampSites(testPark, 3);

		assertThat(result.size(), equalTo(0));
	}
	
	private Campsite[] createCampsites(int numOfCampsites) {
		Campsite[] campsites = new Campsite[numOfCampsites];
		for (int i = 0; i < numOfCampsites; i++) {
			Campsite campToCreate = new Campsite();
			campToCreate.setId(i);
			campToCreate.setName("testCamp" + Integer.toString(i));

			campsites[i] = campToCreate;
		}
		return campsites;
	}

	private Reservation[] createReservations(DateTime start, DateTime end, int campsiteId, int daysToIncrementBy,
			int numOfReservations) {
		Reservation[] res = new Reservation[numOfReservations];
		for (int i = 0; i < numOfReservations; i++) {
			Reservation resToCreate = new Reservation();
			resToCreate.setCampsiteId(campsiteId);
			if (i == 0) {
				resToCreate.setStartDate(start);
				resToCreate.setEndDate(end);
			} else {
				resToCreate.setStartDate(start.plusDays(daysToIncrementBy));
				resToCreate.setEndDate(end.plusDays(daysToIncrementBy));
			}

			res[i] = resToCreate;

		}
		return res;
	}
}
