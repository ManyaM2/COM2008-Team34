public class User {

    private String userForename;
    private String userSurname;

    public String getUserForename() {
        return userForename;
    }

    public String getUserSurname() {
        return userSurname;
    }

    public User (String forename, String surname) {
        this.userForename = forename;
        this.userSurname = surname;
    }
}
