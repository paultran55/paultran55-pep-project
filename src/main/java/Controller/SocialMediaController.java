package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

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

     ObjectMapper mapper = new ObjectMapper();

     public SocialMediaController() {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

     
    public Javalin startAPI() {
        /*
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);
        */
        Javalin app = Javalin.create();
        app.post("/register", this::handleRegister);
        app.post("/login", this::handleLogin);
        app.post("/messages", this::handleCreateMessage);
        app.get("/messages", this::handleGetAllMessages);
        app.get("/messages/{message_id}", this::handleGetMessageById);
        app.delete("/messages/{message_id}", this::handleDeleteMessage);
        app.patch("/messages/{message_id}", this::handleUpdateMessage);
        app.get("/accounts/{account_id}/messages", this::handleGetMessagesByUser);

        return app;


    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    /*
    private void exampleHandler(Context context) {
        context.json("sample text");
    }
    */

    private void handleRegister(Context cxt) throws JacksonException{
        try{
            Account account = cxt.bodyAsClass(Account.class);

            Account newacc = accountService.createAccount(account);

            cxt.json(mapper.writeValueAsString(newacc));
            cxt.status(200);
        }catch(IllegalArgumentException e){
            cxt.status(400).json(e.getMessage());
        }
    }

    private void handleLogin(Context cxt) throws JacksonException{
        try{
            Account account = cxt.bodyAsClass(Account.class);

            Account login = accountService.login(account);

            cxt.json(mapper.writeValueAsString(login));
            cxt.status(200);
        }catch (IllegalArgumentException e) {
            cxt.status(401).json(e.getMessage());
        }
    }

    private void handleCreateMessage(Context cxt) throws JacksonException{
        try {
            Message message = cxt.bodyAsClass(Message.class);

            Message created = messageService.createMessage(message);
            cxt.json(mapper.writeValueAsString(created));
            cxt.status(200);
        } catch (IllegalArgumentException e) {
            cxt.status(400).json("");
        }
    }

    private void handleGetAllMessages(Context cxt){
        cxt.status(200).json(messageService.getAllMessages());
    }

    private void handleGetMessageById(Context cxt){
        try {
            int messageId = Integer.parseInt(cxt.pathParam("message_id"));
            Message message = messageService.getMessageById(messageId);
            cxt.status(200).json(message);
        } catch (IllegalArgumentException e) {
            cxt.status(200).json("");
        }
    }

    private void handleDeleteMessage(Context cxt){
        try{
            int messageId = Integer.parseInt(cxt.pathParam("message_id"));
            Message deletedMessage = messageService.deleteMessage(messageId);
            if(deletedMessage != null){
                cxt.status(200).json(deletedMessage);
            }
            else{
                cxt.status(200).json(null);
            }
        }catch(IllegalArgumentException e){
            cxt.status(200).json("");
        }
    }

    private void handleUpdateMessage(Context cxt) throws JacksonException{
        try{
            int messageId = Integer.parseInt(cxt.pathParam("message_id"));
            String newMessageText = cxt.formParam("message_text");

            Message updatedMessage = messageService.updateMessage(messageId, newMessageText);
            cxt.json(updatedMessage);
            cxt.status(200).json(updatedMessage);
        } catch (IllegalArgumentException e) {
            cxt.status(400).json(e.getMessage());
        }
    }

    private void handleGetMessagesByUser(Context cxt) {
        try {
            int accountId = Integer.parseInt(cxt.pathParam("account_id"));
            List<Message> messages = messageService.getMessagesByUser(accountId);
            cxt.status(200).json(messages);
        } catch (IllegalArgumentException e) {
            cxt.status(400).json(e.getMessage());
        }
    }

}