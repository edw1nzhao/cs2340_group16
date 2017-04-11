package edu.gatech.group16.watersourcingproject;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import edu.gatech.group16.watersourcingproject.controller.HistoricalReportActivity;

/**
 * Created by Tomonari on 4/10/2017.
 */

public class EdwinTest {
    @SuppressWarnings({"unused", "JavaDoc"})
    @Test
    public void testCalculateAverageOne() throws Exception {
        List<Integer> integerList = new ArrayList<>();
        integerList.add(200);
        integerList.add(200);
        integerList.add(200);
        Assert.assertEquals(200.0, .01, HistoricalReportActivity.calculateAverage(integerList));
    }

    @SuppressWarnings({"unused", "JavaDoc"})
    @Test
    public void testCalculateAverageTwo() throws Exception {
        List<Integer> integerList = new ArrayList<>();
        integerList.add(100);
        integerList.add(200);
        integerList.add(300);
        Assert.assertNotEquals(99.0, HistoricalReportActivity.calculateAverage(integerList), .001);
    }

    @SuppressWarnings({"unused", "JavaDoc"})
    @Test
    public void testCalculateAverageNullInput() throws Exception {
        List<Integer> integerList = null;
        Assert.assertEquals(0, HistoricalReportActivity.calculateAverage(integerList), .001);
    }
}
