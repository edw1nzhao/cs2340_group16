package edu.gatech.group16.watersourcingproject;

import org.junit.Assert;
import org.junit.Test;

import edu.gatech.group16.watersourcingproject.controller.login.RegPasswordActivity;

/**
 * Created by Tomonari on 4/10/2017.
 */

public class ChloeTest {
    @SuppressWarnings({"unused", "JavaDoc"})
    @Test
    public void testValidCoordinate() throws Exception {
        Assert.assertTrue(RegPasswordActivity.validateForm("password"));
    }

    @SuppressWarnings({"unused", "JavaDoc"})
    @Test
    public void testInvalidCoordinate() throws Exception {
        Assert.assertFalse(RegPasswordActivity.validateForm("short"));
    }

    @SuppressWarnings({"unused", "JavaDoc"})
    @Test
    public void testOutOfBoundCoordinate() throws Exception {
        Assert.assertFalse(RegPasswordActivity.validateForm("longpasswordlongpasswordlongpasswordlongpassword"));
    }

    @SuppressWarnings({"unused", "JavaDoc"})
    @Test
    public void testNullCoordinate() throws Exception {
        Assert.assertFalse(RegPasswordActivity.validateForm(""));
    }
}
