import java.io.*;
import java.util.Scanner;

// A temporary implementation of AccountSystem geared towards reading from a CSV,
// not a database.
public class FauxAccountSystem {
    static final String DATABASE_NOT_FOUND = "Critical error: database could not be found.";
    // Confirms whether a given user exists in the system.
    static boolean findAccount(String username, int passwordHash){
        try (Scanner scanner = new Scanner(new File("accounts.csv")) ) {
            String[] data;
            String hash = String.valueOf(passwordHash);
            while (scanner.hasNextLine()){
                data = scanner.nextLine().split(",");
                if (username.equals(data[0])){
                    return true;
                }
            }
        } catch (FileNotFoundException e){
            System.out.println(DATABASE_NOT_FOUND);
            e.printStackTrace();
        }
        return false;
    }

    static int createAccount(String username, int passwordHash){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("accounts.csv", true)) ) {
            String data = System.lineSeparator() + username + "," + passwordHash;
            User user = new User(username, passwordHash);

            writer.append(data);
            Main.SessionAccount = user;
            return 0;
        } catch (IOException e){
            System.out.println(DATABASE_NOT_FOUND);
            e.printStackTrace();
        }
        return 753;
    }
}
