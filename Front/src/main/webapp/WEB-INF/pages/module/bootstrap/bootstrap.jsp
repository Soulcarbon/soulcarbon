<%--<link rel="stylesheet" href="/resources/css/table.css">--%>
<div  class="container">
    <h3>{{application.text["bootstrap"]}}</h3>


    <form class="form-horizontal">
        <div class="form-group">
            <label for="inputEmail3" class="col-sm-2 control-label">{{application.text["bootstrap_login"]}}</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="inputEmail3" placeholder="Admin" ng-model="bootstrapModel.login" >
            </div>
        </div>
        <div class="form-group">
            <label for="inputPassword3" class="col-sm-2 control-label">{{application.text["bootstrap_password"]}}</label>
            <div class="col-sm-10">
                <input type="password" class="form-control" id="inputPassword3" placeholder="Password" ng-model="bootstrapModel.password">
            </div>
        </div>
        <div class="form-group">
            <label for="inputEmail3" class="col-sm-2 control-label">{{application.text["bootstrap_full_name"]}}</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="inputEmail3" placeholder="Admin" ng-model="bootstrapModel.fullName" >
            </div>
        </div>
        <div class="form-group">
            <label for="siteName" class="col-sm-2 control-label">{{application.text["bootstrap_site_name"]}}</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="siteName" placeholder="steamLottery.ru" ng-model="bootstrapModel.siteName" >
            </div>
        </div>
        <div class="form-group">
            <label for="bonus" class="col-sm-2 control-label">{{application.text["bootstrap_bonus"]}}</label>
            <div class="col-sm-10">
                <input type="number" class="form-control" id="bonus" placeholder="5" ng-model="bootstrapModel.bonus" >
            </div>
        </div>
        <div class="form-group">
            <label for="rate" class="col-sm-2 control-label">{{application.text["bootstrap_rate"]}}</label>
            <div class="col-sm-10">
                <input type="number" class="form-control" id="rate" placeholder="5" ng-model="bootstrapModel.rate" >
            </div>
        </div>
        <div class="form-group">
            <label for="vk_group" class="col-sm-2 control-label">{{application.text["bootstrap_vk_group"]}}</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="vk_group" placeholder="http://vk.com/cyberfocusfan" ng-model="bootstrapModel.vk_group" >
            </div>
        </div>
        <div class="form-group">
            <div>
                <button class="btn btn-primary" ng-click="bootstrap(bootstrapModel)">Bootstrap</button>
            </div>
        </div>

    </form>

    <div>
        {{bootstrapModel}}
    </div>



    <table class="table"> <!-- cellspacing='0' is important, must stay -->

        <!-- Table Header -->
        <thead>
        <tr>
            <th>{{application.text["bootstrap_id_services"]}}</th>
            <th>{{application.text["bootstrap_service_name"]}}</th>
            <th>{{application.text["bootstrap_state"]}}</th>
            <th>{{application.text["bootstrap_error_message"]}}</th>
        </tr>
        </thead>
        <!-- Table Header -->

        <!-- Table Body -->
        <tbody>

        <tr ng-repeat="service in serviceList">
            <td>{{service.id}}</td>
            <td>{{service.serviceName}}</td>
            <td>{{service.state}}</td>
            <td>{{service.errorMessage}}</td>
        </tr>

        </tbody>
        <!-- Table Body -->

    </table>
</div>
