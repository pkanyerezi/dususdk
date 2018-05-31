package dususdk.dusupay.com.dususdk;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;


import java.util.ArrayList;

public class BottomSheet extends BottomSheetDialog {

    private Context context;
    String itemName;
    String ItemId;
    double itemPrice;
    long transaction_time_stamp;
    String currency;
    String transactionReferenceNumber;
    String environment;
    String merchantId;
    String paymentModule;
    String customer_name="";
    String customer_email="";
    String dusupay_successURL="";
    String mac_signature_url="";
    String callbackUrl;
    String dusupay_redirectURL;

    String dusupay_networkURL;
    String completeNetworkTransactionmerchantcallbackUrl;
    public  static int country_img;




    ArrayList<String> selecteditems = null;
    public BottomSheet(Context context,String itemName, String ItemId,
                       double itemPrice, String currency, String transactionReferenceNumber,
                       String environment, String merchantId, String customer_name,
                       String customer_email, ArrayList items,long transaction_time_stamp,
                       String dusupay_successURL,String mac_signature_url,String callbackUrl, String dusupay_redirectURL,String dusupay_networkURL,String completeNetworkTransactionmerchantcallbackUrl){
        /*      (mContext,this.itemName,this.ItemId,this.itemPrice,this.currency,
                this.transactionReferenceNumber,this.environment,this.merchantId,this.customer_name,
                this.customer_email,this.items,this.transaction_time_stamp,this.dusupay_successURL,this.signature);*/
        super(context);

        this.context = context;
        this.itemName=itemName;
        this.ItemId = ItemId;
        this.selecteditems = items;
        this.itemPrice=itemPrice;
        this.currency=currency;
        this.transactionReferenceNumber = transactionReferenceNumber;
        this.environment = environment;
        this.merchantId=merchantId;
        this.customer_name=customer_name;
        this.customer_email = customer_email;
        this.transaction_time_stamp = transaction_time_stamp;
        this.dusupay_successURL = dusupay_successURL;
        this.mac_signature_url = mac_signature_url;
        this.callbackUrl = callbackUrl;
        this.dusupay_redirectURL = dusupay_redirectURL;
        this.dusupay_networkURL = dusupay_networkURL;
        this.completeNetworkTransactionmerchantcallbackUrl = completeNetworkTransactionmerchantcallbackUrl;


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getLayoutInflater().inflate(R.layout.payment_options, null);
        setContentView(view);
        ImageView close_sheet = (ImageView) view.findViewById(R.id.close_sheet);
        close_sheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheet.this.dismiss();
            }
        });
//        System.out.println("items length "+this.selecteditems.size());

      /*  ArrayList items=new ArrayList();
        items.add( new Item(1,R.drawable.dusulogo, "Bank in Nigeria") );
        items.add( new Item(2,R.drawable.dusulogo, "Rwanda MTN Mobile Money") );
        items.add( new Item(3,R.drawable.dusulogo, "Tanzania All Mobile Money") );
        items.add(new Item(4,R.drawable.dusulogo, "Cameroon MTN Mobile Money"));
        items.add( new Item(5,R.drawable.dusulogo, "Kenya MPESA") );
        items.add( new Item(6,R.drawable.dusulogo, "Uganda Airtel Money") );
        items.add(new Item(7,R.drawable.dusulogo, "Uganda MTN Mobile Money"));
        items.add(new Item(8,R.drawable.dusulogo, "Uganda MTN Mobile Money"));
        items.add(new Item(9,R.drawable.dusulogo, "Ghana All Mobile Money"));
        items.add(new Item(10,R.drawable.dusulogo, "Banks in Uganda"));
        items.add(new Item(11,R.drawable.dusulogo, "Bank UK/EU"));
        items.add(new Item(12,R.drawable.dusulogo, "Bank Accounts in Kenya"));
        items.add(new Item(13,R.drawable.dusulogo, "Bank Ghana"));*/

        final ItemAdapter adapter = new ItemAdapter( this.context, this.selecteditems );

        //ListView for the items
        ListView listView = (ListView) view.findViewById(R.id.list_items);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {

                Item item = (Item) adapter.getItem(position);
                String code = item.getId();
                Toast.makeText(context,item.getId()+"."+ item.getText()+"."+item.getImage(), Toast.LENGTH_SHORT).show();
                country_img = item.getImage();
                DusupaySDK dusupaySDK =  new DusupaySDK(getContext());
                dusupaySDK.setContext(getContext());
                dusupaySDK.setItemId(ItemId);
                dusupaySDK.setItemName(itemName);
                dusupaySDK.setItemPrice(itemPrice);
                dusupaySDK.setCurrency(currency);
                dusupaySDK.setTransactionReferenceNumber(transactionReferenceNumber);
                dusupaySDK.setEnvironment(environment);
                dusupaySDK.setMerchantId(merchantId);
                dusupaySDK.setCustomerEmail(customer_email);
                dusupaySDK.setCustomerName(customer_name);
                dusupaySDK.setTransactionTimeStamp(transaction_time_stamp);
                dusupaySDK.setSuccessfulUrl(dusupay_successURL);
                dusupaySDK.setSignatureUrl(mac_signature_url);
                dusupaySDK.setPaymentModule(""+item.getText());
                dusupaySDK.setImagec(item.getImage());
                dusupaySDK.setCallBackUrl(callbackUrl);
                dusupaySDK.setDusupayRedirectURL(dusupay_redirectURL);
                dusupaySDK.setCompleteNetworkTransactionUrl(dusupay_networkURL);
                dusupaySDK.setCompleteNetworkTransactionmerchantcallbackUrl(completeNetworkTransactionmerchantcallbackUrl);

                dusupaySDK.setCountryCode(""+code);


                 dusupaySDK.load(getContext());
               /* Intent it = null;

                switch(position){
                    case 0:
                        it = new Intent(Intent.ACTION_VIEW);
                        it.setData(Uri.parse("http://www.whatsapp.com"));
                        break;
                    case 1:
                        it = new Intent(Intent.ACTION_VIEW);
                        it.setData(Uri.parse("http://www.facebook.com"));
                        break;
                    case 2:
                        it = new Intent(Intent.ACTION_VIEW);
                        it.setData(Uri.parse("http://plus.google.com"));
                        break;
                    case 3:
                        it = new Intent(Intent.ACTION_VIEW);
                        it.setData(Uri.parse("http://www.twitter.com"));
                        break;
                    case 4:
                        it = new Intent(Intent.ACTION_VIEW);
                        it.setData(Uri.parse("http://www.youtube.com"));
                        break;
                    case 5:
                        it = new Intent(Intent.ACTION_VIEW);
                        it.setData(Uri.parse("http://www.instagram.com"));
                        break;
                    case 6:
                        it = new Intent(Intent.ACTION_VIEW);
                        it.setData(Uri.parse("http://www.stackoverflow.com"));
                        break;
                }

                context.startActivity(it);*/
            }
        });

        //GridView for the items
        /*GridView gridView = (GridView) view.findViewById(R.id.grid_items);
        gridView.setAdapter( adapter );
        gridView.setNumColumns(2);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView&lt;?&gt; parent, View view, int position, long id) {

                Intent it = null;

                switch(position){
                    case 0:
                        it = new Intent(Intent.ACTION_VIEW);
                        it.setData(Uri.parse("http://www.whatsapp.com"));
                        break;
                    case 1:
                        it = new Intent(Intent.ACTION_VIEW);
                        it.setData(Uri.parse("http://www.facebook.com"));
                        break;
                    case 2:
                        it = new Intent(Intent.ACTION_VIEW);
                        it.setData(Uri.parse("http://plus.google.com"));
                        break;
                    case 3:
                        it = new Intent(Intent.ACTION_VIEW);
                        it.setData(Uri.parse("http://www.twitter.com"));
                        break;
                    case 4:
                        it = new Intent(Intent.ACTION_VIEW);
                        it.setData(Uri.parse("http://www.youtube.com"));
                        break;
                    case 5:
                        it = new Intent(Intent.ACTION_VIEW);
                        it.setData(Uri.parse("http://www.instagram.com"));
                        break;
                    case 6:
                        it = new Intent(Intent.ACTION_VIEW);
                        it.setData(Uri.parse("http://www.stackoverflow.com"));
                        break;
                }

                context.startActivity(it);
            }
        });*/

    }
}
