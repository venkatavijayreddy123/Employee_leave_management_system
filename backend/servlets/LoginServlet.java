package backend.servlets;

import backend.DBConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String uname = req.getParameter("username");
        String pass = req.getParameter("password");

        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE username=? AND password=?");
            ps.setString(1, uname);
            ps.setString(2, pass);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                HttpSession session = req.getSession();
                session.setAttribute("user_id", rs.getInt("id"));
                session.setAttribute("role", rs.getString("role"));

                if ("admin".equals(rs.getString("role"))) {
                    res.sendRedirect("frontend/admin_panel.html");
                } else {
                    res.sendRedirect("frontend/dashboard.html");
                }
            } else {
                res.getWriter().write("Invalid Credentials");
            }
        } catch (Exception e) {
            e.printStackTrace();
            res.getWriter().write("Error connecting to database");
        }
    }
}

