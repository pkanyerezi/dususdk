package dususdk.dusupay.com.dususdk;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.BottomSheetDialog;

import java.util.ArrayList;

/**
 * Created by pkanye on 29-Nov-17.
 */

public class DusupaySDK {
    Context mContext;
    String itemName;
    String ItemId;
    double itemPrice;
    String currency="";
    String transactionReferenceNumber="";
    String environment="";
    String merchantId="";
    String paymentModule="";
    long transaction_time_stamp=5658966;
    String customer_name="";
    String customer_email="";
    String dusupay_successURL="";
    String dusupay_redirectURL="";//responds with a status success when a transaction is successful
    String mac_signatureurl="";//used to generate the transaction template with a signature
   int imagec;
    String country_code;
     ArrayList<String> items = null;

    String callbackUrl;//used to submit the transaction to the clients side
    String completeNetworkTransactionUrl;//used to submit the transaction to the clients side
    String completeNetworkTransactionmerchantcallbackUrl;//used to submit the transaction to the clients side
     //new DusupaySDK(MainActivity.this,"Polo T-shirt","2345",3000,"UGX",89766,"sandbox",1047);
    public DusupaySDK(Context context){
        this.mContext = context;

    }
    public void setPaymentChannels( ArrayList<String> itemsToAdd) {

        this.items=itemsToAdd;
    }
    public void loadSDK(Context context){
        this.mContext = context;
        BottomSheet dialog = new BottomSheet(mContext,this.itemName,this.ItemId,this.itemPrice,this.currency,
                this.transactionReferenceNumber,this.environment,this.merchantId,this.customer_name,
                this.customer_email,this.items,this.transaction_time_stamp,this.dusupay_successURL,this.mac_signatureurl,this.callbackUrl,this.dusupay_redirectURL,this.completeNetworkTransactionUrl,this.completeNetworkTransactionmerchantcallbackUrl);
        dialog.show();
        /*String itemName,String ItemId,
                       double itemPrice,String currency,String transactionReferenceNumber,
                       String environment,String merchantId,String paymentModule,String customer_name,String customer_email)*/
    }
    public void load(Context context){
        this.mContext = context;
       // System.out.println("dataflow"+this.itemName+"_"+ItemId+"_"+this.itemPrice+"_"+currency);
        Intent intent = new Intent(mContext, Purchse.class);
        intent.putExtra("imagec", this.imagec);
        intent.putExtra("itemName", ""+this.itemName);
        intent.putExtra("ItemId", ""+this.ItemId);
        intent.putExtra("itemPrice",""+ this.itemPrice);
        intent.putExtra("currency", ""+this.currency);
        intent.putExtra("transactionReferenceNumber", ""+this.transactionReferenceNumber);
        intent.putExtra("environment",""+ this.environment);
        intent.putExtra("merchantId", ""+this.merchantId);
        intent.putExtra("paymetmodule", ""+this.paymentModule);
        intent.putExtra("dusupay_successURL", ""+this.dusupay_successURL);
        intent.putExtra("mac_signatureurl", ""+this.mac_signatureurl);
        intent.putExtra("transaction_time_stamp", ""+this.transaction_time_stamp);
        intent.putExtra("client_name", ""+this.customer_name);
        intent.putExtra("client_email", ""+this.customer_email);
        intent.putExtra("callbackUrl", ""+this.callbackUrl);
        intent.putExtra("dusupay_redirectURL", ""+this.dusupay_redirectURL);
        intent.putExtra("country_code", ""+this.country_code);
        intent.putExtra("completeNetworkTransactionUrl", ""+this.completeNetworkTransactionUrl);
        intent.putExtra("completeNetworkTransactionmerchantcallbackUrl", ""+this.completeNetworkTransactionmerchantcallbackUrl);

        this.mContext.startActivity(intent);
    }
    /*public DusupaySDK(Context context,String itemName,
                      String ItemId,double itemPrice,
                      String currency,int transactionReferenceNumber,
                      String environment,int merchantId){
        this.mContext = context;
        Intent intent = new Intent(this.mContext, Purchse.class);
        intent.putExtra("itemName", ""+itemName);
        intent.putExtra("ItemId", ""+ItemId);
        intent.putExtra("itemPrice",""+ itemPrice);
        intent.putExtra("currency", ""+currency);
        intent.putExtra("transactionReferenceNumber", ""+transactionReferenceNumber);
        intent.putExtra("environment",""+ environment);
        intent.putExtra("merchantId", ""+merchantId);

        this.mContext.startActivity(intent);
    }*/
    public void setImagec(int imagec){
        this.imagec = imagec;
    }
    public void setItemName(String itemName){
        this.itemName = itemName;
    }
    public void setItemId(String ItemId){
        this.ItemId = ItemId;
    }
    public void setItemPrice(double itemPrice){
        this.itemPrice = itemPrice;
    }
    public void setCurrency(String currency){
        this.currency = currency;
    }
    public void setTransactionReferenceNumber(String transactionReferenceNumber){this.transactionReferenceNumber = transactionReferenceNumber;}
    public void setEnvironment(String environment){
        this.environment = environment;
    }
    public void setSuccessfulUrl(String dusupay_successURL){
        this.dusupay_successURL = dusupay_successURL;
    }
    public void setDusupayRedirectURL(String dusupay_redirectURL){
        this.dusupay_redirectURL = dusupay_redirectURL;
    }
    public void setMerchantId(String merchantId){
        this.merchantId = merchantId;
    }
    public void setPaymentModule(String paymentModule){this.paymentModule = paymentModule;}
    public void setCustomerName(String customer_name){
        this.customer_name = customer_name;
    }
    public void setCustomerEmail(String customer_email){
        this.customer_email = customer_email;
    }
    public void setSignatureUrl(String signature){
        this.mac_signatureurl = signature;
    }
    public void setTransactionTimeStamp(long transaction_time_stamp){this.transaction_time_stamp = transaction_time_stamp;}
    public void setContext(Context mContext){
        this.mContext = mContext;
    }

    public void setCallBackUrl(String callbackUrl){
        this.callbackUrl = callbackUrl;
    }
    public void setCountryCode(String country_code){
        this.country_code = country_code;
    }
    public void setCompleteNetworkTransactionmerchantcallbackUrl(String completeNetworkTransactionmerchantcallbackUrl){
        this.completeNetworkTransactionmerchantcallbackUrl = completeNetworkTransactionmerchantcallbackUrl;
    }
    public void setCompleteNetworkTransactionUrl(String completeNetworkTransactionUrl){
        this.completeNetworkTransactionUrl = completeNetworkTransactionUrl;
    }

    public int getImagec() {return imagec;}
    public String getItemName() {return itemName;}
    public String getItemId() {return ItemId;}
    public String getItemPrice() {return ""+itemPrice;}
    public String getCurrency() {return currency;}
    public String getTransactionReferenceNumber() {return transactionReferenceNumber;}
    public String getEnvironment() {return environment;}
    public String getMerchantId() {return merchantId;}
    public String getCustomerName() {return customer_name;}
    public String getCustomerEmail() {return customer_email;}
    public long getTransactionTimeStamp() {return transaction_time_stamp;}
    public String getSuccessUrl() {return dusupay_successURL;}
    public String getSignatureUrl() {return mac_signatureurl;}
    public String getDusupayRedirectURL() {return dusupay_redirectURL;}


}
