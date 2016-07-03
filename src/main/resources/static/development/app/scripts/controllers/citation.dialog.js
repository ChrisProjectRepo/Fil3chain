'use strict';

/**
 * @ngdoc function
 * @name blockchain.controller:signinCtrl
 * @description
 * # signinCtrl
 * Controller of the blockchain
 */
angular.module('blockchainApp')
.controller('citationDialogCtrl',['$scope', '$mdDialog', 'TransactionService','TransactionsFilter','transactionHeaders','transactions', function($scope, $mdDialog, TransactionService,TransactionsFilter, transactionHeaders, transactions){
	console.log('citationDialogCtrl',transactionHeaders, transactions);
	$scope.headers = transactionHeaders;
	$scope.transactions=transactions;
	// Array contenente le transazioni selezionati dall'utente
	var selectedTransactions=[];
	// Variabile che abilita la modalità selezione sulla lista delle transazioni
	$scope.selectionMode = true;
	//Funzione che aggiunge una transazione in selectedTransactions nel caso non fosse gia presente
	//nel caso contrario questa viene rimossa
	function addTransaction( transaction ){
		console.log('walletTransactionCtrl','Transaction click',transaction);
		//verifico se la transazione è gia presente nell'array selectedTransactions
		var selected = TransactionsFilter(selectedTransactions,transaction);
		console.log('walletTransactionCtrl','Transaction is selected',selected);
		// Nel caso in cui sia già presente, la elimino
		if(selected){
			var index = selectedTransactions.indexOf(selected);
			console.log('walletTransactionCtrl','Transaction is selected','index',index);
			if (index > -1) {
				console.log('walletTransactionCtrl','Transaction is selected','index','splice',selectedTransactions.splice(index, 1));
				console.log('walletTransactionCtrl','Transaction is selected','splice',selectedTransactions);

				return;
			}
		}else{
			//Creco la transazione nell'array di transazioni e l'aggiungo al array di transazioni selezionate
			var matched = TransactionsFilter(transactions,transaction);
			console.log('walletTransactionCtrl','Transaction matched result',matched);
			selectedTransactions.push(matched);
			console.log('walletTransactionCtrl','Selected Transaction result',selectedTransactions);
		}
	}


	//Variabile utilizzata per identificare l'item della lista da espandere
	$scope.selectedUserIndex = undefined;
	//Gestione click sugli elementi della lista di transazioni
	$scope.selectUserIndex = function (ev, index) {
		console.log('walletTransactionCtrl','selectUserIndex click',ev.target.parentNode.nodeName,index)
		//Se viene cliccata una checkbox viene invocata la funzione
		//per aggiungere la transazione in selectedTransactions
		if(ev.target.parentNode.nodeName ==='MD-CHECKBOX')return addTransaction($scope.transactions[index])
		//Controlli per l'espansionde di un list item
		if ($scope.selectedUserIndex !== index) {
			$scope.selectedUserIndex = index;
		}
		else {
			$scope.selectedUserIndex = undefined;
		}
	};

	//Funzione per la chiusura del dialog
	$scope.hide = function() {
		$mdDialog.hide();
	};
	//Funzione per la cancellazione del dialog
	$scope.cancel = function() {
		$mdDialog.cancel();
	};
	//Funzione per la risposta del dialog
	$scope.answer = function(answer) {
		//Ritorna l'array di transazioni selezionate
		$mdDialog.hide(selectedTransactions);
	};
}]);
