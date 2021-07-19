package com.lhl.utils;

public class MyStringUtil {

    public static int strInStringCount(String strInput, String strToCount) {
        int originLength = strInput.length();
        String afterStr = "";
        afterStr = strInput.replace(strToCount, "");
        int afterLength;
        afterLength = afterStr.length();
        return originLength - afterLength;
    }


    // 从StartIndex开始找到str中连续的conStr字符后为afterStr的下标
    public static int findFirstStrAfterContinuousStr(String str, int StartIndex, String conStr, String afterStr, int direction) {
        if (StartIndex >= str.length()) {
            System.out.println("error: StartIndex >= str.length");
            return -1;
        }
        String lastStr = "";
        int strOriginLength = str.length();
        boolean isContinuous = false;
        if (direction == 1) {
            str = str.substring(StartIndex);
            for (int i = 0; i < str.length(); i++) {
                String ch = String.valueOf(str.charAt(i));
                if (ch.equals(afterStr) && isContinuous) {
                    return StartIndex + i;
                }
                isContinuous = ch.equals(lastStr) && ch.equals(conStr);
                lastStr = ch;
            }
        } else if (direction == -1) {
            str = str.substring(0, str.length() - StartIndex);
            int step = 0;
            for (int i = str.length() - 1; i >= 0; i--) {
                step++;
                String ch = String.valueOf(str.charAt(i));
                if (ch.equals(afterStr) && isContinuous) {
                    return strOriginLength - StartIndex - step;
                }
                isContinuous = ch.equals(lastStr) && ch.equals(conStr);
                lastStr = ch;
            }
        }
        return -1;
    }
}

