package converge.controllers;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.RedirectView;


import converge.models.Product;
import converge.models.CartItem;


@Controller
@RequestMapping("/cart")
@EnableWebMvc
public class CartController {
    private static final Logger LOG = Logger.getLogger(CartController.class);

    /*
	 * addProduct() method inserts product into the cart
	 * */
    @RequestMapping(value = "/addProduct", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView addProduct(HttpServletRequest request, HttpServletResponse response) {
        LOG.debug("CartController.addProduct() - To add item to cart");
        ModelAndView mv = new ModelAndView();
        CartItem cart = getCartObject(request);
        List<CartItem> cartList = new ArrayList<CartItem>();
        try {
            cartList = new CartDao().insertProductIntoCart(null, cart);

        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        try {


        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        mv.addObject("cartList", cartList);
        mv.setViewName("cart");
        return mv;

    }
    /*
		Fetch all the items that are available into the cart
	*/
    @RequestMapping(value = "/cartitems", method = RequestMethod.GET)
    public ModelAndView getCart() {
        ModelAndView mv = new ModelAndView();
        List<CartItem> cartList = new ArrayList<CartItem>();
        try {
            cartList = new CartDao().getCartDoc(null);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        mv.addObject("cartList", cartList);
        mv.setViewName("cart");
        return mv;
    }

    /*
	 * Remove all the items from the cart
	 * */
    @RequestMapping(value = "/empty-cart", method = RequestMethod.GET)
    public ModelAndView clearCart(HttpServletRequest request, HttpServletResponse response) {
        LOG.debug("CartController.clearCart() - L79");
        ModelAndView mv = new ModelAndView();
        List<CartItem> cartList = new ArrayList<CartItem>();
        try {
            cartList = new CartDao().clearCart(null);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        mv.addObject("cartList", cartList);
        mv.setViewName("cart");
        return mv;

    }

    /*
	 * Update the cart related information, change the count of items
	 * */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView updateCart(HttpServletRequest request, HttpServletResponse response) {
        LOG.debug("CartController.updateCart() - To update the cart");
        List<CartItem> cartList = new ArrayList<CartItem>();
        ModelAndView mv = new ModelAndView();
        String[] pids = request.getParameterValues("pid[]");
        String[] qant = request.getParameterValues("qnt[]");
        try {
            cartList = new CartDao().updateCart(null, pids, qant);

        } catch (Exception e) {
            e.printStackTrace();
        }
        mv.addObject("cartList", cartList);
        mv.setViewName("cart");
        return mv;

    }

    /*
	 * Remove an item from the cart when provided a product identification number
	 * */
    @RequestMapping(value = "/remove-from-cart/{pid}", method = RequestMethod.GET)
    public ModelAndView deleteFromCart(HttpServletRequest request, HttpServletResponse response,
                                       @PathVariable String pid) {
        LOG.debug("CartController.deleteFromCart() - pid " + pid);
        List<CartItem> cartList = new ArrayList<CartItem>();
        CartDao cdao = new CartDao();
        ModelAndView mv = new ModelAndView();
        try {
            cartList = cdao.removeFromCart(null, pid);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        mv.addObject("cartList", cartList);
        mv.setViewName("cart");
        return mv;

    }


    /*
	 * Redirect to the order page
	 * */
    @RequestMapping(value = "/checkout", method = RequestMethod.POST)
    public ModelAndView cartCheckout(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("order");
        return mv;

    }

    @RequestMapping(value = "/checkout", method = RequestMethod.POST, consumes = { "application/json", "text/json" })
    @ResponseBody
    public ModelAndView cartCheckout(HttpServletRequest request, HttpServletResponse response,
                                     @RequestBody String req) {
        LOG.debug("CartController.cartCheckout() - to redirect to checkout");
        ModelAndView mv = new ModelAndView();

        mv.addObject("cartList", req);
        mv.setViewName("order");
        return mv;
    }


    public CartItem getCartObject(HttpServletRequest request) {
        LOG.debug("CartController.getCartObject() - to populate the cart object");
        CartItem cart = new CartItem();

        if (request.getParameter("pid") != null) {

            cart.setPid(request.getParameter("pid"));

        }
        if (request.getParameter("category") != null) {

            cart.setCategory(request.getParameter("category"));

        }
        if (request.getParameter("title") != null) {

            cart.setTitle(request.getParameter("title"));

        }
        if (request.getParameter("details") != null) {

            cart.setDetails(request.getParameter("details"));

        }
        if (request.getParameter("price") != null) {

            cart.setPrice(request.getParameter("price"));

        }
        if (request.getParameter("picture") != null) {

            cart.setPicture(request.getParameter("picture"));

        }
        if (request.getParameter("quantity") != null) {

            cart.setQuantity(Integer.parseInt(request.getParameter("quantity")));

        }

        return cart;
    }


}
