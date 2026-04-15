package stay_and_shop_system.user;

import java.util.*;
import stay_and_shop_system.occupancy.*;

public interface GuestInterface extends User {
    PaymentMethod pm = new PaymentMethod();

    public PaymentMethod getPaymentMethod();
    public void setPaymentMethod(PaymentMethod pm);
    int getGuestId();

}
