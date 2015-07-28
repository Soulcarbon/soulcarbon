<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html ng-app="mainApp" ng-controller="indexController">
<head>
    <title>todo</title>
    <meta charset="utf-8"/>
    <meta name="viewport content=" width=device-width, initial-scale=1"/>
    <link rel="stylesheet" href="/resources/bower_components/bootstrap/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="/resources/bower_components/bootstrap/dist/css/bootstrap-theme.min.css">

    <link href='http://fonts.googleapis.com/css?family=Lora&subset=latin,cyrillic' rel='stylesheet' type='text/css'>
    <link href="/resources/css/index.css" rel="stylesheet">
    <link href='http://fonts.googleapis.com/css?family=Lora:400,700&subset=latin,cyrillic' rel='stylesheet'
          type='text/css'>
    <script type="text/javascript" src="//vk.com/js/api/openapi.js?116"></script>
    <link rel="stylesheet" href="/resources/bower_components/fontawesome/css/font-awesome.min.css">
</head>
<body>
<header class="header-basic">

    <div class="header-limiter">

        <div class="logo">
            <h1>
                <img class="logo2" src="/resources/img/logo4.png">
            </h1>
        </div>

        <nav>

            <a href="#">{{application.text.History}}</a>
            <a href="#">{{application.text.TopWinners}}</a>

            <a href="#">{{application.text.Rules}}</a>
            <a href="#">{{application.text.Contact}}</a>

            <div ng-class="pageInfo.language == 'en' ? 'selected' : ''" ng-click="changeLanguage('en')">en</div>
            <div ng-class="pageInfo.language == 'ru' ? 'selected' : ''" ng-click="changeLanguage('ru')">ru</div>
        </nav>
    </div>

</header>



<div class="wrapper">
    <div ui-view></div>
</div>

<script src="/resources/bower_components/jQuery/dist/jquery.min.js"></script>
<script src="/resources/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
<script src="/resources/bower_components/angular/angular.min.js"></script>
<script src="/resources/bower_components/angular-ui-router/release/angular-ui-router.min.js"></script>
<script src="/resources/bower_components/ng-websocket/ng-websocket.js"></script>
<script src="/resources/application/platform.js"></script>
<script src="/resources/application/index.js"></script>
<c:forEach items="${scripts}" var="src">
    <script src="${src}"></script>
</c:forEach>

<!-- Scripts html5 up -->


</body>
</html>