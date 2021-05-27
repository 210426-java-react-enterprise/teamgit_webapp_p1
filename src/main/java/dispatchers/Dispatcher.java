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
            case "/teamgit-webapp-p1/users.data":
                userController.register(req, resp);
                break;
            case "/teamgit-webapp-p1/auth.data":
                userController.authenticate(req, resp);
                break;
            case "/teamgit-webapp-p1/delete.data":
                userController.delete(req, resp);
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
