package com.lhl.servlet;

import com.lhl.pojo.StaticPara;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        urlPatterns = "/getWordListServlet"
)
public class GetWordServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher view;
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        // from `startpage.jsp`
        // send to `steponetest.jsp`
        if (action.equals(StaticPara.startPageStepOneGetWl)) {
            RequestDispatcher view;
            view = request.getRequestDispatcher("steponetest.jsp");
            response.setContentType("text/html;charset=UTF-8");
            view.forward(request, response);
        }


    }

}
