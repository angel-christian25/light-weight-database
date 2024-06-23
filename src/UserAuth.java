import java.util.List;
import java.util.Random;

public class UserAuth {
    public boolean register(String userId, String password){
        UserCSVConversion userCSV = new UserCSVConversion();
        List<UserInfo> users = userCSV.readUsersFromCSVFile();
        Boolean userExists = false;
        if (users != null) {
            for(UserInfo user: users){
                if(user.getUserId().equals(userId)){
                    userExists = true;
                    break;
                }
            }
        }

        if(!userExists) {
            userCSV.addUserToCSVFile(userId, password);
            return true;
        }
        return false;
    }

    public UserInfo login(String userId, String password) {
        UserCSVConversion userCSV = new UserCSVConversion();
        List<UserInfo> users = userCSV.readUsersFromCSVFile();

        if(users != null) {
            for(UserInfo user: users){
                if(user.getUserId().equals(userId) && user.getUserPassword().equals(password)){
                    return user;
                }
            }
        }
        return null;
    }

    protected String generateCaptcha() {
        String captchaChars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        char[] captcha = new char[6];
        Random rnd = new Random();
        for(int i = 0; i < captcha.length; i++ ){
            int randomIndex = rnd.nextInt(61);
            captcha[i] = captchaChars.charAt(randomIndex);
        }
        return new String(captcha);
    }

    public Boolean matchCaptcha(String captcha, String userCaptcha) {
        return captcha.equals(userCaptcha);
    }
}
