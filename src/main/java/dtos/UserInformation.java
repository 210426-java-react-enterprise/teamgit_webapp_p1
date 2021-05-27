package dtos;


public class UserInformation {

    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String dob;


    public UserInformation(){
        super();
    }

    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getEmail() {
        return email;
    }
    public String getDob() {
        return dob;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setDob(String dob) {
        this.dob = dob;
    }

    @Override
    public String toString() {
        return "AppUser{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dob='" + dob + '\'' +
                '}';
    }
}

