package DAO;

import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {
    
    public Message insertMessage(Message message) {
        
        try{
            Connection connection = ConnectionUtil.getConnection();
            String sql = "insert into message(posted_by, message_text, time_posted_epoch) values (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());

            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if(resultSet.next()) {
                int id = resultSet.getInt(1);
                return new Message(id, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
            }
        }
            catch(Exception e) {
                e.printStackTrace();
            }

            return null;

    }

    public List<Message> getAllMessages() {

        try {
            Connection connection = ConnectionUtil.getConnection();
            String sql = "select * from message";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            List<Message> messages = new ArrayList<>();

            while(resultSet.next()) {
                
                int messageId = resultSet.getInt("message_id");
                int postedBy = resultSet.getInt("posted_by");
                String messageText = resultSet.getString("message_text");
                long timePosted = resultSet.getLong("time_posted_epoch");

                Message message = new Message(messageId, postedBy, messageText, timePosted);
                messages.add(message);

            }

            return messages;
            
        }

        catch(SQLException e) {
            e.printStackTrace();
        }
        
        return new ArrayList<>();
    }

    public Message getMessageById(int messageId) {
        try {
            Connection connection = ConnectionUtil.getConnection();

            String sql = "select * from Message where message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, messageId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                
                int id = resultSet.getInt("message_id");
                int postedBy = resultSet.getInt("posted_by");
                String messageText = resultSet.getString("message_text");
                long timePosted = resultSet.getLong("time_posted_epoch");

                return new Message(id, postedBy, messageText, timePosted);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Message deleteMessage(int messageId) {
        try {
            Message messageToDelete = getMessageById(messageId);

            if (messageToDelete == null) {
                return null;
            }

            Connection connection = ConnectionUtil.getConnection();

            String sql = "delete from Message where message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, messageId);

            int rowsDeleted = preparedStatement.executeUpdate();

            if (rowsDeleted > 0) {
                return messageToDelete;
            }

            else {
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Message> getMessageByUser(int messageUser) {
        try {
            Connection connection = ConnectionUtil.getConnection();

            String sql = "select * from Message where posted_by = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, messageUser);

            ResultSet resultSet = preparedStatement.executeQuery();

            List<Message> messages = new ArrayList<>();

            while(resultSet.next()) {
                
                int messageId = resultSet.getInt("message_id");
                int postedBy = resultSet.getInt("posted_by");
                String messageText = resultSet.getString("message_text");
                long timePosted = resultSet.getLong("time_posted_epoch");

                Message message = new Message(messageId, postedBy, messageText, timePosted);
                messages.add(message);

            }

            return messages;
            
        }

        catch(SQLException e) {
            e.printStackTrace();
        }
        
        return new ArrayList<>();
    }
}