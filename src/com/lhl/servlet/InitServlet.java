package com.lhl.servlet;

import com.lhl.dao.WordDao;
import com.lhl.pojo.WordPojo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.util.ArrayList;

@WebServlet(
        urlPatterns = {"/InitServlet"},
        name = "InitServlet",
        loadOnStartup = 0
)
public class InitServlet extends HttpServlet {
    public static ArrayList<Integer> randInt;
    public static ArrayList<WordPojo> wholeWordList;
    public static String usingTable;
    public ArrayList<String> colName = new ArrayList<>();

    public void init() throws ServletException {
        // TODO Auto-generated method stub
        super.init();
        usingTable = "coca37989";
        colName.add("rank");
        colName.add("word");
        colName.add("total");
        wholeWordList = WordDao.selectColumnsInTable(colName, usingTable);
        System.out.println("自动加载启动.");
        System.out.println(wholeWordList.size());
        System.out.println();
    }
}
