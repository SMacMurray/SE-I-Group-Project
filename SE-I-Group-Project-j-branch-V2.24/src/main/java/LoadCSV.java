import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

// Loading CSV into the RoomService's rooms array
public class LoadCSV {
	public static void loadRooms() {
		List<List<Object>> csvData = new ArrayList<>();
		
        try (Scanner scanner = new Scanner(new File("reserve.csv")) ) {
            scanner.nextLine();
            while (scanner.hasNextLine()) {
            	String[] spl = scanner.nextLine().split("\t");
            	
            	for( int i = spl.length - 8; i > 0; --i) {
            		spl[5] = spl[5] + "," + spl[5 + i];
            		System.out.println("rich");
            	}
            	csvData.add(new ArrayList<>(Arrays.asList(
            			Integer.parseInt(spl[0]),
            			Integer.parseInt(spl[1]),
            			Integer.parseInt(spl[2]),
            			Double.parseDouble(spl[3]),
            			spl[4],
            			spl[5].replaceAll("\"", ""),
            			spl[spl.length - 2],
            			spl[spl.length - 1]
            			
            	)));
            }
        }
        catch(FileNotFoundException e) {
            System.out.println("File not found");
            return;
        }

        
        Object[][] tableArray = csvData.stream()
                .map(l -> l.stream().toArray(Object[]::new))
                .toArray(Object[][]::new);
        
        
        
        for (Object[] a : tableArray) {
        	List<Room.BedType> bedTypes = new ArrayList<>();
        	String str = ((String)a[5]).replaceAll(" ", "");
            String[] spl = str.split(",");
            for (String s : spl) {
            	bedTypes.add(Room.BedType.valueOf(s));
            }
            boolean smokingStatus = ( ((String)a[4] == "Permitted") ? true : false);
        	GlobalVariables.rs.createRoom((int)a[0], (int)a[1], (int)a[2], (double)a[3],
        								smokingStatus, bedTypes,
        								Room.QualityLevel.valueOf((String)a[6]), 
        								Room.RoomSize.valueOf((String)a[7]));
        }
	}
}
