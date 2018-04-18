package servlets;

import common.Customer;
import common.DBService;
import dao.CustomerDao;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ProfilePageServlet extends HttpServlet {

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        DBService.createСonnection();

        getServletContext().getRequestDispatcher("/profile.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String type = "";
        String login = "";
        String password = "";
        String name = "";
        String phone = "";
        String address = "";
        String email = "";
        if (request.getParameter("login") != null) {
            login = new String(request.getParameter("login").getBytes("ISO-8859-1"), "UTF-8");
        }
        if (request.getParameter("password") != null) {
            password = new String(request.getParameter("password").getBytes("ISO-8859-1"), "UTF-8");
        }
        if (request.getParameter("type") != null) {
            type = new String(request.getParameter("type").getBytes("ISO-8859-1"), "UTF-8");
        }
        if (request.getParameter("name") != null) {
            name = new String(request.getParameter("name").getBytes("ISO-8859-1"), "UTF-8");
        }
        if (request.getParameter("phone") != null) {
            phone = new String(request.getParameter("phone").getBytes("ISO-8859-1"), "UTF-8");
        }
        if (request.getParameter("address") != null) {
            address = new String(request.getParameter("address").getBytes("ISO-8859-1"), "UTF-8");
        }
        if (request.getParameter("email") != null) {
            email = new String(request.getParameter("email").getBytes("ISO-8859-1"), "UTF-8");
        }
        if (type.equals("logIn") && CustomerDao.isAuthenticationSuccess(login, password)) {
            HttpSession session = request.getSession(true);
            session.setAttribute("login", login);
            Customer cust = CustomerDao.getCustomer(login);
            request.setAttribute("name", cust.getName());
            request.setAttribute("email", cust.getEmail());
            request.setAttribute("phone", cust.getPhone());
            request.setAttribute("address", cust.getAddress());
        } else if (type.equals("registration")) {
            if (CustomerDao.isCustomerExist(login)) {
                // ошибка
            } else {
                CustomerDao.createCustomer(login, password);
                // успех
            }
        } else if (type.equals("setInfo")) {
            login = request.getSession(false).getAttribute("login").toString();
            CustomerDao.setCustomerInfo(login, name, email, phone, address);
        }
        processRequest(request, response);
    }
}