package Controller;

import java.util.List;

import DAO.AccountDAO;
import DAO.MessageDAO;

import Model.Account;
import Model.Message;

import Service.AccountService;
import Service.MessageService;

import io.javalin.Javalin;
import io.javalin.http.Context;

public class SocialMediaController {

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

    //*----------------HANDLERS----------------*//
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
        List<Message> allMessages = messageService.getAllMessages();

        context.json(allMessages);
        context.status(200);

    }

    private void getMessageByIdHandler(Context context) {
        String messageIdStr = context.pathParam("message_id");
        int messageId = Integer.parseInt(messageIdStr);

        Message foundMessage = messageService.getMessageById(messageId);

        if (foundMessage != null) {
            context.json(foundMessage);
        }
        context.status(200);
    }

    private void deleteMessageHandler(Context context) {
        String messageIdStr = context.pathParam("message_id");
        int messageId;

        try {
            messageId = Integer.parseInt(messageIdStr);
        } catch (NumberFormatException e) {
            context.status(400);
            return;
        }

        Message deletedMessage = messageService.deleteMessage(messageId);
        if (deletedMessage != null) {
            context.json(deletedMessage);
        }
        
        context.status(200);
    }

    private void getMessageByUserHandler(Context context) {
        String messageUserStr = context.pathParam("account_id");
        int messageUser;
        
        try {
            messageUser = Integer.parseInt(messageUserStr);
        } catch (NumberFormatException e) {
            context.status(400);
            return;
        }

        List<Message> foundMessages = messageService.getMessageByUser(messageUser);

        context.json(foundMessages);
        context.status(200);
    }

    private void updateMessageHandler(Context context) {
        String messageIdStr = context.pathParam("message_id");
        String newMessageText;
        int messageId;

        try {
            messageId = Integer.parseInt(messageIdStr);
            Message messageFromBody = context.bodyAsClass(Message.class);
            newMessageText = messageFromBody.getMessage_text();
        } catch (NumberFormatException e) {
            context.status(400);
            return;
        }

        Message updatedMessage = messageService.updateMessage(messageId, newMessageText);
        if(updatedMessage != null) {
            context.json(updatedMessage);
            context.status(200);
        }
        else {
            context.status(400);
        }
    }

}