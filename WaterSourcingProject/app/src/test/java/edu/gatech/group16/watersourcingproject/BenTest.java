package edu.gatech.group16.watersourcingproject;

import org.junit.Assert;
import org.junit.Test;

import edu.gatech.group16.watersourcingproject.controller.NewWaterSourceReport;

/**
 * Created by Edwin Zhao on 4/10/2017.
 */

public class BenTest {
    @SuppressWarnings({"unused", "JavaDoc"})
    @Test
    public void testValidCoordinate() throws Exception {
        Assert.assertTrue(NewWaterSourceReport.validVirusPPM("45"));
    }

    @SuppressWarnings({"unused", "JavaDoc"})
    @Test
    public void testInvalidCoordinate() throws Exception {
        Assert.assertFalse(NewWaterSourceReport.validVirusPPM("50#"));
    }

    @SuppressWarnings({"unused", "JavaDoc"})
    @Test
    public void testOutOfBoundCoordinate() throws Exception {
        Assert.assertFalse(NewWaterSourceReport.validVirusPPM("-200"));
    }

    @SuppressWarnings({"unused", "JavaDoc"})
    @Test
    public void testNullCoordinate() throws Exception {
        Assert.assertFalse(NewWaterSourceReport.validVirusPPM(""));
    }
}
