(function() {
  'use strict';
  /**
  * Config
  */
  var moduleName = 'service.statistics.fil3chain';
  /**
  * Module
  */
  var module;
  try {
    module = angular.module(moduleName);
  } catch(err) {
    // named module does not exist, so create one
    module = angular.module(moduleName, [
      'config.statistics.fil3chain'
    ]);
  }
  module.factory('Statistics', StatisticsService);

  function StatisticsService($log, $http, StatisticsConfig) {
    var StatisticsService = {};

    $log.info('StatisticsService',StatisticsConfig)
    //Prende in ingresso un oggetto del tipo:
    //{type:"",name:"",page:""}
    StatisticsService.get = function(config){
      $log.debug('StatisticsService','get','config',config);
      $log.debug('StatisticsService','get','config',StatisticsConfig.get(config.name));
      return $http({
        methods:'POST',
        url:StatisticsConfig.get(config.name),
        data: config
      });
    }
    return StatisticsService;
  };

})();
