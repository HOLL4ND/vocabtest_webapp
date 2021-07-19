package com.lhl.utils;

import java.util.ArrayList;
import java.util.Random;

public class StepOneUtil {

    public static int[] eachRangeSize = {52, 12, 20, 100, 250, 500, 1000, 1500, 2000, 2598, 2607};// 每个区间的大小
    public static int[] eachRangeNum = {1, 4, 5, 8, 4, 2, 2, 2, 2, 9, 1};// 每个区间的数量
    public static int[] eachRangeCriterion = {1, 3, 4, 5, 3, 2, 1, 2, 2, 6, 1};// 每个区间判断为认识的标准

    public static void checkRangeSetting() {
        int totalTemp = eachRangeSize.length + eachRangeNum.length + eachRangeCriterion.length;
        if (totalTemp % 3 != 0) {
            System.out.println("@ error: setting not right");
            System.out.println("eachRangeSize.length:     "+eachRangeSize.length);
            System.out.println("eachRangeNum.length:      "+eachRangeNum.length);
            System.out.println("eachRangeCriterion.length:"+eachRangeCriterion.length);
        }
        else {
            System.out.println("@ NoProblem: setting right");
            System.out.println("eachRangeSize.length:      "+eachRangeSize.length);
            System.out.println("eachRangeNum.length:       "+eachRangeNum.length);
            System.out.println("eachRangeCriterion.length: "+eachRangeCriterion.length);

        }
    }

    public static ArrayList<Integer> stepOneWordListRandomIndex() {
        ArrayList<Integer> randResult = new ArrayList<>();
        Random r = new Random();
        int start = 1;
        for (int rgNumIndex = 0; rgNumIndex < eachRangeNum.length; rgNumIndex++) {
            for (int i = 0; i < eachRangeNum[rgNumIndex]; i++) {
                int range = eachRangeSize[rgNumIndex];
                int randNum = r.nextInt(range)+start;
                randResult.add(randNum);
                start += eachRangeSize[rgNumIndex];
            }
        }
        return randResult;
    }

    public static void main(String[] args) {

    }
}
