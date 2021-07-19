<%--
  Created by IntelliJ IDEA.
  User: 75768
  Date: 2021/6/24
  Time: 16:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.lhl.Bean.WordBean" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.lhl.pojo.StaticPara" %>

<html>
<head>
    <title>Title</title>
</head>
<style type="text/css">
    .dcenter {
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
    }

    button {
        border: none;
        width: 200px;
        height: 70px;
        margin-bottom: 4px;
        background: #d3d7d4;
        color: #000000;
    }

    button:hover {
        background-color: #a1a3a6
    }

    button:focus {
        background-color: #74787c
    }
</style>
<body>


<div class="dcenter">
    <form action="/vocabtest/getWordListServlet" method="post">
        <button type="submit" name="action" value="<%=StaticPara.startPageStepOneGetWl%>">START</button>
    </form>
</div>


</body>
</html>
