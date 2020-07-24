package converge.controllers;

import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import org.json.simple.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import converge.dbHelper.DBSource;

import converge.models.Analytics;

@Controller
@EnableWebMvc
public class AnalyticsController {

    private static final Logger LOG = Logger.getLogger(AnalyticsController.class);
    DBSource dbs = new DBSource();


    /*To fetch geolocation data stored for a selected area
	 * default selection is MICHIGAN
	 * */
    @RequestMapping("/locator")
    public ModelAndView getStoreLocation(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        LOG.debug("Inside locator handler");
        LOG.info("Inside locator Controller >> getStoreLocation");
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<String> areaOptions = new ArrayList<String>();
        ModelAndView mv = new ModelAndView();
        List<String> mapDataJson = new ArrayList<String>();
        String selectedOption = null;
        if (req.getParameter("area") != null) {
            selectedOption = (String) req.getParameter("area");
        }
        String sqlDistinctArea = "select distinct area_name from store_loc";
        String sqlGetStoreLocation =
            "select STORE_ID,\r\n" + "      STORE_NAME,\r\n" + "      CENTER_LONG,\r\n" + "      CENTER_LAT,\r\n" +
            "      POSTAL_CODE from store_loc where AREA_NAME=?";

        try {

            con = dbs.getJsonXmlConnection();
            pstmt = con.prepareStatement(sqlDistinctArea);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                areaOptions.add(rs.getString(1));

            }
            if (selectedOption == null || !areaOptions.contains(selectedOption)) {
                LOG.debug("reached inside default value");
                selectedOption = "MICHIGAN";
            }
            LOG.debug("Will execute the spatial data " + selectedOption);
            pstmt = con.prepareStatement(sqlGetStoreLocation);
            pstmt.setString(1, selectedOption.trim());
            rs = pstmt.executeQuery();
            Map<String, Object> mapData = new HashMap<String, Object>();

            while (rs.next()) {

                mapData.put("STORE_ID", (Integer) rs.getInt(1));
                mapData.put("STORE_NAME", (String) rs.getString(2));
                mapData.put("CENTER_LONG", (Double) rs.getDouble(3));
                mapData.put("CENTER_LAT", (Double) rs.getDouble(4));
                mapData.put("POSTAL_CODE", (Long) rs.getLong(5));
                mapDataJson.add(JSONObject.toJSONString(mapData));


            }
        } catch (Exception e) {

            if (con != null)
                con.rollback();
            throw e;
        } finally {
            if (con != null)
                con.close();
        }

        mv.addObject("result", mapDataJson);
        mv.addObject("selectedArea", selectedOption);
        mv.addObject("options", areaOptions);
        mv.setViewName("map");
        return mv;
    }


    /*
	 * To serve the dashboard request
	 * Fetched lots of data from different data tables, implement logic and return in a format that can be presented on a browser
	 * */
    @RequestMapping("/dashboard")
    public ModelAndView renderDashboard(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        ModelAndView mv = new ModelAndView();
        Vector vct = new Vector();
        Analytics ant = new Analytics();
        AnalyticsService asr = new AnalyticsService();
        ant = asr.getAnalyticsData();
        mv.addObject("category", ant.getCategoryForCatByCount());
        mv.addObject("categoryList", ant.getCategoryForCatByCountList());
        mv.addObject("count", ant.getCountForCatByCount());
        mv.addObject("productName", ant.getProductNameForTopFiveRevenueSales());
        mv.addObject("orderCount", ant.getNumberOfOrdersForTopFiveRevenueSales());
        mv.addObject("totalValue", ant.getTotalValueForTopFiveRevenueSales());
        mv.addObject("revenueRank", ant.getRevenueRankForTopFiveRevenueSales());
        mv.addObject("dates", ant.getDatesForGetRevenueByDate());
        mv.addObject("value", ant.getValueOfOrdersForGetRevenueByDate());

        mv.addObject("sale", ant.getNameForSalesByGender());
        mv.addObject("maleCount", ant.getMaleCountForSalesByGender());
        mv.addObject("femaleCount", ant.getFemaleCountForSalesByGender());
        //end
        mv.addObject("totalCount", ant.getTotalProductCount());
        mv.setViewName("dashboard");
        return mv;
    }


}
