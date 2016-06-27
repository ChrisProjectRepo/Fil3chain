'use strict';

/**
* @ngdoc function
* @name blockchain.controller:signinCtrl
* @description
* # signinCtrl
* Controller of the blockchain
*/
angular.module('blockchainApp')
.controller('walletTransactionCtrl',function($scope,$http){
  console.log('walletTransactionCtrl');
  $scope.users = [
    {
      name: { first: 'try', last:'try' }
    },
    {
      name: { first: 'try2', last:'try2' }
    },
    {
      name: { first: 'try3', last:'try3' }
    }];


    $scope.selectedUserIndex = undefined;
    
    $scope.selectUserIndex = function (index) {
      if ($scope.selectedUserIndex !== index) {
        $scope.selectedUserIndex = index;
      }
      else {
        $scope.selectedUserIndex = undefined;
      }
    };
    
    function sendTransaction(event){
        console.log($scope.file);
        
       /*
       $http.post(
                '/fil3chain/sendTransaction', 
                {transaction: $scope.file}, 
                {headers: {'Content-Type': 'application/json'} })
        .then(function (response) {
            alert('Transazione inviata con successo');
        }, function errorCallback(response) {
            alert('Errore durante l\'invio della transazione');
        });
        */
       
      $http({
        method: 'POST',
        url: '/fil3chain/sendTransaction',
        data: {'transaction': $scope.file}
      }).success(function successCallback(response) {
        // this callback will be called asynchronously
        // when the response is available
        alert('Transazione inviata con successo');
        console.log(response);
      }).error(function errorCallback(response) {
        // called asynchronously if an error occurs
        // or server returns response with an error status.
        alert('Errore durante l\'invio della transazione');
        console.log(response);
      });
        
        
    };
    
    $scope.sendTransaction = sendTransaction;
});
/*
angular.module('blockchainApp')
.controller('signinCtrl', ['$scope','$mdToast','$state','AuthenticationService',function ($scope,$mdToast,$state,AuthenticationService) {
  console.log('signinCtrl');
  $scope.user={};
  $scope.signin = function(user){
    console.log('signin clicked from signinCtrl',$scope.user);
    AuthenticationService.signin($scope.user)
    .then(function(user){
      console.log('Signin','result',user);
      AuthenticationService.SetCredentials(user.username,user.password);
      $state.go('app.dashboard',{userId:user.id});
    },function(message){
      $mdToast.show(
        $mdToast.simple()
        .textContent(message)
        .position('fil')
        //.position($scope.getToastPosition())
        .hideDelay(5000)
      );
    })
  };;
  //console.log('signinCtrl','scope',$scope);
}]);
*/
