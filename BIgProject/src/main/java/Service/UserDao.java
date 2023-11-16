package Service;

import Model.JDBC;
import Model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;


public class UserDao implements IUserDAO{
    //luu ten anh
    public void AddImage(String Image,int id) throws SQLException, ClassNotFoundException {
        String INSERT_IMAGE="update user set image=? where id=?";
        PreparedStatement statement=JDBC.connection().prepareStatement(INSERT_IMAGE);
        statement.setString(1,Image);
        statement.setInt(2,id);
        statement.executeUpdate();
    }



    //Tim email trong mysql de thay doi
    public boolean ConfirmEmail(String Email) throws SQLException, ClassNotFoundException {
        String FIND_EMAIL="select email from user where email=?";
        PreparedStatement preparedStatement = JDBC.connection().prepareStatement(FIND_EMAIL);
        preparedStatement.setString(1,Email);
        ResultSet resultSet=preparedStatement.executeQuery();
        return  resultSet.next();
    }


    //Thay doi password thong qua email
    public void ChangePassword(String email, String password) throws SQLException, ClassNotFoundException {
        String CHANGE_PASSWORD_BY_EMAIL="UPDATE user set password=? where email=?";
        PreparedStatement preparedStatement = JDBC.connection().prepareStatement(CHANGE_PASSWORD_BY_EMAIL);
        preparedStatement.setString(1,password);
        preparedStatement.setString(2,email);
        preparedStatement.executeUpdate();
    }

// kiem tra thong tin dang ky
    @Override
    public boolean Login(String userName, String password) {
        try {
            String CHECK_USER = "select * from user where userName=? and password=?";
            PreparedStatement statement = JDBC.connection().prepareStatement(CHECK_USER);
            statement.setString(1, userName);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    //sau khi dang ky tao ra 1 session
    @Override
    public User TakeSessionOfUser(String userName, String password) throws SQLException, ClassNotFoundException {
        String CHECK_USER = "select * from user where userName=? and password=?";
        User user = null;
        PreparedStatement statement=JDBC.connection().prepareStatement(CHECK_USER);
        statement.setString(1,userName);
        statement.setString(2,password);
        ResultSet resultSet=statement.executeQuery();
        while (resultSet.next()){
            int id=resultSet.getInt("id");
            String image=resultSet.getString("image");
            String fullName = resultSet.getString("fullName");
            String email = resultSet.getString("email");
            String gender = resultSet.getString("gender");
            Date birthdate = resultSet.getDate("birthdate");
            int phoneNumber = resultSet.getInt("phoneNumber");
            user=new User(id,fullName,userName,password,gender,birthdate,phoneNumber,image,email);
        }
        return user;
    }
    public void UpdateUser(User user) throws SQLException, ClassNotFoundException {
        String UPDATE_USER="UPDATE user set fullName=?,gender=?,birthdate=?,phoneNumber=? WHERE id = ?";
        PreparedStatement statement=JDBC.connection().prepareStatement(UPDATE_USER);
        statement.setInt(5,user.getId());
        statement.setString(1, user.getFullName());
        statement.setString(2,user.getGender());
        statement.setDate(3, new java.sql.Date(user.getBirthdate().getTime()));
        statement.setInt(4,user.getPhoneNumber());
        statement.executeUpdate();

    }
    //Lay thong tin dang ky
    @Override
    public void Register(User user) throws SQLException, ClassNotFoundException {
        String REGISTER_USER = "insert into user(password,username,email) values (?,?,?)";
        PreparedStatement preparedStatement = JDBC.connection().prepareStatement(REGISTER_USER);
        preparedStatement.setString(1, user.getPassword());
        preparedStatement.setString(2, user.getUserName());
        preparedStatement.setString(3, user.getEmail());
        preparedStatement.executeUpdate();
    }


    //Kiem tra username
    @Override
    public boolean CheckUser( String username) throws SQLException, ClassNotFoundException {
        String CHECK_INF_USER = "SELECT userName FROM user WHERE userName=? ";
        PreparedStatement preparedStatement = JDBC.connection().prepareStatement(CHECK_INF_USER);
        preparedStatement.setString(1,username);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return true;
        } else {
            return false;
        }
    }


        //Xac nhan email
    @Override
    public boolean EMAIL(String email) throws SQLException, ClassNotFoundException {
        String FIND_EMAIL="select email from user where email=?";
        PreparedStatement preparedStatement=JDBC.connection().prepareStatement(FIND_EMAIL);
        preparedStatement.setString(1,email);
        ResultSet resultSet=preparedStatement.executeQuery();
        boolean gay=false;
        while (resultSet.next()){
            String Pan=resultSet.getString("email");
            if (email.equals(Pan)){
                gay=true;
            }
        }
        return gay;
    }
}
