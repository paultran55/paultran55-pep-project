package Service;
import DAO.MessageDAO;
import Model.Message;
import java.util.List;
import DAO.AccountDAO;
import Model.Account;

public class MessageService {
    private MessageDAO messageDAO;
    private AccountDAO accountDAO;

    public MessageService() {
        this.messageDAO = new MessageDAO();
        this.accountDAO = new AccountDAO();
    }

    public Message createMessage(Message message) {
        if (message.message_text == null || message.message_text.isBlank() || message.message_text.length() > 255) {
            throw new IllegalArgumentException("");
        }

        Account account = accountDAO.getAccountByID(message.getPosted_by());
        if (account == null) {
            throw new IllegalArgumentException("");
    }
        return messageDAO.createMessage(message);
    }

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    public Message getMessageById(int messageId) {
        Message message = messageDAO.getMessageById(messageId);
        if (message == null) {
            throw new IllegalArgumentException("");
        }
        return message;
    }

    public Message deleteMessage(int messageId) {
        Message deletedMessage = messageDAO.deleteMessage(messageId);
        if (deletedMessage == null) {
            throw new IllegalArgumentException("");
        }
        return deletedMessage;
    }
    public Message updateMessage(int messageId, String newMessageText) {
        if (newMessageText == null || newMessageText.isEmpty() || newMessageText.length() > 255) {
            throw new IllegalArgumentException("");
        }
        if(messageDAO.getMessageById(messageId) == null){
            throw new IllegalArgumentException("");
        }
        return messageDAO.updateMessage(messageId, newMessageText);
    }

    public List<Message> getMessagesByUser(int accountId) {
        return messageDAO.getMessagesByUser(accountId);
    }
}
