package converge.controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLXML;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;

import converge.dbHelper.DBSource;

import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;

import oracle.xdb.XMLType;

class XmlDao {
    private DBSource dbs = new DBSource();
    private static String INSERT_XML = "INSERT INTO xml_type (doc) VALUES (?)";
    private static String READ_XML =
        "select doc from xml_type where xmlcast(xmlquery('$p/order/id/text()' passing doc AS \"p\" returning content) AS varchar2(5))=?";
    private static String GET_IDS = "SELECT extractValue(DOC, '/order/id') as ID FROM xml_type";

    private static String UPDATE_XML =
        "\r\n" + "UPDATE xml_type\r\n" + "    set doc=updateXML(doc, ?, ?)\r\n" +
        "    WHERE xmlcast(xmlquery('$p/order/id/text()' passing doc AS \"p\" returning content) AS varchar2(5))=?";

    private static String DELETE_XML_BY_ID =
        "delete from xml_type where xmlcast(xmlquery('$p/order/id/text()' passing doc AS \"p\" returning content) AS varchar2(5))=?";

    private static String CHECK_IF_XML_WITH_ID_EXISTS =
        "select count(*) from xml_type where xmlcast(xmlquery('$p/order/id/text()' passing doc AS \"p\" returning content) AS varchar2(5))=?";

    public void deleteXML(Connection con, String id) throws Exception {
        //	System.out.println("Inside deleteXml function - with id "+id);
        Connection conn = con;
        PreparedStatement pstmt;
        int rsCount = 0;
        ResultSet rs;

        try {

            if (conn == null)
                conn = dbs.getXMLDS();
            pstmt = conn.prepareStatement(DELETE_XML_BY_ID);
            pstmt.setString(1, id);

            rs = pstmt.executeQuery();
            System.out.println("deleted");
            conn.commit();

        } catch (Exception e) {
            if (conn != null) {
                conn.rollback();
            }
            e.printStackTrace();

        } finally {
            if (conn != null && con == null) {
                conn.close();
            }
        }
    }

    public void updateXML(Connection con, String id, String query, String value) throws Exception {

        Connection conn = con;
        PreparedStatement pstmt;
        int rsCount = 0;
        System.out.println("printing values");
        System.out.println(id);
        System.out.println(query);
        System.out.println(value);

        try {

            if (conn == null)
                conn = dbs.getXMLDS();
            pstmt = conn.prepareStatement(UPDATE_XML);
            pstmt.setString(1, query);
            pstmt.setString(2, value);
            pstmt.setString(3, id);
            pstmt.executeQuery();
            conn.commit();
            System.out.println(rsCount + " rows updated");
            System.out.println("updated");

        } catch (Exception e) {
            if (conn != null) {
                conn.rollback();
            }
            e.printStackTrace();

        } finally {
            if (conn != null && con == null) {
                conn.close();
            }
        }
    }

    public String insertXml(Connection con, String doc) throws Exception {
        String message = "Cannot inesrt";

        Connection conn = con;
        OraclePreparedStatement opsmt;
        PreparedStatement pstmt;
        int i = 0;
        System.out.println(doc);
        try {
            if (conn == null)
                conn = dbs.getXMLDS();
            opsmt = (OraclePreparedStatement) conn.prepareStatement(INSERT_XML);
            XMLType poXML = XMLType.createXML(conn, doc);
            opsmt.setObject(1, poXML);
            i = opsmt.executeUpdate();
            message = i + " elements inserted";
            conn.commit();
        } catch (Exception ex) {
            if (conn != null) {
                conn.rollback();
            }
        } finally {
            if (con == null || conn != null) {
                conn.close();
            }
        }

        return message;

    }

    public String fetchXML(Connection con, String id) throws Exception {
        Connection conn = con;
        OraclePreparedStatement opstmt;
        ResultSet rs;
        OracleResultSet ors;
        String xmlString = "";
        System.out.println("reached to read the xml value for id " + id);
        try {
            if (conn == null)
                conn = dbs.getXMLDS();
            opstmt = (OraclePreparedStatement) conn.prepareStatement(READ_XML);
            opstmt.setString(1, id);
            rs = opstmt.executeQuery();

            ors = (OracleResultSet) rs;
            SQLXML poxml = null;


            while (ors.next()) {

                poxml = ors.getSQLXML(1);

            }
            xmlString = poxml.getString();


        } catch (Exception ex) {
            if (conn != null)
                conn.rollback();
            throw ex;
        } finally {

            if (con == null && conn != null) {
                conn.close();
            }
        }
        return xmlString;
    }

    public List<Number> getId(Connection con) throws Exception {
        Connection conn = con;
        ResultSet rs;
        PreparedStatement pstmt;
        List<Number> ids = new ArrayList<Number>();

        try {
            if (conn == null)
                conn = dbs.getXMLDS();
            pstmt = conn.prepareStatement(GET_IDS);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ids.add(rs.getInt(1));
            }


        } catch (Exception ex) {
            if (conn != null)
                conn.rollback();
            throw ex;
        } finally {

            if (con == null && conn != null) {
                conn.close();
            }
        }
        return ids;
    }
}
