var model = angular.module("mainApp" , ["BackEndService" , 'ngWebsocket']);
model.controller("indexController",function($scope,$http,applicationService,$state,$websocket){

    var ws = $websocket.$new('ws://localhost:8080/connectToGame'); // instance of ngWebsocket, handled by $websocket service

    ws.$on('$open', function () {
        console.log('Oh my gosh, websocket is really open! Fukken awesome!');

        ws.$emit('ping', 'hi listening websocket server'); // send a message to the websocket server

        var data = {
            level: 1,
            text: 'ngWebsocket rocks!',
            array: ['one', 'two', 'three'],
            nested: {
                level: 2,
                deeper: [{
                    hell: 'yeah'
                }, {
                    so: 'good'
                }]
            }
        };

        ws.$emit('pong', data);
    });

    ws.$on('$message', function (data) {
        console.log('The websocket server has sent the following data:');
        console.log(data);

    });

    ws.$on('$close', function () {
        console.log('Noooooooooou, I want to have more fun with ngWebsocket, damn it!');
    });

    $scope.wsEmit = function() {
        var data = {
            level: 1,
            text: 'ngWebsocket rocks!',
            array: ['one', 'two', 'three'],
            nested: {
                level: 2,
                deeper: [{
                    hell: 'yeah'
                }, {
                    so: 'good'
                }]
            }
        };

        ws.$emit('pong', data);
    };

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