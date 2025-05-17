<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Победа!</title>
</head>
<body>
<div class="container win">
    <h1>Я угадал! Твое число <%= session.getAttribute("guess") %>!</h1>
    <a href="index.jsp">Сыграть ещё раз</a>
</div>
</body>
</html>