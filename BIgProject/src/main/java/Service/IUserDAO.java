package Service;

import Model.User;

import java.sql.SQLException;

public interface IUserDAO {
    void Register(User user) throws SQLException, ClassNotFoundException;
    boolean CheckUser(String username) throws SQLException, ClassNotFoundException;
    boolean EMAIL(String email) throws SQLException, ClassNotFoundException;
    boolean Login(String userName, String password);
    User TakeSessionOfUser(String userName, String password) throws SQLException, ClassNotFoundException;
    boolean ConfirmEmail(String Email) throws SQLException, ClassNotFoundException;
    void ChangePassword(String email, String password) throws SQLException, ClassNotFoundException;
    public void UpdateUser(User user) throws SQLException, ClassNotFoundException;
}