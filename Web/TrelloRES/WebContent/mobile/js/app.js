
angular.module('myApp', [
                     	'ngRoute',
                     	'Filters',
                     	'MyServices',
                     	'MyControllers'
                     ]).
                     config(['$routeProvider', function($routeProvider) {
                     	$routeProvider.when('/boards', {templateUrl: 'partials/boards.html', controller: 'CtrlBoards'});
                     	$routeProvider.when('/boards/:id', {templateUrl: 'partials/lists.html', controller: 'CtrlLists'});
                     	$routeProvider.when('/lists/:id', {templateUrl: 'partials/cards.html', controller: 'CtrlCards'});
                     	$routeProvider.when('/cards/:id', {templateUrl: 'partials/details.html', controller: 'CtrlDetails'});
                     	$routeProvider.when('/login', {templateUrl: 'partials/login.html', controller: 'CtrlLogin'});
                     	$routeProvider.otherwise({redirectTo: '/login'});
                     }]);



