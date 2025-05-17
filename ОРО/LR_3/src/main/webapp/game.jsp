<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Угадай число</title>

</head>
<body>
<div class="container">
    <h1>Вы загадали число <%= session.getAttribute("guess") %>?</h1>
    <form action="guess" method="post">
        <div class="game-options">
            <button type="submit" name="answer" value="greater" class="game-btn">
                Нет, число больше
            </button>
            <button type="submit" name="answer" value="less" class="game-btn">
                Нет, число меньше
            </button>
            <button type="submit" name="answer" value="equals" class="game-btn">
                Ты угадал!
            </button>
        </div>
    </form>
</div>
</body>
</html>