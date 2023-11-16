package Controller;

import Model.User;
import Service.UserDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet(urlPatterns = "/Servlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 50
        ,maxFileSize = 1024 *1024 *500
        ,maxRequestSize = 1024 *1024 *500)
public class UserServlet extends HttpServlet {
    UserDao userDao = new UserDao();







    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action=req.getParameter("action");
        switch (action){

            case "update":
                UpdateForm(req,resp);
        }
    }

    private void UpdateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("updateUserForm.jsp").forward(req,resp);
    }

    // dang suat tai khoan









    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");
        switch (action) {
            case "Home":
                try {
                    Home(req, resp);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "addUser":
                try {
                    addUser(req, resp);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "Login":
                try {
                    Login(req, resp);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "FindEmail":
                try {
                    FindEmail(req, resp);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "ChangePByE":
                try {
                    ChangePByE(req, resp);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "Logout":
                Logout(req,resp);
                break;
            case "UpdateProfile":
                try {
                    UpdateProfile(req,resp);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            case "uploadPicture":
                try {
                    UpPicture(req,resp);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }

        }
    }

    private void UpPicture(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException, SQLException, ClassNotFoundException {
        Part filePart = req.getPart("file");
        String fileName = extractFileName(filePart);
        filePart.write(this.getFolderUpload().getAbsolutePath() + File.separator + fileName);
        HttpSession session= req.getSession();
        User user= (User) session.getAttribute("user");
        int id= user.getId();
        user.setImage("fileImage/" + fileName);
        userDao.AddImage(user.getImage(),id);
        session.setAttribute("user",user);
        req.getRequestDispatcher("updateUserForm.jsp").forward(req,resp);
    }
    public File getFolderUpload() {
        File folderUpload = new File(System.getProperty("user.home") + "/Big/tuan5/BIgProject/src/main/webapp/fileImage");
        if (!folderUpload.exists()) {
            folderUpload.mkdirs();
        }
        return folderUpload;
    }
    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2).replaceAll("\"", "").trim();
            }
        }
        System.out.println(contentDisp);
        return "";
    }

    private void UpdateProfile(HttpServletRequest req, HttpServletResponse resp) throws ParseException, SQLException, ClassNotFoundException, ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        String a = req.getCharacterEncoding();
        String userName = user.getUserName();
        String password = user.getPassword();
        int id = user.getId();
        String email = user.getEmail();
        int phoneNumber = Integer.parseInt(req.getParameter("phoneNumber"));
        String fullName = new String(req.getParameter("fullName").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        String BirthdateModal = req.getParameter("birthdate");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = dateFormat.parse(BirthdateModal);
        String gender=req.getParameter("gender");
        String image=user.getImage();
        user=new User(id,fullName,userName,password,gender,date,phoneNumber,image,email);
        userDao.UpdateUser(user);
        session.setAttribute("user",user);
        req.getRequestDispatcher("updateUserForm.jsp").forward(req,resp);
    }



    // dang suat tai khoan
    private void Logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session=req.getSession();
        session.invalidate();
        req.getRequestDispatcher("Login.jsp").forward(req,resp);
    }

// An vao Home thi se vao Trang Home
    private void Home(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ClassNotFoundException, ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        String userName = user.getUserName();
        String password = user.getPassword();
        userDao.Login(userName, password);
        req.getRequestDispatcher("Home.jsp").forward(req, resp);

    }
// Chuc nang dat lai mat khau qua email
    private void ChangePByE(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ClassNotFoundException, ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String confirmP = req.getParameter("confirmP");
        if (email.equals(confirmP)) {
            userDao.ChangePassword(email, password);
            req.getRequestDispatcher("Login.jsp").forward(req, resp);
        } else {
            req.setAttribute("messageFailureP", "Password do not match");
            req.getRequestDispatcher("ChangePForm.jsp").forward(req, resp);

        }
    }
//Chuc nang tim email(de lay lai mat khau)
    private void FindEmail(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ClassNotFoundException, ServletException, IOException {
        String email = req.getParameter("email");
        if (userDao.ConfirmEmail(email)) {
            req.setAttribute(email, "email");
            req.getRequestDispatcher("ChangePForm.jsp").forward(req, resp);
        } else {
            req.setAttribute("messageEmailFail", "Email do not exit");
            req.getRequestDispatcher("emailEditForm.jsp").forward(req, resp);
        }
    }

//user dang nhap tai khoan
    private void Login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException, ClassNotFoundException {

        String userName = req.getParameter("username");
        String password = req.getParameter("password");

        if (userDao.Login(userName, password)) {
            User user = userDao.TakeSessionOfUser(userName, password);
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            req.getRequestDispatcher("Home.jsp").forward(req, resp);

        } else {
            req.setAttribute("messageLoginF", "Tài khoản không tồn tại!");
            req.getRequestDispatcher("Login.jsp").forward(req, resp);
        }

    }
//user dang ky tai khoan
    private void addUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, ClassNotFoundException, ServletException, IOException {
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String email = request.getParameter("email");

        if (userDao.CheckUser(userName)) {
            request.setAttribute("messageFailureUserName", "Username has been used");
        } else if (userName.contains(" ")) {
            request.setAttribute("messageFailureUserName", "The data must not contain spaces");
        } else if (userName.length() < 6) {
            request.setAttribute("messageFailureUserName", "UserName must contain 6 characters");
        } else if (!userName.matches(".*\\d.*")) {
            request.setAttribute("messageFailureUserName", "UserName must contain at least one number");
        } else if (userName.isEmpty()) {
            request.setAttribute("messageFailureUserName", "Enter your Username");
        } else if (password.isEmpty()) {
            request.setAttribute("messageFailurePasswordNull", "Enter Passwords");
        } else if (!password.equals(confirmPassword)) {
            request.setAttribute("messageFailurePassword", "Passwords do not match");
        } else if (confirmPassword.isEmpty()) {
            request.setAttribute("messageFailurePasswordNull", "Enter repeat Password");
        } else if (userDao.EMAIL(email)) {
            request.setAttribute("messageFailureEmail", "Email has been used");
        } else if (email.isEmpty()) {
            request.setAttribute("messageFailureEmail", "Enter your email");
        } else if (password.length() < 6) {
            request.setAttribute("messageFailurePasswordNull", "Password must contain 6 characters");
        } else if (password.contains(" ")) {
            request.setAttribute("messageFailurePasswordNull", "Password must not contain spaces");
        } else {
            userDao.Register(new User(userName, password, email));
            request.setAttribute("messageSC", "         Register successful, go back to Sign in");
        }
        request.getRequestDispatcher("Register.jsp").forward(request, response);
    }
    @Override
    public void destroy() {
        super.destroy();
    }
}
