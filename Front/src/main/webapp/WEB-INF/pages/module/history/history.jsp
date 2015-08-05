<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<div class="row">--%>
<%--<div class="col-md-12">--%>
<h1>{{application.text["history"]}}</h1>
<div class="card">

    <table class="table table-hover">
        <thead>
        <tr>
            <th>#</th>
            <th>{{application.text["history_count"]}}</th>
            <th>{{application.text["history_total"]}} {{application.text.valute}}</th>
            <th>{{application.text["history_winner"]}}</th>
        </tr>
        </thead>
        <tbody>
        <tr ng-repeat="element in statistic.previousWinner">
            <td ng-show="element.winnerNickName">{{element.id}}</td>
            <td ng-show="element.winnerNickName">{{element.countWeapons}}</td>
            <td ng-show="element.winnerNickName">{{application.language == 'ru' ? element.total.rub.toFixed(2) : element.total.usd.toFixed(2)}}</td>
            <td ng-show="element.winnerNickName">{{element.winnerNickName}}</td>
        </tr>
        </tbody>
    </table>
</div>