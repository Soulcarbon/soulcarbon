<link rel="stylesheet" href="/resources/css/table.css">
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

</form>

<div>
    {{bootstrapModel}}
</div>

<li><a href="#" class="button big" ng-click="bootstrap(bootstrapModel)">Bootstrap</a></li>


<table cellspacing='0'> <!-- cellspacing='0' is important, must stay -->

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