var model = angular.module("mainApp" , ["BackEndService"]);
model.controller("indexController",function($scope,$http,applicationService,$state){

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