<%--
  Created by IntelliJ IDEA.
  User: 75768
  Date: 2021/6/26
  Time: 20:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.lhl.pojo.WordPojo" %>
<%@ page import="java.util.*" %>
<%@ page import="com.lhl.pojo.StaticPara" %>


<%
    ArrayList<WordPojo> wordList = (ArrayList<WordPojo>) session.getAttribute(StaticPara.sA2_stepTwoSendWordList);
    String stepOneResult = (String) session.getAttribute(StaticPara.sA1_stepOneResult);
    String stepTwoResult = String.join("", Collections.nCopies(wordList.size(), "0"));
%>

<html>
<head>
    <title>Title</title>

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
            var StepTwoResultInput = document.getElementById("StepTwoResult");
            var buttonState = []; // 用于保存按钮状态的数组
            var wordListResult = [];// 用于保存单词结果的数组

            var stepTwoResult = "<%=stepTwoResult%>";

            // 初始化wordListResult和buttonState
            for (i = 0; i < stepTwoResult.length; i++) {
                wordListResult[i] = "0";
                buttonState[i] = "0";
            }

            console.log("wordListLength:"+wordListResult.length)
            console.log("stepTwoResult:"+stepTwoResult)

            for (i = 0; i < Button.length; i++) {
                // buttonState[i] = 0;
                Button[i].onclick = function () {

                    console.log("button "+this.value+" pressed")

                    // 用于访问状态数组的下标
                    var indexValue = this.value - 1;//因为按钮的value从1开始

                    wordListResult[indexValue] = reverseState(wordListResult[indexValue]);

                    setButtonByState(this.value, buttonState[indexValue]);
                    buttonState[indexValue] = reverseState(buttonState[indexValue]);

                    var stepOneResult = wordListResult.join("")
                    console.log(stepOneResult)
                    console.log(stepOneResult.length)

                    // 结果写入hidden input 的 value 用于提交
                    StepTwoResultInput.value = stepOneResult;
                    // console.log(StepOneWordResult.value);

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

<br>

<div align="center">
    <%
        int index = 1;
        for (int i = 0; i < wordList.size(); i++) {
            String word = String.valueOf(wordList.get(i).getWord().toLowerCase(Locale.ROOT));
            out.println("<button class=\"buttonWord\" value= " + index + " id=\"word-" + index + "\">" + word + "</button>");
            if ((i + 1) % 4 == 0) {
                out.print("<br>");
            }
            index++;
        }
    %>
</div>

<br>

<div align="center">
    <form action="/vocabtest/proResult" method="post">
        <input type="hidden" name="<%=StaticPara.fa2_stepTwoResultString%>" id="StepTwoResult" value="<%=stepTwoResult%>"/>
        <button type="submit" name="action" value="<%=StaticPara.fa2_BackStepOne%>">返回上一测试</button>
        <button type="submit" name="action" value="<%=StaticPara.fa2_stepTwoResultSubmitButton%>">提交答案</button>
    </form>
</div>
<br>

</body>

</html>
