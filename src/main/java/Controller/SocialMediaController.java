package Controller;

import DAO.AccountDAO;
import DAO.MessageDAO;

import Model.Account;
import Model.Message;

import Service.AccountService;
import Service.MessageService;

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
    private MessageService messageService;

    public SocialMediaController() {
        this.accountService = new AccountService(new AccountDAO());
        this.messageService = new MessageService(new MessageDAO());
    }

    public Javalin startAPI() {
        Javalin app = Javalin.create();

        //posts
        app.post("/register", this::registerHandler);
        app.post("/login", this::loginHandler);
        app.post("/messages", this::messageHandler);

        //gets
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByIdHandler);
        app.get("/accounts/{account_id}/messages", this::getMessageByUserHandler);

        //deletes
        app.delete("/messages/{message_id}", this::deleteMessageHandler);

        //patches
        app.patch("/messages/{message_id}", this::updateMessageHandler);

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

    private void loginHandler(Context context) {
        Account loginAttempt = context.bodyAsClass(Account.class);
        Account verifiedAccount = accountService.login(loginAttempt);

        if (verifiedAccount != null) {
            context.json(verifiedAccount);
            context.status(200); // ok - default
        } 
        
        else {
            context.status(401); //unauthorized
        }

    }

    private void messageHandler(Context context) {
        Message incomingMessage = context.bodyAsClass(Message.class);
        Message savedMessage = messageService.makeMessage(incomingMessage);

        if (savedMessage == null) {
            context.status(400);
        }
        else {
            context.json(savedMessage);
            context.status(200);
        }
    }

    private void getAllMessagesHandler(Context context) {
        return null;
    }

    private void getMessageByIdHandler(Context context) {
        return null;
    }

    private void deleteMessageHandler(Context context) {
        return null;
    }

    private void getMessageByUserHandler(Context context) {
        return null;
    }

    private void updateMessageHandler(Context context) {
        return null;
    }

}