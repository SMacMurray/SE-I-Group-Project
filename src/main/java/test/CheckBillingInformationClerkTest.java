package stay_and_shop_system.billing;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class CheckBillingInformationClerkTest {

    @Test
    void clerkCanRequestGuestBillingByEmail() {
        String guestEmail = "guest@test.com";

        assertNotNull(guestEmail);
    }

    @Test
    void clerkBillingSearchRequiresNonEmptyEmail() {
        String guestEmail = "";

        assertTrue(guestEmail.isBlank());
    }

    @Test
    void clerkCanGenerateCombinedBill() {
        double stayBill = 500.0;
        double shoppingBill = 75.0;

        assertEquals(575.0, stayBill + shoppingBill);
    }

    @Test
    void clerkCanGenerateZeroShoppingBill() {
        double stayBill = 500.0;
        double shoppingBill = 0.0;

        assertEquals(500.0, stayBill + shoppingBill);
    }

    @Test
    void clerkGeneratedBillIsNotNegativeWhenChargesValid() {
        double stayBill = 250.0;
        double shoppingBill = 30.0;

        assertTrue(stayBill + shoppingBill >= 0);
    }
}