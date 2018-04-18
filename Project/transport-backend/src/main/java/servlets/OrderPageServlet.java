package servlets;

import common.DBService;
import common.Service;
import dao.OrderDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class OrderPageServlet extends HttpServlet {
    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        DBService.createСonnection();

        if (request.getParameter("action") != null && request.getSession(false) != null && request.getSession(false).getAttribute("login") != null) {
            String login = request.getSession(false).getAttribute("login").toString();
            if (request.getParameter("action").equals("remove")) {
                OrderDao.deleteProductFromCart(login, Integer.parseInt(request.getParameter("id")));
            }
        }

        if (request.getSession(false).getAttribute("login") != null) {
            String login = request.getSession(false).getAttribute("login").toString();
            ArrayList<Service> services = OrderDao.getServicesInOrder(login);
            request.setAttribute("services", services);
        }
        getServletContext().getRequestDispatcher("/order.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Очищение заказа
        if (request.getParameter("action") != null && request.getSession(false) != null && request.getSession(false).getAttribute("login") != null) {
            String login = request.getSession(false).getAttribute("login").toString();
            String action = request.getParameter("action");
            switch (action) {
                case "clear":
                    OrderDao.clearShopCart(login);
                    break;
                case "continue":
                    OrderDao.clearShopCart(login);
                    break;
            }
        }
        processRequest(request, response);
    }
}
