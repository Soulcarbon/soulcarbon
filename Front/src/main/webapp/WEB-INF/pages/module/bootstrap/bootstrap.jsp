Bootstraping model

<div>
    <label>Login</label> <input type="text" ng-model="bootstrapModel.login" name="login"> <br>
    <label>Password</label> <input type="text" ng-model="bootstrapModel.password" name="password"> <br>
    <label>FullName</label> <input type="text" ng-model="bootstrapModel.fullName" name="fullName"> <br>
</div>

<div>
    {{bootstrapModel}}
</div>

<li><a href="#" class="button big" ng-click="bootstrap(bootstrapModel)">Bootstrap</a></li>

<link rel="stylesheet" href="/resources/css/table.css">
<table cellspacing='0'> <!-- cellspacing='0' is important, must stay -->

    <!-- Table Header -->
    <thead>
    <tr>
        <th>Id service</th>
        <th>Service Name</th>
        <th>State</th>
        <th>ErrorMessage</th>
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