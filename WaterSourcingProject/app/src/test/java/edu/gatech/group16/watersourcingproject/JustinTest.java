package edu.gatech.group16.watersourcingproject; /**
 * Created by Edwin Zhao on 4/10/2017.
 */

import org.junit.Test;
import org.junit.Assert;


import edu.gatech.group16.watersourcingproject.controller.NewWaterSourceReport;

@SuppressWarnings("JavaDoc")
public class JustinTest {

    @SuppressWarnings({"unused", "JavaDoc"})
    @Test
    public void testValidCoordinate() throws Exception {
        Assert.assertTrue(NewWaterSourceReport.validContaminantPPM("45"));
    }

    @SuppressWarnings({"unused", "JavaDoc"})
    @Test
    public void testInvalidCoordinate() throws Exception {
        Assert.assertFalse(NewWaterSourceReport.validContaminantPPM("50#"));
    }

    @SuppressWarnings({"unused", "JavaDoc"})
    @Test
    public void testOutOfBoundCoordinate() throws Exception {
        Assert.assertFalse(NewWaterSourceReport.validContaminantPPM("-200"));
    }

    @SuppressWarnings({"unused", "JavaDoc"})
    @Test
    public void testNullCoordinate() throws Exception {
        Assert.assertFalse(NewWaterSourceReport.validContaminantPPM(""));
    }
}
