<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Угадай число</title>
</head>
<body>
<div class="container">
  <h1>Загадайте число</h1>
  <form action="${pageContext.request.contextPath}/guess" method="post">
    <div class="input-row">
      <label for="min">от:</label>
      <input type="number" name="min" id="min" required>
    </div>
    <div class="input-row">
      <label for="max">до:</label>
      <input type="number" name="max" id="max" required>
    </div>
    <input type="submit" value="Начать">
  </form>
</div>
</body>
</html>