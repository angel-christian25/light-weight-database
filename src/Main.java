import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        UserCSVConversion csvConversion = new UserCSVConversion();
        csvConversion.setCSVFilePath("users_data.txt");
        UserAuth userAuth = new UserAuth();
        UserQuery userQuery = new UserQuery();
        try (Scanner userInput = new Scanner(System.in)) {
            System.out.println("1. Sign Up");
            System.out.println("2. Sign In");
            System.out.println("3. Exit");
            System.out.print("Select an option from above: ");
            int userChoice = userInput.nextInt();

            switch (userChoice) {
                case 1:
                    System.out.println("Enter UserId: ");
                    String userId = userInput.next();
                    System.out.println("Enter Password: ");
                    String password = userInput.next();
                    if (userAuth.register(userId, password)) {
                        System.out.print("User successfully registered!");
                    } else {
                        System.out.print("User already exists");
                    }
                    break;
                case 2:
                    System.out.println("Enter Username: ");
                    String userIdForLogin = userInput.next();
                    System.out.println("Enter Password: ");
                    String passwordForLogin = userInput.next();
                    System.out.println("Enter Captcha shown below:");
                    String captcha = userAuth.generateCaptcha();
                    System.out.println(captcha);
                    String userCaptcha = userInput.next();

                    UserInfo user = userAuth.login(userIdForLogin, passwordForLogin);
                    Boolean doesCaptchMatches = userAuth.matchCaptcha(captcha, userCaptcha);
                    if (user != null) {
                        if (doesCaptchMatches) {
                            System.out.println("Logged in" + " " + user.getUserId());
                            System.out.print("Select an option from below: ");
                            System.out.println("1. Open SQL Workbench and write query");
                            System.out.println("2. Logout");
                            int loggedInUserChoice = userInput.nextInt();
                            userQuery.performQuery(loggedInUserChoice);

                        } else {
                            System.out.println("Captcha didn't match!");
                        }
                    } else {
                        System.out.println("User doesn't exist or Credentials don't match");
                    }
                    break;
                case 3:
                    System.out.println("Ok bye!");
                    break;
            }
        }

        ;
    }
}