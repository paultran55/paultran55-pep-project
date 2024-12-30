package DAO;

import java.sql.*;
import Util.ConnectionUtil;
import java.util.*;
import Model.Message;

public class MessageDAO {
    

    public Message createMessage(Message message){

        String sql = "INSERT INTO Message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?)";

        try{
            Connection connection = ConnectionUtil.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);

            stmt.setInt(1, message.getPosted_by());
            stmt.setString(2, message.getMessage_text());
            stmt.setLong(3, message.getTime_posted_epoch());

            int updateRows = stmt.executeUpdate();

            
            if(updateRows > 0){
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        int messageId = rs.getInt(1);
                        return new Message(messageId, message.getPosted_by(), message.message_text, message.getTime_posted_epoch());
                    }
                }
            }
                
        }catch(SQLException e){
            e.printStackTrace();
        }

        return message;
    }

    public List<Message> getAllMessages(){
        List<Message> messages = new ArrayList<>();

        try{
            Connection connection = ConnectionUtil.getConnection();
            String sql = "SELECT * FROM Message";
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int messageId = rs.getInt("message_id");
                int postedById = rs.getInt("posted_by");
                String messageText = rs.getString("message_text");
                long timePostedEpoch = rs.getLong("time_posted_epoch");

                messages.add(new Message(messageId, postedById, messageText, timePostedEpoch));
            }


        }catch(SQLException e){
            e.printStackTrace();
        }

        return messages;
    }

    public Message getMessageById(int messageId){

        try{
            Connection connection = ConnectionUtil.getConnection();
            String sql = "SELECT * FROM Message WHERE message_id = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1,messageId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int postedById = rs.getInt("posted_by");
                    String messageText = rs.getString("message_text");
                    long timePostedEpoch = rs.getLong("time_posted_epoch");

                    return new Message(messageId, postedById, messageText, timePostedEpoch);
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }

        return null;
    }
    
    public Message deleteMessage(int messageId){
        if(getMessageById(messageId) == null){
            return null;
        }

        try{
            Connection connection = ConnectionUtil.getConnection();
            String sql = "DELETE FROM Message WHERE message_id = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setInt(1,messageId);

            int updateRows = stmt.executeUpdate();
            if(updateRows > 0){
                return getMessageById(messageId);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public Message updateMessage(int messageId, String newMessage){

        try{
            Connection connection = ConnectionUtil.getConnection();
            String sql = "UPDATE Message SET message_text = ? WHERE message_id = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            
            stmt.setString(1, newMessage);
            stmt.setInt(2,messageId);

            int updateRows = stmt.executeUpdate();
            if(updateRows > 0){
                return getMessageById(messageId);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    public List<Message> getMessagesByUser(int accountId){

        List<Message> messages = new ArrayList<>();
        try{
            Connection connection = ConnectionUtil.getConnection();
            String sql = "SELECT * FROM Message WHERE posted_by = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setInt(1,accountId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int messageId = rs.getInt("message_id");
                    String messageText = rs.getString("message_text");
                    long timePostedEpoch = rs.getLong("time_posted_epoch");

                    messages.add(new Message(messageId, accountId, messageText, timePostedEpoch));
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }

        return messages;
    }


}
