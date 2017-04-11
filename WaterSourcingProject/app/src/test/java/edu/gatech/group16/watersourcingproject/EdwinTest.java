package edu.gatech.group16.watersourcingproject;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import edu.gatech.group16.watersourcingproject.controller.HistoricalReportActivity;

/**
 * Created by Edwin Zhao on 4/10/2017.
 */

public class EdwinTest {
    @SuppressWarnings({"unused", "JavaDoc"})
    @Test
    public void testCalculateAverageOne() throws Exception {
        List<Integer> integerList = new ArrayList<>();
        //noinspection MagicNumber
        integerList.add(200);
        //noinspection MagicNumber
        integerList.add(200);
        //noinspection MagicNumber
        integerList.add(200);
        //noinspection MagicNumber,MagicNumber
        Assert.assertEquals(200.0, .01, HistoricalReportActivity.calculateAverage(integerList));
    }

    @SuppressWarnings({"unused", "JavaDoc"})
    @Test
    public void testCalculateAverageTwo() throws Exception {
        List<Integer> integerList = new ArrayList<>();
        integerList.add(100);
        //noinspection MagicNumber
        integerList.add(200);
        //noinspection MagicNumber
        integerList.add(300);
        //noinspection MagicNumber,MagicNumber
        Assert.assertNotEquals(99.0, HistoricalReportActivity.calculateAverage(integerList), .001);
    }

    @SuppressWarnings({"unused", "JavaDoc"})
    @Test
    public void testCalculateAverageNullInput() throws Exception {
        List<Integer> integerList = null;
        //noinspection MagicNumber
        Assert.assertEquals(0, HistoricalReportActivity.calculateAverage(null), .001);
    }
}
