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
	
	int number = 0;
	int beds = 0;
	int maxOccupancy = 0;
	double baseDailyRate = 0;
	double dailyRate = 0;
	boolean smokingStatus = false;
	List<BedType> bedTypes = new ArrayList<>();
	QualityLevel qualityLevel = QualityLevel.Executive;
	RoomSize roomSize = RoomSize.Single;
	
	// For Testing purposes
	Room() {
		
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

		dailyRate = q.getPrice() + baseDailyRate;
	}
	public int getNumber() { return number; }
	public int getBeds() { return beds; }
	public int getMaxOccupancy() { return maxOccupancy; }
	public double getBaseDailyRate() { return baseDailyRate; }
	public boolean getSmokingStatus() { return smokingStatus; }
	public List<BedType> getBedTypes() { return bedTypes; }
	public QualityLevel getQualityLevel() { return qualityLevel; }
	public RoomSize getRoomSize() { return roomSize; }
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
}
