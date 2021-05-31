import dtos.Principal;
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
    private Principal mockPrincipal;

    @Before
    public void setUp(){
        mockRepo = mock(Repo.class);
        sut = new UserService(mockRepo);
        mockPrincipal = mock(Principal.class);
    }

    @After
    public void tearDown(){
        //tear down every mock or sut after each test run
        sut = null;
        mockRepo = null;
        mockPrincipal = null;
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

        final AppUser user2 = new AppUser();
        assertThrows(NullPointerException.class, () -> sut.isUserValid(user2));

        final AppUser user3 = new AppUser("starwarskid"
                ,"pentiumAmmonium",""
                ,"Somebody","Zero", "1999-11-01");
        if(sut.isUserValid(user).equals("")){
            isEmpty = true;
        }
        assertFalse(isEmpty);
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
        assertEquals(appUser, sut.authenticateUserCredentials(username, password));

        password = "bsPassword";//password doesn't match
        assertNull(sut.authenticateUserCredentials(username, password));

        username = "bsUsername";//username doesn't match
        password = "password123";//password matches
        assertNull(sut.authenticateUserCredentials(username, password));

        password = "bsPassword";//neither username or password should match at this point
        assertNull(sut.authenticateUserCredentials(username, password));
        verify(mockRepo, times(4)).select(any());
    }


    @Test
    public void test_verifyUserDeletion() throws IllegalAccessException {
        AppUser appUser = new AppUser("swekevin"
                ,"password123","kevin@revature.net"
                ,"Kevin","Chang", "1999-11-09");

        AppUser appUserWithId = new AppUser("swekevin"
                ,"password123","kevin@revature.net"
                ,"Kevin","Chang", "1999-11-09");
        appUserWithId.setId(1);

        when(mockPrincipal.getId()).thenReturn(1);
        when(mockPrincipal.getUsername()).thenReturn(appUser.getUsername());
        AppUser.Role role = AppUser.Role.BASIC_USER;
        when(mockPrincipal.getRole()).thenReturn(role);


        //TEST SUCCESSFUL VALIDATION AND DELETION OF 1 USER FROM DATABASE
        when(mockRepo.delete(appUser)).thenReturn(1);//0 means nothing deleted, > 0 means something was deleted
        ArrayList<Object> appUserList = new ArrayList<>();
        appUserList.add(appUserWithId);
        when(mockRepo.select(appUser)).thenReturn(appUserList);
        boolean userVerified = sut.verifyDeletion(appUser);//verify that user is in database and can be deleted
        assertTrue(userVerified);
        boolean tokenVerified = sut.verifyToken(mockPrincipal, appUser);
        assertTrue(tokenVerified);
        int rowsDeleted = sut.doDeletion(appUser);
        assertEquals(1, rowsDeleted);


        //TEST UNSUCCESSFUL USER VALIDATION
        when(mockRepo.delete(appUser)).thenReturn(0);//nothing would be deleted
        appUser.setUsername(null);
        appUser.setEmail(null);
        appUser.setId(0);//invalid Id
        appUserList.set(0, appUser);//update what is returned from select
        userVerified = sut.verifyDeletion(appUser);
        assertFalse(userVerified);

        
        //TEST UNSUCCESSFUL TOKEN VALIDATION
        when(mockPrincipal.getId()).thenReturn(0);
        tokenVerified = sut.verifyToken(mockPrincipal, appUser);
        assertFalse(tokenVerified);

        verify(mockRepo, times(1)).delete(any());//make sure delete was only ran once
    }


}
