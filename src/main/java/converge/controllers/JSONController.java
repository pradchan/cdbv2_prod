package converge.controllers;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import converge.models.Product;

import java.util.logging.Logger;

@Controller
@RequestMapping("/json")
public class JSONController {


    @RequestMapping(value = "/product/{id}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String findProductById(@PathVariable String id) {

        String jsonString = "";
        JSONDao jdao = new JSONDao();
        try {
            jsonString = jdao.getProductById(null, id);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return jsonString;
    }


    @RequestMapping(value = "/product/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,
                    consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String updateProductById(@RequestBody String str) {
        //System.out.println(str);
        String jsonString = str;
        JSONDao jdao = new JSONDao();
        String message = "Error while inserting";
        int recordsModified = 0;

        try {
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(jsonString);

            if (json.get("pid") == null)
                return "pid missing please correct";
            else
                recordsModified = jdao.updateProductById(null, jsonString, String.valueOf(json.get("pid")));
            message = recordsModified + " records updated";

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        return message;
    }

    @RequestMapping(value = "/product/delete", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String deleteProductById(@RequestBody String str) {
        //System.out.println(str);
        String messageString = "";
        String jsonString = str;
        JSONDao jdao = new JSONDao();
        int recordsModified = 0;

        try {
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(jsonString);


            recordsModified = jdao.deleteProductById(null, String.valueOf(json.get("pid")));
            messageString = recordsModified + " records modified";

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }
        return messageString;

    }


    @RequestMapping(value = "/product/insert", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String insertJson(@RequestBody String str) {
        //System.out.println(str);
        String jsonString = str;
        JSONDao jdao = new JSONDao();
        String recordsModified = "";

        try {
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(jsonString);

            if (json.get("pid") == null)
                return "pid missing please correct";
            else
                recordsModified = jdao.insertJson(null, jsonString, String.valueOf(json.get("pid")));


        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return recordsModified;

    }

}
