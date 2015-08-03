<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<h1>Правила Сайта</h1>
<div class="card">

  <div class="rules_text">
    {{application.text["rules_welcome"]}}<br>
    {{application.text["rules_question"]}}<br>
    {{application.text["rules_info"]}}<br>
    {{application.text["rules_example"]}}<br>
    {{application.text["rules_example2"]}}<br>
    {{application.text["rules"]}}<br>
    <ul>
      <li ng-repeat="n in [1,2,3,4,5,6,7,8,9,10]">
        {{application.text["rules_"+n]}}
      </li>
    </ul>
  </div>
</div>