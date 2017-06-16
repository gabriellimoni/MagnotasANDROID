package com.example.gabri.mag_notas.AsyncTask;

        import android.util.Log;

        import org.json.JSONException;
        import org.json.JSONObject;

        import java.io.BufferedInputStream;
        import java.io.BufferedReader;
        import java.io.DataOutputStream;
        import java.io.IOException;
        import java.io.InputStream;
        import java.io.InputStreamReader;
        import java.io.UnsupportedEncodingException;
        import java.net.HttpURLConnection;
        import java.net.URL;
        import java.net.URLEncoder;
        import java.util.HashMap;

public class JSONParser {

    String charset = "UTF-8";
    //String charset = "ISO8859-1";
    HttpURLConnection conn;
    DataOutputStream wr;
    StringBuilder result;
    URL urlObj;
    JSONObject jObj = null;
    StringBuilder sbParams;
    String paramsString;

    int errorcode = 0; //1 = Conexão; //2 = conversão para jsonobject

    int getErrorcode()
    {


        return errorcode;
    }


    public JSONObject makeHttpRequest(String url, String method,
                                      HashMap<String, String> params) {

        sbParams = new StringBuilder();
        boolean error = false;
        errorcode = 0;


        int i = 0;

//        if (!params.isEmpty())
        //      {
        for (String key : params.keySet()) {
            try {
                if (i != 0) {
                    sbParams.append("&");
                }
                sbParams.append(key).append("=")
                        .append(URLEncoder.encode(params.get(key), charset));
                //sbParams.append(key).append("=").append(params.get(key));

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return null;
            }
            i++;
        }
        //    }
        //    Log.i("OBJ","Antes POST");
        if (method.equals("POST")) {
            // request method is POST
            try {
                urlObj = new URL(url);

                conn = (HttpURLConnection) urlObj.openConnection();

                conn.setDoOutput(true);

                conn.setRequestMethod("POST");

                conn.setRequestProperty("Accept-Charset", charset);

                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);

                conn.connect();

                Log.i("PARAMS","ANTES DE CONVERTER PARA STRING");
                paramsString = sbParams.toString();
                Log.i("PARAMS","DEPOIS DE CONVERTER PARA STRING");
                Log.i("PARAMS",paramsString);
                wr = new DataOutputStream(conn.getOutputStream());
                wr.writeBytes(paramsString);
                wr.flush();
                wr.close();

            } catch (IOException e) {
                e.printStackTrace();
                error=true;
                errorcode = 1;
            }
        }
        else if(method.equals("GET")){
            // request method is GET

            if (sbParams.length() != 0) {
                url += "?" + sbParams.toString();
            }

            try {
                urlObj = new URL(url);

                conn = (HttpURLConnection) urlObj.openConnection();

                conn.setDoOutput(false);

                conn.setRequestMethod("GET");

                conn.setRequestProperty("Accept-Charset", charset);

                conn.setConnectTimeout(15000);

                conn.connect();

            } catch (IOException e) {
                error = true;
                errorcode = 1;
                e.printStackTrace();

            }

        }

        if (!error) {
            try {
                //Receive the response from the server

//                Log.i("CONN", "ANTES");
                InputStream in = new BufferedInputStream(conn.getInputStream());
                //              Log.i("CONN", "ANTES INPUTSTREAM");
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                result = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

//                Log.d("JSON Parser", "result: " + result.toString());

            } catch (IOException e) {
                errorcode = 1;
                e.printStackTrace();

            }
        }

        conn.disconnect();

        // try parse the string to a JSON object
        if (!error) {
            try {
                if (result != null) {
                    Log.i("RESULT",result.toString());
                    jObj = new JSONObject(result.toString());

                }
                // Log.i("OBJ",result.toString());
            } catch (JSONException e) {
                errorcode = 2;
                Log.e("JSON Parser", "Error parsing data " + e.toString());
            }
        }
        // return JSON Object
        return jObj;
    }
}