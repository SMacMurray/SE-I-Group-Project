package stay_and_shop_system.user;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class PaymentMethod {
    private String creditCardNumber = "";
    private String ccvNumber = "";
    private String billingAddress = "";
    private Calendar expDate = Calendar.getInstance();
    private SimpleDateFormat formatter = new SimpleDateFormat("MM/yy");

    public PaymentMethod() {
    }
    public PaymentMethod(String ccn, String ccv, String ba, Calendar eD) {
        creditCardNumber = ccn;
        ccvNumber = ccv;
        billingAddress = ba;
        expDate = eD;
    }
    public PaymentMethod(String ccn, String ccv, String ba, String eD) {
        creditCardNumber = ccn;
        ccvNumber = ccv;
        billingAddress = ba;
        try {
            expDate.setTime(formatter.parse(eD));
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
    }
    public void setPaymentMethod(PaymentMethod pm) {
        creditCardNumber = pm.creditCardNumber;
        ccvNumber = pm.ccvNumber;
        billingAddress = pm.billingAddress;
        expDate = pm.expDate;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }
    public String getCcvNumber() {
        return ccvNumber;
    }
    public String getBillingAddress() {
        return billingAddress;
    }
    public Calendar getExpDate() {
        return expDate;
    }
    public String getExpDateAsString() {
        return formatter.format(expDate.getTime());
    }
}
