package converge.controllers;

import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import converge.dbHelper.DBSource;

import converge.models.CartItem;
import converge.models.Product;

class CartDao {
    private DBSource dbs = new DBSource();
    private static final Logger LOG = Logger.getLogger(CartDao.class);
    private static String GET_LIST_OF_PIDS_IN_CART = "select c.cart_document.pid from user_cart c";
    private static String CHECK_IF_IN_CART =
        "select c.cart_document.pid from user_cart c where c.cart_document.pid = ?";
    private static String INSERT_INTO_CART = "UPDATE user_cart SET cart_document= ?";
    private static String FETCH_CART_DOC = "select c.cart_document from user_cart c ";

    public void JSONReplace(Connection con, List<CartItem> cartList) throws Exception {

        Connection conn = con;
        PreparedStatement pstmt = null;
        String insertData = "[";
        ResultSet rs = null;

        try {
            if (conn == null)
                conn = dbs.getJsonXmlConnection();

            int ctLength = cartList.size();

            for (int i = 0; i < ctLength; i++) {
                insertData += cartList.get(i).toString();
                if (i < ctLength - 1)
                    insertData += ",";
            }

            insertData += "]";

            pstmt = conn.prepareStatement(INSERT_INTO_CART);
            pstmt.setString(1, insertData);
            rs = pstmt.executeQuery();
            conn.commit();


        } catch (Exception e) {
            if (conn != null)
                conn.rollback();
            throw e;
        } finally {
            if (con == null && conn != null) {
                conn.close();
                LOG.debug("closed the connection");
            }

        }

    }

    public List<CartItem> updateCart(Connection con, String[] pids, String[] qant) throws Exception {
        LOG.debug("CartDao.updateCart() - to update the cart");
        Connection conn = con;
        List<CartItem> cartList = new ArrayList<CartItem>();
        try {
            if (conn == null)
                conn = dbs.getJsonXmlConnection();
            cartList = getCartDoc(conn);
            int i = 0;
            for (i = 0; i < pids.length; i++) {
                String tempPid = pids[i];
                String tempQant = qant[i];
                for (int k = 0; k < cartList.size(); k++) {
                    if (cartList.get(k)
                                .getPid()
                                .equals(tempPid)) {
                        cartList.get(k).setQuantity(Long.parseLong(tempQant));
                        break;
                    }
                }

            }
            LOG.debug(cartList);
            JSONReplace(conn, cartList);


        } catch (Exception e) {
            if (conn != null) {
                conn.rollback();
            }
            throw e;
        } finally {
            if (con == null && conn != null)
                conn.close();
        }
        return cartList;
    }


    public List<CartItem> removeFromCart(Connection con, String pid) throws Exception {

        Connection conn = con;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<CartItem> cartList = new ArrayList<CartItem>();
        try {
            if (conn == null)
                conn = dbs.getJsonXmlConnection();
            cartList = getCartDoc(conn);
            for (int i = 0; i < cartList.size(); i++) {
                if (cartList.get(i)
                            .getPid()
                            .equals(pid)) {
                    cartList.remove(i);
                    break;
                }
            }

            this.JSONReplace(conn, cartList);

        } catch (Exception e) {
            if (conn != null)
                conn.rollback();
            throw e;
        } finally {
            if (con == null && conn != null) {
                conn.close();
                LOG.debug("closed the connection");
            }

        }
        return cartList;

    }

    public List<CartItem> insertProductIntoCart(Connection con, CartItem ct) throws Exception {
        LOG.debug("CartDao.insertProductIntoCart() - To insert item into the cart");
        Connection conn = con;
        ResultSet rs = null;

        PreparedStatement pstmt = null;

        List<CartItem> returnCartList = new ArrayList<CartItem>();
        List<CartItem> cartList = new ArrayList<CartItem>();
        boolean flag = true;
        String insertData = "[";

        try {
            if (conn == null)
                conn = dbs.getJsonXmlConnection();

            cartList = getCartDoc(conn);

            for (int i = 0; i < cartList.size(); i++) {
                if (cartList.get(i)
                            .getPid()
                            .equals(ct.getPid())) {
                    cartList.get(i).setQuantity(cartList.get(i).getQuantity() + 1);
                    flag = false;
                    break;
                }
            }
            if (flag)
                cartList.add(ct);


            int ctLength = cartList.size();
            for (int i = 0; i < ctLength; i++) {
                insertData += cartList.get(i).toString();
                if (i < ctLength - 1)
                    insertData += ",";
            }

            insertData += "]";
            LOG.debug(insertData);

            pstmt = conn.prepareStatement(INSERT_INTO_CART);
            pstmt.setString(1, insertData);
            rs = pstmt.executeQuery();
            conn.commit();
            returnCartList = getCartDoc(conn);


        } catch (Exception e) {
            if (conn != null)
                conn.rollback();
            throw e;
        } finally {
            if (con == null && conn != null)
                conn.close();
        }
        return returnCartList;

    }


    public List<String> getCartItemPids(Connection con) throws Exception {
        LOG.info("CartDao.getCartItemPids() - L80");
        List<String> pids = new ArrayList<String>();
        Connection conn = con;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            if (conn == null)
                conn = dbs.getJsonXmlConnection();
            pstmt = conn.prepareStatement(GET_LIST_OF_PIDS_IN_CART);
            rs = pstmt.executeQuery();
            while (rs.next()) {

                pids.add(rs.getString(1));


            }
        } catch (Exception e) {
            if (conn != null)
                conn.rollback();
            throw e;
        } finally {
            if (con == null && conn != null)
                conn.close();
        }
        return pids;
    }

    public boolean checkIfInCart(Connection con, String pid) throws Exception {
        LOG.info("Reached to checkIfInCart");
        boolean b = false;
        Connection conn = con;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            if (conn == null)
                conn = dbs.getJsonXmlConnection();
            pstmt = conn.prepareStatement(CHECK_IF_IN_CART);
            pstmt.setString(1, pid);
            rs = pstmt.executeQuery();
            while (rs.next()) {

                b = true;


            }
        } catch (Exception e) {
            if (conn != null)
                conn.rollback();
            throw e;
        } finally {
            if (con == null && conn != null)
                conn.close();
        }
        return b;
    }

    public boolean checkInCartPidList(Connection con, String pid) throws Exception {
        LOG.debug("CartDao.checkInCartPidList() - L132");
        boolean b = false;
        Connection conn = con;
        PreparedStatement pstmt = null;
        String emptyString = "[]";
        JSONArray jo = (JSONArray) new JSONParser().parse(emptyString);
        ResultSet rs = null;
        try {
            if (conn == null)
                conn = dbs.getJsonXmlConnection();
            pstmt = conn.prepareStatement(GET_LIST_OF_PIDS_IN_CART);
            rs = pstmt.executeQuery();

            while (rs.next()) {

                if (rs.getString(1) != null && !rs.getString(1).equalsIgnoreCase("null")) {

                    if (rs.getString(1).equals(pid)) {
                        b = true;
                        break;
                    }
                    jo = (JSONArray) new JSONParser().parse(rs.getString(1));
                }

            }

            if (jo.contains(pid))
                b = true;

        } catch (Exception e) {
            if (conn != null)
                conn.rollback();
            throw e;
        } finally {
            if (con == null && conn != null)
                conn.close();
        }
        return b;
    }

    public List<CartItem> getCartDoc(Connection con) throws Exception {
        LOG.info("Inside ->>getCartDoc");
        LOG.debug("Reached to the getCartDoc function");
        String returnVal = null;
        Connection conn = con;
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        List<CartItem> cartList = new ArrayList<CartItem>();
        try {
            if (conn == null)
                conn = dbs.getJsonXmlConnection();
            pstmt = conn.prepareStatement(FETCH_CART_DOC);
            rs = pstmt.executeQuery();
            Clob clob = null;

            while (rs.next()) {


                clob = rs.getClob(1);
                returnVal = clob.getSubString(1, (int) clob.length());


            }
            JSONArray jo = (JSONArray) new JSONParser().parse(returnVal);
            for (int i = 0; i < jo.size(); i++) {
                CartItem crt = new CartItem();
                JSONObject jobj = (JSONObject) jo.get(i);
                crt.setCategory((String) jobj.get("category"));
                crt.setDetails((String) jobj.get("details"));
                crt.setPicture((String) jobj.get("picture"));
                crt.setPrice(String.valueOf(jobj.get("price")));
                crt.setPid(String.valueOf(jobj.get("pid")));
                crt.setQuantity(Integer.parseInt(String.valueOf(jobj.get("quantity"))));
                crt.setTitle((String) jobj.get("title"));

                cartList.add(crt);

            }

        } catch (Exception e) {
            if (conn != null)
                conn.rollback();
            throw e;
        } finally {
            if (con == null && conn != null)
                conn.close();
        }

        return cartList;
    }

    public List<CartItem> clearCart(Connection con) throws Exception {
        List<CartItem> emptyString = new ArrayList<CartItem>();
        String emptyArrayString = JSONArray.toJSONString(emptyString);
        Connection conn = con;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            if (conn == null)
                conn = dbs.getJsonXmlConnection();
            pstmt = conn.prepareStatement(INSERT_INTO_CART);
            pstmt.setString(1, emptyArrayString);
            rs = pstmt.executeQuery();
            conn.commit();
        } catch (Exception e) {
            if (conn != null)
                conn.rollback();
            throw e;
        } finally {
            if (con == null && conn != null) {
                conn.close();
            }
        }
        return emptyString;
    }


}
