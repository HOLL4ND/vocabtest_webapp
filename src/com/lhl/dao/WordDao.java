package com.lhl.dao;

import com.lhl.pojo.WordPojo;
import com.lhl.utils.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WordDao {
    public static int getTableRows(String table) {
        System.out.println("Function called (WordDao.getTableRows)");
        int rows = 0;
        String sql = "select count(*) from " + table;
        Connection conn = null;
        PreparedStatement pstmt = null;
        conn = JdbcUtil.getConnection();
        assert conn != null;
        try {
            pstmt = conn.prepareStatement(sql);
            ResultSet re = pstmt.executeQuery();
            while (re.next()) {
                rows = re.getInt(1);
            }
            return rows;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JdbcUtil.close(null, pstmt, conn);
        }
        return rows;
    }

    public static ArrayList<WordPojo> selectByWord(String word, String table) {
        System.out.println("Function called (WordDao.selectByWord)");
        ArrayList<WordPojo> resultList = new ArrayList<>();
        String sql = "select * from " + table + " where word = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            //得到链接
            conn = JdbcUtil.getConnection();
            //stmt = conn.createStatement();
            assert conn != null;
            pstmt = conn.prepareStatement(sql);

            //设置预编译语句的数据
            pstmt.setString(1, word);

            ResultSet re = pstmt.executeQuery();
            while (re.next()) {
                WordPojo resultWord = new WordPojo();
                resultWord.setWord(word);
                resultWord.setRank(re.getInt("rank"));
                resultWord.setFrequency(re.getInt("total"));
                resultList.add(resultWord);
            }
            return resultList;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.close(null, pstmt, conn);
        }
        return resultList;
    }

    public static ArrayList<WordPojo> selectColumnsInTable(ArrayList<String> colName, String table) {
        System.out.println("Function called (WordDao.selectColumnsInTable)");
        ArrayList<WordPojo> resultList = new ArrayList<>();
        String selectColumn = "`" + String.join("`,`", colName) + "`";
        String sql = "select "+selectColumn+" from " + table;
        System.out.println(sql);

        Connection conn=null;
        PreparedStatement pstmt =null;

        conn = JdbcUtil.getConnection();
        assert conn != null;

        try {
            pstmt = conn.prepareStatement(sql);

            ResultSet re = pstmt.executeQuery();
            while(re.next()) {
                WordPojo resultWord = new WordPojo();
                resultWord.setRank(re.getInt("rank"));
                resultWord.setWord(re.getString("word"));
                resultWord.setFrequency(re.getInt("total"));
                resultList.add(resultWord);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JdbcUtil.close(null,pstmt,conn);
        }
        return resultList;
    }


    public static void main(String[] args) {
//        ArrayList<WordPojo> searchResult = new ArrayList<WordPojo>();
//        searchResult = selectByWord("only", "coca60000");
//        if (searchResult.size() != 0) {
//            for (WordPojo word : searchResult) {
//                System.out.printf("%-16s", word.getWord());
//                System.out.printf("%-8s", word.getRank());
//                System.out.println("");
//            }
//        System.out.println(getTableRows("coca20000"));
//        ArrayList<String> colName = new ArrayList<>();
//        colName.add("hello");
//        colName.add("world");
//        System.out.println(String.join("`,`", colName));
//        selectColumnsInTable(colName,"coca20000");


    }


}
