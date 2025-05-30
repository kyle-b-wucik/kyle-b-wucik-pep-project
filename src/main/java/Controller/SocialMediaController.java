package Controller;

import DAO.AccountDAO;
import Model.Account;
import Service.AccountService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    
    private AccountService accountService;

    public SocialMediaController() {
        this.accountService = new AccountService(new AccountDAO());
    }

    public Javalin startAPI() {
        Javalin app = Javalin.create();

        app.post("/register", this::registerHandler);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void registerHandler(Context context) {
        Account incomingAccount = context.bodyAsClass(Account.class);

        if (incomingAccount.getUsername() == null || incomingAccount.getUsername().isBlank() ||
            incomingAccount.getPassword() == null || incomingAccount.getPassword().length() < 4) {
            context.status(400);
            return;
        }

        Account createdAccount = accountService.register(incomingAccount);

        if(createdAccount == null) {
            context.status(400);
            return;
        }

        context.json(createdAccount);

    }

}