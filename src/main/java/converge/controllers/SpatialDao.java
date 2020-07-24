package converge.controllers;

import converge.dbHelper.DBSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

class SpatialDao {
    SpatialDao() {
        super();
    }
    private DBSource dbs = new DBSource();
    private static String getAllCoordinates = "select city_id,city_name,latitude,longitude from city_points";
    private static String GET_CITIES_JSON =
        " select json_object(\n" + "    'id' value city.city_id,\n" + "    'name' value city.city_name,\n" +
        "    'lat' value city.latitude,\n" + "    'long' value city.longitude\n" + "   ) from city_points city";

    private static String INSERT_NEW_CITY =
        "INSERT INTO city_points (city_id, city_name, latitude, longitude)\n" + "  VALUES (?, ?, ?, ?)";

    private static String UPDATE_SHAPE =
        " UPDATE city_points SET shape = \n" + "  SDO_GEOMETRY(\n" + "    2001,\n" + "    8307,\n" +
        "    SDO_POINT_TYPE(LONGITUDE, LATITUDE, NULL),\n" + "    NULL,\n" + "    NULL\n" + "   ) where city_id =?";

    private static String GET_LAST_ID = "select max(city_id) from city_points";

    private static String DELETE_CITY = "delete from city_points where city_id=?";


    public JSONArray getCitiesAsJSON(Connection con) throws Exception {
        //      System.out.println("Inside deleteXml function - with id "+id);
        Connection conn = con;
        PreparedStatement pstmt;
        int rsCount = 0;
        ResultSet rs;
        JSONArray jarray = new JSONArray();


        try {

            if (conn == null)
                conn = dbs.getSpatialDS();
            pstmt = conn.prepareStatement(GET_CITIES_JSON);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                JSONObject temp = (JSONObject) new JSONParser().parse(rs.getString(1));
                // String temp = rs.getString(1);
                jarray.add(temp);
            }


        } catch (Exception e) {

            e.printStackTrace();

        } finally {
            if (conn != null && con == null) {
                conn.close();
            }
        }
        return jarray;
    }


    public String insertNewCity(Connection con, String str) throws Exception {
        //      System.out.println("Inside deleteXml function - with id "+id);
        Connection conn = con;
        PreparedStatement pstmt;
        int rsCount = 0;
        ResultSet rs;
        String message = "Unable to insert";
        JSONObject jo = (JSONObject) new JSONParser().parse(str);


        try {

            if (conn == null)
                conn = dbs.getSpatialDS();
            pstmt = conn.prepareStatement(GET_LAST_ID);
            rs = pstmt.executeQuery();
            int id = 0;
            while (rs.next()) {
                id = rs.getInt(1);
            }
            pstmt = conn.prepareStatement(INSERT_NEW_CITY);

            int newId = id + 1;
            pstmt.setInt(1, newId);
            pstmt.setString(2, String.valueOf(jo.get("name")));
            pstmt.setString(3, (String) jo.get("lat"));
            pstmt.setString(4, (String) jo.get("long"));
            rs = pstmt.executeQuery();

            pstmt = conn.prepareStatement(UPDATE_SHAPE);
            pstmt.setInt(1, newId);
            pstmt.executeUpdate();
            conn.commit();
            message = "Inserted";
        } catch (Exception e) {
            if (conn != null)
                conn.rollback();
            e.printStackTrace();

        } finally {
            if (conn != null && con == null) {
                conn.close();
            }
        }
        return message;

    }


    public String deleteCity(Connection con, String id) throws Exception {
        //      System.out.println("Inside deleteXml function - with id "+id);
        Connection conn = con;
        PreparedStatement pstmt;
        String message = "Unable to delete";
        ResultSet rs;


        try {

            if (conn == null)
                conn = dbs.getSpatialDS();
            pstmt = conn.prepareStatement(DELETE_CITY);
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();
            conn.commit();
            message = "deleted";


        } catch (Exception e) {
            if (conn != null)
                conn.rollback();
            e.printStackTrace();

        } finally {
            if (conn != null && con == null) {
                conn.close();
            }
        }
        return message;
    }
}
