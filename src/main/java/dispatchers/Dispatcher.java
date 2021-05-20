package dispatchers;

import controllers.*;

import javax.servlet.http.*;
import java.io.*;

public class Dispatcher {
    private Controller controller = new Controller();

    public void dataDispatch(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        switch(req.getRequestURI()){
            case "/teamgit_webapp_p1/users.data":
                controller.register(req, resp);
                break;

            case "/teamgit_webapp_p1/auth.data":
                controller.authenticate(req, resp);
                break;

            case "/teamgit_webapp_p1/deposit.data":
                controller.validateDeposit(req, resp);
                break;

            case"/teamgit_webapp_p1/withdraw.data":
                controller.validateWithdrawal(req, resp);
                break;
            default:
                resp.setStatus(400);
                resp.getWriter().println("The request uri was not recognized!");

        }
    }

}
