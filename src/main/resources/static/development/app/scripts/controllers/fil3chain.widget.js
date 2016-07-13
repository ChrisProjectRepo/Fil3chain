'use strict';

/**
 * @ngdoc function
 * @name blockchain.controller:signinCtrl
 * @description
 * # signinCtrl
 * Controller of the blockchain
 */
angular.module('blockchainApp')
.controller('fil3chainWidget',[
  '$scope', '$mdDialog', function($scope, $mdDialog){
	console.log('fil3chainWidget');
 var json = {"id": 1, "name": "Parent", "children": [
  {"id": 2, "name": "Child 1", "children": [
    {"id": 3, "name": "Child 1.1", "children": [
        {"id": 4,"name": "Child 1.1.1"},
        {"id": 5, "name": "Child 1.1.2"}
      ]
    }]
  },
  {"id": 6, "name": "Child 2", "children": [
    {"id": 7,"name": "Child 2.2"},
    {"id": 8, "name": "Child 2.3"}
  ]}
]}
  function updateFilechain(){
    var chart = $("#chart");
    console.log('Chart founded', chart);
    var tree = new dndTree("#chart");
    tree.update(json);
  }

}]);
