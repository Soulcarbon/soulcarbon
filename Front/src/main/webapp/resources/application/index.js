var model = angular.module("mainApp" , ["BackEndService" , 'ngWebsocket']);
model.controller("indexController",function($scope,$http,applicationService,$state,$websocket){

    var ws = $websocket.$new('ws://localhost:8080/connectToGame'); // instance of ngWebsocket, handled by $websocket service

    $scope.game = null;
    ws.$on('$open', function () {
        console.log('web socket open');
    });

    ws.$on('$message', function (data) {
        console.log("Game was updated");
        $scope.$apply(function() {
            $scope.game = data;
        });
    });

    ws.$on('$close', function () {
        console.log('webscoket close');
    });


    applicationService.pageInfo($scope);

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