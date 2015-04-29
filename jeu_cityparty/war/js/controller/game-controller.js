var controller = angular.module('game-controller', ['geolocation']);

controller.controller('game-controller', ['$scope', 'geolocation',  'GAuth', 'GApi', '$state',
    function gameController($scope, geolocation, GAuth, GApi, $state) {
	
	$scope.UIready = false;
	
	
	$scope.event= $state.params.game;
	
	console.log($state);
	
	geolocation.getLocation().then(function(data){
		
			
		      $scope.coords = {lat:data.coords.latitude, long:data.coords.longitude};
		      
		      // si l'utilisateur lance le jeu au pres du point de ralliement
		      if ( data.coords.latitude > $scope.event.latitute +1 || data.coords.latitude > $scope.event.latitute -1 &&  
		    		  data.coords.longitude > $scope.event.longitude +1 || data.coords.longitude > $scope.event.longitude -1 ) {
		    	  
		    	  $scope.UIready = true;

		      } else {
		    	  // sinon redirection 
		    	  state.go('home');
		      }
		      
		  	
		  	console.log( $scope.coords);

		    });
	
	  $scope.UIready = true;
	  
	  
	  $scope.resultat = function(param) {
		  
		  score = Math.abs(  50 - param);
		  
		  alert(" votre score est de " + score );
		  
		  GApi.executeAuth('eventendpoint', 'ajouterScore', {event : event.key, score : score}).then(function(resp) {
				console.log('resultat' + resp);
			},function(resp) {
				console.log('resultat' + resp);
			});
		  
		  
    	 // state.go('home');

		  
		  
	  }

	
	
	
	
        
        
    }
])