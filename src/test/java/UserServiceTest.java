import exceptions.AttemptedOverdraftException;
import exceptions.NegativeDepositException;
import exceptions.NegativeWithdrawalException;
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
    public void test_validateDepositValid() {
        try {
            sut.validateDeposit(21.12);

        } catch (NegativeDepositException e){
            verify(mockRepo, times(1)).update(any());
        }
    }

    @Test
    public void test_validateDepositNegative(){
        try {
            sut.validateDeposit(-21.12);

        } catch (NegativeDepositException e){
            verify(mockRepo, times(0)).update(any());
        }
    }

    @Test
    public void test_validateWithdrawalValid() {
        try {
            sut.validateWithdrawPos(21.12);
        } catch (NegativeWithdrawalException | AttemptedOverdraftException e){
            verify(mockRepo, times(1)).update(any());
        }
    }

    @Test
    public void test_validateWithdrawalNegative(){
        try {
            sut.validateWithdrawPos(-21.12);
        } catch (NegativeWithdrawalException | AttemptedOverdraftException e){
            verify(mockRepo, times(0)).update(any());
        }
    }


    @Test
    public void test_validateWithdrawalOverdraft() {
        try {
            UserAccount userAccount = new UserAccount(1, 1, 40.00);
            sut.validateWithdrawPos(41.12);
        } catch (NegativeWithdrawalException | AttemptedOverdraftException e) {
        }
        verify(mockRepo, times(0)).select(any());
        verify(mockRepo, times(0)).update(any());
    }



}