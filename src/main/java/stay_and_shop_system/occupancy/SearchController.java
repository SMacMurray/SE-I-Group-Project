package stay_and_shop_system.occupancy;

import java.text.SimpleDateFormat;
import java.util.*;

public class SearchController {
	RoomService rs = new RoomService();
	ReservationController reS = new ReservationController();
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");

	public List<Room> searchAvailableRooms(RoomCriteria rc) throws IllegalArgumentException {
		if (rc == null || rc.getRoomSizes() == null || rc.getRoomSizes().contains(null) ||
				rc.getRoomRange() == null || rc.getRoomRange().contains(null) || rc.getBedTypes() == null ||
				rc.getBedTypes().contains(null) || rc.getBedRange() == null || rc.getBedRange().length != 2 ||
				rc.getGuestRange() == null || rc.getGuestRange().length != 2 || rc.getSmokingStatuses() == null ||
				rc.getSmokingStatuses().contains(null) || rc.getCostRange() == null || rc.getCostRange().length != 2 ||
				rc.getDateRange() == null || rc.getDateRange().length != 2 || rc.getDateRange()[0] == null ||
				rc.getDateRange()[1] == null) {
			throw new IllegalArgumentException("There is/are null or empty input.");
		}
		if (rc.getBedRange()[0] > rc.getBedRange()[1]) {
			throw new IllegalArgumentException("Bed Range: The end value can't be less than the start value.");
		}
		if (rc.getBedRange()[0] < 0) {
			throw new IllegalArgumentException("Bed Range: No value can be negative.");
		}
		Calendar todayDate = Calendar.getInstance();
		try {
			// Getting rid of the minutes and seconds in today's date.
			todayDate.setTime(formatter.parse(formatter.format(Calendar.getInstance().getTime())));
		}
		catch (Exception e) {
			throw new RuntimeException("Could not parse today's date.");
		}
		if (rc.getDateRange()[0].after(rc.getDateRange()[1])) {
			throw new IllegalArgumentException("Date Range: The start date can't be after the end date.");
		}
		if (rc.getDateRange()[0].before(todayDate)) {
			throw new IllegalArgumentException("Date Range: The start date can't be before today's date.");
		}
		if (rc.getCostRange()[0] > rc.getCostRange()[1]) {
			throw new IllegalArgumentException("Cost Range: The end value can't be less than the start value.");
		}
		if (rc.getCostRange()[0] < 0) {
			throw new IllegalArgumentException("Cost Range: No value can be negative.");
		}
		if (rc.getGuestRange()[0] > rc.getGuestRange()[1]) {
			throw new IllegalArgumentException("Cost Range: The end value can't be less than the start value.");
		}
		if (rc.getGuestRange()[0] < 0) {
			throw new IllegalArgumentException("Guest Range: No value can be negative.");
		}


		return reS.deleteOverlapRooms(rs.findCandidateRooms(rc), rc.getDateRange());
	}
}
