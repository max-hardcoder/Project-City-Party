var router = angular.module('router', []);

router
    .config(['$urlRouterProvider',
        function($urlRouterProvider) {

            $urlRouterProvider.otherwise("/login");

        }]);

router
    .config(['$stateProvider',
        function($stateProvider) {

            $stateProvider

                .state('login', {
                    url :'/login',
                    views :  {
                        '': {
                            templateUrl: 'view/login.html',
                            controller: 'login-controller',
                        },
                    },
                })
                
                
                 .state('game', {
                    url :'/games/{game}/',
                    templateUrl: function(params){ return '/games/' + params.game + ".html"; },
                    controller: 'game-controller'


                })
                
                
                .state('webapp', {
                    abstract: true,
                    url: '',
                     controller: 'home-controller',
                    templateUrl: 'view/home.html',
                })
                
                

                .state('webapp.home', {
                    url :'/',
                    views :  {
                        '': {
                             controller: 'event-controller',
                            templateUrl: 'view/events.html',
                        },
                    },
                })
                
                   


               



    }])