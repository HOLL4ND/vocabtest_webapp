package com.lhl.pojo;

public class WordPojo implements Comparable{
    int rank;
    String word;
    long frequency;
    boolean isUserKnow = false;

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public long getFrequency() {
        return frequency;
    }

    public void setFrequency(long frequency) {
        this.frequency = frequency;
    }

    public boolean isUserKnow() {
        return isUserKnow;
    }

    public void setUserKnow(boolean userKnow) {
        isUserKnow = userKnow;
    }

    public int compareTo(Object o) {
        // TODO Auto-generated method stub
        WordPojo word = (WordPojo)o;
        if (this.rank>=word.rank) {
            return 1;
        }
        return -1;
    }
}
