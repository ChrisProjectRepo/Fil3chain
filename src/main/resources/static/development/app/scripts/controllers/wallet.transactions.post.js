'use strict';

/**
* @ngdoc function
* @name blockchain.controller:signinCtrl
* @description
* # signinCtrl
* Controller of the blockchain
*/
angular.module('blockchainApp')
.controller('walletTransactionPostCtrl',['$scope', '$mdDialog', '$mdMedia','$mdToast','TransactionService','transactions','transactionHeaders',
                                         function($scope,$mdDialog, $mdMedia,$mdToast,TransactionService, transactions , transactionHeaders){
  console.log('walletTransactionPostCtrl');
  $scope.fileToSend = {};
  $scope.fileToSend.citations = [];
  function showAlert(ev, transactions) {
    var useFullScreen = ($mdMedia('sm') || $mdMedia('xs')) ;// && $scope.customFullscreen;
    console.log('walletTransactionPostCtrl','useFullScreen',useFullScreen);
    $mdDialog.show({
      controller: 'citationDialogCtrl',
      templateUrl: 'views/citation.dialog.html',
      parent: angular.element(document.body),
      targetEvent: ev,
      locals: {
        transactions: transactions,
        transactionHeaders : transactionHeaders,
        citations : $scope.fileToSend.citations
      },
      clickOutsideToClose:true,
      fullscreen: true
    })
    /*
    .then(function(citations) {
      console.log('You said the information was ', citations);
      //$scope.fileToSend.citations = citations;
    }, function() {
      console.log('You cancelled the dialog.');
      $scope.status = 'You cancelled the dialog.';
    });
    */
  };
  function submitTransaction(file){
	  console.log(file)
	  TransactionService.post(file)
	  .then(function(response){
		  $mdToast.show(
			        $mdToast.simple()
			        .textContent('Transaction submitted:\n '+response.response)
			        .position('bottom')
			        //.position($scope.getToastPosition())
			        .hideDelay(5000)
			      );
	  },function(error){
		  $mdToast.show(
			        $mdToast.simple()
			        .textContent('Error Transaction submit: '+error)
			        .position('bottom')
			        //.position($scope.getToastPosition())
			        .hideDelay(5000)
			      );
	  })
  }
  $scope.submitTransaction = submitTransaction;


  function showCitation(ev){
    TransactionService.get()
    .then(function(response){
      console.log('walletTransactionPostCtrl','success');
      $scope.transactions = response;
      showAlert(ev,$scope.transactions);
    },function(response){
      console.log('walletTransactionPostCtrl','error');

      $scope.transactions = [];
      showAlert(ev,$scope.transactions);
    })

  }
  $scope.showCitation = showCitation;
}]);