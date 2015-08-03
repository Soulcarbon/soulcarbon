model.controller("bootstrapCtrl" , function ($scope,applicationService) {
    applicationService.pageInfo($scope);
    var className = "ru.sw.modules.bootstrapService.InitialServiceInfo";

    applicationService.list($scope, "serviceList",className);

    $scope.bootstrap = function(model){
        applicationService.action($scope,"serviceList",className ,"bootstrap", model);
    }
});