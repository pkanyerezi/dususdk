package dususdk.dusupay.com.dususdk.model;

/**
 * Created by pkanye on 22-Mar-18.
 */


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class TransactionResponse implements Serializable {

    private Response response;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}