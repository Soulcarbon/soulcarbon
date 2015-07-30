<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<h1>Топ Игроков</h1>

<div class="card">

  <table class="table">
    <thead>
    <tr>
      <th>#</th>
      <th>Ник</th>
      <th>Количество выйгранных вещей</th>
      <th>Сумма  {{application.text.valute}}</th>

    </tr>
    </thead>
    <tbody>
      <tr ng-repeat="element in statistic.topPlayer">
        <td>{{element.id}}</td>
        <td>{{element.winnerNickName}}</td>
        <td>{{element.countWeapons}}</td>
        <td>{{application.language == 'ru' ? element.total.rub.toFixed(2) : element.total.usd.toFixed(2)}}</td>
      </tr>
    </tbody>
  </table>
</div>