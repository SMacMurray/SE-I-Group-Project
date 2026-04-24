package stay_and_shop_system.occupancy;

import java.util.*;

public class Room {
	public enum BedType {
		Twin,
		Full,
		Queen,
		King
	}
	public enum QualityLevel {
		Economy(0),
		Comfort(3),
		Business(5.5),
		Executive(10.99);
		
		private double price;
		QualityLevel(double p) {
			price = p;
		}
		
		public double getPrice() {
			return price;
		}
	}
	public enum RoomSize {
		Single,
		Double,
		Family,
		Suite,
		Deluxe,
		Standard
	}
	public enum RoomStatus {
		UnOccupied,
		Occupied
	}
	
	private int number = 0;
	private int beds = 0;
	private int maxOccupancy = 0;
	private double baseDailyRate = 0;
	private double dailyRate = 0;
	private boolean smokingStatus = false;
	private List<BedType> bedTypes = new ArrayList<>();
	private QualityLevel qualityLevel = QualityLevel.Executive;
	private RoomSize roomSize = RoomSize.Single;
	private RoomStatus roomStatus = RoomStatus.UnOccupied;
	
	// For Testing purposes
    public Room() {
		
	}
	public Room(int n, int b, int p, double r, boolean s, List<BedType> t, QualityLevel q, RoomSize rs) {
		number = n;
		beds = b;
		maxOccupancy = p;
		baseDailyRate = r;
		smokingStatus = s;
		bedTypes = t;
		qualityLevel = q;
		roomSize = rs;

		dailyRate = Math.round((q.getPrice() + baseDailyRate) * 100.0) / 100.0;
    }
	public int getNumber() { return number; }
	public int getBeds() { return beds; }
	public int getMaxOccupancy() { return maxOccupancy; }
	public double getBaseDailyRate() { return baseDailyRate; }
	public boolean getSmokingStatus() { return smokingStatus; }
	public List<BedType> getBedTypes() { return bedTypes; }
	public QualityLevel getQualityLevel() { return qualityLevel; }
	public RoomSize getRoomSize() { return roomSize; }
	public RoomStatus getRoomStatus() { return roomStatus; }
	public double getDailyRate() {return dailyRate;}

	public void setNumber(int n) { number = n; }
	public void setBeds(int b) { beds = b;}
	public void setMaxOccupancy(int p) { maxOccupancy = p; }
	public void setBaseDailyRate(double r) {
		baseDailyRate = r;
		updateDailyRate();
	}
	public void setSmokingStatus(boolean s) { smokingStatus = s; }
	public void addBedType(BedType t) { bedTypes.add(t); }
	public void setQualityLevel(QualityLevel q) {
		qualityLevel = q;
		updateDailyRate();
	}
	public void setRoomSize(RoomSize rs) { roomSize = rs; }
	public void setRoomStatus(RoomStatus rs) { roomStatus = rs; }
	private void updateDailyRate() {
		dailyRate = qualityLevel.getPrice() + baseDailyRate;
	}
	@Override
	public boolean equals(Object o) {
		if (this == o) return true; 
		if (!(o instanceof Room)) return false;
		Room temp = (Room)o;
		return number == temp.number;
	}
	@Override
	public int hashCode() {
		return Objects.hash(number);
	}

	@Override
	public String toString() {
		String str = "Room of number " + number;
		str += "\n Bed Number: " + beds;
		str += "\n Max Occupancy: " + maxOccupancy;
		str += "\n Base Daily Rate: " + baseDailyRate;
		str += "\n Daily Rate: " + dailyRate;
		str += "\n Smoking Status: " + smokingStatus;
		str += "\n Bed Types: ";
		for (BedType bt : bedTypes) {
			str += bt.toString() + " ";
		}
		str += "\n Quality Level: " + qualityLevel.toString();
		str += "\n RoomSize: " + roomSize.toString();
		str += "\n RoomStatus: " + roomStatus.toString();

		return str;
	}
}
