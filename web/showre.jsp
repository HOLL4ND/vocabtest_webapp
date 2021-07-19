<%--
  Created by IntelliJ IDEA.
  User: 75768
  Date: 2021/6/24
  Time: 20:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Test Result</title>
    <link rel="stylesheet" href="css/style.css" media="screen" type="text/css"/>

</head>
<body>
<canvas id="text" width="500" height="100"></canvas>
<canvas id="stage" width="500" height="100"></canvas>

<%
    String result = (String) session.getAttribute("userVocab");
//    result = new String((request.getParameter("result")).getBytes("ISO-8859-1"), "UTF-8");

%>
<input id="jsget" type="hidden" value="<%=result%>"/>

<form id="form">
    <input type="hidden" id="inputText" value=""/>
    <input type="text" id="test" value="您的词汇量估计为" disabled>
</form>

<script src='js/EasePack.min.js'></script>
<script src='js/TweenLite.min.js'></script>
<script src='js/easeljs-0.7.1.min.js'></script>
<script src='js/requestAnimationFrame.js'></script>
<script src="js/index.js"></script>

</body>
</html>
