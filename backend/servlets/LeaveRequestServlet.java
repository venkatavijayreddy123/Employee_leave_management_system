package backend.servlets;

import backend.DBConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet("/apply-leave")
public class LeaveRequestServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession session = req.getSession();
        int userId = (int) session.getAttribute("user_id");

        String start = req.getParameter("start_date");
        String end = req.getParameter("end_date");
        String reason = req.getParameter("reason");

        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO leave_requests (user_id, start_date, end_date, reason) VALUES (?, ?, ?, ?)");
            ps.setInt(1, userId);
            ps.setString(2, start);
            ps.setString(3, end);
            ps.setString(4, reason);
            ps.executeUpdate();
            res.sendRedirect("frontend/dashboard.html");
        } catch (Exception e) {
            e.printStackTrace();
            res.getWriter().write("Failed to apply leave");
        }
    }
}

