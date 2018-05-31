package dususdk.dusupay.com.dususdk;

/**
 * Created by pkanye on 25-May-18.
 */

//public class MyCustomObject {
    // Listener defined earlier
    public interface MyCustomObject {
        void processFinish(Object output);

        void processFinish(String output);
    }