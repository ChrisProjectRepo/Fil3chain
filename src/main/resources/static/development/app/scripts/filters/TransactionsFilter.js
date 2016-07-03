(function () {
	'use strict';

	angular
	.module('blockchainApp')
	.filter('Transactions', TransactionsFilter);

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
})();
