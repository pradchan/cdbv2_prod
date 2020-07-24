package converge.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import converge.models.CartItem;
import converge.models.Order;

@Controller
@RequestMapping("/order")
public class OrderController {

    @RequestMapping(value = "/placeOrder", method = RequestMethod.POST)
    public ModelAndView placeOrder(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("Reached to place the order");
        Order od = getOrderObjectFromRequest(request);
        Order finalOrder = new Order();
        try {
            finalOrder = new OrderDao().placeOrder(null, od);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        ModelAndView mv = new ModelAndView();
        mv.setViewName("orderConfirmation");
        return mv;


    }

    @RequestMapping(value = "/getOrder", method = RequestMethod.GET)
    public ModelAndView getOrder(HttpServletRequest request, HttpServletResponse response) {
        Order order = new Order();
        try {
            order = new OrderDao().getOrder(null);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        ModelAndView mv = new ModelAndView();
        mv.addObject("order", order);
        mv.setViewName("orderStatus");
        return mv;
    }


    public Order getOrderObjectFromRequest(HttpServletRequest request) {

        Order order = new Order();

        if (request.getParameter("name") != null) {

            order.setOrderedBy(request.getParameter("name"));

        }
        if (request.getParameter("email") != null) {

            order.setEmail(request.getParameter("email"));

        }
        if (request.getParameter("phone") != null) {

            order.setPhone(request.getParameter("phone"));

        }
        if (request.getParameter("address") != null) {

            order.setAddress(request.getParameter("address"));

        }
        if (request.getParameter("city") != null) {

            order.setCity(request.getParameter("city"));

        }
        if (request.getParameter("pincode") != null) {

            order.setPincode(request.getParameter("pincode"));

        }

        return order;
    }

}
