package controllers;



import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import dtos.*;
import exceptions.*;
import models.*;
import security.*;
import services.*;
import utils.Logger;

import javax.servlet.http.*;
import java.io.*;
import java.util.*;


public class UserController {

    private final Logger logger = Logger.getLogger();
    private final UserService userService;
    private final JwtConfig jwtConfig;
    private final JwtService jwtService;

    public UserController(UserService userService, JwtConfig jwtConfig, JwtService jwtService) {
        this.userService = userService;
        this.jwtConfig = jwtConfig;
        this.jwtService = jwtService;

    }


    public void register(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");

        try {

            //Acquire user information using a DTO
            UserInformation userInfo = mapper.readValue(req.getInputStream(), UserInformation.class);

            // 2. construct an AppUser with the mapped DTO
            AppUser appUser = new AppUser(
                    userInfo.getUsername(),
                    userInfo.getPassword(),
                    userInfo.getEmail(),
                    userInfo.getFirstName(),
                    userInfo.getLastName(),
                    userInfo.getDob());

            ArrayList<Object> registeredUser = userService.verifyRegistration(appUser);

            //using Jackson to map the user into a JSON
            writer.write(mapper.writeValueAsString(registeredUser));

        }catch(ResourceInvalidException | UsernameUnavailableException | EmailUnavailableException e){
            resp.setStatus(400);
            writer.write(e.getMessage());
        }catch(Exception e){
            resp.setStatus(500);
            e.printStackTrace();
        }
    }




    //TODO implement authenticate return void
    public boolean authenticate(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        boolean result = false;
        ObjectMapper mapper = new ObjectMapper();
        PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");

        try {
            Credentials creds = mapper.readValue(req.getInputStream(), Credentials.class);

            String username = creds.getUsername();
            String password = creds.getPassword();

            AppUser authenticatedUser = userService.authenticateUserCredentials(username, password);

            if(authenticatedUser != null){
                result = true;
                String token = jwtService.createJwt(authenticatedUser);
                resp.setHeader(jwtConfig.getHEADER(), token);

            }

            if(!result)
                writer.write("Authentication failed!");
            else
                writer.write("Authentication succeeded!");


        }catch(AuthenticationException e){
            resp.setStatus(401);
            e.printStackTrace();
            writer.write(e.getMessage());
        }catch(AuthenticationFailedException e){
            resp.setStatus(401);
            writer.write(e.getMessage());
        } catch(Exception e){
            resp.setStatus(500);
            writer.write("Something went wrong!");
            e.printStackTrace();
        }



        return result;
    }

    /*
    Delete user based on unique value.
     */
    public void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");

        try{
            AppUser appUser = mapper.readValue(req.getInputStream(), AppUser.class);

            logger.info("Verifying deletion permission...");
            //TODO: add boolean method userService.verifyDeletion(appUser);

            logger.info("Attempting to delete user...");



            //AppUser appuser1 = repo.select(appUser);
            /*ArrayList<Object> userArray = repo.select(appUser);
            writer.write(mapper.writeValueAsString(userArray));*/
            //writer.write(mapper.writeValueAsString(appUser));
            //repo.delete(appUser);

        } catch (MismatchedInputException e){
            logger.warn(e.getMessage());
            writer.write("Mismatched input error!");
            resp.setStatus(400);
        }
    }

}
