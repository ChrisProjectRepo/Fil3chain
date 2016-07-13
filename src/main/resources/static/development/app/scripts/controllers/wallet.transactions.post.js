'use strict';

/**
 * @ngdoc function
 * @name blockchain.controller:signinCtrl
 * @description
 * # signinCtrl
 * Controller of the blockchain
 */
angular.module('blockchainApp')
.controller('walletTransactionPostCtrl',[
  '$scope', '$mdDialog', '$mdMedia','$mdToast','$state','TransactionService','transactions','transactionHeaders','TransactionsCitationsAdapterFilter',
  function($scope,$mdDialog, $mdMedia,$mdToast,$state,TransactionService, transactions , transactionHeaders, TransactionsCitationsAdapterFilter){
	console.log('walletTransactionPostCtrl');
	$scope.showCitationsList=false;
	$scope.citationsContainer ={
			show: false,
			false:'icons/ic_keyboard_arrow_down_black_24px.svg',
			true:'icons/ic_keyboard_arrow_up_black_24px.svg'
	}
	$scope.fileToSend = {};
	$scope.fileToSend.citations = [];
	function showAlert(ev, transactions, citations) {
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
				citations : citations
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
		console.log('walletTransactionPostCtrl','submitTransaction',file);
    file.citations = TransactionsCitationsAdapterFilter(file.hashFile, file.citations);
    console.log('walletTransactionPostCtrl','submitTransaction','Adapted',file);

		TransactionService.post(file)
		.then(function(response){
			$mdToast.show(
					$mdToast.simple()
					.textContent('Transaction submitted:\n '+response.response)
					.position('bottom')
					//.position($scope.getToastPosition())
					.hideDelay(5000)
			);
      $state.go('app.wallet.transactions')
		},function(error){
			$mdToast.show(
					$mdToast.simple()
					.textContent('Error Transaction submit: '+error)
					.position('bottom')
					//.position($scope.getToastPosition())
					.hideDelay(5000)
			);
      $state.go('app.wallet.transactions')
		})
	}
	$scope.submitTransaction = submitTransaction;
	function deleteCitation(index){
		console.log('walletTransactionPostCtrl','deleteCitation',index);
		console.log('walletTransactionCtrl','Transaction is selected','index','splice',$scope.fileToSend.citations.splice(index, 1));
		console.log('walletTransactionCtrl','Transaction is selected','splice',$scope.fileToSend.citations);

	}
	$scope.deleteCitation = deleteCitation;

	function showCitation(ev){
		TransactionService.get()
		.then(function(response){
			console.log('walletTransactionPostCtrl','success');
			$scope.transactions = response;
			showAlert(ev, $scope.transactions, $scope.fileToSend.citations);
		},function(response){
			console.log('walletTransactionPostCtrl','error');

			$scope.transactions = [];
			//showAlert(ev, $scope.transactions, $scope.fileToSend.citations);
		})

	}
	$scope.showCitation = showCitation;
}]);
