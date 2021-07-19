package com.lhl.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class StepTwoUtil {

    public static ArrayList<Integer> randomIntListExceptGivenInt(int exceptInt, int startInt, int endInt, int listSize) {
        ArrayList<Integer> randIntList = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < listSize; i++) {
            int randNum = r.nextInt(endInt - startInt + 1) + startInt;
            while (randNum == exceptInt) {
                randNum = r.nextInt(endInt - startInt + 1) + startInt;
            }
            randIntList.add(randNum);
        }
        return randIntList;
    }

    public static ArrayList<Integer> randomIntListExceptGivenIntList(ArrayList<Integer> exceptInt, int startInt, int endInt, int listSize) {
        ArrayList<Integer> randIntList = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < listSize; i++) {
            int randNum = r.nextInt(endInt - startInt + 1) + startInt;
            while (exceptInt.contains(randNum)) {
                randNum = r.nextInt(endInt - startInt + 1) + startInt;
            }
            randIntList.add(randNum);
        }
        return randIntList;
    }

    public static ArrayList<Integer> stepTwoWordIndex(HashMap<String, Integer> stepTwoRangeInfo, ArrayList<Integer> stepOneWordIntList) {

        ArrayList<Integer> stepTwoWordIndex = new ArrayList<>();
        int start = stepTwoRangeInfo.get("start");
        int end = stepTwoRangeInfo.get("end");
        int margin = end - start + 1;
        int step2num = -1;
        int stepTwoWordNum = 0;
        if (margin <= 100) {
            stepTwoWordNum = 20;
        } else if (margin <= 1000) {
            stepTwoWordNum = 50;
        } else if (margin <= 5000) {
            stepTwoWordNum = 100;
        } else {
            stepTwoWordNum = 120;
        }

        step2num = margin / stepTwoWordNum;
        int lastRange = margin % step2num;
        int nowStart = start;

        for (int i = 0; i < stepTwoWordNum; i++) {
            int randNum = randomIntListExceptGivenIntList(stepOneWordIntList, nowStart, nowStart + step2num - 1, 1).get(0);
            stepTwoWordIndex.add(randNum);
            nowStart += step2num;
        }
        if (lastRange != 0) {
            int randNum = randomIntListExceptGivenIntList(stepOneWordIntList, nowStart, end, 1).get(0);
            stepTwoWordIndex.add(randNum);
        }
        return stepTwoWordIndex;
    }
    public static ArrayList<Integer> getStepTwoWordIndex(ArrayList<Integer> stepOneWordIntList,String StepOneResult){
        HashMap<String, Integer>twoRange = VocabResultUtil.getStepTwoRange(StepOneResult);
        return stepTwoWordIndex(twoRange,stepOneWordIntList);
    }


    public static void main(String[] args) {
    }
}
