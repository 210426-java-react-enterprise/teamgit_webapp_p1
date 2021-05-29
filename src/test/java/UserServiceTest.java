import models.*;
import org.junit.*;
import repos.*;
import services.*;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    private UserService sut;
    private Repo mockRepo;

    @Before
    public void setUp(){
        mockRepo = mock(Repo.class);
        sut = new UserService(mockRepo);
    }

    @After
    public void tearDown(){
        //tear down every mock or sut after each test run
        sut = null;
        mockRepo = null;
    }

    @Test
    public void test_verifyRegistrationAvailableUsernamePassword(){
        when(mockRepo.select(any())).thenReturn(new ArrayList<Object>());


        AppUser user = new AppUser("swekevin"
                ,"password123","kevin@revature.net"
                ,"Kevin","Chang", "1999-11-09");

        sut.verifyRegistration(user);

        verify(mockRepo, times(3)).select(any());
        verify(mockRepo, times(1)).create(any());
        verify(mockRepo, times(1)).insert(any());

    }



    //Tests if the isUserValid() method in UserService working
    @Test
    public void test_IsUserValidWithInvalidCredentials() {
        boolean isEmpty = false;
        String expected = "Username, Password, Email, First name, Last name, Date of birth input(s) were not valid.";
        AppUser user = new AppUser("sw"
                ,"pa2","kevinrevature.net"
                ,"","Ch2ang", "1999-11-091");
        if(sut.isUserValid(user).equals("")){
            isEmpty = true;
        }
        assertFalse(isEmpty);
        assertEquals(expected, "Username, Password, Email, First name, Last name, Date of birth input(s) were not valid.");

    }


    @Test
    public void test_isUserValidWithValidCredentials(){
        //Assert
        //test valid AppUser
        assertEquals("", sut.isUserValid(new AppUser("swekevin"
                ,"password123","kevin@revature.net"
                ,"Kevin","Chang", "1999-11-09")));
    }



    @Test
    public void test_verifyDeposit(){ }


    @Test
    public void test_verifyWithdrawal(){ }


    @Test
    public void test_authenticateUserCredentials(){
        AppUser appUser = new AppUser("swekevin"
                ,"password123","kevin@revature.net"
                ,"Kevin","Chang", "1999-11-09");

        String username = "swekevin";
        String password = "password123";

        ArrayList<Object> mockArray = new ArrayList<>();
        mockArray.add(appUser);

        when(mockRepo.select(any())).thenReturn(mockArray);
        assertTrue(sut.authenticateUserCredentials(username, password));

        password = "bsPassword";//password doesn't match
        assertFalse(sut.authenticateUserCredentials(username, password));

        username = "bsUsername";//username doesn't match
        password = "password123";//password matches
        assertFalse(sut.authenticateUserCredentials(username, password));

        password = "bsPassword";//neither username or password should match at this point
        assertFalse(sut.authenticateUserCredentials(username, password));

        verify(mockRepo, times(4)).select(any());
    }


    @Test
    public void test_verifyUserDeletion() throws IllegalAccessException {
        AppUser appUser = new AppUser("swekevin"
                ,"password123","kevin@revature.net"
                ,"Kevin","Chang", "1999-11-09");

        when(mockRepo.delete(appUser)).thenReturn(1);
        boolean value1 = sut.verifyDeletion(appUser);//delete from database
        assertTrue(value1);

        when(mockRepo.delete(any())).thenReturn(0);

        AppUser appUser2 = new AppUser();
        value1 = sut.verifyDeletion(appUser2);//won't run delete for null
        assertFalse(value1);

        appUser2.setUsername("tester");
        value1 = sut.verifyDeletion(appUser2);//will run delete, but won't delete anything
        assertFalse(value1);

        appUser2.setUsername(null);
        appUser2.setEmail("test@revature.net");
        value1 = sut.verifyDeletion(appUser2);//same as above
        assertFalse(value1);

        appUser2.setUsername("tester");
        value1 = sut.verifyDeletion(appUser2);//will run delete, but won't delete as it's not in database
        assertFalse(value1);

        verify(mockRepo, times(4)).delete(any());
    }


}
