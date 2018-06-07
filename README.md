# dususdk
Integrate the dusupay into your android app in just a few seconds

# Screenshots
![2](https://user-images.githubusercontent.com/4737239/41094303-80793490-6a56-11e8-9f51-051ccf02cb2e.png) ![1](https://user-images.githubusercontent.com/4737239/41094325-974dc406-6a56-11e8-8b6d-04409509c38f.png)



# How To Install

# Step 1. Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://www.jitpack.io' }
		}
	}
Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.pkanyerezi:dususdk:1.0'
	}
  
# Payment integration
When the user decides to make a purchase or a transaction,the SDK will be called and a callback will be sent back to the Activity or fragment through a BroadcastReceiver.

# How To Use
First, we'll assume that you're going to launch the payment from a button, And the code below can be put in your onclick event to start the SDK.

                ArrayList items=new ArrayList();
                items.add( new Item("250",R.drawable.mtnrwanda, "Rwanda MTN Mobile Money") );
                items.add( new Item("255",R.drawable.tanzaniaall, "Tanzania All Mobile Money") );
                items.add(new Item("237",R.drawable.mtncameroon, "Cameroon MTN Mobile Money"));
                items.add( new Item("254",R.drawable.mpesakenya, "Kenya MPESA") );
                items.add( new Item("256",R.drawable.airteluganda, "Uganda Airtel Money") );
                items.add(new Item("256",R.drawable.mtnuganda, "Uganda MTN Mobile Money"));
                items.add(new Item("233",R.drawable.ghana, "Ghana All Mobile Money"));


                DusupaySDK dusupaySDK =  new DusupaySDK(context);
                dusupaySDK.setPaymentChannels(items);
                dusupaySDK.setContext(context);
                dusupaySDK.setItemId("....");
                dusupaySDK.setItemName("..");
                dusupaySDK.setItemPrice(price_as_Double);
                dusupaySDK.setCurrency("USD");
                dusupaySDK.setTransactionReferenceNumber("....");
                 dusupaySDK.setMerchantId("...");
                dusupaySDK.setSignatureUrl("http://....");
                dusupaySDK.setEnvironment("sandbox");
                // dusupaySDK.setEnvironment("live");
                dusupaySDK.setCompleteNetworkTransactionUrl("http://......");
                dusupaySDK.loadSDK(context);
                
                
 # Callback
 The Payment or transaction results are sent to a broadcastReceiver as shown below.
 
         BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context arg0, Intent intent) {
                String action = intent.getAction();
                System.out.println("networkcheckresponse "+action.toString());
                if (action.equals("transaction_callback")) {
                  //  finish();
                    // Receive Callback for the initialized transaction including all transaction details but in pending state

                    Bundle args = intent.getBundleExtra("DUSU_DATA");
                    TransactionResponse transactionResponse = (TransactionResponse) args.getSerializable("dusu_obj");
                    System.out.println("checkresponse"+transactionResponse.getResponse().getCurrency() + " "+transactionResponse.getResponse().getAmount()+" "+ transactionResponse.getResponse().getMessage());

                }
                if(action.equals("network_transaction_callback")){
                    // Receive Callback after prviding the network id marking the transaction as successful
                    Bundle args = intent.getBundleExtra("DUSU_NETWORK_DATA");
                    TransactionResponse transactionResponse = (TransactionResponse) args.getSerializable("dusu_network_response");
                    System.out.println("networkcheckresponse "+ transactionResponse.getResponse().getMessage());
                    System.out.println("networkcheckresponseid "+ transactionResponse.getResponse().getId());

                }
            }
        };
        registerReceiver(broadcastReceiver, new IntentFilter("transaction_callback"));
        registerReceiver(broadcastReceiver, new IntentFilter("network_transaction_callback"));
        
  
 # Attributes for setting a transaction
 
  
|Name  | Description |Type|
| ------------- | ------------- |-------------|
|.setPaymentChannels() |A list ofThe different countries and payment gateways supported    |      ArrayList       |
| .setContext()  |The Activity contex. It can be MainActivity.this |   Context          |
| .setItemId()  | The Id of the item being made a transaction for.  |    String         |
| .setItemName()  | The name f the item being made a transaction for.  |    String         |
| .setItemPrice()  |The Price of the item being made a transaction for.  |    Double         |
| .setCurrency()  |The preffered currency by the merchant. These include(UGX,USD,KES,GHS,XAF,RWF,ZAR,TZS,NGN) depending on the primary transaction currency.   |      String       |
| .setTransactionReferenceNumber()  | The reference number set on the merchant's side (it can be any string as set by the developer on the merchant's side).  |    String         |
| .setEnvironment()  |This is the environment in which the developer is carrying out tests on it can be sandbox or live.  |    String         |
| .setMerchantId()  | The merchant id from dusupay.com dashboard. This can be from the (sandbox) https://sandbox.dusupay.com or (live) https://dusupay.com. Depending on the set Envirnment.  |   String          |
| .setSignatureUrl()  | This is a link to your php files that gives the transaction a signature so that you get  [this data format](https://dusupay.com/docs/build/#mobile-money) follow the parameters given for V2 of the API while creating your php file And  [here  ](https://dusupay.com/docs/build/#generate-request-signature) is the php example. Also check at the bottom t see an example of the php POST variables sent to the provided link by the SDK. |     String        |
|.setCompleteNetworkTransactionUrl()  | This is a link to your php files that gives the details  when the client provides the network Id received from an sms  a signature. [Here](https://dusupay.com/docs/build/#complete-payment-by-network-id) are the parameters expected. Also check at the bottom t see an example of the php POST variables sent to the provided link by the SDK.|    String         |
|.setCustomerEmail() |The Email of the transacting client (Optional).  |     String        |
| .setCustomerName()  | Name of the transacting client (Optinal). |   String          |




# Sample Php Code expected for the file on the link provided in setSignatureUrl()
```php
<?php
	/*Have tried to hard code the variables here*/
    $requestData = [
		
	 "merchant_id"=> $_POST['dusupay_merchantId'],
        "amount"=> $_POST['dusupay_amount'],
        "currency"=> $_POST['dusupay_currency'],
        "merchant_reference"=>$_POST['dusupay_transactionReference'],
        "timestamp"=> $_POST['dusupay_transactionTimestamp'],
        "account_number"=> $_POST['dusupay_customerPhone'],
         "account_name"=> "Customer1",
        // "success_url"=>"(optional)https://domain.com/myIPNCallbackURL",
         "item_id"=>$_POST['dusupay_itemId'],
         "item_name"=> $_POST['dusupay_itemName'],
        "account_email"=> "customer@mail.com",
        // "simulatePayBill"=> true, // (Optional) - if set to true, use the completePaymentByNetworkId API to complete the transaction
         "simulatePayBill"=> $_POST['simulatePayBill'], // (Optional) - if set to true, use the completePaymentByNetworkId API to complete the transaction
    
    ];

    /*The API docs explain how to get the Mackey
	
	https://dusupay.com/docs/build/#generate-request-signature
	*/
  $requestData['signature'] = getSignature($requestData,'Mackey');


    // Function to generate request signature
    function getSignature($requestData, $merchantMackey){
        ksort($requestData);
        $stringData = '';
        foreach ($requestData as $key => $value) {
            if(in_array($key, ['signature'])) continue;
            $stringData .= $value;
        }
        return hash_hmac('sha1', $stringData, $merchantMackey);
    }
    echo json_encode($requestData);

?>
```
# Sample Php Code expected for the file on the link provided in setCompleteNetworkTransactionUrl()

```php
<?php

	/*Have tried to hard code the variables here*/
    $requestData = [
        "id"=> $_POST['transaction_id'],
        "merchant_id"=> $_POST['dusupay_merchantId'],
        "network_transaction_id"=> $_POST['network_transaction_id'],
        "account_number"=> $_POST['account_number'],
        "timestamp"=> time(),
    ];

    /*The API docs explain how to get the Mackey
	
	https://dusupay.com/docs/build/#generate-request-signature
	*/
/*The mac key gotten from your merchant account dashboard*/
  $requestData['signature'] = getSignature($requestData,'MacKey');
 

    // Function to generate request signature
    function getSignature($requestData, $merchantMackey){
        ksort($requestData);
        $stringData = '';
        foreach ($requestData as $key => $value) {
            if(in_array($key, ['signature'])) continue;
            $stringData .= $value;
        }
		
        return hash_hmac('sha1', $stringData, $merchantMackey);
    }

    echo json_encode($requestData);

?>
```

# License

[Dusupay](https://dusupay.com/pages/dusupay_privacy_policy) Privacy Policy
