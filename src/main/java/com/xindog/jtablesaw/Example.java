package com.xindog.jtablesaw;

import tech.tablesaw.api.DoubleColumn;

/**
 * Created by Xindog.com(TM).
 * Author:  Shawn.Wang / i.am@shawn.wang
 * Date:    6/29/20
 * Time:    5:36 PM
 * Project: learn
 */
public class Example {

    public static void main(String[] args) {
        double[] numbers = {1, 2, 3, 4};
        DoubleColumn nc = DoubleColumn.create("nc", numbers);
        System.out.println(nc.print());
    }
}
