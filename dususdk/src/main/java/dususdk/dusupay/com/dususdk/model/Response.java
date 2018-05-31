package dususdk.dusupay.com.dususdk.model;

/**
 * Created by pkanye on 22-Mar-18.
 */


import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Response implements Serializable {

    private String id;
    private Integer amount;
    private String currency;
    private String item_id;
    private String merchant_reference;
    private Double charge;
    private String charge_currency;
    private String transaction_status;
    private String date;
    private Boolean status;
    private String account_id;
    private String account_name;
    private String account_email;
    private String account_number;
    private Boolean must_use_pay_bill_instructions;
    private List<PayBillInstruction> pay_bill_instructions = null;
    private String message;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getItemId() {
        return item_id;
    }

    public void setItemId(String itemId) {
        this.item_id = itemId;
    }

    public String getMerchantReference() {
        return merchant_reference;
    }

    public void setMerchantReference(String merchantReference) {
        this.merchant_reference = merchantReference;
    }

    public Double getCharge() {
        return charge;
    }

    public void setCharge(Double charge) {
        this.charge = charge;
    }

    public String getChargeCurrency() {
        return charge_currency;
    }

    public void setChargeCurrency(String chargeCurrency) {
        this.charge_currency = chargeCurrency;
    }

    public String getTransactionStatus() {
        return transaction_status;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transaction_status = transactionStatus;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getAccountId() {
        return account_id;
    }

    public void setAccountId(String accountId) {
        this.account_id = accountId;
    }

    public String getAccountName() {
        return account_name;
    }

    public void setAccountName(String accountName) {
        this.account_name = accountName;
    }

    public String getAccountEmail() {
        return account_email;
    }

    public void setAccountEmail(String accountEmail) {
        this.account_email = accountEmail;
    }

    public String getAccountNumber() {
        return account_number;
    }

    public void setAccountNumber(String accountNumber) {
        this.account_number = accountNumber;
    }

    public Boolean getMustUsePayBillInstructions() {
        return must_use_pay_bill_instructions;
    }

    public void setMustUsePayBillInstructions(Boolean mustUsePayBillInstructions) {
        this.must_use_pay_bill_instructions = mustUsePayBillInstructions;
    }

    public List<PayBillInstruction> getPayBillInstructions() {
        return pay_bill_instructions;
    }

    public void setPayBillInstructions(List<PayBillInstruction> payBillInstructions) {
        this.pay_bill_instructions = payBillInstructions;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}