
import exceptions.*;
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

    @Test
    public void test_AuthenticateUserCorrectCredentials(){
        ArrayList<Object> arr = new ArrayList<>();
        AppUser user = new AppUser("swekevin"
                ,"password123","kevin@revature.net"
                ,"Kevin","Chang", "1999-11-09");
        user.setRole(AppUser.Role.BASIC_USER);

        arr.add(user);
        when(mockRepo.select(any())).thenReturn(arr);


        assertEquals(user, sut.authenticateUserCredentials("swekevin", "password123"));
    }

    @Test(expected = AuthenticationFailedException.class)
    public void test_AuthenticateUserWrongCredentials(){
        when(mockRepo.select(any())).thenReturn(new ArrayList<Object>(1));

        sut.authenticateUserCredentials("123", "123");
    }

    @Test(expected = AuthenticationException.class)
    public void test_AuthenticateUserNullCredentials(){
        sut.authenticateUserCredentials(null, null);
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
    public void test_validateDepositValid() throws IllegalAccessException {
        try {
            sut.validateDeposit(21.12);

        } catch (NegativeDepositException e){
            verify(mockRepo, times(1)).update(any());
        }
    }

    @Test
    public void test_validateDepositNegative() throws IllegalAccessException {
        try {
            sut.validateDeposit(-21.12);

        } catch (NegativeDepositException e){
            verify(mockRepo, times(0)).update(any());
        }
    }

    @Test
    public void test_validateWithdrawalValid() throws IllegalAccessException {
        try {
            sut.validateWithdrawPos(21.12);
        } catch (NegativeWithdrawalException | AttemptedOverdraftException e){
            verify(mockRepo, times(1)).update(any());
        }
    }

    @Test
    public void test_validateWithdrawalNegative() throws IllegalAccessException {
        try {
            sut.validateWithdrawPos(-21.12);
        } catch (NegativeWithdrawalException | AttemptedOverdraftException e){
            verify(mockRepo, times(0)).update(any());
        }
    }


    @Test
    public void test_validateWithdrawalOverdraft() throws IllegalAccessException {
        try {
            sut.validateWithdrawBal(40.00, 41.12);
        } catch (NegativeWithdrawalException | AttemptedOverdraftException e) {
            verify(mockRepo, times(0)).update(any());
        }
        verify(mockRepo, times(0)).update(any());
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