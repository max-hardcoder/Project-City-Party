var app = angular.module('event-controller',[]);

app.controller('event-controller',
	['$scope', 'GAuth','$state' , 'GApi', function ( $scope, GAuth,$state, GApi ) {
		
		// represente l'evenement a enregister
		$scope.event = {};
		
		$scope.event.latitude= 47,2;
		$scope.event.longitude= -1.5;
		$scope.event.url= "devine";

		
		GApi.executeAuth('eventendpoint', 'listEvent').then( function(resp) {
			$scope.events = resp.items;
			console.log(resp)
		});
		
		
	$scope.isJoined = function(event) {
	
		for ( var x in event.players) {
			if (event.players[x] == $scope.user.id) return true;
		}
		
		return false;
	}
	
	$scope.isStarted = function(event) {
		
		return ( Date.parse(event.date) < new Date());
		
	}
	
	$scope.playEvent = function(event) {
		$state.go('game' , { game : event.url , event : event});
		
	}
	
	
	$scope.registerGamer = function(event) {
			
		GApi.executeAuth('eventendpoint', 'ajoutPlayer', {id : event.key, user : $scope.user.id}).then(function(resp) {
			console.log('ajoutPlayer' + resp);
			$scope.reloadEvent();
		},function(resp) {
			console.log('ajoutPlayerError' + resp);
		});
			
	}
		
			
	$scope.reloadEvent = function() {
		
		 GApi.executeAuth('eventendpoint', 'listEvent').then( function(resp) {
		        $scope.events = resp.items;
		        $scope.event = {}
		      //  $scope.event.date ="";
		      //  $scope.events.$apply();
				});
	}
		
			
		
	
	
	$scope.addEvent = function(){


		GApi.executeAuth('eventendpoint', 'insertEvent', 
				$scope.event)
				 .then( function(resp) {
					 $scope.reloadEvent();
					 console.log(resp);

					
				 }, 
				 function(err) {
					 console.log(err)
    });
		
	};

	
	
	
/*
	
	
	
	
	$scope.events = [{

	name : 'Gala ANEM',
	price : '25€',
	desc : 'lorem ipsum',
	date : '02/05/2015',
	place : 'nantes'
}
,
{
	name : 'Laser Game ANEM',
	price : '15€',
	description : 'lorem ipsum',
	date : '05/11/2014',
	place : 'nantes'

}
];*/

	}
         
        ]
) 