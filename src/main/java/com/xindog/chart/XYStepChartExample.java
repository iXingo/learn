package com.xindog.chart;

/**
 * Created by Xindog.com(TM).
 * Author:  Shawn.Wang / i.am@shawn.wang
 * Date:    6/17/20
 * Time:    11:14 AM
 * Project: learn
 */

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;

/**
 * @author imssbora
 */
public class XYStepChartExample extends JFrame {

    private static final long serialVersionUID = 1L;

    public XYStepChartExample(String title) {
        super(title);
        // Create dataset
        IntervalXYDataset dataset = createDataset();
        // Create chart
        JFreeChart chart = ChartFactory.createXYBarChart(
                "XY Step Chart ", // Chart title
                "X-Axis", // X-Axis Label
                false,
                "Y-Axis", // Y-Axis Label
                dataset
        );

        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(new Color(229, 150, 97, 60));

        ChartPanel panel = new ChartPanel(chart);
        setContentPane(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            XYStepChartExample example = new XYStepChartExample("XY Step Chart Example");
            example.setSize(800, 400);
//            example.setLocationRelativeTo(null);
            example.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            example.setVisible(true);
        });
    }

    private IntervalXYDataset createDataset() {
        XYSeriesCollection dataset = new XYSeriesCollection();

        XYSeries series1 = new XYSeries("Series1");
        XYSeries series2 = new XYSeries("Series2");
        XYSeries series3 = new XYSeries("Series3");


        series1.add(1, 4);
        series2.add(1, 6);
        series3.add(1, 7);

        series1.add(2, 2);
        series2.add(2, 9);
        series3.add(2, 5);

        series1.add(3, 29);
        series2.add(3, 8);
        series3.add(3, 6);

        series1.add(4, 9);
        series2.add(4, 11);
        series3.add(4, 18);

        series1.add(5, 6);
        series2.add(5, 5);
        series3.add(5, 9);

        series1.add(6, 11);
        series2.add(6, 18);
        series3.add(6, 18);

        // Add series to dataset
        dataset.addSeries(series1);
        dataset.addSeries(series2);
        dataset.addSeries(series3);
        return dataset;
    }
}
