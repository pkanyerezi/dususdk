package sdkdusupay.dusu.com.testsdkapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import dususdk.dusupay.com.dususdk.DusupaySDK;
import dususdk.dusupay.com.dususdk.Item;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList items=new ArrayList();
                /*  items.add( new Item(1,R.drawable.dusulogo, "Bank in Nigeria") );*/
                items.add( new Item("252",R.drawable.mtnrwanda, "Rwanda MTN Mobile Money") );
                items.add( new Item("254",R.drawable.tanzaniaall, "Tanzania All Mobile Money") );
                items.add(new Item("233",R.drawable.mtncameroon, "Cameroon MTN Mobile Money"));
                items.add( new Item("255",R.drawable.mpesakenya, "Kenya MPESA") );
                items.add( new Item("256",R.drawable.airteluganda, "Uganda Airtel Money") );
                items.add(new Item("256",R.drawable.mtnuganda, "Uganda MTN Mobile Money"));
                items.add(new Item("256",R.drawable.dusulogo, "Uganda MTN Mobile Money"));
           /* items.add(new Item("b1",R.drawable.dusulogo, "Ghana All Mobile Money"));
             items.add(new Item("b1",R.drawable.dusulogo, "Banks in Uganda"));
           items.add(new Item("b1",R.drawable.dusulogo, "Bank UK/EU"));
            items.add(new Item("b1",R.drawable.dusulogo, "Bank Accounts in Kenya"));
            items.add(new Item("b1",R.drawable.dusulogo, "Bank Ghana"));*/


                DusupaySDK dusupaySDK =  new DusupaySDK(MainActivity.this);
                dusupaySDK.setPaymentChannels(items);
                dusupaySDK.setContext(MainActivity.this);
                dusupaySDK.setItemId("234565");
                dusupaySDK.setItemName("Nike Shoes");
                dusupaySDK.setItemPrice(10);
                dusupaySDK.setCurrency("USD");
                dusupaySDK.setTransactionReferenceNumber("34456");
                dusupaySDK.setEnvironment("");
                dusupaySDK.setMerchantId("1391");
                //dusupaySDK.setMerchantId("2110");
                dusupaySDK.setDusupayRedirectURL("myapp://dusupayapp&dusupay_successURL=");
                dusupaySDK.setSignatureUrl("http://192.168.43.187/dusukey.php");
                //  dusupaySDK.setSignatureUrl("http://192.168.88.145/dusukey.php");
                dusupaySDK.setCustomerName("kanyerezi patrick");
                dusupaySDK.setCustomerEmail("patrick@dusupay.com");
                dusupaySDK.setCallBackUrl("http://192.168.43.187/dusukey1.php");
                // dusupaySDK.setCallBackUrl("http://192.168.88.145/dusukey1.php");
                dusupaySDK.setEnvironment("sandbox");
                // dusupaySDK.setEnvironment("live");
                dusupaySDK.setCompleteNetworkTransactionUrl("http://192.168.43.187/dusuTransactionById.php");
                dusupaySDK.setCompleteNetworkTransactionmerchantcallbackUrl("http://192.168.43.187/dusupaynetwork.php");/**/
                //  dusupaySDK.setCompleteNetworkTransactionmerchantcallbackUrl("http://192.168.88.145/dusupaynetwork.php");
                dusupaySDK.loadSDK(MainActivity.this);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
