<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<div class="row">--%>
<%--<div class="col-md-12">--%>
<h1>История Игр</h1>

<div class="card">

    <table class="table ">
        <thead>
        <tr>
            <th>#</th>
            <th>Количество разыгранных вещей</th>
            <th>Сумма {{application.text.valute}}</th>
            <th>Победитель</th>
        </tr>
        </thead>
        <tbody>
        <tr ng-repeat="element in statistic.previousWinner">
            <td>{{element.id}}</td>
            <td>{{element.countWeapons}}</td>
            <td>{{application.language == 'ru' ? element.total.rub.toFixed(2) : element.total.usd.toFixed(2)}}</td>
            <td>{{element.winnerNickName}}</td>
        </tr>
        </tbody>
    </table>
</div>