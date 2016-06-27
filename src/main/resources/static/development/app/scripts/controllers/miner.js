'use strict';

/**
* @ngdoc function
* @name blockchain.controller:signinCtrl
* @description
* # signinCtrl
* Controller of the blockchain
*/
angular.module('blockchainApp')
.controller('minerCtrl',function($scope, $mdDialog, $mdMedia, $http, $location){
  console.log('minerCtrl');
  var start = false;
  var buttonMinerStart = {
    icon:'icons/ic_play_arrow_white_24px.svg',
    label:'Start miner'
  };
  var buttonMinerStop = {
    icon:'icons/ic_stop_white_24px.svg',
    label:'Stop miner'
  };

  $scope.buttonMiner = buttonMinerStart;
  function minerButtonClick(event){
    console.log('minerCtrl',start);

    if(!start) {
      // Simple GET request example:
      $http({
        method: 'GET',
        url: '/fil3chain/starMining'
      }).then(function successCallback(response) {
        // this callback will be called asynchronously
        // when the response is available
        console.log('minerStart','success',response)
      }, function errorCallback(response) {
        // called asynchronously if an error occurs
        // or server returns response with an error status.
        console.log('minerStart','error',response)
      });
    }
    else {
      // Simple GET request example:
      $http({
        method: 'GET',
        url: '/fil3chain/stopMining'
      }).then(function successCallback(response) {
        // this callback will be called asynchronously
        // when the response is available
        console.log('minerStop','success',response)
      }, function errorCallback(response) {
        // called asynchronously if an error occurs
        // or server returns response with an error status.
        console.log('minerStop','error',response)
      });
    }


    //Qui si deve avviare il metodo di mining
    if(!start) {
      //showAlert(event);
      $scope.buttonMiner = buttonMinerStop;
    }
    else {
      $scope.buttonMiner = buttonMinerStart;
    }
    start=!start;
    return;
  }
  
  $scope.minerButtonClick = minerButtonClick;
  
  var uploadFileButton = {
      icon:'icons/ic_cloud_upload.svg',
      label: 'Upload file'
  };
  
  $scope.uploadFileButton = uploadFileButton;
  
  function uploadFileButtonClick(event) {
      $location.path('/wallet/transactions/post').replace();
  }
  
  $scope.uploadFileButtonClick = uploadFileButtonClick;

  function showAlert(ev) {
    var useFullScreen = ($mdMedia('sm') || $mdMedia('xs')) ;// && $scope.customFullscreen;

    $mdDialog.show({
      controller: 'minerDialogCtrl',
      templateUrl: 'views/miner.dialog.html',
      parent: angular.element(document.body),
      targetEvent: ev,
      clickOutsideToClose:true,
      fullscreen: useFullScreen
    })
    .then(function(answer) {
      $scope.status = 'You said the information was "' + answer + '".';
    }, function() {
      $scope.status = 'You cancelled the dialog.';
    });
  };
    function showAlert1(ev) {
      // Appending dialog to document.body to cover sidenav in docs app
      // Modal dialogs should fully cover application
      // to prevent interaction outside of dialog
      $mdDialog.show(
        $mdDialog.alert()
        .parent(angular.element(document.querySelector('body')))
        .clickOutsideToClose(true)
        .title('This is an alert title')
        .textContent('You can specify some description text in here.')
        .ariaLabel('Alert Dialog Demo')
        .ok('Got it!')
        .targetEvent(ev)
      );
    };

  });
