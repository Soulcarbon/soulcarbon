<%--
  Created by IntelliJ IDEA.
  User: Денис
  Date: 25.07.2015
  Time: 16:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html ng-app="adminApp">
<head>
    <title>Admin</title>
    <link rel="stylesheet" href="/resources/bower_components/bootstrap/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="/resources/bower_components/bootstrap/dist/css/bootstrap-theme.min.css">
    <script src="/resources/bower_components/jQuery/dist/jquery.min.js"></script>
    <script src="/resources/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
    <script src="/resources/bower_components/angular/angular.min.js"></script>
    <style>
      .users{
        display: inline-block;
        border: 2px solid #000000;
        margin: 10px;
        padding: 10px;
      }
    </style>
    <script>
      var module = angular.module("adminApp" , []);

      module.controller("adminCtrl" , function($scope,$http){

        var className = "ru.sw.modules.settings.Settings";

        $http.get("/platform/data/list?className="+className).success(function(data){
          $scope.bootstrapModel = data[0];
        });

        $scope.doWinner = function(steamId) {
          console.log(steamId);
          $http.get("/game/adminPlayer?key=gdsWRs94211&steamId=" + steamId).success(function (data) {
            console.log(data);
            $scope.users = data;
          });
        }

        $scope.updateSetting = function() {
          $http({
            method: 'POST',
            url: '/platform/data/update',
            data: $.param({className: className,
              object : JSON.stringify($scope.bootstrapModel)}),
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
          }).success(function (data){
            $scope[name] = data;
          });
        }

        $http.get("/game/adminPlayer?key=gdsWRs94211").success(function(data){
          $scope.users = data;
        });

      });
    </script>
</head>
<body ng-controller="adminCtrl">
<form class="form-horizontal">
  <div class="form-group">
    <label for="siteName" class="col-sm-2 control-label">Наименование сайта</label>
    <div class="col-sm-10">
      <input type="text" class="form-control" id="siteName" placeholder="steamLottery.ru" ng-model="bootstrapModel.siteName" >
    </div>
  </div>
  <div class="form-group">
    <label for="link_trade_offer" class="col-sm-2 control-label">Ссылка на трейд</label>
    <div class="col-sm-10">
      <input type="text" class="form-control" id="link_trade_offer" placeholder=" https://steamcommunity.com/tradeoffer/new/?partner=126557548&token=PrPPNMhP" ng-model="bootstrapModel.linkTradeOffer" >
    </div>
  </div>
  <div class="form-group">
    <label for="bonus" class="col-sm-2 control-label">Бонус, %</label>
    <div class="col-sm-10">
      <input type="number" class="form-control" id="bonus" placeholder="5" ng-model="bootstrapModel.bonus" >
    </div>
  </div>
  <div class="form-group">
    <label for="rate" class="col-sm-2 control-label">Ставка, %</label>
    <div class="col-sm-10">
      <input type="number" class="form-control" id="rate" placeholder="5" ng-model="bootstrapModel.rate" >
    </div>
  </div>
  <div class="form-group">
    <label for="vk_group" class="col-sm-2 control-label">Ссылка на группу в вк</label>
    <div class="col-sm-10">
      <input type="text" class="form-control" id="vk_group" placeholder="http://vk.com/cyberfocusfan" ng-model="bootstrapModel.vkgroupUrl" >
    </div>
  </div>
  <div class="form-group">
    <label for="countPlayersForStartGame" class="col-sm-2 control-label">Количество игроков для начала игры</label>
    <div class="col-sm-10">
      <input type="number" class="form-control" id="countPlayersForStartGame" placeholder="3" ng-model="bootstrapModel.countPlayerForStartGame" >
    </div>
  </div>
  <div class="form-group">
    <label for="setting_before_game_over" class="col-sm-2 control-label">Длительность игры,сек</label>
    <div class="col-sm-10">
      <input type="number" class="form-control" id="setting_before_game_over" placeholder="300" ng-model="bootstrapModel.secondsBeforeGameOver" >
    </div>
  </div>
  <div class="form-group">
    <div class="col-sm-10">
      <button class="btn btn-primary" ng-click="updateSetting()">Задать настройки</button>
    </div>
  </div>
  <hr>
  <div ng-repeat="user in users" class="users">
    <img ng-src="{{user.imageUrl}}">
    <p>Ник : {{user.nickName}}</p>
    <p>Вероятность : {{user.probability}} %</p>
    <button class="btn btn-success" ng-click="doWinner(user.steamId)">Сделать победителем</button>
  </div>
</form>
</body>
</html>
