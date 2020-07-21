package com.springmarket.core;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "servlet1", value = {"/products", "/test"}, loadOnStartup = 0)
public class ProductServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        resp.getWriter().print(cart);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Product product = objectMapper.readValue(req.getInputStream(), Product.class);
        HttpSession session = req.getSession();
        if (session.isNew()){
            Cart cart = new Cart();
            cart.getProducts().add(product);
            session.setAttribute("cart", cart);
        } else {
            Cart cart = (Cart)session.getAttribute("cart");
            cart.getProducts().add(product);
        }
        resp.getWriter().print(List.of("Reebok", "Puma"));
    }
}
