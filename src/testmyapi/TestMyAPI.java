/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testmyapi;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;
import java.net.*;
import java.io.*;
/**
 *
 * @author wrxue
 */
public class TestMyAPI {
    String apiUrl;
    /**
     * @param args the command line arguments
     */
    public TestMyAPI(){
        apiUrl = "http://localhost/api/user";
    }
    public static void main(String[] args) throws MalformedURLException, IOException {
        // TODO code application logic here
        TestMyAPI api = new TestMyAPI();
        JSONObject jsonObject = api.queryUser();
        System.out.println("The number of users：" + jsonObject.getJSONArray("records"));
        
        
    }

    public static String httpRequest(String requestUrl,String requestMethod,String outputStr){
        StringBuffer buffer=null;
        try{
            //根據requestUrl創建一個HttpURLConnection物件，此時只是創物件，還沒有真的連線。
            URL url=new URL(requestUrl);
            HttpURLConnection conn=(HttpURLConnection)url.openConnection();
            //需要寫JSON給API，所以DoOutput要打開，要讀API來的JSON，DoInput也要打開。
            conn.setDoOutput(true);
            conn.setDoInput(true);
            //設定http的Request方法，GET/POST
            conn.setRequestMethod(requestMethod);
            //要傳給server的Data，此時只有寫進物件，還沒有送上server。
            if(null!=outputStr){
                OutputStream os=conn.getOutputStream();
                os.write(outputStr.getBytes("utf-8"));
                os.close();
            }

            //getInputStream這函式會將上面的設定，傳送給server，InputStream以Byte為單位的輸入串流。
            InputStream is=conn.getInputStream();
            //InputStream, InputStreamReader, BufferedReader三者關係參考
            //https://dotblogs.com.tw/hanktom/2016/01/26/180721
            InputStreamReader isr=new InputStreamReader(is,"utf-8");
            BufferedReader br=new BufferedReader(isr);
            buffer=new StringBuffer();
            String line=null;
            while((line=br.readLine())!=null){
                buffer.append(line);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return buffer.toString();
    }
    
    public JSONObject queryUser(){
        String str = httpRequest(apiUrl + "/read.php","GET", null);
        JSONObject jsonObject = new JSONObject(str);
        return jsonObject;
    }
}
