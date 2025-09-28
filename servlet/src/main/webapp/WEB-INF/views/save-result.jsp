<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
</head>
<body>
성공
<ul>
    <!-- request.getAttribute() 를 사용해서 데이터를 꺼내면 되지만,  jsp가 제공하는 $ {}문법을 통해서 request(model)에 담긴 데이터를 편리하게 조회 할 수 있음!-->
    <li>id=${member.id}</li>
    <li>username=${member.username}</li>
    <li>age=${member.age}</li>
</ul>
<a href="/index.html">메인</a>
</body>
</html>