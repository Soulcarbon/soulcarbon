model.controller("bootstrapCtrl" , function ($scope,applicationService) {

    applicationService.list($scope, "serviceList","ru.sw.modules.bootstrapService.InitialServiceInfo");

    $scope.bootstrap = function(model){
        applicationService.action($scope,"serviceList","ru.sw.modules.bootstrapService.InitialServiceInfo" ,"bootstrap", model);
    }
});