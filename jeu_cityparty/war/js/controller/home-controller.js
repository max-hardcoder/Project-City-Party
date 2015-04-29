var app = angular.module('home-controller',[]);

app.controller('home-controller',
	['$scope', 'GAuth','$state' , 'GApi', function ( $scope, GAuth,$state, GApi ) {

		$scope.logout = function() {

			console.log('logout');
			GAuth.logout().then(
		            function () {
		                $state.go('login');
		            });
 

		}
		
		
		
		

	}
         
        ]
) 