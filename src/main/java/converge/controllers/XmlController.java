package converge.controllers;

import java.util.ArrayList;
import java.util.List;

import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/xml")
public class XmlController {


    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ResponseBody
    public String insertXML(@RequestBody String str) {
        XmlDao xd = new XmlDao();

        String message = null;
        System.out.println("From xml inset function");
        System.out.println(str);
        try {
            message = xd.insertXml(null, str);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return message;
    }


    @RequestMapping(value = "/read/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public String fetchXml(@PathVariable String id) {
        String message = "cannot read";
        XmlDao xd = new XmlDao();
        try {
            message = xd.fetchXML(null, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return message;
    }


    @RequestMapping(value = "/ids", method = RequestMethod.GET)
    @ResponseBody
    public List<Number> getXmlIds() {
        List<Number> idList = new ArrayList<Number>();
        XmlDao xd = new XmlDao();
        try {
            idList = xd.getId(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return idList;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String updateXml(@RequestBody String jsonString) {
        String message = "";
        XmlDao xd = new XmlDao();
        try {
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(jsonString);
            String query = String.valueOf(json.get("query"));
            String id = String.valueOf(json.get("id"));
            String value = String.valueOf(json.get("value"));
            xd.updateXML(null, id, query, value);
            message = "updated Successfully";
        } catch (Exception e) {
            e.printStackTrace();
        }


        return message;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String deleteXml(@RequestBody String jsonString) {
        System.out.println("Inside delete xml doc");
        String message = "";
        XmlDao xd = new XmlDao();
        try {
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(jsonString);
            System.out.println(String.valueOf(json.get("id")));
            String id = String.valueOf(json.get("id"));
            xd.deleteXML(null, id);
            message = "deleted successfully";

        } catch (Exception e) {
            message = "error while deleting";
            e.printStackTrace();
        }

        return message;
    }
}
