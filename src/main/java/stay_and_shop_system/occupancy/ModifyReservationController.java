package stay_and_shop_system.occupancy;

import java.util.*;


public class ModifyReservationController {
	ReservationService reS;
	SearchController sc;
	
	public List<Room> modifyReservation(boolean room, Calendar start, Calendar end, int guestNum) {
		// reservationService.modifyReservationStatus(start, end, guestNum);
		
		if (room) {
			//sc.searchAvailableRoom( criteria );
		}
		return new ArrayList<Room>();
	}
}
