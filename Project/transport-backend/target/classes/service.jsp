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
    <title>Услуги</title>
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
                        <form method="get" action="order">
                            <button class="box1btn" type="submit">Заказ</button>
                        </form>
                    </li>
                </ul>
            </div>
        </div>
    </div>
    <div class="two">
        <div class="box-flex sort">
            <div class="deal">
                <form class="search" method="get" action="service">
                    <div style="float: left;">
                        <input type="text" class="placeholder" pattern="[0-9]*" name="min" placeholder="Мин. цена">
                        <input type="text" class="placeholder" pattern="[0-9]*" name="max" placeholder="Мах. цена">
                    </div>
                    <div style="float: left;">
                        <input type="text" class="placeholder" name="serviceName" placeholder="Название услуги">
                        <select name="serviceCat">
                            <option value="-1">
                                <c:out value="Все"/>
                            </option>
                            <c:forEach var="category" items="${service_categories}">
                                <option value="${category.id}">
                                    <c:out value="${category.name}"/>
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                    <div style="float: right; margin-top: 40px;">
                        <button class="box3btn" type="submit">Поиск</button>
                    </div>
                </form>
            </div>
        </div>
        <div id="left">
            <!--<table class="simple-little-table" cellspacing='0'>-->
            <table class="table_price">
                <caption>Услуги</caption>
                <tr>
                    <th>id</th>
                    <th>Название</th>
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
                            <c:if test="${isAuthorized == 1}">
                            <form method="post" action="service">
                                <input type="hidden" name="orderId" value="${service.id}">
                                <button class="box2btn" type="submit">Добавить</button>
                            </form>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
</div>
</body>
</html>