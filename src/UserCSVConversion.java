import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserCSVConversion {
    List<UserInfo> usersList = new ArrayList<>();
    static String csvFilePath;

    public void setCSVFilePath(String csvFilePath) {
        UserCSVConversion.csvFilePath = csvFilePath;
    }

    public void addUserToCSVFile(String userId, String password) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFilePath, true))) {
            String userRecord = userId + "," + password;
            writer.write(userRecord);
            writer.newLine();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public List<UserInfo> readUsersFromCSVFile() {
        try {
            FileReader file = new FileReader(csvFilePath);
            try (BufferedReader reader = new BufferedReader(file)) {
                String fileLine = reader.readLine();
                while ((fileLine) != null) {
                    String[] parts = fileLine.split(",");
                    if (parts.length == 2) {
                        String username = parts[0];
                        String password = parts[1];
                        usersList.add(new UserInfo(username, password));
                    }
                    fileLine = reader.readLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return usersList;
    }
}
