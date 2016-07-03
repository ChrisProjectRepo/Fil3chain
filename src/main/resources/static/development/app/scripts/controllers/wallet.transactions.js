'use strict';

/**
* @ngdoc function
* @name blockchain.controller:signinCtrl
* @description
* # signinCtrl
* Controller of the blockchain
*/
angular.module('blockchainApp')
.controller('walletTransactionCtrl',['$scope','$mdToast','transactions','transactionHeaders', function($scope, $mdToast, transactions, transactionHeaders){
  console.log('walletTransactionCtrl',transactions,transactionHeaders);
	
  if(!transactions || !transactionHeaders){
	  console.log('TRANSACTIONS NULL');
	  $mdToast.show(
		        $mdToast.simple()
		        .textContent('No transaction found')
		        .position('bottom')
		        //.position($scope.getToastPosition())
		        .hideDelay(5000)
		      );
  }else{
	  $scope.transactions = transactions;
	  $scope.headers = transactionHeaders;
  }

  $scope.selectionMode = false;
  $scope.addTransaction=function(transaction){
	  console.log('walletTransactionCtrl','Transaction click',transaction)
  }
 


    $scope.selectedUserIndex = undefined;
    $scope.selectUserIndex = function (index) {
      if ($scope.selectedUserIndex !== index) {
        $scope.selectedUserIndex = index;
      }
      else {
        $scope.selectedUserIndex = undefined;
      }
    };
}]);
