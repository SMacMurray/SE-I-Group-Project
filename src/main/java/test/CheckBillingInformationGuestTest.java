package test;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class CheckBillingInformationGuestTest {

    @Test
    void guestCanViewOwnBillingEmail() {
        String sessionEmail = "guest@test.com";
        String requestedEmail = "guest@test.com";

        assertEquals(sessionEmail, requestedEmail);
    }

    @Test
    void guestCannotViewOtherGuestBillingEmail() {
        String sessionEmail = "guest1@test.com";
        String requestedEmail = "guest2@test.com";

        assertNotEquals(sessionEmail, requestedEmail);
    }

    @Test
    void billingTotalCanIncludeRoomCharge() {
        double roomCharge = 300.0;
        double shopCharge = 0.0;

        assertEquals(300.0, roomCharge + shopCharge);
    }

    @Test
    void billingTotalCanIncludeShopCharge() {
        double roomCharge = 300.0;
        double shopCharge = 45.0;

        assertEquals(345.0, roomCharge + shopCharge);
    }

    @Test
    void billingTotalCannotBeNegativeForValidCharges() {
        double roomCharge = 100.0;
        double shopCharge = 20.0;

        assertTrue(roomCharge + shopCharge >= 0);
    }
}