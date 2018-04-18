<%@page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@page import="common.Customer"%>
<%@page import="common.Order"%>
<%@page import="common.Service"%>
<%@page import="java.util.ArrayList"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="css/deal.css">
    <link rel="stylesheet" href="js/main.js">
    <title>Заказ</title>
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
<div class="container flexand">
    <div class="one">
        <div class="spoiler">
            <input type="checkbox" id="spoilerid_1">
            <label for="spoilerid_1">Меню</label>
            <div class="spoiler_body">
                <ul>
                    <li>
                        <form method="get" action="profile">
                            <button class="box1btn" type="submit">Пользователь</button>
                        </form>
                    </li>
                    <li>
                        <form method="get" action="service">
                            <button class="box1btn" type="submit">Услуги</button>
                        </form>
                    </li>
                </ul>
            </div>
        </div>
    </div>
    <div class="two">
        <div id="left">
            <table class="simple-little-table" cellspacing='0'>
                <tr>
                    <th>id</th>
                    <th>Название услуги</th>
                    <th>Стоимость</th>
                    <th>Категория</th>
                    <th>Описание</th>
                    <th>Действие</th>
                </tr>
                <c:forEach var="service" items="${services}">
                    <tr>
                        <td>
                            <c:out value="${service.id}"/>
                        </td>
                        <td>
                            <c:out value="${service.name}"/>
                        </td>
                        <td>
                            <c:out value="${service.price}"/>
                            руб.
                        </td>
                        <td>
                            <c:out value="${service.categoryName}"/>
                        </td>
                        <td>
                            <c:out value="${service.descr}"/>
                        </td>
                        <td>
                            <a id="no" href="order?action=remove&id=${service.id}">X</a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
            <div class="container">
                <div class="buttons flex-1" style="padding-top:30px;">
                    <form method="post" action="order?action=clear">
                        <button type="submit" class="box1btn">Очистить</button>
                    </form>
                <!--</div>-->
                <!--<div class="buttons flex-1" style="padding-top:30px;">-->
                    <form method="post" action="order?action=continue">
                        <button type="submit" class="box1btn">Продолжить</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>