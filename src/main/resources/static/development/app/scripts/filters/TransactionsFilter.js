(function () {
	'use strict';

	angular
	.module('blockchainApp')
	.filter('Transactions', TransactionsFilter)
	.filter('TransactionsSelected', TransactionsSelectedFilter);

	TransactionsFilter.$inject = [];
	function TransactionsFilter() {
		return function(items, match){
			var matching = [], matches, falsely = true;

			// Return the items unchanged if all filtering attributes are falsy
			angular.forEach(items, function(value, key){
				
				if(value.hashFile === match.hashFile){
					console.log("Transaction Matching",value,key)
					matching.push(value);
				}

			});


			//matching.push(item);
			return matching[0];
		};
		
	}
	
	TransactionsSelectedFilter.$inject = [];
	function TransactionsSelectedFilter() {
		return function(transactions, citations){
			console.log('TransactionsSelectedFilter');
			var matching = [], matches, falsely = true;
			angular.forEach(transactions, function(transaction, key){
				angular.forEach(citations, function(citation, key){

					if(citation.hashFile === transaction.hashFile){
						console.log("TransactionCitation Matching",transaction, citation)
						transaction.selected = true;
					}
				});

			});

			return transactions;

		};
		
	}
})();
