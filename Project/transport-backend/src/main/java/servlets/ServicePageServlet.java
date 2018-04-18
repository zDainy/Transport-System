package servlets;

import common.Category;
import common.DBService;
import common.Global;
import common.Service;
import dao.OrderDao;
import dao.ServiceDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class ServicePageServlet extends HttpServlet {
    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        DBService.createСonnection();

        int minPrice = 0;
        int maxPrice = 99999;
        String serviceName = Global.NONE;
        int catId = -1;
        if (request.getParameter("min") != null && !request.getParameter("min").equals("")) {
            minPrice = Integer.parseInt(request.getParameter("min"));
        }
        if (request.getParameter("max") != null && !request.getParameter("max").equals("")) {
            maxPrice = Integer.parseInt(request.getParameter("max"));
        }
        if (request.getParameter("serviceName") != null && !request.getParameter("serviceName").equals("")) {
            serviceName = request.getParameter("serviceName");
        }
        if (request.getParameter("serviceCat") != null ) {
            catId = Integer.parseInt(request.getParameter("serviceCat"));
        }

        ArrayList<Category> categories = ServiceDao.getCategories();
        request.setAttribute("service_categories", categories);

        ArrayList<Service> services = ServiceDao.getServices(Global.NONE, minPrice, maxPrice, serviceName, catId);
        request.setAttribute("services", services);

        getServletContext().getRequestDispatcher("/service.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productId = request.getParameter("orderId");
        String login = request.getSession(false).getAttribute("login").toString();
        OrderDao.addToCart(login, Integer.parseInt(productId));
        processRequest(request, response);
    }
}
