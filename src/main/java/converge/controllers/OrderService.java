package converge.controllers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;

import java.sql.Clob;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import converge.models.CartItem;
import converge.models.Order;

class OrderService {
    private static final Logger LOG = Logger.getLogger(OrderService.class);

    /*
	 * To map xml data to Order object and return
	 */
    public Order getObject(Document doc) throws Exception {

        Order order = new Order();
        try {
            doc.getDocumentElement().normalize();

            NodeList nListProducts = doc.getElementsByTagName("products");
            List<CartItem> cartItemList = new ArrayList<CartItem>();
            for (int i = 0; i < nListProducts.getLength(); i++) {
                CartItem cartm = new CartItem();
                Node nNode = nListProducts.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    cartm.setPid(eElement.getElementsByTagName("pid")
                                         .item(0)
                                         .getTextContent());
                    cartm.setCategory(eElement.getElementsByTagName("category")
                                              .item(0)
                                              .getTextContent());
                    cartm.setTitle(eElement.getElementsByTagName("title")
                                           .item(0)
                                           .getTextContent());
                    cartm.setDetails(eElement.getElementsByTagName("details")
                                             .item(0)
                                             .getTextContent());
                    cartm.setPrice(eElement.getElementsByTagName("price")
                                           .item(0)
                                           .getTextContent());
                    cartm.setPicture(eElement.getElementsByTagName("picture")
                                             .item(0)
                                             .getTextContent());
                    cartm.setQuantity(Long.parseLong(eElement.getElementsByTagName("quantity")
                                                             .item(0)
                                                             .getTextContent()));
                }
                cartItemList.add(cartm);

            }
            order.setCartList(cartItemList);
            NodeList nListUser = doc.getElementsByTagName("userDetails");
            for (int i = 0; i < nListUser.getLength(); i++) {
                Node nNode = nListUser.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    order.setAddress(eElement.getElementsByTagName("address")
                                             .item(0)
                                             .getTextContent());
                    order.setCity(eElement.getElementsByTagName("city")
                                          .item(0)
                                          .getTextContent());
                    order.setEmail(eElement.getElementsByTagName("email")
                                           .item(0)
                                           .getTextContent());
                    order.setOrderedBy(eElement.getElementsByTagName("name")
                                               .item(0)
                                               .getTextContent());
                    order.setPhone(eElement.getElementsByTagName("phone")
                                           .item(0)
                                           .getTextContent());
                    order.setPincode(eElement.getElementsByTagName("pincode")
                                             .item(0)
                                             .getTextContent());

                }
            }


        } catch (Exception e) {
            throw e;
        }
        return order;
    }
    /*
	 * The wirteToXML accepts the order and convert it to a XML, return back the document
	 */
    public String writeToXML(Order order) throws Exception {
        String rs = null;
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        try {

            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();


            // root element
            Element rootElement = doc.createElement("order");
            doc.appendChild(rootElement);

            for (int i = 0; i < order.getCartList().size(); i++) {

                Element products = doc.createElement("products");
                rootElement.appendChild(products);

                // setting attribute to element
                Element elmPid = doc.createElement("pid");
                elmPid.setTextContent(order.getCartList()
                                           .get(i)
                                           .getPid());
                products.appendChild(elmPid);


                // setting attribute to element

                Element elmcategory = doc.createElement("category");
                elmcategory.setTextContent(order.getCartList()
                                                .get(i)
                                                .getCategory());
                products.appendChild(elmcategory);


                // setting attribute to element

                Element elmtitle = doc.createElement("title");
                elmtitle.setTextContent(order.getCartList()
                                             .get(i)
                                             .getTitle());
                products.appendChild(elmtitle);


                // setting attribute to element

                Element elmdetails = doc.createElement("details");
                elmdetails.setTextContent(order.getCartList()
                                               .get(i)
                                               .getDetails());
                products.appendChild(elmdetails);


                // setting attribute to element

                Element elmprice = doc.createElement("price");
                elmprice.setTextContent(order.getCartList()
                                             .get(i)
                                             .getPrice());
                products.appendChild(elmprice);


                // setting attribute to element

                Element elmpicture = doc.createElement("picture");
                elmpicture.setTextContent(order.getCartList()
                                               .get(i)
                                               .getPicture());
                products.appendChild(elmpicture);


                // setting attribute to element

                Element elmquantity = doc.createElement("quantity");
                elmquantity.setTextContent(String.valueOf(order.getCartList()
                                                               .get(i)
                                                               .getQuantity()));
                products.appendChild(elmquantity);
            }
            // supercars element
            Element userDetails = doc.createElement("userDetails");
            rootElement.appendChild(userDetails);


            Element elmname = doc.createElement("name");
            elmname.setTextContent(order.getOrderedBy());
            userDetails.appendChild(elmname);


            Element elmemail = doc.createElement("email");
            elmemail.setTextContent(order.getEmail());
            userDetails.appendChild(elmemail);


            Element elmphone = doc.createElement("phone");
            elmphone.setTextContent(order.getPhone());
            userDetails.appendChild(elmphone);


            Element elmaddress = doc.createElement("address");
            elmaddress.setTextContent(order.getAddress());
            userDetails.appendChild(elmaddress);


            Element elmcity = doc.createElement("city");
            elmcity.setTextContent(order.getCity());
            userDetails.appendChild(elmcity);


            Element elmpincode = doc.createElement("pincode");
            elmpincode.setTextContent(order.getPincode());
            userDetails.appendChild(elmpincode);


            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");


            //--------------

            //         ByteArrayOutputStream baos = new ByteArrayOutputStream();
            //         Result outputTarget = new StreamResult(baos);
            //         transformer.transform(new DOMSource(doc), outputTarget);
            //         InputStream is = new ByteArrayInputStream(baos.toByteArray());


            //---------------


            StringWriter writer = new StringWriter();

            //transform document to string
            transformer.transform(new DOMSource(doc), new StreamResult(writer));

            rs = writer.getBuffer().toString();
            //System.out.println(rs);

        } catch (Exception e) {
            LOG.error("Error Converting to XML");
        }

        return rs;
    }
}
