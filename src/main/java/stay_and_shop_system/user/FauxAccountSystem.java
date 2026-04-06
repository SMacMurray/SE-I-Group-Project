package stay_and_shop_system.user;

import stay_and_shop_system.Main;

import java.io.*;
import java.util.Scanner;

// A temporary implementation of AccountSystem geared towards reading from a CSV,
// not a database.
public class FauxAccountSystem {
    static final String DatabaseFile = "src/main/resources/accounts.csv";
    static final String DATABASE_NOT_FOUND = "Critical error: database could not be found.";
    // Confirms whether a given user exists in the system.
    static boolean findAccount(String username, int passwordHash){
        try (Scanner scanner = new Scanner(new File("src/main/resources/accounts.csv")) ) {
            String[] data;
            String hash = String.valueOf(passwordHash);
            scanner.nextLine(); // Ignore header text
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
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DatabaseFile, true)) ) {
            String data = System.lineSeparator() + username + "," + passwordHash;
            User user = new Guest(username, passwordHash);

            writer.append(data);
            Main.SessionAccount = user;
            return 0;
        } catch (IOException e){
            System.out.println(DATABASE_NOT_FOUND);
            e.printStackTrace();
        }
        return -1;
    }

    static int authenticate(String username, int passwordHash){
        try (Scanner scanner = new Scanner(new File(DatabaseFile)) ) {
            String[] data;
            String hash = String.valueOf(passwordHash);
            scanner.nextLine(); // Ignore header text
            while (scanner.hasNextLine()){
                data = scanner.nextLine().split(",");
                if (username.equals(data[0])){
                    if (hash.equals(data[1])){
                        Main.SessionAccount = new Guest(username, passwordHash);
                        return 0;
                    }
                    else {
                        return 1;
                    }
                }
            }
        } catch (FileNotFoundException e){
            System.out.println(DATABASE_NOT_FOUND);
            e.printStackTrace();
        }
        return 1;
    }
}
