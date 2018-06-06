package dususdk.dusupay.com.dususdk;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import customfonts.MyEditText;
import customfonts.MyTextView;

public class Purchse extends AppCompatActivity {
    public static String itemName;
    public static String phoneNumber;
    String ItemId;
    public static String currency;
    String transactionReferenceNumber;
    private static final String TAG = Purchse.class.getSimpleName();
   // String environment;
    public static String merchantId;
    public static String itemPrice;
    public static String paymetmodule;
    MyEditText item_name;
    MyEditText amount_id;
    MyEditText reference_id;
    MyEditText phone_number_id;
    MyEditText currency_id;
    MyTextView confirmation_btn;
    public static String customer_phone_number;
    public static String completebynetwork;
    public static String to_pay_mount;
    public static String to_pay_currency;
    String mac_key_signatureurl;
    String client_name;
    String client_email;
    String dusupay_redirectURL;
    String country_code;
    int imagec;
    public static String callbackUrl;
    public static String completeNetworkTransactionUrl;
    public static String completeNetworkTransactionmerchantcallbackUrl;
    public static String environment;
    Button btn_submit;
    Button btn_bottom_sheet;
    LinearLayout payment_btn;
    LinearLayout complete_by_network_btn;
    ProgressDialog progress;
    public static Context mcontext;
    public static Context mcontextactivity;

    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    BottomSheetBehavior sheetBehavior;
    CheckBox paybillinstructions_id;
    ImageView channel_pic;
    public static boolean simulatePayBill = true;
    public static String simulatePayBillcheck = "";
    public static boolean newtworkid_completion = false;
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
    public boolean onSupportNavigateUp(){
        finish();
        return  true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchse);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        channel_pic = (ImageView) findViewById(R.id.channel_pic);
        item_name = (MyEditText) findViewById(R.id.item_name);
        paybillinstructions_id = (CheckBox) findViewById(R.id.paybillinstructions_id);
        amount_id = (MyEditText) findViewById(R.id.amount_id);
        reference_id = (MyEditText) findViewById(R.id.reference_id);
        phone_number_id = (MyEditText) findViewById(R.id.client_phone_id);
        currency_id = (MyEditText) findViewById(R.id.currency_id);
        confirmation_btn = (MyTextView) findViewById(R.id.confirmation_btn);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        payment_btn = (LinearLayout) findViewById(R.id.payment_btn);
        complete_by_network_btn = (LinearLayout) findViewById(R.id.network_btn);
        Intent intent = getIntent();
        itemName = intent.getStringExtra("itemName");
        ItemId = intent.getStringExtra("ItemId");
        itemPrice = intent.getStringExtra("itemPrice");
        currency = intent.getStringExtra("currency");
        mac_key_signatureurl = intent.getStringExtra("mac_signatureurl");
        transactionReferenceNumber = intent.getStringExtra("transactionReferenceNumber");
        environment = intent.getStringExtra("environment");
        merchantId = intent.getStringExtra("merchantId");
        paymetmodule = intent.getStringExtra("paymetmodule");
        client_name = intent.getStringExtra("client_name");
        client_email = intent.getStringExtra("client_email");
        callbackUrl = intent.getStringExtra("callbackUrl");
        dusupay_redirectURL = intent.getStringExtra("dusupay_redirectURL");
        country_code = intent.getStringExtra("country_code");
       // System.out.println("xxxxxxx"+intent.getStringExtra("imagec"));
      //  imagec = Integer.parseInt(intent.getStringExtra("imagec"));
        completeNetworkTransactionUrl = intent.getStringExtra("completeNetworkTransactionUrl");
        completeNetworkTransactionmerchantcallbackUrl = intent.getStringExtra("completeNetworkTransactionmerchantcallbackUrl");
        mcontext = Purchse.this;

        channel_pic.setImageResource(BottomSheet.country_img);
        //channel_pic.setImageResource(ItemAdapter.imagedraw);
        System.out.println("paymetmodule "+paymetmodule);
       // setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Complete Payment");
        if(toolbar != null){
            toolbar.setNavigationIcon(R.drawable.ic_close_black_24dp);

            //Toast.makeText(getApplicationContext(), "close clicked", Toast.LENGTH_SHORT).show();

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                    finish();
                    //System.out.println("back button pressed...........");
                    //Toast.makeText(getApplicationContext(), "close clicked", Toast.LENGTH_SHORT).show();

                }
            });
        }
        phone_number_id.setHint(country_code+"7XXXXXXXX");
        item_name.setText(itemName);
        amount_id.setText(itemPrice);
        reference_id.setText(transactionReferenceNumber);
        currency_id.setText(currency);
       // phone_number_id.setEnabled(false);
        item_name.setEnabled(false);
        amount_id.setEnabled(false);
        confirmation_btn.setText("CONFIRM AND PAY "+ currency +" "+itemPrice);

        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context arg0, Intent intent) {
                String action = intent.getAction();
                if (action.equals("finish_activity")) {
                    finish();
                    // DO WHATEVER YOU WANT.

                }
            }
        };
        registerReceiver(broadcastReceiver, new IntentFilter("finish_activity"));

        complete_by_network_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPref= getApplication().getApplicationContext().getSharedPreferences("myPref", Context.MODE_PRIVATE);
                editor=sharedPref.edit();
                editor.apply();
                String result = sharedPref.getString("TransactionInfo","");

                CompleteTransactionNetwork completeTransactionNetwork = new CompleteTransactionNetwork(Purchse.this,result);
                completeTransactionNetwork.show();
            }
        });
        payment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Post the transaction data to dusupay*/

                customer_phone_number = phone_number_id.getText().toString();
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Purchse.this);
                if (amount_id.getText().toString().trim().isEmpty() || amount_id.getText().toString().trim().length() == 0 || amount_id.getText().toString().trim().equals("") || amount_id.getText().toString().trim() == null) {
                    alertDialogBuilder.setTitle("Invalid Amount");
                    alertDialogBuilder.setMessage("Missing transaction amount");
                    alertDialogBuilder.setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {

                                }
                            });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }else if (phone_number_id.getText().toString().trim().isEmpty() || phone_number_id.getText().toString().trim().length() == 0 || phone_number_id.getText().toString().trim().length() >15 || phone_number_id.getText().toString().trim().equals("") || phone_number_id.getText().toString().trim() == null) {
                    alertDialogBuilder.setTitle("Invalid Phone number");
                    alertDialogBuilder.setMessage("Missing valid phone number in an international format e.g 25670120XXXX.");
                    alertDialogBuilder.setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {

                                }
                            });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }else if (currency_id.getText().toString().trim().isEmpty() || currency_id.getText().toString().trim().length() == 0 || currency_id.getText().toString().trim().equals("") || currency_id.getText().toString().trim() == null) {
                    alertDialogBuilder.setTitle("Invalid Currency");
                    alertDialogBuilder.setMessage("Missing currency  e,g UGX");
                    alertDialogBuilder.setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {

                                }
                            });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
              //  }else if (amount_id.toString().trim().isEmpty() || amount_id.toString().trim().length() == 0 || amount_id.toString().trim().equals("") || amount_id.toString().trim() == null) {

                }else if (mac_key_signatureurl.toString().trim().isEmpty() || mac_key_signatureurl.toString().trim().length() == 0 || mac_key_signatureurl.toString().trim().equals("") || mac_key_signatureurl.toString().trim() == null) {
                    alertDialogBuilder.setTitle("Invalid Signature Url");
                    alertDialogBuilder.setMessage("Missing Link to return the string with a signature");
                    alertDialogBuilder.setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {

                                }
                            });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }else if (merchantId.toString().trim().isEmpty() || merchantId.toString().trim().length() == 0 || merchantId.toString().trim().length() >4 || merchantId.toString().trim().equals("") || merchantId.toString().trim() == null) {
                    alertDialogBuilder.setTitle("Invalid Merchant ID");
                    alertDialogBuilder.setMessage("Missing Valid merchant ID");
                    alertDialogBuilder.setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {

                                }
                            });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }else if (currency.trim().isEmpty() || currency.trim().length() == 0 || currency.trim().equals("") || currency.trim() == null) {
                    alertDialogBuilder.setTitle("Invalid Currency");
                    alertDialogBuilder.setMessage("Missing currency  e,g UGX");
                    alertDialogBuilder.setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {

                                }
                            });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }else if (completeNetworkTransactionUrl.trim().isEmpty() || completeNetworkTransactionUrl.trim().length() == 0 || completeNetworkTransactionUrl.trim().equals("") || completeNetworkTransactionUrl.trim() == null) {
                    alertDialogBuilder.setTitle("Invalid Network transaction Url");
                    alertDialogBuilder.setMessage("Missing the Complete Network Transaction Url link that receive the Network transaction claim");
                    alertDialogBuilder.setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {

                                }
                            });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }else if (country_code.trim().isEmpty() || country_code.trim().length() == 0 || country_code.trim().equals("") || country_code.trim() == null) {
                    alertDialogBuilder.setTitle("Invalid Country Code");
                    alertDialogBuilder.setMessage("Missing the country code e.g 256");
                    alertDialogBuilder.setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {

                                }
                            });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
              //  }else if (dusupay_redirectURL.trim().isEmpty() || dusupay_redirectURL.trim().length() == 0 || dusupay_redirectURL.trim().equals("") || dusupay_redirectURL.trim() == null) {

                }else if (callbackUrl.trim().isEmpty() || callbackUrl.trim().length() == 0 || callbackUrl.trim().equals("") || callbackUrl.trim() == null) {
                    alertDialogBuilder.setTitle("Invalid Callback Url");
                    alertDialogBuilder.setMessage("Missing callbck url that receives the json string of the initialized transaction with a pending status");
                    alertDialogBuilder.setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {

                                }
                            });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
             //   }else if (client_email.trim().isEmpty() || client_email.trim().length() == 0 || client_email.trim().equals("") || client_email.trim() == null) {

              //  }else if (client_name.trim().isEmpty() || client_name.trim().length() == 0 || client_name.trim().equals("") || client_name.trim() == null) {

                }else if (paymetmodule.trim().isEmpty() || paymetmodule.trim().length() == 0 || paymetmodule.trim().equals("") || paymetmodule.trim() == null) {
                    alertDialogBuilder.setTitle("Invalid Payment Module");
                    alertDialogBuilder.setMessage("Missing Payment Module");
                    alertDialogBuilder.setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {

                                }
                            });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
               // }else if (environment.trim().isEmpty() || environment.trim().length() == 0 || environment.trim().equals("") || environment.trim() == null) {

                }else if (transactionReferenceNumber.trim().isEmpty() || transactionReferenceNumber.trim().length() == 0 || transactionReferenceNumber.trim().equals("") || transactionReferenceNumber.trim() == null) {
                    alertDialogBuilder.setTitle("Invalid AmouTransaction Reference");
                    alertDialogBuilder.setMessage("Missing Transaction Reference Number");
                    alertDialogBuilder.setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {

                                }
                            });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }else if (itemPrice.trim().isEmpty() || itemPrice.trim().length() == 0 || itemPrice.trim().equals("") || itemPrice.trim() == null) {
                    alertDialogBuilder.setTitle("Invalid Price");
                    alertDialogBuilder.setMessage("Missing Item Price");
                    alertDialogBuilder.setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {

                                }
                            });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
              //  }else if (ItemId.trim().isEmpty() || ItemId.trim().length() == 0 || ItemId.trim().equals("") || ItemId.trim() == null) {

                //}else if (itemName.trim().isEmpty() || itemName.trim().length() == 0 || itemName.trim().equals("") || itemName.trim() == null) {

               }else{
                progress = new ProgressDialog(Purchse.this);
                progress.setMessage("Initializing Transaction... ");
                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progress.setIndeterminate(true);
                    phoneNumber = phone_number_id.getText().toString();
                if(paybillinstructions_id.isChecked()){
                    simulatePayBill = true;
                    simulatePayBillcheck = "true";
                }else{
                    simulatePayBill = false;
                    simulatePayBillcheck = "false";
                }
                progress.show();
                new SendPostRequest().execute();
            }


                //BottomSheet dialog = new BottomSheet(mContext)
              //  new Connection().execute();
               /* HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://sandbox.dusupay.com/merchant-api/collections/mobile/requestPayment.json");
                Log.d("response", "checking");
                try {
                    // Add your data
                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                    nameValuePairs.add(new BasicNameValuePair("dusupay_merchantId", merchantId));
                    nameValuePairs.add(new BasicNameValuePair("dusupay_amount", itemPrice));
                    nameValuePairs.add(new BasicNameValuePair("dusupay_itemId", ItemId));
                    nameValuePairs.add(new BasicNameValuePair("dusupay_itemName", itemName));
                    nameValuePairs.add(new BasicNameValuePair("dusupay_transactionReference", transactionReferenceNumber));
                    nameValuePairs.add(new BasicNameValuePair("dusupay_customerPhone", customer_phone_number));
                    nameValuePairs.add(new BasicNameValuePair("dusupay_redirectURL", "myapp://dusupayapp&dusupay_successURL="));
                    nameValuePairs.add(new BasicNameValuePair("dusupay_environment", "sandbox"));
                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    // Execute HTTP Post Request
                    HttpResponse response = httpclient.execute(httppost);
                    System.out.println("response"+response.toString());
                    Log.d("response", response.toString());

                } catch (ClientProtocolException e) {
                    Log.d("response", e.getMessage());
                } catch (IOException e) {
                    Log.d("response", e.getMessage());
                }*/

               /* Map<String, String> postData = new HashMap<>();
                postData.put("dusupay_merchantId", merchantId);
                postData.put("dusupay_amount", itemPrice);
                postData.put("dusupay_itemId",ItemId );
                postData.put("dusupay_itemName", itemName);
                postData.put("dusupay_transactionReference", transactionReferenceNumber);
                postData.put("dusupay_customerPhone", customer_phone_number);
                postData.put("dusupay_redirectURL", "myapp://dusupayapp&dusupay_successURL=");
                postData.put("dusupay_environment", "sandbox");
                HttpPostAsyncTask task = new HttpPostAsyncTask(postData);
                task.execute("http://sandbox.dusupay.com/merchant-api/collections/mobile/requestPayment.json");*/

            }
        });

    }


    public class SendPostRequest extends AsyncTask<String, Void, String> {

        protected void onPreExecute(){}

        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL(mac_key_signatureurl); // here is your URL path
              //  URL url = new URL("http://192.168.43.187/dusukey.php"); // here is your URL path
                long time= System.currentTimeMillis();
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("dusupay_merchantId", merchantId);
                postDataParams.put("dusupay_amount", itemPrice);
                postDataParams.put("dusupay_itemId", ItemId);
                postDataParams.put("dusupay_itemName", itemName);
                postDataParams.put("dusupay_transactionReference", transactionReferenceNumber);
                postDataParams.put("dusupay_customerPhone", customer_phone_number);
                postDataParams.put("dusupay_currency", currency);
                postDataParams.put("account_email", client_email);
                postDataParams.put("dusupay_transactionTimestamp", String.valueOf(time));
                postDataParams.put("simulatePayBill", simulatePayBill);
              //  postDataParams.put("dusupay_redirectURL", "myapp://dusupayapp&dusupay_successURL=");
                postDataParams.put("dusupay_redirectURL", dusupay_redirectURL);
                Log.e("params",postDataParams.toString());
                Log.e("params",mac_key_signatureurl);

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
            System.out.println("response"+result.toString());
            Log.d("response", result);
            progress.dismiss();
            if(isJson(result)) {
                CompletePayment completePayment = new CompletePayment(Purchse.this, result);
                completePayment.show();
            }else{
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Purchse.this);
                alertDialogBuilder.setTitle("INVALID");
                alertDialogBuilder.setMessage("Server response is not a valid json string");
                alertDialogBuilder.setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
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
