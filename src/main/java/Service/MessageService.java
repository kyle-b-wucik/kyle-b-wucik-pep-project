package Service;
import java.util.List;

import DAO.MessageDAO;
import Model.Message;

public class MessageService {
    private MessageDAO messageDAO;

    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    public Message makeMessage(Message message) {
        
        //null check
        if (message == null) {
            return null;
        }

        //checks to make sure message isnt blank
        if (message.getMessage_text() == null || message.getMessage_text().isBlank()) {
            return null;
        }
        //checks to make sure message is under 255 chars
        if(message.getMessage_text().length() >= 255) {
            return null;
        }

        //if all checks out return to DAO
        return messageDAO.insertMessage(message);

    }

    public List<Message> getAllMessages() {

        return messageDAO.getAllMessages();

    }

    public Message getMessageById(int messageId) {
        Message existingMessage = messageDAO.getMessageById(messageId);

        return existingMessage;
    }

    public Message deleteMessage(int messageId) {
        Message deletedMessage = messageDAO.deleteMessage(messageId);

        return deletedMessage;
    }

    public List<Message> getMessageByUser(int messageUser) {
        return messageDAO.getMessageByUser(messageUser);
    }

    public Message updateMessage(int messageId, String newMessageText) {

        if (newMessageText == null || newMessageText.isBlank()) {
            return null;
        }

        if (newMessageText.length() >= 255) {
            return null;
        }

        Message updatedMessage = messageDAO.updateMessage(messageId, newMessageText);

        return updatedMessage;
    }
}
