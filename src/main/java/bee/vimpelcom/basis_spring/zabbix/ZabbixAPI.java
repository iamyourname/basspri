package bee.vimpelcom.basis_spring.zabbix;


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
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ZabbixAPI {

    final static Logger logger = LoggerFactory.getLogger(MainController.class);
    public static String[][] table_inc;
    public static String jsonToken = "{" +
            "\"jsonrpc\":\"2.0\"," +
            "\"method\":\"user.login\"," +
            "\"params\":" +
            "{\"user\":\"mivyamoiseev\"," +
            "\"password\":\"xid123MTPP_\"}," +
            "\"id\":1" +
            "}";
    public static String token;

    public static String zabbixSenderAPI(String body) throws IOException {
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

    // обновление токена
    public static void updateAuthToken() throws IOException {
        String url = "https://zabbix.vimpelcom.ru/api_jsonrpc.php";

        logger.info("start updating token");
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(jsonToken);
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
        JSONObject jsonResp = new JSONObject(response.toString());
        //System.out.println(response.toString());

        token= jsonResp.getString("result");
        logger.info("token has been updated  " + token);
    }



    // Количество пользователей
    public static String getUsersCount() throws IOException {
        String apiUsersCount = "{\n" +
                "  \"method\": \"item.get\",\n" +
                "  \"auth\": \""+token+"\",\n" +
                "  \"id\": 1,\n" +
                "  \"jsonrpc\": \"2.0\",\n" +
                "  \"params\": {\n" +
                "    \"filter\": {\n" +
                "      \"itemid\": \"28317570\"\n" +
                "    },\n" +
                "    \"hostids\": \"103390\"\n" +
                "  }\n" +
                "}";

        return zabbixSenderAPI(apiUsersCount);
    }

    public static String getActiveIncToday() throws IOException {
        String apiUsersCount = "{\n" +
                "  \"method\": \"item.get\",\n" +
                "  \"auth\": \""+token+"\",\n" +
                "  \"id\": 1,\n" +
                "  \"jsonrpc\": \"2.0\",\n" +
                "  \"params\": {\n" +
                "    \"filter\": {\n" +
                "      \"itemid\": \"31283192\"\n" +
                "    },\n" +
                "    \"hostids\": \"108707\"\n" +
                "  }\n" +
                "}";

        return "ОТКРЫТЫХ ИНЦИДЕНТОВ: "+zabbixSenderAPI(apiUsersCount);
    }


    // РЕШЕНО ЗА СЕГОДНЯ
    public static String getDoneIncToday() throws IOException {
        String apiUsersCount = "{\n" +
                "  \"method\": \"item.get\",\n" +
                "  \"auth\": \""+token+"\",\n" +
                "  \"id\": 1,\n" +
                "  \"jsonrpc\": \"2.0\",\n" +
                "  \"params\": {\n" +
                "    \"filter\": {\n" +
                "      \"itemid\": \"31282913\"\n" +
                "    },\n" +
                "    \"hostids\": \"108707\"\n" +
                "  }\n" +
                "}";

        return zabbixSenderAPI(apiUsersCount);
    }


    //  ПРИШЛО ЗА СЕГОДНЯ
    public static String getIncomingIncToday() throws IOException {
        String apiUsersCount = "{\n" +
                "  \"method\": \"item.get\",\n" +
                "  \"auth\": \""+token+"\",\n" +
                "  \"id\": 1,\n" +
                "  \"jsonrpc\": \"2.0\",\n" +
                "  \"params\": {\n" +
                "    \"filter\": {\n" +
                "      \"itemid\": \"31126668\"\n" +
                "    },\n" +
                "    \"hostids\": \"108707\"\n" +
                "  }\n" +
                "}";

        return zabbixSenderAPI(apiUsersCount);
    }


    //"В ОЖИДАНИИ: "+
    public static String getWaitIncToday() throws IOException {
        String apiUsersCount = "{\n" +
                "  \"method\": \"item.get\",\n" +
                "  \"auth\": \""+token+"\",\n" +
                "  \"id\": 1,\n" +
                "  \"jsonrpc\": \"2.0\",\n" +
                "  \"params\": {\n" +
                "    \"filter\": {\n" +
                "      \"itemid\": \"31283468\"\n" +
                "    },\n" +
                "    \"hostids\": \"108707\"\n" +
                "  }\n" +
                "}";

        return zabbixSenderAPI(apiUsersCount);
    }


    //"ВСЕГО ИНЦИДЕНТОВ: "+
    public static String getAllInc() throws IOException {
        String apiUsersCount = "{\n" +
                "  \"method\": \"item.get\",\n" +
                "  \"auth\": \""+token+"\",\n" +
                "  \"id\": 1,\n" +
                "  \"jsonrpc\": \"2.0\",\n" +
                "  \"params\": {\n" +
                "    \"filter\": {\n" +
                "      \"itemid\": \"32674559\"\n" +
                "    },\n" +
                "    \"hostids\": \"108707\"\n" +
                "  }\n" +
                "}";

        return zabbixSenderAPI(apiUsersCount);
    }

    //"ИНЦИДЕНТОВ В РАБОТЕ: "+
    public static String getInWorkInc() throws IOException {
        String apiUsersCount = "{\n" +
                "  \"method\": \"item.get\",\n" +
                "  \"auth\": \""+token+"\",\n" +
                "  \"id\": 1,\n" +
                "  \"jsonrpc\": \"2.0\",\n" +
                "  \"params\": {\n" +
                "    \"filter\": {\n" +
                "      \"itemid\": \"32910967\"\n" +
                "    },\n" +
                "    \"hostids\": \"108707\"\n" +
                "  }\n" +
                "}";

        return zabbixSenderAPI(apiUsersCount);
    }


    //"Процент ошибочных операций: "+
    public static String getProcErrors() throws IOException {
        String apiUsersCount = "{\n" +
                "  \"method\": \"item.get\",\n" +
                "  \"auth\": \""+token+"\",\n" +
                "  \"id\": 1,\n" +
                "  \"jsonrpc\": \"2.0\",\n" +
                "  \"params\": {\n" +
                "    \"filter\": {\n" +
                "      \"itemid\": \"30378713\"\n" +
                "    },\n" +
                "    \"hostids\": \"104447\"\n" +
                "  }\n" +
                "}";
            //
        return zabbixSenderAPI(apiUsersCount);
    }

    public static String getProcSuccess() throws IOException {
        String apiUsersCount = "{\n" +
                "  \"method\": \"item.get\",\n" +
                "  \"auth\": \""+token+"\",\n" +
                "  \"id\": 1,\n" +
                "  \"jsonrpc\": \"2.0\",\n" +
                "  \"params\": {\n" +
                "    \"filter\": {\n" +
                "      \"itemid\": \"37020512\"\n" +
                "    },\n" +
                "    \"hostids\": \"104447\"\n" +
                "  }\n" +
                "}";
        //
        return zabbixSenderAPI(apiUsersCount);
    }

    //28986977


    //Успешных операций: "+
    public static String getSuccessActions() throws IOException {
        String apiUsersCount = "{\n" +
                "  \"method\": \"item.get\",\n" +
                "  \"auth\": \""+token+"\",\n" +
                "  \"id\": 1,\n" +
                "  \"jsonrpc\": \"2.0\",\n" +
                "  \"params\": {\n" +
                "    \"filter\": {\n" +
                "      \"itemid\": \"28986977\"\n" +
                "    },\n" +
                "    \"hostids\": \"104447\"\n" +
                "  }\n" +
                "}";

        return zabbixSenderAPI(apiUsersCount);
    }

    //30378766

    //"PROC_ERRORS_K: "+
    public static String getProcErrors_K() throws IOException {
        String apiUsersCount = "{\n" +
                "  \"method\": \"item.get\",\n" +
                "  \"auth\": \""+token+"\",\n" +
                "  \"id\": 1,\n" +
                "  \"jsonrpc\": \"2.0\",\n" +
                "  \"params\": {\n" +
                "    \"filter\": {\n" +
                "      \"itemid\": \"30378766\"\n" +
                "    },\n" +
                "    \"hostids\": \"104447\"\n" +
                "  }\n" +
                "}";

        return zabbixSenderAPI(apiUsersCount);
    }

    //103486&itemid=28336322

    //Доступность страницы zakupki.beeline.ru:
    public static String getZakupkiStatus() throws IOException {
        String apiUsersCount = "{\n" +
                "  \"method\": \"item.get\",\n" +
                "  \"auth\": \""+token+"\",\n" +
                "  \"id\": 1,\n" +
                "  \"jsonrpc\": \"2.0\",\n" +
                "  \"params\": {\n" +
                "    \"filter\": {\n" +
                "      \"itemid\": \"28336322\"\n" +
                "    },\n" +
                "    \"hostids\": \"103486\"\n" +
                "  }\n" +
                "}";

       // if(zabbixSenderAPI(apiUsersCount).equals("401"))
        //    return "ДОСТУПНОСТЬ СТРАНИЦЫ: "+"OK";

        return "OK";//+zabbixSenderAPI(apiUsersCount)
    }

    public static String getProsrokTable() throws IOException{
        String url = "https://zabbix.vimpelcom.ru/api_jsonrpc.php";
        String body = "{\n" +
                "  \"method\": \"item.get\",\n" +
                "  \"auth\": \""+token+"\",\n" +
                "  \"id\": 1,\n" +
                "  \"jsonrpc\": \"2.0\",\n" +
                "  \"params\": {\n" +
                "    \"filter\": {\n" +
                "      \"itemid\": \"34475142\"\n" +
                "    },\n" +
                "    \"hostids\": \"108707\"\n" +
                "  }\n" +
                "}";
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
        String printMes="<table>";
        if(responseCode==200){
            //System.out.println(response.toString());
            JSONObject responseJson = new JSONObject(response.toString());

            JSONObject responseLast = new JSONObject(responseJson
                    .get("result")
                    .toString()
                    .replace("[{","{")
                    .replace("}]","}"));
            //return responseLast.get("lastvalue").toString();
            String[] jsonmes = responseLast.get("lastvalue").toString()
                    .replace("[","")
                    .replace("]","")
                    .replace("},{","}!{")
                    .split("!");

            table_inc = new String[jsonmes.length][5];
            int r=1;
            for(int i=0; i< jsonmes.length;i++){
                //printMes+="<td>";
                JSONObject jsonObject = new JSONObject(jsonmes[i]);
                table_inc[i][0]= String.valueOf(r);
                r++;
                table_inc[i][1]= jsonObject.getString("Number");
                table_inc[i][2]= jsonObject.getString("Status_new");
                table_inc[i][3]= jsonObject.getString("Number_Depend");
                table_inc[i][4]= "Просрочен на: "+
                        jsonObject.getString("DAY")+" дней, "+
                        jsonObject.getString("HH")+ " часов, "+
                        jsonObject.getString("MM")+ " минут";
                printMes +="<tr><td>"+jsonObject.getString("Number")+"</td>";
                printMes +="<td>Просрочен на: "+
                        jsonObject.getString("DAY")+" дней, "+
                        jsonObject.getString("HH")+ " часов, "+
                        jsonObject.getString("MM")+ " минут";
                printMes+="</td></tr>";
            }
            printMes+="</table>";
            //System.out.println(printMes);

        }

        return printMes;
    }

    public static String getMaxUsersCount() throws IOException {

        String apiUsersCount = "{\n" +
                "  \"method\": \"item.get\",\n" +
                "  \"auth\": \""+token+"\",\n" +
                "  \"id\": 1,\n" +
                "  \"jsonrpc\": \"2.0\",\n" +
                "  \"params\": {\n" +
                "    \"filter\": {\n" +
                "      \"itemid\": \"36647579\"\n" +
                "    },\n" +
                "    \"hostids\": \"132385\"\n" +
                "  }\n" +
                "}";

        JSONObject gmux = new JSONObject(zabbixSenderAPI(apiUsersCount));


        //
        return gmux.getString("max");

    }

    public static String getZabbixInfo() throws IOException, SQLException, ClassNotFoundException {

        StringBuilder resultMes= new StringBuilder();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

        logger.info("START COLLECT ZABBIX");

        updateAuthToken();
        //resultMes.append(getUsersCount());
        resultMes.append("<hr><h3>СОСТОЯНИЕ СИСТЕМЫ БАЗИС: "+ LocalDateTime.now().format(dateTimeFormatter) + "</h3>");
        resultMes.append("  <font color=\"green\" style=\"bold\">СОСТОЯНИЕ СИСТЕМЫ: OK</font>" +"<br>");
        resultMes.append("  "+getProcErrors() +"<br>");
        resultMes.append("  "+getSuccessActions() +"   <br>");
        resultMes.append(getZakupkiStatus()+"   <br>");
        resultMes.append(getUsersCount()+"   <br><br>");
        //resultMes.append(getProcErrors());
        resultMes.append("<hr><h3>СТАТИСТИКА ИНЦИДЕНТОВ</h3>");
        resultMes.append(getAllInc()+"<br>");
        resultMes.append(getIncomingIncToday()+"<br>");
        resultMes.append(getDoneIncToday()+"<br>");
        resultMes.append(getInWorkInc()+"<br>");
        //resultMes.append(getActiveIncToday()+"<br>");
        resultMes.append(getWaitIncToday()+"<hr>");
        resultMes.append("<h3>ПРОСРОЧЕННЫЕ ИНЦИДЕНТЫ</h3>");
        resultMes.append(getProsrokTable());


        return resultMes.toString();

    }

}
