package dususdk.dusupay.com.dususdk;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import dususdk.dusupay.com.dususdk.model.TransactionResponse;


/**
 * Created by pkanye on 06-Feb-18.
 */

public class CompletePayment extends BottomSheetDialog {

    public static Context context;
    public static String info;
    static String transactionlog = "";
    public static  SharedPreferences sharedPref;
    public static SharedPreferences.Editor editor;
    public static ProgressDialog progress;
    String sandbox = "http://sandbox.dusupay.com/";
    String live = "https://dusupay.com/";
    public static String parentlink ="";
    TextView item_price_id;
    TextView item_name;
    TextView country_id;
    TextView payment_gateway;
    static TransactionResponse transactionResponse;
    public CompletePayment(Context context,String info){
        super(context);

        this.context = context;
        this.info = info;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getLayoutInflater().inflate(R.layout.complete_payment, null);
        setContentView(view);
        Button confirm_btn = (Button) view.findViewById(R.id.confirm_btn);
        item_price_id = (TextView) view.findViewById(R.id.item_price_id);
        item_name = (TextView) view.findViewById(R.id.item_name);
        country_id = (TextView) view.findViewById(R.id.country_id);
        payment_gateway = (TextView) view.findViewById(R.id.payment_gateway);
        item_price_id.setText(Purchse.currency+"."+ Purchse.itemPrice);
        item_name.setText(Purchse.itemName);
        payment_gateway.setText(Purchse.phoneNumber);

        country_id.setText(Purchse.paymetmodule );
        if (Purchse.environment.trim().isEmpty() || Purchse.environment.trim().length() == 0 || Purchse.environment.trim().equals("") || Purchse.environment.trim() == null) {

            parentlink =live;
        }else if(Purchse.environment.trim().equals("sandbox")){
      //  }else if(Purchse.environment.trim() == "sandbox".trim()){
            parentlink =sandbox;
        }else if(Purchse.environment.trim().equals("live")){
       // }else if(Purchse.environment.trim() == "live".trim()){
            parentlink =live;
        }else{
            parentlink =live;
        }
            confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CompletePayment.this.dismiss();
                progress = new ProgressDialog(getContext());
                progress.setMessage("Completing Transaction... ");
                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progress.setIndeterminate(true);
                progress.show();
                new SendPostRequests().execute();
               /* Toast.makeText(context, info,
                        Toast.LENGTH_LONG).show();*/
            }
        });

    }


    public static class SendPostRequests extends AsyncTask<String, Void, String> {


        protected void onPreExecute(){}

        public DusuTransactionDetails delegate = null;//Call back interface

        protected String doInBackground(String... arg0) {
            CompleteTransactionNetwork.network_status=false;
            try {

                URL url = new URL(parentlink+"merchant-api/collections/v2/mobile/requestPayment.json"); // here is your URL path
                //  URL url = new URL("http://192.168.43.187/dusukey.php"); // here is your URL path
               System.out.println(Purchse.environment.trim()+"  v "+parentlink+"merchant-api/collections/v2/mobile/requestPayment.json");
                long time= System.currentTimeMillis();
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
                os.write(info.getBytes("UTF-8"));

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
            System.out.println("xresponse" + result.toString());
            Log.d("xresponse", result);
           // delegate.processFinish(result);
            transactionlog = result;
            Gson gson = new Gson();
             transactionResponse = gson.fromJson(result, TransactionResponse.class);
            progress.dismiss();
          if(Purchse.isJson(result)){
            if (transactionResponse.getResponse().getStatus() == false) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("INVALID");
                alertDialogBuilder.setMessage(transactionResponse.getResponse().getMessage());
                alertDialogBuilder.setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            } else {

//            Log.d("xresponsesteps", transactionResponse.getResponse().getAmount().toString());
                //  Log.d("xresponsesteps", transactionResponse.getResponse().getPayBillInstructions().get(0).getInstructions().get(0));
                List<String> instructions = transactionResponse.getResponse().getPayBillInstructions().get(0).getInstructions();
                Intent intent = new Intent("transaction_callback");
                Bundle args = new Bundle();
                args.putSerializable("dusu_obj",(Serializable)transactionResponse);
                intent.putExtra("DUSU_DATA",args);
                context.sendBroadcast(intent);
                if (Purchse.simulatePayBill) {
                    String steps = "";
                    String name = transactionResponse.getResponse().getPayBillInstructions().get(0).getName();
                    Purchse.completebynetwork = "\n\n Click on complete Transaction By Network to fill in the transaction ID received from the telecom\n\nNB: A transaction fee has been added to your transaction";
                    Purchse.to_pay_currency = transactionResponse.getResponse().getCurrency();
                    Purchse.to_pay_mount = transactionResponse.getResponse().getAmount().toString();
                    for (int i = 0; i < instructions.size(); i++) {
                        System.out.println(instructions.get(i));
                        steps += instructions.get(i) + "\n";
                    }

                    // String steps = transactionResponse.getResponse().getPayBillInstructions().toString();
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    alertDialogBuilder.setTitle(name + " Paying " + Purchse.to_pay_currency + " " + Purchse.to_pay_mount);
                    alertDialogBuilder.setMessage(steps + "" + Purchse.completebynetwork);
                    alertDialogBuilder.setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {

                                }
                            });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();

                }else{
                   /* Intent intent = new Intent("finish_activity");
                    context.sendBroadcast(intent);*/
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    alertDialogBuilder.setTitle("Wait..");
                    alertDialogBuilder.setMessage("A push prompt will be sent to your phone shortly");
                    alertDialogBuilder.setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {

                                }
                            });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
            /* JSONObject obj_JSONObject;
            JSONObject obj_JSONObject2;
            JSONArray obj_JSONArray = null;
            try {
                 obj_JSONObject = new JSONObject(result);
                 obj_JSONArray = obj_JSONObject.getJSONArray("response");

                 obj_JSONObject2 = obj_JSONArray.getJSONObject(5);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            System.out.println(obj_JSONArray);
           // Log.d("xresponse", ""+obj_JSONArray.length());

            System.out.println("xresponse"+obj_JSONArray.length());*/
                sharedPref = context.getSharedPreferences("myPref", Context.MODE_PRIVATE);
                editor = sharedPref.edit();

                // Save your string in SharedPref
                editor.putString("TransactionInfo", result);
                editor.commit();
                Intent service = new Intent(context, DusuPayTransaction.class);
                // service.putExtra("TransactionDetails", result);
                context.startService(service);
                progress.dismiss();

                // Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
            }
        }else{
              AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
              alertDialogBuilder.setTitle("INVALID");
              alertDialogBuilder.setMessage("Server response is not a valid json string "+result.toString());
              alertDialogBuilder.setPositiveButton("Ok",
                      new DialogInterface.OnClickListener() {
                          @Override
                          public void onClick(DialogInterface arg0, int arg1) {

                          }
                      });

              AlertDialog alertDialog = alertDialogBuilder.create();
              alertDialog.show();
        }
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


    public static class MyAsyncTask extends AsyncTask<String, Void, String> {

        public MyCustomObject delegate = null;//Call back interface

        public MyAsyncTask(MyCustomObject asyncResponse) {
            delegate = asyncResponse;//Assigning call back interfacethrough constructor
        }

        @Override
        protected String doInBackground(String... arg0) {

            //My Background tasks are written here
            if(transactionlog.isEmpty()){
                return "";
            }else{

                return ""+transactionlog;
            }



            //return TransactionResponse Object;
        }

        @Override
        protected void onPostExecute(String result) {
            delegate.processFinish(result);
        }

    }
/*
    public static class MyCustomObject {
        // Listener defined earlier
        public interface MyCustomObjectListener {
            public void onObjectReady(String title);
          //  public void onDataLoaded(TransactionResponse data);
            public void onDataLoaded(String title);
        }

        // Member variable was defined earlier
        private MyCustomObjectListener listener;

        // Constructor where listener events are ignored
        public MyCustomObject(MyCustomObjectListener myCustomObjectListener) {
            // set null or default listener or accept as argument to constructor
            this.listener = null;
            loadDataAsync();
        }

        // ... setter defined here as shown earlier

        public void loadDataAsync() {
            if (listener != null)
             //   listener.onObjectReady("key"); // <---- fire listener here
                listener.onDataLoaded("xxxxtrying all"); // <---- fire listener here
        }
    }*/

   /* public static class MyCustomObject {
        // Listener defined earlier
        public interface MyCustomObjectListener {
            public void onObjectReady(String title);
            public void onDataLoaded(TransactionResponse data);
        }


        // Inside the child object
        private MyCustomObjectListener listener;

        // Assign the listener implementing events interface that will receive the events
        private void setCustomObjectListener(MyCustomObjectListener listener) {
            this.listener = listener;
            loadDataAsync();
        }
        // ... setter defined here as shown earlier
        private void loadDataAsync() {
            if (listener != null)
                listener.onDataLoaded(transactionResponse);
        }
    }*/
}
