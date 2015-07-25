<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html ng-app="mainApp" ng-controller="indexController">
<head>
    <title>todo</title>
    <meta charset="utf-8" />
    <meta name="viewport content="width=device-width, initial-scale=1" />
    <!--[if lte IE 8]><script src="assets/js/ie/html5shiv.js"></script><![endif]-->
    <link rel="stylesheet" href="/resources/css/main.css" />
    <!--[if lte IE 8]><link rel="stylesheet" href="assets/css/ie8.css" /><![endif]-->
    <link href='http://fonts.googleapis.com/css?family=Lora&subset=latin,cyrillic' rel='stylesheet' type='text/css'>
    <link href="/resources/css/index.css" rel="stylesheet">


</head>
<body class="homepage">
<div id="page-wrapper">
    <!-- Header -->
    <div id="header-wrapper">
        <div id="header">

            <!-- Logo -->
            <h1><a href="index.html">{{application.projectName}}</a></h1>

            <!-- Nav -->
            <nav id="nav">
                <ul>
                    <li class="current"><a href="index.html">Новости</a></li>
                    <li>
                        <a href="#">Математика</a>
                        <ul>
                            <li><a href="#">Курсы</a></li>
                            <li><a href="#">Математические модели</a></li>
                            <li><a href="#">Форум</a></li>
                            <li>
                                <a href="#">Материалы</a>
                                <ul>
                                    <li><a href="#">Книги</a></li>
                                    <li><a href="#">Программы</a></li>
                                    <li><a href="#">Источники</a></li>

                                </ul>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <a href="#">Физика</a>
                        <ul>
                            <li><a href="#">Курсы</a></li>
                            <li><a href="#">Физические модели</a></li>
                            <li><a href="#">Форум</a></li>
                            <li>
                                <a href="#">Материалы</a>
                                <ul>
                                    <li><a href="#">Книги</a></li>
                                    <li><a href="#">Программы</a></li>
                                    <li><a href="#">Источники</a></li>

                                </ul>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <a href="#">Программирование</a>
                        <ul>
                            <li><a href="#">Языки</a></li>
                            <li><a href="#">Исходники</a></li>
                            <li><a href="#">Форум</a></li>
                            <li>
                                <a href="#">Материалы</a>
                                <ul>
                                    <li><a href="#">Книги</a></li>
                                    <li><a href="#">Программы</a></li>
                                    <li><a href="#">Источники</a></li>
                                </ul>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <a href="#">Дизайн</a>
                        <ul>
                            <li><a href="#">Курсы</a></li>
                            <li><a href="#">Шаблоны</a></li>
                            <li><a href="#">Форум</a></li>
                            <li>
                                <a href="#">Материалы</a>
                                <ul>
                                    <li><a href="#">Книги</a></li>
                                    <li><a href="#">Программы</a></li>
                                    <li><a href="#">Источники</a></li>
                                </ul>
                            </li>
                        </ul>
                    </li>
                    <li class="language" ng-class="pageInfo.language == 'ru' ? 'language-active' : ''" ng-click="changeLanguage('ru')">Ru</li>
                    <li class="language" ng-class="pageInfo.language == 'en' ? 'language-active' : ''" ng-click="changeLanguage('en')">En</li>

                </ul>
            </nav>


            <%--Module--%>
            <div ui-view></div>
        </div>
    </div>

    <div ng-include src="'/resources/template/footerTemplate.html'"></div>
</div>

<script src="/resources/bower_components/jQuery/dist/jquery.min.js"></script>
<script src="/resources/bower_components/angular/angular.min.js"></script>
<script src="/resources/bower_components/angular-ui-router/release/angular-ui-router.min.js"></script>
<script src="/resources/application/platform.js"></script>
<script src="/resources/application/index.js"></script>
<c:forEach items="${scripts}" var="src">
    <script src="${src}"> </script>
</c:forEach>

<!-- Scripts html5 up -->

<script src="/resources/js_external/html5up/jquery.dropotron.min.js"></script>
<script src="/resources/js_external/html5up/skel.min.js"></script>
<script src="/resources/js_external/html5up/skel-viewport.min.js"></script>
<script src="/resources/js_external/html5up/util.js"></script>
<!--[if lte IE 8]>
<script src="assets/js/ie/respond.min.js"></script><![endif]-->
<script src="/resources/js_external/html5up/main.js"></script>
</body>
</html>