package dispatchers;

import controllers.*;
import services.UserService;

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
            case "/users.data":
            case "/teamgit-webapp-p1/users.data":
                userController.register(req, resp);
                break;
            case "/auth.data":
            case "/teamgit-webapp-p1/auth.data":
                userController.authenticate(req, resp);
                break;
            case "/delete.data":
            case "/teamgit-webapp-p1/delete.data":
                userController.delete(req, resp);
                break;
            case "/deposit.data":
            case "/teamgit-webapp-p1/deposit.data":
                transactionController.deposit(req, resp);
                break;
            case"/withdraw.data":
            case"/teamgit-webapp-p1/withdraw.data":
                transactionController.withdrawal(req, resp);
                break;
            case"/balance.data":
            case"/teamgit-webapp-p1/balance.data":
                transactionController.balance(req, resp);
                break;
            case"/transactions.data":
            case"/teamgit-webapp-p1/transactions.data":
                transactionController.transactions(req, resp);
                break;
            default:
                resp.setStatus(400);
                resp.getWriter().println("The request uri was not recognized!");

        }
    }

}
