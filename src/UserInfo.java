public class UserInfo {
    String userId;
    String password;

    public UserInfo(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    };

    public String getUserPassword() {
        return password;
    };
}
