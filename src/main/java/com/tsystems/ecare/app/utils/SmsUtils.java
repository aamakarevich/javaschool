package com.tsystems.ecare.app.utils;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Sms sending utility methods.
 */
public class SmsUtils {

    private static final String TOKEN = "CDA112A0-2E68-CE3A-1040-21ED51F72CE2";
    private static final String URL = "http://sms.ru/sms/send";

    private static Logger logger = Logger.getLogger(SmsUtils.class);

    /**
     * Send sms with password to phone number.
     *
     * @param phoneNumber 10 digits of phone number
     * @param password password string
     * @return response
     */
    public static String sendSms(String phoneNumber, String password) {
        HttpURLConnection connection = null;
        try {
            //Create connection
            URL url = new URL(URL);
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");

            String urlParameters = "api_id=" + TOKEN + "&to=7" + phoneNumber + "&text=" + password;

            connection.setRequestProperty("Content-Length",
                    Integer.toString(urlParameters.getBytes().length));
            connection.setRequestProperty("Content-Language", "en-US");

            connection.setUseCaches(false);
            connection.setDoOutput(true);

            //Send request
            DataOutputStream wr = new DataOutputStream (
                    connection.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.close();

            //Get Response
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder(); // or StringBuffer if not Java 5+
            String line;
            while((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            return response.toString();
        } catch (Exception ex) {
            logger.info(ex);
            return null;
        } finally {
            if(connection != null) {
                connection.disconnect();
            }
        }
    }
}
