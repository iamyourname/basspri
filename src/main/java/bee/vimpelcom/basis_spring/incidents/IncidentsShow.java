package bee.vimpelcom.basis_spring.incidents;

import bee.vimpelcom.basis_spring.controller.MainController;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class IncidentsShow {

    final static Logger logger = LoggerFactory.getLogger(IncidentsShow.class);
    public static String token;
    public static String jsonToken = "{" +
            "\"jsonrpc\":\"2.0\"," +
            "\"method\":\"user.login\"," +
            "\"params\":" +
            "{\"user\":\"mivyamoiseev\"," +
            "\"password\":\"xid123MTPP_\"}," +
            "\"id\":1" +
            "}";

    public static void updateAuthTokenIncidents()  {
        String url = "https://zabbix.vimpelcom.ru/api_jsonrpc.php";
        try{
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(jsonToken);
            wr.flush();
            wr.close();
            int responseCode = con.getResponseCode();
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            JSONObject jsonResp = new JSONObject(response.toString());
            token= jsonResp.getString("result");
            logger.info("token has been updated  " + token);
        }catch(IOException e){
            logger.error(e.toString());
        }



    }

    public static String apiSender(String body) throws IOException {
        String url = "https://zabbix.vimpelcom.ru/api_jsonrpc.php";
        //logger.info(body);
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(body);
        wr.flush();
        wr.close();
        //https://code-with-me.jetbrains.com.cn/Dj540epCQVhviRMByy4GfA#p=IU&fp=DD6B74042F9C965451EA4FDF1096193F149115560A937603923899706CDF3971&newUi=true
        int responseCode = con.getResponseCode();
        //System.out.println("Response Code: " + responseCode);
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        //System.out.println(response.toString());
        if(responseCode==200){
            // logger.info(response.toString());
            JSONObject responseJson = new JSONObject(response.toString());
            JSONObject responseLast = new JSONObject(responseJson
                    .get("result")
                    .toString()
                    .replace("[{","{")
                    .replace("}]","}"));
            return responseLast.get("lastvalue").toString();

        }else {
            return Integer.toString(responseCode);
        }

    }


    public static String getAllIncCount(){

        String apiUsersCount = "{\n" +
                "  \"method\": \"item.get\",\n" +
                "  \"auth\": \""+token+"\",\n" +
                "  \"id\": 1,\n" +
                "  \"jsonrpc\": \"2.0\",\n" +
                "  \"params\": {\n" +
                "    \"filter\": {\n" +
                "      \"itemid\": \"36281172\"\n" +
                "    },\n" +
                "    \"hostids\": \"108707\"\n" +
                "  }\n" +
                "}";


        return "response";
    }


}
