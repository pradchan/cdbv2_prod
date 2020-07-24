package converge.controllers;


import java.io.InputStream;

import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLXML;

import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import converge.dbHelper.DBSource;

import converge.models.CartItem;
import converge.models.Order;

import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;

import oracle.xdb.XMLType;

class OrderDao {
    private DBSource dbs = new DBSource();

    private static String INSERT_ORDER = "";
    private static String GET_ORDER = "select id,order_document from orders";
    private static String GET_COUNT = "select count(*) from orders";
    private static String INSERT_NEW = "INSERT INTO orders (order_document) VALUES (?)";
    private static String UPDATE_DOC = "UPDATE orders SET order_document=?";


    public Order placeOrder(Connection con, Order od) throws Exception {

        Connection conn = con;
        ResultSet rs = null;
        PreparedStatement ptsmt;
        OraclePreparedStatement opsmt;
        CartDao cdao = new CartDao();
        OrderService ors = new OrderService();
        try {
            if (conn == null)
                conn = dbs.getJsonXmlConnection();
            List<CartItem> ctlist = cdao.getCartDoc(conn);
            od.setCartList(ctlist);

            String xml = ors.writeToXML(od);

            ptsmt = conn.prepareStatement(GET_COUNT);
            rs = ptsmt.executeQuery();
            int count = 0;
            while (rs.next()) {
                count = rs.getInt(1);
            }
            if (count > 0) {
                opsmt = (OraclePreparedStatement) conn.prepareStatement(UPDATE_DOC);
            } else {
                opsmt = (OraclePreparedStatement) conn.prepareStatement(INSERT_NEW);
            }
            XMLType poXML = XMLType.createXML(conn, xml);
            opsmt.setObject(1, poXML);
            boolean flag = opsmt.execute();

            cdao.clearCart(conn);

            conn.commit();


        } catch (Exception e) {
            if (conn != null)
                conn.rollback();
        } finally {
            if (con == null && conn != null) {
                conn.close();
            }
        }

        return od;
    }


    public Order getOrder(Connection con) throws Exception {
        Connection conn = con;
        ResultSet rs = null;
        Document podoc = null;
        OraclePreparedStatement opstmt;
        OracleResultSet ors = null;
        PreparedStatement stmt;
        Order od = new Order();

        OrderService osr = new OrderService();
        try {
            if (conn == null)
                conn = dbs.getJsonXmlConnection();
            opstmt = (OraclePreparedStatement) conn.prepareStatement(GET_ORDER);

            rs = opstmt.executeQuery();

            ors = (OracleResultSet) rs;
            SQLXML poxml = null;
            Document doc;

            while (ors.next()) {

                poxml = ors.getSQLXML(2);

            }


            InputStream binaryStream = poxml.getBinaryStream();
            DocumentBuilder parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            doc = parser.parse(binaryStream);
            binaryStream.close();


            od = osr.getObject(doc);


        } catch (Exception ex) {
            if (conn != null)
                conn.rollback();
            throw ex;
        } finally {

            if (con == null && conn != null) {
                conn.close();
            }
        }


        return od;
    }
}
