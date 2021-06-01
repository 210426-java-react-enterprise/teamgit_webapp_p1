package controllers;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import dtos.*;
import exceptions.*;
import io.jsonwebtoken.Claims;
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


    /**
     * Registers a new user into the database.
     * @param req HttpServletRequest
     * @param resp HttpServletResponse
     * @throws IOException
     */
    public void register(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");

        String errorMessage = "Null values were inputted, and are not valid!";

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

            userService.isUserValid(appUser);

            ArrayList<Object> registeredUser = userService.verifyRegistration(appUser);

            //using Jackson to map the user into a JSON
            writer.write(mapper.writeValueAsString(registeredUser));

        }catch(ResourceInvalidException | UsernameUnavailableException | EmailUnavailableException e){
            resp.setStatus(400);
            writer.write(e.getMessage());
        }catch(NullPointerException e){
            resp.setStatus(400);
            writer.write(errorMessage);
        }catch (Exception e){
            resp.setStatus(500);
            e.printStackTrace();
        }
    }


    /**
     * Ensures the username and matching password is in the database.
     * @param req HttpServletRequest
     * @param resp HttpServletResponse
     * @throws IOException
     */
    public void authenticate(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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
        //return result;
    }


    /**
     * Deletes user data based on their username or email.
     * @param req HttpServletRequest
     * @param resp HttpServletResponse
     * @throws IOException in case input isn't valid
     */
    public void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");

        try{
            AppUser appUser = mapper.readValue(req.getInputStream(), AppUser.class);

            logger.info("Verifying deletion permission...");

            if(userService.verifyDeletion(appUser)) {//if user can be deleted, will set user's id from database
                jwtService.parseToken(req);
                //Claims jwtAttribute = (Claims) req.getUserPrincipal();
                Principal principal = (Principal) req.getAttribute("principal");
                if(userService.verifyToken(principal, appUser)) {
                    userService.doDeletion(appUser);//do deletion
                    writer.write("User data successfully deleted.");
                }else{
                    throw new IllegalAccessException();
                }
            }else{
                throw new IllegalAccessException();
            }

        } catch (MismatchedInputException e){
            logger.warn(e.getMessage());
            writer.write("Mismatched input error!  User data not deleted!");
            resp.setStatus(400);
        } catch (IllegalAccessException | IllegalArgumentException e){
            logger.warn(e.getMessage());
            writer.write("You are not authorized to delete this!  User data not deleted!");
            resp.setStatus(401);
        }
    }

}
