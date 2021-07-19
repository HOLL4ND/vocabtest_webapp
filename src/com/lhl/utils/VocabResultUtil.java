package com.lhl.utils;

import com.lhl.pojo.WordPojo;
import com.lhl.servlet.InitServlet;
import com.lhl.utils.MyStringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class VocabResultUtil {

    public static ArrayList<Integer> getEachBlockStrCount(String userResult, int[] blocks, String countStr) {
        ArrayList<Integer> countResult = new ArrayList<>();
        int blockStart = 0;
        for (int i = 0; i < blocks.length; i++) {
            String subStr = userResult.substring(blockStart, blockStart + blocks[i]);
            countResult.add(MyStringUtil.strInStringCount(subStr, countStr));
            blockStart += blocks[i];
        }
        return countResult;
    }

    public static ArrayList<String> isEachRangeReachCriterion(ArrayList<Integer> eachRangeResult, int[] eachRangeCriterion) {
        ArrayList<String> verifyResult = new ArrayList<>();
        if (eachRangeResult.size() != eachRangeCriterion.length) {
            System.out.println("error in function : isEachRangeReachCriterion() Criterion and result doesn't match");
            return null;
        }
        for (int i = 0; i < eachRangeCriterion.length; i++) {
            if (eachRangeResult.get(i) >= eachRangeCriterion[i]) {
                verifyResult.add("Y");//提交且达标的区间
            } else if (eachRangeResult.get(i) != 0) {
                verifyResult.add("N");//提交但未达标区间
            } else {
                verifyResult.add("O");//该区间未提交
            }
        }
        return verifyResult;
    }


    public static HashMap<String, Integer> generateStepTwoRange(String stepOneMatchingResult, String userResult) {
        HashMap<String, Integer> stepTwoRange = new HashMap<>();

        String startStr = "start";
        String endStr = "end";

        int start = 1;//`rank`的值从1开始
        int end = 0;

        // 如果第一步结果为空 则范围选取为=前两个范围
        if (!stepOneMatchingResult.contains("Y") && !stepOneMatchingResult.contains("N")) {
            stepTwoRange.put(startStr, 1);
            for (int i = 0; i < 2; i++) {
                end += StepOneUtil.eachRangeSize[i] * StepOneUtil.eachRangeNum[i];
            }
            stepTwoRange.put(startStr, start);
            stepTwoRange.put(endStr, end);
            return stepTwoRange;

        }

        // 如果有未达标,没有达标区间,则范围选取为=第一个N区间的前一个区间~最后一个N区间的下一个区间
        if (!stepOneMatchingResult.contains("Y") && stepOneMatchingResult.contains("N")) {
            int startRange = stepOneMatchingResult.indexOf("N");
            int endRange = stepOneMatchingResult.lastIndexOf("N");

            if (startRange != 0) {
                startRange -= 1;
            }
            if (endRange < StepOneUtil.eachRangeNum.length - 2) {
                endRange += 1;
            }
            for (int i = 0; i <= endRange; i++) {
                end += StepOneUtil.eachRangeSize[i] * StepOneUtil.eachRangeNum[i];
            }
            for (int i = 0; i < startRange; i++) {
                start = start + StepOneUtil.eachRangeSize[i] * StepOneUtil.eachRangeNum[i];
            }
            stepTwoRange.put(startStr, start);
            stepTwoRange.put(endStr, end);
            return stepTwoRange;
        }
        if (stepOneMatchingResult.contains("Y")) {
            int startRange = stepOneMatchingResult.indexOf("Y");
            int endRange = stepOneMatchingResult.lastIndexOf("N");
            int yend = stepOneMatchingResult.lastIndexOf("Y");
            endRange = Math.max(endRange, yend);

            if (endRange == 2) {
                endRange += 1;
            }

            for (int i = startRange; i < stepOneMatchingResult.length() - 1; i++) {
                if (String.valueOf(stepOneMatchingResult.charAt(i)).equals("Y")) {
                    if (String.valueOf(stepOneMatchingResult.charAt(i + 1)).equals("Y")) {
                        startRange = i + 1;
                    } else {
                        break;
                    }
                }
            }
            if (startRange != 0) {
                startRange -= 1;
            }
            if (endRange < StepOneUtil.eachRangeNum.length - 2) {
                endRange += 1;
            }
            for (int i = 0; i < startRange; i++) {
                start = start + StepOneUtil.eachRangeSize[i] * StepOneUtil.eachRangeNum[i];
            }
            for (int i = 0; i <= endRange; i++) {
                end += StepOneUtil.eachRangeSize[i] * StepOneUtil.eachRangeNum[i];
            }
            stepTwoRange.put(startStr, start);
            stepTwoRange.put(endStr, end);
            return stepTwoRange;
        }
        return stepTwoRange;
    }


    public static HashMap<String, Integer> getStepTwoRange(String userResult) {
        HashMap<String, Integer> stepTwoRange = new HashMap<>();
        ArrayList<Integer> countResult = getEachBlockStrCount(userResult, StepOneUtil.eachRangeNum, "1");
        ArrayList<String> strResult = isEachRangeReachCriterion(countResult, StepOneUtil.eachRangeCriterion);
        String resultStr = String.join("", strResult);
        stepTwoRange = generateStepTwoRange(resultStr, userResult);
        return stepTwoRange;
    }


    public static int getIndexOfTopReachCriterionWord(String step1result) {
        int topIndex = -1;
        int nowStart = 0;
        for (int i = 0; i < StepOneUtil.eachRangeNum.length; i++) {
            String subStr = step1result.substring(nowStart, nowStart + StepOneUtil.eachRangeNum[i]);
//            System.out.println(subStr);
//            System.out.println(MyStringUtil.strInStringCount(subStr, "1") + ":" + StepOneUtil.eachRangeCriterion[i]);
            if (MyStringUtil.strInStringCount(subStr, "1") >= StepOneUtil.eachRangeCriterion[i]) {
                topIndex = nowStart + subStr.lastIndexOf("1");
            }
            nowStart += StepOneUtil.eachRangeNum[i];
        }
        System.out.println(topIndex);
        return topIndex;
    }


    public static int getUserVocab(ArrayList<WordPojo> step1wl, ArrayList<WordPojo> step2wl, String step1result, String step2result) {
        int stepOneTop = getIndexOfTopReachCriterionWord(step1result);
        WordPojo step1ResultWord = step1wl.get(stepOneTop);
        System.out.println("@VocabResult: getVocab step 1 judge :" + step1ResultWord.getWord());

        int topRankInStep1 = step1ResultWord.getRank();
        int topRankInStep2 = topRankInStep1;

        int userVocab = 0;
        int resultComputeNum = 0;
        int totalTableWord = InitServlet.wholeWordList.size();

        if (step2result.contains("1")) {
            topRankInStep2 = step2wl.get(step2result.lastIndexOf("1")).getRank();

            for (int i = 0; i < step1result.length(); i++) {
                String ch = String.valueOf(step1result.charAt(i));
                if (ch.equals("1")) {
                    step1wl.get(i).setUserKnow(true);
                }
            }
            for (int i = 0; i < step2result.length(); i++) {
                String ch = String.valueOf(step2result.charAt(i));
                if (ch.equals("1")) {
                    step2wl.get(i).setUserKnow(true);
                }
            }

            ArrayList<WordPojo> totalList = new ArrayList<>();
            totalList.addAll(step1wl);
            totalList.addAll(step2wl);

            Collections.sort(totalList);

            StringBuilder concatResult = new StringBuilder();
            for (int i = 0; i < totalList.size(); i++) {
//                System.out.println("@VocabResult: " + (i + 1) +"  "+ totalList.get(i).getWord() + "-" + totalList.get(i).getRank());
//                if ((i + 1) % 10 == 0) {
//                    System.out.println("");
//                }
                if (totalList.get(i).isUserKnow()) {
                    concatResult.append("1");
                } else {
                    concatResult.append("0");
                }

            }

            System.out.println("@VocabResult: " + concatResult);

            int exactresult = 0;
            int approachresult = 0;
            int smallestMargin = 32767;
            boolean isFindExact = false;
            boolean isFindSmallMargin = false;

            int point = MyStringUtil.strInStringCount(String.valueOf(concatResult), "1");
            System.out.println("@VocabResult: " + point);
            System.out.println("@VocabResult: " + concatResult.length());
            topRankInStep2 = totalList.get(point).getRank();
            System.out.println(totalList.get(point).getRank());
            System.out.println(totalList.get(point).getWord());
            userVocab = topRankInStep2;
        } else {
            userVocab = step1ResultWord.getRank();
        }
        return userVocab;
    }

    public static void main(String[] args) {

    }
}
