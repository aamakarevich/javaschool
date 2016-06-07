<!DOCTYPE html>
<html lang="en">

<head>
    <script src="js/angular.min.js"></script>
</head>

<body ng-app="myapp">

<div ng-controller="MyController">
    <button ng-click="getDataFromServer()">Fetch data from server</button>
    <p>First Name : {{customer.email}}</p>
    <p>Last Name : {{customer.password}}</p>
    <p>Status : {{customer.status}}</p>
</div>

<script>
    var app = angular.module('myapp', []);

    app.controller("MyController", function ($scope, $http) {
        $scope.getDataFromServer = function () {
            $http({
                method: 'GET',
                url: 'login'
            }).success(function (data, status, headers, config) {
                $scope.customer = data;
                $scope.customer.status = "loaded";
            }).error(function (data, status, headers, config) {
                $scope.customer = {};
                $scope.customer.status = "error";
            })
        }
    });

</script>

</body>

</html>