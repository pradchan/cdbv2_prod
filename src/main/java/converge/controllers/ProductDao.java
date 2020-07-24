package converge.controllers;

import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;

import org.springframework.web.servlet.ModelAndView;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

import converge.dbHelper.DBSource;

import converge.models.Product;

class ProductDao {
    private static final Logger LOG = Logger.getLogger(ProductDao.class);

    private static String PRODUCT_BY_ID = "select * from products where id = ?";

    private static String ALL_PRODUCT =
        "SELECT product_document FROM (\r\n" + "    SELECT\r\n" + "      prd.*,\r\n" +
        "      row_number() over (ORDER BY prd.id ASC) line_number\r\n" + "    FROM products prd\r\n" +
        "  where lower(prd.product_document.category) like '%'||?||'%' or lower(prd.product_document.title) like '%'||?||'%')" +
        " WHERE line_number BETWEEN ? AND ?  ORDER BY line_number";


    private static String GET_COUNT =
        "select count(id) from products prd" +
        " where lower(prd.product_document.category) like '%'||?||'%' or lower(prd.product_document.title) like '%'||?||'%'";

    private static String GET_PRODUCT_BY_ID =
        "SELECT a.product_document from products a where a.product_document.pid=?";
    
    // DECLARE THE NEW SQL QUERY BELOW  TO ACCESS THE INDIVIDUAL JSON ATTRIBUTE AS STRING
    
    
    

    private DBSource dbs = new DBSource();


    public Product getProductById(Connection con, String id) throws Exception {
        LOG.debug("ProductDao.getProductById() - L45");
        Connection conn = con;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Clob clob = null;
        
        Product pdt = new Product();

        try {
            if (conn == null)
                conn = dbs.getJsonXmlConnection();
            
            //COMMENT THE pstmt LINE BELOW AND THEN ADD THE NEW QUERY STRING CODE
            pstmt = conn.prepareStatement(GET_PRODUCT_BY_ID);
            
            
            
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();
            while (rs.next()) {

//START COMMENTING FROM BELOW

                clob = rs.getClob(1);
                String wholeClob = clob.getSubString(1, (int) clob.length());

                JSONObject jo = (JSONObject) new JSONParser().parse(wholeClob);
                if (jo.get("pid") == null)
                    continue;
         
            
                pdt.setCategory((String) jo.get("category"));
                pdt.setDetails((String) jo.get("details"));
                pdt.setPicture((String) jo.get("picture"));
                pdt.setPid(jo.get("pid").toString());
                pdt.setPrice(jo.get("price").toString());
                pdt.setTitle((String) jo.get("title"));
                
                //END COMMENTING

            //  UNCOMMENT THE BELOW SECTION FOR LAB ON JSON DATA ACCESS
            /*
            pdt.setPid(rs.getString(1));
            pdt.setCategory(rs.getString(2));
            pdt.setTitle(rs.getString(3));
            pdt.setDetails(rs.getString(4));
            pdt.setPrice(rs.getString(5));
            pdt.setPicture(rs.getString(6));
             */

            }

        } catch (Exception e) {
            if (conn != null)
                conn.rollback();
            throw e;
        } finally {
            if (con == null && conn != null)
                conn.close();
        }

        return pdt;
    }

    public Vector getAllProducts(int page, String searchQuery) throws Exception {
        LOG.debug("ProductDao.getAllProducts() - L93");

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int totalRowCount = 0;
        Clob clob = null;

        List<Product> productList = new ArrayList();

        Vector v = new Vector();


        try {
            con = dbs.getJsonXmlConnection();
            pstmt = con.prepareStatement(ALL_PRODUCT);
            int startRow = 12 * (page - 1) + 1;
            pstmt.setString(1, searchQuery);
            pstmt.setString(2, searchQuery);
            pstmt.setInt(3, startRow);
            pstmt.setInt(4, page * 12);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Product pdt = new Product();
                clob = rs.getClob(1);
                String wholeClob = clob.getSubString(1, (int) clob.length());

                JSONObject jo = (JSONObject) new JSONParser().parse(wholeClob);

                if (jo.get("pid") == null)
                    continue;
                pdt.setCategory((String) jo.get("category"));
                pdt.setDetails((String) jo.get("details"));
                pdt.setPicture((String) jo.get("picture"));
                pdt.setPid(jo.get("pid").toString());
                pdt.setPrice(jo.get("price").toString());
                pdt.setTitle((String) jo.get("title"));


                productList.add(pdt);


            }
            totalRowCount = getProductCount(con, searchQuery);


            v.add(productList);
            v.add(totalRowCount);

        } catch (Exception e) {
            if (con != null)
                con.rollback();
            throw e;
        } finally {
            if (con != null)
                con.close();
        }

        return v;
    }

    public int getProductCount(Connection conn, String query) throws Exception {
        LOG.debug("ProductDao.getProductCount() - L157");

        Connection con = conn;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int count = 0;

        try {
            if (con == null)
                con = dbs.getJsonXmlConnection();
            pstmt = con.prepareStatement(GET_COUNT);
            pstmt.setString(1, query);
            pstmt.setString(2, query);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                count = rs.getInt(1);

            }
        } catch (Exception e) {
            if (con != null)
                con.rollback();
            throw e;
        } finally {
            if (conn == null && con != null)
                con.close();
        }

        return count;
    }
}
