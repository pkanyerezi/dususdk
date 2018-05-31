package dususdk.dusupay.com.dususdk.model;

/**
 * Created by pkanye on 22-Mar-18.
 */


import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PayBillInstruction implements Serializable {

    private String id;
    private String name;
    private List<String> instructions = null;
    private List<String> notes = null;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getInstructions() {
        return instructions;
    }

    public void setInstructions(List<String> instructions) {
        this.instructions = instructions;
    }

    public List<String> getNotes() {
        return notes;
    }

    public void setNotes(List<String> notes) {
        this.notes = notes;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}