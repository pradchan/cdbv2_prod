package converge.controllers;

import org.json.simple.JSONArray;

import org.json.simple.JSONObject;

import org.json.simple.parser.JSONParser;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/spatial")
public class SpatialController {
    public SpatialController() {
        super();
    }


    @RequestMapping(value = "/allCities", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public JSONArray getAllCities() throws Exception {
        String message = "";
        SpatialDao sdao = new SpatialDao();
        JSONArray jarray = new JSONArray();
        try {
            jarray = sdao.getCitiesAsJSON(null);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return jarray;
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE,
                    consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String insertNew(@RequestBody String str) throws Exception {
        String JsonString = str;
        String message = "Unable to insert";
        SpatialDao sdao = new SpatialDao();
        try {
            message = sdao.insertNewCity(null, JsonString);

        } catch (Exception e) {
            e.printStackTrace();
        }


        return message;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE,
                    consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String unpinCity(@RequestBody String str) throws Exception {
        String JsonString = str;
        String message = "Unable to delete";
        SpatialDao sdao = new SpatialDao();
        JSONObject json = (JSONObject) new JSONParser().parse(JsonString);
        try {
            message = sdao.deleteCity(null, String.valueOf(json.get("id")));

        } catch (Exception e) {
            e.printStackTrace();
        }


        return message;
    }

}
