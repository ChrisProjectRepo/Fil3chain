'use strict';

/**
* @ngdoc function
* @name blockchain.controller:signinCtrl
* @description
* # signinCtrl
* Controller of the blockchain
*/
angular.module('blockchainApp')
.controller('walletTransactionPostCtrl',['$scope', '$mdDialog', '$mdMedia','TransactionService',function($scope,$mdDialog, $mdMedia,TransactionService){
  console.log('walletTransactionPostCtrl');
  var transactionsMock=[
    {
      hashFile:'oihsoaidhsodhsad',
      filename:'filename',
      index_in_block:'null',
      blockContainer:'null',
      authorContainer:'author',
      citationContainer:[

      ]
    },{
      hashFile:'hash2',
      filename:'filename',
      index_in_block:'null',
      blockContainer:'null',
      authorContainer:'author',
      citationContainer:[

      ]
    }
  ]
  function showAlert(ev, transactions) {
    var useFullScreen = ($mdMedia('sm') || $mdMedia('xs')) ;// && $scope.customFullscreen;
    console.log('walletTransactionPostCtrl','useFullScreen',useFullScreen);
    $mdDialog.show({
      controller: 'citationDialogCtrl',
      templateUrl: 'views/citation.dialog.html',
      parent: angular.element(document.body),
      targetEvent: ev,
      locals: {
        transactions: transactionsMock,
        transactionHeaders : TransactionService.getTransactionHeader()
      },
      clickOutsideToClose:true,
      fullscreen: true
    })
    .then(function(citations) {
      console.log('You said the information was ', citations);
      $scope.citations = citations;
    }, function() {
      console.log('You cancelled the dialog.');
      $scope.status = 'You cancelled the dialog.';
    });
  };



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