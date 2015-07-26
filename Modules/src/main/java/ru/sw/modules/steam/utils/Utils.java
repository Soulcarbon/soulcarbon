package ru.sw.modules.steam.utils;


import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.CharacterData;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Utils {
    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }
    public static String getCharacterDataFromElement(Element e) {
        Node child = e.getFirstChild();
        if (child instanceof CharacterData) {
            CharacterData cd = (CharacterData) child;
            return cd.getData();
        }
        return "";
    }

    public static ValuteEntry convertDollarToRuble(String dollar) throws ParserConfigurationException, IOException, SAXException, ParseException {
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = builder.parse("http://www.cbr.ru/scripts/XML_daily.asp?");

        NodeList nodes = doc.getElementsByTagName("Valute");
        Double course = new Double(0.0);
        NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);
        Number number = null;
        for (int i = 0; i < nodes.getLength(); i++) {
            Element element = (Element) nodes.item(i);
            NodeList title = element.getElementsByTagName("CharCode");
            Element element2 = (Element) title.item(0);
            String charCode = Utils.getCharacterDataFromElement(element2);
            if(charCode.equals("USD")){
                NodeList title2 = element.getElementsByTagName("Value");
                Element element3 = (Element) title2.item(0);
                number = format.parse(Utils.getCharacterDataFromElement(element3));
                course = number.doubleValue();
                break;
            }
        }

        format =  NumberFormat.getInstance(Locale.US);
        number = format.parse(dollar);
        double usd = number.doubleValue();
        double rub = course*usd;
        return new ValuteEntry(rub,usd);
    }
}
