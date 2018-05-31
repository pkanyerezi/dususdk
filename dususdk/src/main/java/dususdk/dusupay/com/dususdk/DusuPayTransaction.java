package dususdk.dusupay.com.dususdk;

/**
 * Created by pkanye on 09-Feb-18.
 */
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

import dususdk.dusupay.com.dususdk.model.TransactionResponse;

public class DusuPayTransaction extends Service{

    private static String TAG = "Inchoo.net tutorial";
    SharedPreferences sharedPref;
    SharedPreferences sharedPrefnetork;
    SharedPreferences.Editor editor;
    String myCallbackurl;
    String networkCallbackurl;
    String TransactionDetails;
    String TransactionDetailsnetwork;
    String completeNetworkTransactionmerchantcallbackUrl;
    String sandbox = "http://sandbox.dusupay.com/";
    String live = "https://dusupay.com/";
    String parentlink ="";

    String networkResponseToMerchant="";
    public static boolean isJson(String Json) {

        try {
            new JSONObject(Json);
        } catch (JSONException e) {
            try {
                new JSONArray(Json);
            } catch (JSONException e1) {
                return false;
            }
            //  e.printStackTrace();
        }

        return true;
    }
    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        // TODO Auto-generated method stub
        super.onStart(intent, startId);
        myCallbackurl = Purchse.callbackUrl;
        networkCallbackurl = Purchse.completeNetworkTransactionUrl;
        completeNetworkTransactionmerchantcallbackUrl = Purchse.completeNetworkTransactionmerchantcallbackUrl;
        sharedPref= getSharedPreferences("myPref", Context.MODE_PRIVATE);
        sharedPrefnetork= getSharedPreferences("networkmyPref", Context.MODE_PRIVATE);
         TransactionDetails=sharedPref.getString("TransactionInfo","");
        TransactionDetailsnetwork=sharedPref.getString("TransactionInfonetwork","");
        //String TransactionDetails = intent.getStringExtra("TransactionDetails");
      //  Log.d(TAG, "FirstService started");
        Log.d(TAG, "FirstService started"+TransactionDetails);
        Log.d(TAG, "FirstService started"+myCallbackurl);
        Log.d(TAG, "FirstService started"+TransactionDetailsnetwork);
      //  new SendPostRequests().execute();
        if (Purchse.environment.trim().isEmpty() || Purchse.environment.trim().length() == 0 || Purchse.environment.trim().equals("") || Purchse.environment.trim() == null) {

            parentlink =live;
        }else if(Purchse.environment.trim().equals("sandbox")){
       // }else if(Purchse.environment.trim() == "sandbox"){
            parentlink =sandbox;
        }else if(Purchse.environment.trim().equals("live")){
            parentlink =live;
        }else{
            parentlink =live;
        }
        if(CompleteTransactionNetwork.network_status){
            new SendNetworkRequests().execute();
        }else{
            new SendPostRequests().execute();
        }


      /*  AlertDialog.Builder builder = new AlertDialog.Builder(Purchse.mcontext);
        builder.setTitle("Complete The Transaction");
        builder.setMessage("A prompt to complete this transaction will be sent to your phone in a moment");
        builder.setCancelable(true);
        builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Neutral button clicked", Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();*/

        this.stopSelf();
    }


    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        Log.d(TAG, "FirstService destroyed");
    }



    public class SendPostRequests extends AsyncTask<String, Void, String> {

        protected void onPreExecute(){}

        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL(myCallbackurl); // here is your URL path
                //  URL url = new URL("http://192.168.43.187/dusukey.php"); // here is your URL path

                long time= System.currentTimeMillis();
                // JSONObject postDataParams = new JSONObject(info);
                // postDataParams.put("dusupay_redirectURL", "myapp://dusupayapp&dusupay_successURL=");
                //Log.e("params",postDataParams.toString());

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("POST");
               // conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                conn.setRequestProperty("Content-Type","application/json; charset=utf-8");
                conn.setRequestProperty("Accept","application/json; charset=utf-8");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                // OutputStream os = conn.getOutputStream();


                OutputStream os = conn.getOutputStream();
                //   BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                // writer.write(info.getBytes("UTF-8"));
                os.write(TransactionDetails.getBytes("UTF-8"));

                os.flush();
                os.close();
                // os.close();

                int responseCode=conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in=new BufferedReader(new
                            InputStreamReader(
                            conn.getInputStream()));

                    StringBuffer sb = new StringBuffer("");
                    String line="";

                    while((line = in.readLine()) != null) {

                        sb.append(line);
                        break;
                    }

                    in.close();
                    return sb.toString();

                }
                else {
                    return new String("reponse1 : "+responseCode);
                }
            }
            catch(Exception e){
                return new String("reponse2: " + e.getMessage());
            }

        }

        @Override
        protected void onPostExecute(String result) {
            Log.d("xresponse12",myCallbackurl);
            Log.d("xresponse12",TransactionDetails);
            System.out.println("xresponsenn2"+result.toString());
            Log.d("xresponsenn2", result);
           /* Intent service = new Intent(mContext, DusuPayTransaction.class);
            mContext.stopService(service);*/

            // Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
        }
    }


    public class SendNetworkRequests extends AsyncTask<String, Void, String> {

        protected void onPreExecute(){}

        protected String doInBackground(String... arg0) {

            try {

               // URL url = new URL(parentlink+"collections/v2/mobile/completePaymentByNetworkId.json"); // here is your URL path
                URL url = new URL(parentlink+"merchant-api/collections/v2/mobile/completePaymentByNetworkId.json"); // here is your URL path
                //  URL url = new URL("http://192.168.43.187/dusukey.php"); // here is your URL path
                System.out.println(sandbox+ "vv" +parentlink+"collections/v2/mobile/completePaymentByNetworkId.json");
                long time= System.currentTimeMillis();
                // JSONObject postDataParams = new JSONObject(info);
                // postDataParams.put("dusupay_redirectURL", "myapp://dusupayapp&dusupay_successURL=");
             //   Log.e("xresponsenetwork",TransactionDetailsnetwork);
                System.out.println("xresponsenetwork"+CompleteTransactionNetwork.networkresponse);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                // OutputStream os = conn.getOutputStream();


                OutputStream os = conn.getOutputStream();
                //   BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                // writer.write(info.getBytes("UTF-8"));
                os.write(CompleteTransactionNetwork.networkresponse.getBytes("UTF-8"));

                os.flush();
                os.close();
                // os.close();

                int responseCode=conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in=new BufferedReader(new
                            InputStreamReader(
                            conn.getInputStream()));

                    StringBuffer sb = new StringBuffer("");
                    String line="";

                    while((line = in.readLine()) != null) {

                        sb.append(line);
                        break;
                    }

                    in.close();
                    return sb.toString();

                }
                else {
                    return new String("reponse1 : "+responseCode);
                }
            }
            catch(Exception e){
                return new String("reponse2: " + e.getMessage());
            }

        }

        @Override
        protected void onPostExecute(String result) {
            Log.d("xresponse17", myCallbackurl);
            Log.d("xresponse17", TransactionDetailsnetwork);
            Log.d("xresponse17", result);
            Gson gson = new Gson();

            if (isJson(result)) {

            TransactionResponse transactionResponse = gson.fromJson(result, TransactionResponse.class);
            CompleteTransactionNetwork.progress.dismiss();
            if (Purchse.isJson(result)) {
                if (transactionResponse.getResponse().getStatus() == false) {
                    android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(Purchse.mcontext);
                    alertDialogBuilder.setTitle("INVALID");
                    alertDialogBuilder.setMessage(transactionResponse.getResponse().getMessage());
                    alertDialogBuilder.setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {

                                }
                            });

                    android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                } else {
                    Intent intent = new Intent("network_transaction_callback");
                    Bundle args = new Bundle();
                    args.putSerializable("dusu_network_response",(Serializable)transactionResponse);
                    intent.putExtra("DUSU_NETWORK_DATA",args);
                    sendBroadcast(intent);
                    android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(Purchse.mcontext);
                    alertDialogBuilder.setTitle("Successful");
                    alertDialogBuilder.setMessage(transactionResponse.getResponse().getMessage());
                    alertDialogBuilder.setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {

                                }
                            });

                    android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                    networkResponseToMerchant = result;
                    System.out.println("xresponsennetwork7" + result);
                    Log.d("xresponsennetwork7", result);
                    new SendNetworkRequeststomerchanturl().execute();
                }
            }
        }else{
                android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(Purchse.mcontext);
                alertDialogBuilder.setTitle("INVALID JSON STRING");
                alertDialogBuilder.setMessage(result.toString());
                alertDialogBuilder.setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                            }
                        });

                android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

        }

           /* Intent service = new Intent(mContext, DusuPayTransaction.class);
            mContext.stopService(service);*/

            // Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
        }
    }

    public class SendNetworkRequeststomerchanturl extends AsyncTask<String, Void, String> {

        protected void onPreExecute(){}

        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL(completeNetworkTransactionmerchantcallbackUrl); // here is your URL path
                //  URL url = new URL("http://192.168.43.187/dusukey.php"); // here is your URL path
             //  Gson obj = new Gson();
               JSONObject obj = new JSONObject();
                String id_n = CompleteTransactionNetwork.transaction_id;
                String network_id_n = CompleteTransactionNetwork.network_reference_no;
                String account_no_n = CompleteTransactionNetwork.account_number;
                long time= System.currentTimeMillis();
                obj.put("status",true);
                obj.put("id",id_n);
                obj.put("network_transaction_id",network_id_n);
                obj.put("account_number",account_no_n);
                obj.put("account_number",account_no_n);
                obj.put("timestamp",time);
                System.out.println("feedback"+obj.toString());
              // String feedback =  "{"+"status":true,"id":"1180426101929256701203975","network_transaction_id":"ggfggff","account_number":"256701203975","timestamp":1524740296}

                // JSONObject postDataParams = new JSONObject(info);
                // postDataParams.put("dusupay_redirectURL", "myapp://dusupayapp&dusupay_successURL=");
                //Log.e("params",postDataParams.toString());

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                // OutputStream os = conn.getOutputStream();


                OutputStream os = conn.getOutputStream();
                //   BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                // writer.write(info.getBytes("UTF-8"));
                os.write(obj.toString().getBytes("UTF-8"));

                os.flush();
                os.close();
                // os.close();

                int responseCode=conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in=new BufferedReader(new
                            InputStreamReader(
                            conn.getInputStream()));

                    StringBuffer sb = new StringBuffer("");
                    String line="";

                    while((line = in.readLine()) != null) {

                        sb.append(line);
                        break;
                    }

                    in.close();
                    return sb.toString();

                }
                else {
                    return new String("reponse1 : "+responseCode);
                }
            }
            catch(Exception e){
                return new String("reponse2: " + e.getMessage());
            }

        }

        @Override
        protected void onPostExecute(String result) {
            Log.d("xresponse1",myCallbackurl);
            Log.d("xresponse1",TransactionDetails);
            System.out.println("xresponsennetwork"+result.toString());
            Log.d("xresponsennetwork", result);
           if(Purchse.simulatePayBill = false) {
               AlertDialog.Builder builder = new AlertDialog.Builder(Purchse.mcontext);
               builder.setTitle("Complete The Transaction");
               builder.setMessage("" + result);
               builder.setCancelable(true);
               builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {

                       Toast.makeText(getApplicationContext(), "Neutral button clicked", Toast.LENGTH_SHORT).show();
                   }
               });
               builder.show();
           }
           /* Intent service = new Intent(mContext, DusuPayTransaction.class);
            mContext.stopService(service);*/

            // Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
        }
    }

    public String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while(itr.hasNext()){

            String key= itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        return result.toString();
    }
}