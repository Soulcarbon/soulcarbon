<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<h1>{{application.text["top"]}}</h1>

<div class="card">

  <table class="table table-hover">
    <thead>
    <tr>
      <th>#</th>
      <th>{{application.text["top_winner"]}}</th>
      <th>{{application.text["top_count"]}}</th>
      <th>{{application.text["top_total"]}}  {{application.text.valute}}</th>

    </tr>
    </thead>
    <tbody>
      <tr ng-repeat="element in statistic.topPlayer">
        <td ng-show="element.winnerNickName">{{element.id}}</td>
        <td ng-show="element.winnerNickName">{{element.winnerNickName}}</td>
        <td ng-show="element.winnerNickName">{{element.countWeapons}}</td>
        <td ng-show="element.winnerNickName">{{application.language == 'ru' ? element.total.rub.toFixed(2) : element.total.usd.toFixed(2)}}</td>
      </tr>
    </tbody>
  </table>
</div>