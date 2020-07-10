package com.xindog.nio;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by Xindog.com(TM).
 * Author:  Shawn.Wang / i.am@shawn.wang
 * Date:    3/31/20
 * Time:    4:36 PM
 * Project: learn
 *
 * @author shawang
 */
public class PathAndDirectory {
    public static void main(String[] args) {
        ArrayList<String> files = new ArrayList<String>();
        File file = new File(args[0]);
        File[] tempList = file.listFiles();

        for (int i = 0; i < Objects.requireNonNull(tempList).length; i++) {
            if (tempList[i].isFile()) {
                System.out.println("" + tempList[i]);
                files.add(tempList[i].toString());
            }
            if (tempList[i].isDirectory()) {
                System.out.println("    " + tempList[i]);
            }
        }
        System.out.println(files);
    }
}
