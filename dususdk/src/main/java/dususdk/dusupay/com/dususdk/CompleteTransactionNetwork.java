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
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

import customfonts.MyEditText;
import dususdk.dusupay.com.dususdk.model.TransactionResponse;


/**
 * Created by pkanye on 06-Feb-18.
 */

public class CompleteTransactionNetwork extends BottomSheetDialog {

    private Context context;
    String info;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
   public static ProgressDialog progress;

    public static String account_number;
    public static String transaction_id;
    public static String network_reference_no;
    public static boolean network_status = false;
    MyEditText dusutransaction_id;
    MyEditText account_phone_number;
    MyEditText network_transaction_id;
    public static  String networkresponse;
    public CompleteTransactionNetwork(Context context, String info){
        super(context);

        this.context = context;
        this.info = info;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getLayoutInflater().inflate(R.layout.complete_transaction_network, null);
        setContentView(view);
        Button confirm_btn = (Button) view.findViewById(R.id.confirm_network_btn);
         dusutransaction_id = (MyEditText) view.findViewById(R.id.transaction_id);
        account_phone_number = (MyEditText) view.findViewById(R.id.account_number);
        network_transaction_id = (MyEditText) view.findViewById(R.id.network_transaction_id);
        Gson gson = new Gson();
        TransactionResponse transactionResponse= gson.fromJson(info, TransactionResponse.class);
      //  Log.d("xresponse", transactionResponse.getResponse().getAmount().toString());
        if(transactionResponse != null){
            account_number=transactionResponse.getResponse().getAccountNumber();
            transaction_id=transactionResponse.getResponse().getId();
            account_phone_number.setText(account_number);
            dusutransaction_id.setText(transaction_id);
        }else{
            CompleteTransactionNetwork.this.dismiss();
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setMessage("No initialized transaction.");
            alertDialogBuilder.setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {

                        }
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }

        //dusutransaction_id.setEnabled(false);
        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String networkReference = network_transaction_id.getText().toString();
                progress = new ProgressDialog(getContext());
                progress.setMessage("Completing Transaction by network... ");
                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progress.setIndeterminate(true);
                if(network_transaction_id.toString().trim().isEmpty() || network_transaction_id.toString().trim().length() == 0 || network_transaction_id.toString().trim().equals("") || network_transaction_id.toString().trim() == null)
                {
                    //EditText is empty
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    alertDialogBuilder.setMessage("Provide the network ID sent to your phone");
                    alertDialogBuilder.setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {

                                }
                            });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
                else if(account_phone_number.toString().trim().isEmpty() || account_phone_number.toString().trim().length() == 0 || account_phone_number.toString().trim().equals("") || account_phone_number.toString().trim() == null) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    alertDialogBuilder.setMessage("Provide a valid account/phone number in an international format");
                    alertDialogBuilder.setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {

                                }
                            });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
                else if(dusutransaction_id.toString().trim().isEmpty() || dusutransaction_id.toString().trim().length() == 0 || dusutransaction_id.toString().trim().equals("") || dusutransaction_id.toString().trim() == null) {
                    //EditText is not empty
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    alertDialogBuilder.setMessage("Provide the Dusupay transaction ID");
                    alertDialogBuilder.setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {

                                }
                            });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }else
                {
                    progress.show();
                    Purchse.newtworkid_completion = true;
                    network_status=true;
                    CompleteTransactionNetwork.this.dismiss();
                    new SendPostRequests().execute();
                  //  Toast.makeText(context, info, Toast.LENGTH_LONG).show();
                }

            }
        });

    }


    public class SendPostRequests extends AsyncTask<String, Void, String> {

        protected void onPreExecute(){}

        protected String doInBackground(String... arg0) {

            network_reference_no =  network_transaction_id.getText().toString();
            account_number =  account_phone_number.getText().toString();
            transaction_id = dusutransaction_id.getText().toString();
            try {
                String networkurl=Purchse.completeNetworkTransactionUrl;
               // URL url = new URL("https://dusupay.com/merchant-api/collections/v2/mobile/completePaymentByNetworkId.json"); // here is your URL path
                System.out.println("xresponsenet"+networkurl);
                System.out.println("xresponsenet"+transaction_id+"=="+Purchse.merchantId+"=="+network_reference_no+"=="+account_number);
                URL url = new URL(networkurl); // here is your URL path
                //  URL url = new URL("http://192.168.43.187/dusukey.php"); // here is your URL path
                long time= System.currentTimeMillis();
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("transaction_id", transaction_id);
                postDataParams.put("dusupay_merchantId", Purchse.merchantId);
                postDataParams.put("network_transaction_id", network_reference_no);
                postDataParams.put("account_number", account_number);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
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
            System.out.println("xresponsenet"+result.toString());
            Log.d("xresponsenet", result);
            Gson gson = new Gson();
            if(Purchse.isJson(result)){
                networkresponse=result;
                sharedPref= getContext().getSharedPreferences("networkmyPref", Context.MODE_PRIVATE);
                editor=sharedPref.edit();

                // Save your string in SharedPref
                editor.putString("TransactionInfonetwork", result);
                editor.commit();
                Intent service = new Intent(getContext(), DusuPayTransaction.class);
                service.putExtra("NetworkTransactionDetails", result);
                getContext().startService(service);
                progress.dismiss();
            }else{
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("RESPONSE NOT A VALID JSON STRING");
                alertDialogBuilder.setMessage(result);
                alertDialogBuilder.setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
           // TransactionResponse transactionResponse= gson.fromJson(result, TransactionResponse.class);
           // Log.d("xresponse", transactionResponse.getResponse().getAmount().toString());
          //  String steps = transactionResponse.getResponse().getPayBillInstructions().toString();

        /*
        *
        * Make a custom dialog box to send the tranasction details to the server
        *
        * */

          /*  AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setMessage("Confirm this transaction.");
                    alertDialogBuilder.setPositiveButton("Ok",
                            new OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {

                                }
                            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();*/

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
