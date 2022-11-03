package com.moneytransfer.backendserver.helpers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.HttpURLConnection;
import java.util.Map;
import org.json.JSONObject; // add library manually using ./tempLibFiles/json-20220924.jar file. This will be added using gradle in Android Studio.
//reference for gradle: https://mvnrepository.com/artifact/org.json/json/20220924


/**
 *  Class for make API Call
 */
public class ApiCallHelper {

    /**
     * Method for call API with GET method.
     * @param Url url to send
     * @param headerMap header option map <K = option name, V = option value>
     * @return return as JSONobject.
     * @throws Exception if some parameter are missed.
     */
    public JSONObject callGet(String Url, Map<String, String> headerMap, Map<String, String> paramMap) throws Exception{
        return callAPI("GET", Url, headerMap, null, paramMap);
    }

    /**
     * Method for call API with POST method.
     * @param Url url to send
     * @param headerMap header option map <K = option name, V = option value>
     * @param obj JSON object to send
     * @return return as JSONobject.
     * @throws Exception if some parameter are missed.
     */
    public JSONObject callPost(String Url, Map<String, String> headerMap, JSONObject obj) throws Exception{
        return callAPI("POST", Url, headerMap, obj, null);
    }

    /**
     * Method for call API with PUT method.
     * @param Url url to send
     * @param headerMap header option map <K = option name, V = option value>
     * @param obj JSON object to send
     * @return return as JSONobject.
     * @throws Exception if some parameter are missed.
     */
    public JSONObject callPut(String Url, Map<String, String> headerMap, JSONObject obj) throws Exception{
        return callAPI("PUT", Url, headerMap, obj, null);
    }

    /**
     * Method for call API with DELETE method.
     * @param Url url to send
     * @param headerMap header option map <K = option name, V = option value>
     * @return return as JSONobject.
     * @throws Exception if some parameter are missed.
     */
    public JSONObject callDelete(String Url, Map<String, String> headerMap) throws Exception{
        return callAPI("DELETE", Url, headerMap, null, null);
    }

    /**
     * Method for make API call
     * @param method method of API
     * @param Url address
     * @param headerMap header option map <K = option name, V = option value>
     * @param obj object to send.
     * @return return as JSONobject.
     * @throws Exception if some parameter are missed.
     */
    private JSONObject callAPI(String method, String Url
            , Map<String, String> headerMap, JSONObject obj, Map<String, String> paramMap) throws Exception {

        if (method.equalsIgnoreCase("GET") && paramMap != null) {
            String paramStr = "?";
            boolean isFirst = true;
            for (String paramName : paramMap.keySet()) {
                if (isFirst) {
                    isFirst = false;
                } else {
                    paramStr += "&";
                }
                paramStr += paramName;
                paramStr += "=";
                paramStr += paramMap.get(paramName);
            }
            Url += paramStr;
        }

        if (!method.equalsIgnoreCase("GET") && !method.equalsIgnoreCase("POST") && !method.equalsIgnoreCase("PUT") && !method.equalsIgnoreCase("DELETE")){
            throw new Exception("Method of API must be specified. Please check again.");
        }

        URL url = new URL(Url);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setRequestMethod(method);

        //if header has options
        for (String key : headerMap.keySet()) {
            connection.setRequestProperty(key, headerMap.get(key));
        }

        if (method.equalsIgnoreCase("POST") || method.equalsIgnoreCase("PUT") || method.equalsIgnoreCase("DELETE")) {
            connection.setDoInput(true); //input required
        }
        if (method.equalsIgnoreCase("GET") || method.equalsIgnoreCase("POST") || method.equalsIgnoreCase("PUT")) {
            connection.setDoOutput(true); //output required
        }

        if ((method.equalsIgnoreCase("POST") || method.equalsIgnoreCase("PUT"))) {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
            bw.write(obj.toString());
            bw.flush();
            bw.close();
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder res = new StringBuilder();
        String line = null;
        while((line = br.readLine()) != null) {
            res.append(line);
        }

        JSONObject ret = new JSONObject(res.toString());
        return ret;
    }
}
