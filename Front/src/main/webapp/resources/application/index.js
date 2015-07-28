var model = angular.module("mainApp" , ["BackEndService" , 'ngWebsocket']);
model.controller("indexController",function($scope,$timeout,$http,applicationService,$state,$websocket){
    var startCounter = false;
    var ws = $websocket.$new('ws://localhost:8080/connectToGame'); // instance of ngWebsocket, handled by $websocket service

    $scope.counter = 0;
    $scope.onTimeout = function(){
        if(startCounter) {
            $scope.counter++;
        }
        mytimeout = $timeout($scope.onTimeout,1000);
    };
    var mytimeout = $timeout($scope.onTimeout,1000);

    $scope.stop = function(){
        $timeout.cancel(mytimeout);
    };
    $scope.game = null;
    ws.$on('$open', function () {
        console.log('web socket open');
    });

    ws.$on('$message', function (data) {
        console.log("Game was updated");
        $scope.$apply(function() {
            $scope.game = data;
            if(data.state == "Wait") {
                startCounter = false;
                $scope.counter = 0;
            }
            if(data.secondsFromStartGame != null) {
                $scope.counter = data.secondsFromStartGame;
                startCounter = true;
            }
        });
    });

    ws.$on('$close', function () {
        console.log('webscoket close');
    });




    applicationService.pageInfo($scope);

    applicationService.list($scope,"setting","ru.sw.modules.settings.Settings");

    applicationService.action($scope, "statistic" , "ru.sw.modules.statistics.Statistics" , "statistics" , {})


    $scope.changeLanguage = function(language) {
        var str = window.location.hash.split("/").splice(2).join("/");
        if(str) {
            $state.go("modules",{
                language : language,
                path: str
            });
        } else {
            $state.go("home",{
                language : language
            });
        }
        applicationService.pageInfo($scope);
    };

});