package converge.controllers;

import org.apache.log4j.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


import java.sql.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

import oracle.jdbc.OracleResultSet;

import oracle.sql.OPAQUE;

import converge.dbHelper.*;

import converge.models.Product;

@Controller
//@RequestMapping("/product")
public class ProductController {
    private static final Logger LOG = Logger.getLogger(ProductController.class);
    DBSource dbs = new DBSource();

    /*
	 * To get the product by id
	 * */

    @RequestMapping(value = "/product/{id}", method = RequestMethod.GET)
    public ModelAndView findProductById(@PathVariable String id) {
        LOG.debug("ProductController.findProductById() - L56");
        ModelAndView mv = new ModelAndView();
        Product pdt = new Product();
        boolean flag = false;
        try {
            pdt = new ProductDao().getProductById(null, id);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        mv.addObject("product", pdt);
        mv.setViewName("productInfo");
        return mv;
    }

    /**
     *
     * Returns all the products
     */

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public ModelAndView fetchAllProducts(HttpServletRequest request, HttpServletResponse response,
                                         @RequestParam(value = "page", required = false) String page) {
        LOG.debug("ProductController.fetchAllProducts() - L77");
        String searchQuery = "";
        if (request.getParameter("searchText") != null) {
            searchQuery = request.getParameter("searchText");

        }

        int pageNumber = 1;
        int totalPageCount = 0;
        int actualCount = 0;
        ProductDao pdao = new ProductDao();
        CartDao cdao = new CartDao();
        Vector v = new Vector();
        if (page != null) {
            pageNumber = Integer.parseInt(page);

        }
        List<Product> productList = new ArrayList();
        ModelAndView mv = new ModelAndView();
        try {
            v = pdao.getAllProducts(pageNumber, searchQuery);
            productList = (List<Product>) v.get(0);
            actualCount = (Integer) v.get(1);
            totalPageCount = actualCount / 10;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        mv.addObject("actualCount", actualCount);
        mv.addObject("currentPage", pageNumber);
        mv.addObject("totalPageCount", totalPageCount);
        mv.addObject("productList", productList);
        mv.addObject("searchText", searchQuery);
        mv.setViewName("product");
        return mv;

    }


}

