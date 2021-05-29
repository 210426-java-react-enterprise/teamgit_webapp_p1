package dispatchers;

import controllers.*;

import javax.servlet.http.*;
import java.io.*;

public class Dispatcher {
    private UserController userController;
    private TransactionController transactionController;

    public Dispatcher(UserController userController, TransactionController transactionController) {
        this.userController = userController;
        this.transactionController = transactionController;
    }

    public void dataDispatch(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        switch(req.getRequestURI()){
            //http://localhost:8080/teamgit-webapp-p1/users.data
            /*
                {
                    "username":"edwardboy9",
                    "password":"edwardboy123",
                    "email":"edwardboy9@edward.com",
                    "firstName":"Edward",
                    "lastName":"Boy",
                    "dob":"1996-12-10"
                 }
             */
            case "/teamgit-webapp-p1/users.data":
                userController.register(req, resp);
                break;
            case "/teamgit-webapp-p1/auth.data":
                userController.authenticate(req, resp);
                break;

            case "/teamgit-webapp-p1/deposit.data":
                transactionController.validateDeposit(req, resp);
                break;

            case"/teamgit-webapp-p1/withdraw.data":
                transactionController.validateWithdrawal(req, resp);
                break;
            default:
                resp.setStatus(400);
                resp.getWriter().println("The request uri was not recognized!");

        }
    }

}
