<%--
  Created by IntelliJ IDEA.
  User: 75768
  Date: 2021/6/24
  Time: 14:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.io.*,java.util.*" %>
<%@ page import="com.lhl.servlet.InitServlet" %>
<%@ page import="com.lhl.pojo.WordPojo" %>
<%@ page import="com.lhl.pojo.StaticPara" %>
<%@ page import="com.lhl.utils.StepOneUtil" %>

<%

    ArrayList<WordPojo> wordList = new ArrayList<>();
    String stepOneResult = String.join("", Collections.nCopies(40, "0"));

    // 是否为返回页面----------------------------------------------------------------
    int isBack;
    if (session.getAttribute("isBackValue") == null) {
        isBack = 0;
        // 获取单词列表------------------------------------------------------------------
        ArrayList<Integer> intList = StepOneUtil.stepOneWordListRandomIndex();
        for (Integer i : intList) {
            wordList.add(InitServlet.wholeWordList.get(i));
        }
        session.setAttribute(StaticPara.sA1_stepOneWordList, wordList);
        session.setAttribute(StaticPara.sA1_stepOneWordIntList,intList);
        // 获取单词列表------------------------------------------------------------------
    } else {
        isBack = 1;
        if (session.getAttribute(StaticPara.sA1_stepOneWordList) != null) {
            wordList = (ArrayList<WordPojo>) session.getAttribute(StaticPara.sA1_stepOneWordList);
        }
        if (session.getAttribute(StaticPara.sA1_stepOneResult) != null) {
            stepOneResult = (String) session.getAttribute(StaticPara.sA1_stepOneResult);
        }
    }
    session.removeAttribute("isBackValue");// 移除属性等待跳转后再设置
    session.removeAttribute(StaticPara.sA2_stepTwoSendWordList);
    // 是否为返回页面----------------------------------------------------------------
%>

<html>
<head>
    <title>STEP ONE TEST</title>
    <title>Document</title>
    <style>
        button {
            border: none;
            width: 220px;
            height: 75px;
            margin-bottom: 4px;
            font-size: 20px;
            font-family: Georgia, 'Times New Roman', Times, serif;
            background: #d3d7d4;
            color: #000000;
        }

        button:hover {
            background-color: #a1a3a6 !important;
        }
    </style>
    <script type="text/javascript">
        window.onload = function () {

            var Button = document.getElementsByClassName("buttonWord");
            var StepOneWordResult = document.getElementById("StepOneWordResult");
            var buttonState = []; // 用于保存按钮状态的数组
            var wordListResult = [];// 用于保存单词结果的数组

            var stepOneResult = "<%=stepOneResult%>";


            // 初始化wordListResult和buttonState
            for (i = 0; i < stepOneResult.length; i++) {
                var resultOfOne = stepOneResult.charAt(i)
                wordListResult[i] = resultOfOne;
                buttonState[i] = "0";
                if (resultOfOne == "1") {
                    setButtonByState(i + 1, "0");
                    buttonState[i] = "1";
                }
            }
            console.log("wordlistResult:")
            console.log(wordListResult.join(""));
            console.log(buttonState.join(""));

            for (i = 0; i < Button.length; i++) {
                // buttonState[i] = 0;
                Button[i].onclick = function () {
                    console.log(this.id)

                    // 用于访问状态数组的下标
                    var indexValue = this.value - 1;//因为按钮的value从1开始

                    wordListResult[indexValue] = reverseState(wordListResult[indexValue]);

                    setButtonByState(this.value, buttonState[indexValue]);
                    buttonState[indexValue] = reverseState(buttonState[indexValue]);

                    var stepOneResult = wordListResult.join("")
                    console.log(stepOneResult)

                    // 结果写入hidden input 的 value 用于提交
                    StepOneWordResult.value = stepOneResult;
                    // console.log(StepOneWordResult.value);

                    // 用于在下方的input中显示当前的结果
                    var showResult = document.getElementById("resultOnShow");
                    showResult.value = stepOneResult;

                }
            }
        }


        function testButton() {
            console.log("test button click!")
            var lastResult = document.getElementById("resultSave");
            var resultOut = document.getElementById("StepOneWordResult");
            lastResult.value = "1";
        }

        function setButtonByState(id, state) {
            var idStr = "word-" + id;
            var button = document.getElementById(idStr);
            if (state == "0") {
                //选择后的颜色
                button.style.backgroundColor = "#74787c";
            } else if (state == "1") {
                //取消选择后的颜色(初始化的颜色)
                button.style.backgroundColor = "#d3d7d4";
            }
        }

        function reverseState(content) {
            return content == "0" ? "1" : "0";
        }

    </script>
</head>
<body>
<div align="center">
    <%
        int wordnum = 1;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 4; j++) {
                int index = (wordnum) + (j * 10);
                String vocab = wordList.get(index - 1).getWord().toLowerCase();
                int wordRank = wordList.get(index - 1).getRank();
                out.println("<button class=\"buttonWord\" value= " + index + " id=\"word-" + index + "\">" + vocab + "</button>");
            }
            wordnum++;
            out.println("<br>");
        }
    %>
</div>
<br>
<form action="/vocabtest/proResult" method="post">
    <input type="hidden" name="<%=StaticPara.f1_stepOneResult%>" id="StepOneWordResult" value="<%=stepOneResult%>"/>
    <input type="hidden" id="resultSave" value="0"/>
    <div align="center">
        <button type="submit" name="action" value="<%=StaticPara.fa1_stepOneResultSubmit%>">S U B M I T</button>
    </div>
</form>

<div align="center">
    <input type="text" id="resultOnShow" value="<%=session.getId()%>" style="width: 320px" disabled>
</div>
<br>

</body>
</html>