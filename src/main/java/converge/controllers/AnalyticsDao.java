package converge.controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.log4j.Logger;

import converge.dbHelper.DBSource;

import converge.models.Analytics;

import java.io.BufferedReader;

import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.URL;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

class AnalyticsDao {
    private static final Logger LOG = Logger.getLogger(AnalyticsDao.class);

    private DBSource dbs = new DBSource();

    private static String PRODUCT_COUNT_BY_GROUP =
        "SELECT a.product_document.category, count(a.product_document.category) as counts from products a  group by a.product_document.category HAVING   Count(a.product_document.category) > 10";

    private static String TOTAL_PRODUCT_COUNT = "select count(a.product_document.pid) as Total from products a";

    private static String PRODUCT_COUNT_BY_PRICE =
        "SELECT a.product_document.price, count(a.product_document.price) as pricecounts from products a  group by a.product_document.price HAVING   (a.product_document.price) < 20";

    private static String SALES_BY_GENDER_ON_OFFER =
        "select distinct (st.sales_type_name),count(case when gender='M' then 1 end) as Mcounts,count(case when gender='F' then 1 end) as Fcounts from  customer_order_products cop inner join sales_type st on cop.sales_type_id=st.sales_type_id group by sales_type_name";

    private static String PRODUCT_BY_REVENUE =
        "select p.product_name, \r\n" + "                count(*) number_of_orders,\r\n" +
        "                sum ( oi.quantity * oi.unit_price ) total_value,\r\n" + "                rank () over ( \r\n" +
        "                  order by sum ( oi.quantity * oi.unit_price ) desc \r\n" +
        "                ) revenue_rank\r\n" + "         from   products p\r\n" + "         join   order_items oi\r\n" +
        "         on     p.product_id = oi.product_id\r\n" + "         group  by p.product_name\r\n" +
        "         order  by total_value desc\r\n" + "         fetch  first 5 rows only";

    private static String REVENUE_BY_DATE =
        " with dates as (\r\n" + "                        select date'2018-02-03' + level dt \r\n" +
        "                        from   dual\r\n" + "                        connect by level <= 433\r\n" +
        "                      ), order_totals as (\r\n" +
        "                        select trunc ( o.order_datetime ) order_date,\r\n" +
        "                               count ( distinct o.order_id ) number_of_orders,\r\n" +
        "                               sum ( oi.quantity * oi.unit_price ) value_of_orders\r\n" +
        "                        from   orders o\r\n" + "                        join   order_items oi\r\n" +
        "                        on     o.order_id = oi.order_id\r\n" +
        "                        group  by trunc ( o.order_datetime )\r\n" + "                      )\r\n" +
        "                        select to_char ( dt, 'DD-MON' ) sale_date, \r\n" +
        "                               nvl ( number_of_orders, 0 ) number_of_orders, \r\n" +
        "                               nvl ( value_of_orders, 0 ) value_of_orders\r\n" +
        "                        from   dates\r\n" + "                        left   join order_totals\r\n" +
        "                        on     dt = order_date\r\n" +
        "                        order  by dt  fetch first 90 rows only";


    private static String sqlString =
        "SELECT a.product_document.category as category, count(a.product_document.category) as counts from products a  group by a.product_document.category HAVING   Count(a.product_document.category) > 10";
    private static String sqlString2 = "select count(a.product_document.pid) as Total from products a";
    private static String sqlString3 =
        "SELECT a.product_document.price, count(a.product_document.price) as pricecounts from products a  group by a.product_document.price HAVING   (a.product_document.price) < 20";

    private static String urlStringForProductByCategory = "http://localhost:9090/ords/apppdb/appnodejs/app/app1/";
    /*
	 * To get the count of products by its category
	 * Products are stored as JSON object in the table.
	 * */
    public Vector getProductCountByCategory(Connection con) throws Exception {

        LOG.debug("Reached to getProductCountByCategory");
        //UNCOMMENT THE LINE BELOW TO CALL getProductCountByCategoryOrds() to showcase ORDS in action
        //Vector v = getProductCountByCategoryOrds();

        //REMOVE // IN THE BELOW LINE WHILE SHOWCASING ORDS FEATURES - comment the DB connection code.

        
        List<Map<String, String>> listOfMap = new ArrayList<Map<String, String>>();
        Connection conn = con;
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Map<String, String> pcat_count = null;
        Vector v = new Vector();
        List<String> category = new ArrayList<String>();
        List<String> count = new ArrayList<String>();
        try {
            if (conn == null)
                conn = dbs.getJsonXmlConnection();
            pstmt = conn.prepareStatement(PRODUCT_COUNT_BY_GROUP);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                category.add(rs.getString(1));
                count.add(rs.getString(2));
            }
            v.add(category);
            v.add(count);
        } catch (Exception ex) {
            if (conn != null) {
                conn.rollback();
            }
            LOG.error(ex);
        } finally {
            if (conn != null && con == null) {
                conn.close();
            }
        }
        //REMOVE // IN THE BELOW LINE WHILE SHOWCASING ORDS FEATURES
        //*/

        return v;

    }

    /*
     * To get the count of products by its category using ORDS call
     * Products are stored as JSON object in the table.
     * */
    public Vector getProductCountByCategoryOrds() throws Exception {
        Vector v = new Vector();
        String readLine = null;
        List<String> category = new ArrayList<String>();
        List<String> count = new ArrayList<String>();
        try {
            URL url = new URL(urlStringForProductByCategory);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer response = new StringBuffer();
            while ((readLine = in.readLine()) != null) {
                response.append(readLine);
            }
            in.close();
            JSONObject jo = (JSONObject) new JSONParser().parse(response.toString());
            JSONArray items = (JSONArray) jo.get("items");
            for (int i = 0; i < items.size(); i++) {
                JSONObject temp = (JSONObject) items.get(i);
                category.add(String.valueOf(temp.get("category")));
                count.add(String.valueOf(temp.get("counts")));

            }
            v.add(category);
            v.add(count);

        } catch (Exception e) {
            e.printStackTrace();
        }


        return v;

    }

    /*
	 * To get the total count of the product
	 * */
    public int getTotalProductCount(Connection con) throws Exception {

        Connection conn = con;
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        int count = 0;

        try {
            if (conn == null)
                conn = dbs.getJsonXmlConnection();
            pstmt = conn.prepareStatement(TOTAL_PRODUCT_COUNT);
            rs = pstmt.executeQuery();
            while (rs.next())
                count = rs.getInt(1);

        } catch (Exception ex) {
            if (conn != null) {
                conn.rollback();
            }
            LOG.error(ex);
        } finally {
            if (conn != null && con == null) {
                conn.close();
            }
        }

        return count;
    }

    /*
	 * To get the count of product by price
	 * */
    public Vector getProductCountByPrice(Connection con) throws Exception {
        Map<String, String> pcount_price = null;

        Connection conn = con;
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Vector v = new Vector();
        List<String> price = new ArrayList<String>();
        List<String> count = new ArrayList<String>();

        List<Map<String, String>> prodCountByPrice = new ArrayList<Map<String, String>>();
        try {
            if (conn == null)
                conn = dbs.getJsonXmlConnection();
            pstmt = conn.prepareStatement(PRODUCT_COUNT_BY_PRICE);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                price.add(rs.getString(1));
                count.add(rs.getString(2));

            }
            v.add(price);
            v.add(count);

        } catch (Exception ex) {
            if (conn != null) {
                conn.rollback();
            }
            LOG.error(ex);
        } finally {
            if (conn != null && con == null) {
                conn.close();
            }
        }

        return v;

    }

    /*
	 * Get the sales categorized by gender
	 * */
    public Vector getSalesByGender(Connection con) throws Exception {

        Connection conn = con;
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Vector v = new Vector();
        List<String> SALES_TYPE_NAME = new ArrayList<String>();
        List<String> MCOUNTS = new ArrayList<String>();
        List<String> FCOUNTS = new ArrayList<String>();

        try {
            if (conn == null)
                conn = dbs.getAnalyticsSpatialConnection();
            pstmt = conn.prepareStatement(SALES_BY_GENDER_ON_OFFER);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                SALES_TYPE_NAME.add(rs.getString(1));
                MCOUNTS.add(rs.getString(2));
                FCOUNTS.add(rs.getString(3));
            }
            v.add(SALES_TYPE_NAME);
            v.add(MCOUNTS);
            v.add(FCOUNTS);
        } catch (Exception ex) {
            if (conn != null) {
                conn.rollback();
            }
            LOG.error(ex);
        } finally {
            if (conn != null && con == null) {
                conn.close();
            }
        }

        return v;
    }


    /**
     *	To get the top 5 products which are currently generating max revenue
     */
    public Vector getTopFiveRevenueSales(Connection con) throws Exception {

        Connection conn = con;
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Vector v = new Vector();
        List<String> productName = new ArrayList<String>();
        List<Integer> numberOfOrders = new ArrayList<Integer>();
        List<Double> totalValue = new ArrayList<Double>();
        List<Integer> revenueRank = new ArrayList<Integer>();

        try {
            if (conn == null)
                conn = dbs.getAnalyticsSpatialConnection();
            pstmt = conn.prepareStatement(PRODUCT_BY_REVENUE);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                productName.add(rs.getString(1));
                numberOfOrders.add(rs.getInt(2));
                totalValue.add(rs.getDouble(3));
                revenueRank.add(rs.getInt(4));
            }
            v.add(productName);
            v.add(numberOfOrders);
            v.add(totalValue);
            v.add(revenueRank);

        } catch (Exception ex) {
            if (conn != null) {
                conn.rollback();
            }
            LOG.error(ex);
        } finally {
            if (conn != null && con == null) {
                conn.close();
            }
        }

        return v;
    }

    /*
	 * To pull out the revenue by date
	 *
	 * */
    public Vector getRevenueByDate(Connection con) throws Exception {

        Connection conn = con;
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Vector v = new Vector();
        List<String> dates = new ArrayList<String>();
        List<Integer> numberOfOrders = new ArrayList<Integer>();
        List<Double> valueOfOrders = new ArrayList<Double>();


        try {
            if (conn == null)
                conn = dbs.getAnalyticsSpatialConnection();
            pstmt = conn.prepareStatement(REVENUE_BY_DATE);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                dates.add(rs.getString(1));
                numberOfOrders.add(rs.getInt(2));
                valueOfOrders.add(rs.getDouble(3));
            }
            v.add(dates);
            v.add(numberOfOrders);
            v.add(valueOfOrders);


        } catch (Exception ex) {
            if (conn != null) {
                conn.rollback();
            }
            LOG.error(ex);
        } finally {
            if (conn != null && con == null) {
                conn.close();
            }
        }

        return v;
    }


    public Vector getAllAnalyticsData() throws Exception {

        Vector allResult = new Vector();
        Analytics ant = new Analytics();
        Connection con1 = null;

        Connection con2 = null;
        try {
            con1 = dbs.getJsonXmlConnection();
            con2 = dbs.getAnalyticsSpatialConnection();
            Vector prdByCat = getProductCountByCategory(con1);
            allResult.add(0, prdByCat);

            int count = getTotalProductCount(con1);
            allResult.add(1, count);

            Vector prdByPrice = getProductCountByPrice(con1);
            allResult.add(2, prdByPrice);

            Vector v = getSalesByGender(con2);
            allResult.add(3, v);

            Vector v1 = getTopFiveRevenueSales(con2);
            allResult.add(4, v1);

            Vector v2 = getRevenueByDate(con2);
            allResult.add(5, v2);

        } catch (Exception e) {
            if (con1 != null)
                con1.rollback();

            if (con2 != null)
                con2.rollback();
            throw e;
        } finally {
            if (con1 != null)
                con1.close();
            if (con2 != null)
                con2.close();
        }


        return allResult;

    }


}
