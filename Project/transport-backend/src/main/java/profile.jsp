<%@page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="css/profile.css">
    <title>Профиль</title>
</head>
<body>
<div class="header">
    <div class="container box-flex">
        <a class="logo" href="service">Транспортные услуги</a>
        <% if (request.getSession(false).getAttribute("login") != null) {
        request.setAttribute("isAuthorized", 1);
        } %>
    </div>
</div>
<div class="container flex-1">
    <% if (request.getSession(false).getAttribute("login") != null) {
    request.setAttribute("isAuthorized", 1);
    }
    if (request.getParameter("name") != null) {
    request.setAttribute("name", request.getParameter("name"));
    }
    if (request.getParameter("email") != null) {
    request.setAttribute("email", request.getParameter("email"));
    }
    if (request.getParameter("phone") != null) {
    request.setAttribute("phone", request.getParameter("phone"));
    }
    if (request.getParameter("address") != null) {
    request.setAttribute("address", request.getParameter("address"));
    }%>
    <c:set var="isAuthorized" scope="request" value="${isAuthorized}"/>
    <c:set var="name" scope="session" value="${name}"/>
    <c:set var="email" scope="session" value="${email}"/>
    <c:set var="phone" scope="session" value="${phone}"/>
    <c:set var="address" scope="session" value="${address}"/>
    <c:if test="${isAuthorized == 1}">
        <div class="client ">
            <h2>Исправить информацию</h2>
            <form id="correct" action="profile" method="post">
                <input type="text" class="placeholder" name="phone" placeholder="Телефон" value="${phone}">
                <input type="text" class="placeholder" name="email" placeholder="Почта" value="${email}">
                <input type="hidden" name="type" value="setInfo">
                <button type="submit" class="box1btn">Применить</button>
            </form>
        </div>
    </c:if>
    <c:if test="${isAuthorized != 1}">
        <div class="client">
            <h2>Регистрация</h2>
            <h3>Создайте нового пользователя</h3>
            <form id="registration" action="profile" method="post">
                <input type="text" class="placeholder" name="login" placeholder="Логин">
                <input type="password" name="password" class="placeholder" placeholder="Пароль">
                <input type="hidden" name="type" value="registration">
                <button type="submit" class="box1btn">Регистрация</button>
            </form>
        </div>
        <div class="client">
            <h2>Уже зарегистрированы?</h2>
            <h3>Войдите</h3>
            <form id="login" action="profile" method="post">
                <input type="text" class="placeholder" name="login" placeholder="Логин">
                <input type="password" name="password" class="placeholder" placeholder="Пароль">
                <input type="hidden" name="type" value="logIn">
                <button type="submit" class="box1btn">Вход</button>
            </form>
        </div>
    </c:if>
</div>
</body>
</html>