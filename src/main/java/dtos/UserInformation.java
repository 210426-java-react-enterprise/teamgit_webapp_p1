package dtos;

import annotations.*;

@Entity()
@Connection(url = "jdbc:postgresql://database-swekevin.cwvfowetr0c7.us-east-1.rds.amazonaws.com:5432/postgres?currentSchema=project1",
        //url = "jdbc:postgresql://project0-accounts.comfkmj3hfze.us-west-1.rds.amazonaws.com:5432/postgres?currentSchema=public",
        //username = "thomas",
        username = "postgres",
        password = "revature")
@Table(name = "users")
public class UserInformation {


    @Column(name = "username", nullable = false, unique = true, type = "varchar", length = "20", updateable = false)
    private String username;

    @Column(name = "password", nullable = false, unique = false, type = "varchar", length = "255", updateable = true)
    private String password;

    @Column(name = "email", nullable = false, unique = true, type = "varchar", length = "255", updateable = false)
    private String email;

    @Column(name = "first_name", nullable = false, unique = false, type = "varchar", length = "25", updateable = true)
    private String firstName;

    @Column(name = "last_name", nullable = false, unique = false, type = "varchar", length = "25", updateable = true)
    private String lastName;


    @Column(name = "dob", nullable = false, unique = false, type = "date", updateable = false)
    private String dob;


    //dob should be YYYY-MM-DD format, regex
    @Constructor(name = "users")
    public UserInformation(String username, String password, String email, String firstName, String lastName, String dob) {
        System.out.println("Registering user...");
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
    }

    @Getter(name = "username")
    public String getUsername() {
        return username;
    }

    @Getter(name = "password")
    public String getPassword() {
        return password;
    }
    @Getter(name = "first_name")
    public String getFirstName() {
        return firstName;
    }
    @Getter(name = "last_name")
    public String getLastName() {
        return lastName;
    }
    @Getter(name = "email")
    public String getEmail() {
        return email;
    }

    @Getter(name = "dob")
    public String getDob() {
        return dob;
    }

    //Setters
    @Setter(name = "username")
    public void setUsername(String username) {
        this.username = username;
    }

    @Setter(name = "password")
    public void setPassword(String password) {
        this.password = password;
    }

    @Setter(name = "email")
    public void setEmail(String email) {
        this.email = email;
    }

    @Setter(name = "first_name")
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Setter(name = "last_name")
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Setter(name = "dob")
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

