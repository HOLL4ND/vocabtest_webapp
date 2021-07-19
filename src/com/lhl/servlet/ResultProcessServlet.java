package com.lhl.servlet;

import com.lhl.pojo.StaticPara;
import com.lhl.pojo.WordPojo;
import com.lhl.utils.StepTwoUtil;
import com.lhl.utils.VocabResultUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/proResult")
public class ResultProcessServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher view;
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action.equals(StaticPara.fa1_stepOneResultSubmit)) {
            request.getSession().setAttribute("isBackValue", 1);

            // 输出结果
            String stepOneResultList = request.getParameter(StaticPara.f1_stepOneResult);
            System.out.print("From ResultProcessServlet:");
            System.out.println(stepOneResultList);
            request.getSession().setAttribute(StaticPara.sA1_stepOneResult, stepOneResultList);

            // 往session中设置 `单词列表` 和 `用户结果`
            ArrayList<WordPojo> step1wordList = (ArrayList<WordPojo>) request.getSession().getAttribute(StaticPara.sA1_stepOneWordList);

            ArrayList<Integer> stepOneWOrdIntList = (ArrayList<Integer>) request.getSession().getAttribute(StaticPara.sA1_stepOneWordIntList);

            ArrayList<WordPojo> stepTwoWL = createStepTwoWordList(stepOneWOrdIntList, stepOneResultList);
            request.getSession().setAttribute(StaticPara.sA2_stepTwoSendWordList, stepTwoWL);

            RequestDispatcher view;
            view = request.getRequestDispatcher("steptwotest.jsp");
            view.forward(request, response);
        }

        if (action.equals(StaticPara.fa2_stepTwoResultSubmitButton)) {
            System.out.println("@ResultProcessServlet: step two submit!");

            ArrayList<WordPojo> step1wordList = (ArrayList<WordPojo>) request.getSession().getAttribute(StaticPara.sA1_stepOneWordList);
            String stepOneResultList = (String) request.getSession().getAttribute(StaticPara.sA1_stepOneResult);

            ArrayList<WordPojo> step2wordList = (ArrayList<WordPojo>) request.getSession().getAttribute(StaticPara.sA2_stepTwoSendWordList);
            String stepTwoResult = request.getParameter(StaticPara.fa2_stepTwoResultString);
            System.out.println("@ResultProcessServlet: word2length"+stepTwoResult.length());
            System.out.println("@ResultProcessServlet: step2result"+stepTwoResult);
            System.out.println("@ResultProcessServlet: step2result.get(0)"+step2wordList.get(0).getWord());

            // 处理这个结果的函数
            int userVocab = VocabResultUtil.getUserVocab(step1wordList, step2wordList, stepOneResultList, stepTwoResult);
            // 得到处理的结果

            System.out.println("@ResultProcessServlet: vocab Result " + userVocab);
            request.getSession().setAttribute("userVocab", String.valueOf(userVocab));
//            request.getSession().setAttribute("stepTwoResult",stepTwoResult);
            RequestDispatcher view;
            view = request.getRequestDispatcher("showre.jsp");
            view.forward(request, response);

        }

        // from 'steptwotest.jsp' back to `steponetest.jsp`
        if (action.equals(StaticPara.fa2_BackStepOne)) {
            RequestDispatcher view;
            view = request.getRequestDispatcher("steponetest.jsp");
            view.forward(request, response);
        }
    }


    private ArrayList<WordPojo> createStepTwoWordList(ArrayList<Integer> stepOneWordIntList, String StepOneResult) {
        ArrayList<Integer> stepTwoIntList = StepTwoUtil.getStepTwoWordIndex(stepOneWordIntList, StepOneResult);
        ArrayList<WordPojo> stepTwoWordList = new ArrayList<>();
        for (Integer i : stepTwoIntList) {
            stepTwoWordList.add(InitServlet.wholeWordList.get(i));
        }
        return stepTwoWordList;
    }

}
