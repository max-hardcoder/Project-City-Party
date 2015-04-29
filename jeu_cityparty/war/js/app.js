var app = angular.module('cityparty', 
    [  'ui.router',
    'angular-google-gapi',
    'router','home-controller', 'login-controller','event-controller','game-controller']);



app.run(['GAuth', 'GApi', '$state', '$rootScope',
    function(GAuth, GApi, $state, $rootScope) {

          var CLIENT = '455156628316-lvvpem66r74bgmsnd59q3li04gr77b43.apps.googleusercontent.com';    
          var BASE = 'https://project-city-party.appspot.com/_ah/api';

      GApi.load('eventendpoint','v1',BASE);
     
      GAuth.setClient(CLIENT);
        GAuth.checkAuth().then(
            function () {
                if($state.includes('login')) {
                	
                }
                    $state.go('webapp.home');
            },
            function() {
                $state.go('login');
            }
        );

     
    }
]);