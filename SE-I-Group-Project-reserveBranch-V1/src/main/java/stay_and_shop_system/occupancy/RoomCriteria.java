package stay_and_shop_system.occupancy;

import java.util.*;

public class RoomCriteria {
    private int[] guestRange;
    private int[] bedRange;
    private List<Boolean> smokingStatuses;
    private List<Integer> roomRange;
    private List<Room.BedType> bedTypes;
    private List<Room.RoomSize> roomSizes;
    private double[] costRange;
    private Calendar[] dateRange;

    public RoomCriteria(int[] gR, int[] bR, List<Boolean> sS, List<Integer> rR, List<Room.BedType> bT,
                        List<Room.RoomSize> rS, double[] cR, Calendar[] dR) {
        guestRange = gR;
        bedRange = bR;
        smokingStatuses = sS;
        roomRange = rR;
        bedTypes = bT;
        roomSizes = rS;
        costRange = cR;
        dateRange = dR;
    }


    public int[] getGuestRange() {
        return guestRange;
    }

    public int[] getBedRange() {
        return bedRange;
    }

    public List<Boolean> getSmokingStatuses() {
        return smokingStatuses;
    }

    public List<Integer> getRoomRange() {
        return roomRange;
    }

    public List<Room.BedType> getBedTypes() {
        return bedTypes;
    }

    public List<Room.RoomSize> getRoomSizes() {
        return roomSizes;
    }

    public double[] getCostRange() {
        return costRange;
    }

    public Calendar[] getDateRange() {
        return dateRange;
    }
}
