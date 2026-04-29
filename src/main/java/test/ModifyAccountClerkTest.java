package test;

import org.junit.jupiter.api.*;
import stay_and_shop_system.user.GuestClerk;

import static org.junit.jupiter.api.Assertions.*;

public class ModifyAccountClerkTest {
    GuestClerk clerk;

    @BeforeEach
    void setup() {
        clerk = new GuestClerk("clerk@test.com", "Old Name", "oldpass".hashCode(), "+1 909 909 9999", "pay1");
    }

    @Test
    void modifyClerkName() {
        clerk.setName("New Clerk");
        assertEquals("New Clerk", clerk.getName());
    }

    @Test
    void modifyClerkEmail() {
        clerk.setEmail("newclerk@test.com");
        assertEquals("newclerk@test.com", clerk.getEmail());
    }

    @Test
    void modifyClerkPhoneNumber() {
        clerk.setPhoneNumber("+1 909-909-9999");
        assertEquals("+1 909-909-9999", clerk.getPhoneNumber());
    }

    @Test
    void modifyClerkPasswordHashesPassword() {
        clerk.setPassword("newpass");
        assertEquals("newpass".hashCode(), clerk.getPassword());
    }

    @Test
    void clerkKeepsUpdatedMultipleFields() {
        clerk.setName("Jeff");
        clerk.setEmail("jeff@test.com");
        clerk.setPhoneNumber("+1 909 909 9999");

        assertEquals("Jeff", clerk.getName());
        assertEquals("jeff@test.com", clerk.getEmail());
        assertEquals("+1 909 909 9999", clerk.getPhoneNumber());
    }
}