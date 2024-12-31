package DAO;

import java.sql.*;
import Util.ConnectionUtil;
import Model.Account;

public class AccountDAO {
    
    public Account createAccount(String username, String password){

        Connection connection = ConnectionUtil.getConnection();
        try{

            String sql = "INSERT INTO Account (username, password) VALUES (?,?)";
            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, username);
            stmt.setString(2, password);

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    int accountId = rs.getInt(1);
                    return new Account(accountId, username, password);
                }
            }

        }
        catch(SQLException e){
            e.printStackTrace();
        }


        return null;
    }

    public Account getAccountByUsername(String username){

        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT * FROM Account WHERE username = ?";

            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setString(1, username);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }


        return null;
    }

    public Account getAccountByID(int id){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT * FROM Account WHERE account_id = ?";

            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

}
