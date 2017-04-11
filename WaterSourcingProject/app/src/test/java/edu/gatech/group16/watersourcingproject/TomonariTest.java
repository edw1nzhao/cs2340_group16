package edu.gatech.group16.watersourcingproject;

/**
 * Created by Tomonari on 4/10/2017.
 */
import org.junit.Test;
import org.junit.Before;
import org.junit.Assert;
import org.junit.Assert.*;
import org.junit.rules.Timeout;

import java.util.ArrayList;
import java.util.List;
import android.widget.EditText;
import android.content.Context;
import edu.gatech.group16.watersourcingproject.controller.NewWaterSourceReport;

public class TomonariTest {
    @SuppressWarnings({"unused", "JavaDoc"})
    @Test
    public void testValidCoordinate() throws Exception {
        Assert.assertTrue(NewWaterSourceReport.validCoordinate("45", "45"));
    }

    @SuppressWarnings({"unused", "JavaDoc"})
    @Test
    public void testInvalidCoordinate() throws Exception {
        Assert.assertFalse(NewWaterSourceReport.validCoordinate("50#", "50#"));
    }

    @SuppressWarnings({"unused", "JavaDoc"})
    @Test
    public void testOutOfBoundCoordinate() throws Exception {
        Assert.assertFalse(NewWaterSourceReport.validCoordinate("181", "181"));
    }

    @SuppressWarnings({"unused", "JavaDoc"})
    @Test
    public void testNullCoordinate() throws Exception {
        Assert.assertFalse(NewWaterSourceReport.validCoordinate("", ""));
    }
}

