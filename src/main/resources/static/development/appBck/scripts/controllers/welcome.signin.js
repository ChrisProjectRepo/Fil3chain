'use strict';

/**
* @ngdoc function
* @name blockchain.controller:signinCtrl
* @description
* # signinCtrl
* Controller of the blockchain
*/
angular.module('blockchainApp')
.controller('signinCtrl',['$scope', '$http','$state', '$mdToast',function($scope ,$http, $state, $mdToast){
  $scope.signin = function(user){
      console.log('signinCtrl','user',user)
      $http({
          url:'/fil3chain/sign_in',
          method:'POST',
          data: user
      })
          .then(function(response){
              console.log('response success',response)
              $mdToast.show(
                $mdToast.simple()
                .textContent('Welcome '+response.data.username)
                .position('bottom')
                //.position($scope.getToastPosition())
                .hideDelay(5000)
              );
              $state.go('app.wallet.profile');
          },function(response){
              console.log('response error ',response)
              $mdToast.show(
                $mdToast.simple()
                .textContent(response.status+' '+response.error)
                .position('bottom')
                //.position($scope.getToastPosition())
                .hideDelay(5000)
              );
          });
  };
}]);
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
