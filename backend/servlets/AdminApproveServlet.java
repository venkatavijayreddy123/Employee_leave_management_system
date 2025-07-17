package backend.servlets;

import backend.DBConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet("/update-status")
public class AdminApproveServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        int leaveId = Integer.parseInt(req.getParameter("leave_id"));
        String status = req.getParameter("status");

        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement("UPDATE leave_requests SET status=? WHERE id=?");
            ps.setString(1, status);
            ps.setInt(2, leaveId);
            ps.executeUpdate();
            res.sendRedirect("frontend/admin_panel.html");
        } catch (Exception e) {
            e.printStackTrace();
            res.getWriter().write("Error updating leave status");
        }
    }
}

