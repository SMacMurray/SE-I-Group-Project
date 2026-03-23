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
	boolean smokingStatus = false;
	List<BedType> bedTypes = new ArrayList<>();
	QualityLevel qualityLevel = QualityLevel.Executive;
	RoomSize roomSize = RoomSize.Single;
	
	Room(int n, int b, int p, double r, boolean s, List<BedType> t, QualityLevel q, RoomSize rs) {
		number = n;
		beds = b;
		maxOccupancy = p;
		baseDailyRate = r;
		smokingStatus = s;
		bedTypes = t;
		qualityLevel = q;
		roomSize = rs;
	}
	public int getNumber() { return number; }
	public int getBeds() { return beds;}
	public int getMaxOccupancy() { return maxOccupancy; }
	public double getBaseDailyRate() { return baseDailyRate; }
	public boolean getSmokingStatus() { return smokingStatus; }
	public List<BedType> getBedTypes() { return bedTypes; }
	public QualityLevel getQualityLevel() { return qualityLevel; }
	public RoomSize getRoomSize() { return roomSize; }
	
	public void setNumber(int n) { number = n; }
	public void setBeds(int b) { beds = b;}
	public void setMaxOccupancy(int p) { maxOccupancy = p; }
	public void setBaseDailyRate(double r) { baseDailyRate = r; }
	public void setSmokingStatus(boolean s) { smokingStatus = s; }
	public void addBedType(BedType t) { bedTypes.add(t); }
	public void setQualityLevel(QualityLevel q) {qualityLevel = q; }
	public void setRoomSize(RoomSize rs) { roomSize = rs; }
	
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
