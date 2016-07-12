/**
* uiBreadcrumbs automatic breadcrumbs directive for AngularJS & Angular ui-router.
*
* https://github.com/michaelbromley/angularUtils/tree/master/src/directives/uiBreadcrumbs
*
* Copyright 2014 Michael Bromley <michael@michaelbromley.co.uk>'+
*/


(function() {

  /**
  * Config
  */
  var moduleName = 'directive.statistics.fil3chain';
  /**
  * Module
  */
  var module;
  try {
    module = angular.module(moduleName);
  } catch(err) {
    // named module does not exist, so create one
    module = angular.module(moduleName, [
      'service.statistics.fil3chain',
      'config.statistics.fil3chain'
    ]);
  }
  module.directive('widget', GraphDirective);

  GraphDirective.$inject = [
    '$log',
    '$compile',
    '$http',
    '$state',
    'Statistics',
    'StatisticsConfig'
  ];
  function GraphDirective($log, $compile, $http, $state, Statistics, StatisticsConfig) {

    return{
      restrict:'E',
      scope:{
        type:'@',
        name:'@'
      },
      compile: function compile(tElement, tAttrs, transclude) {
        return {
          pre: function preLink(scope, iElement, iAttrs, controller) {
            $log.debug('GraphDirective','pre',$state.current.name);
            $log.debug('GraphDirective','pre',Statistics, StatisticsConfig);//),Navbar.get($state.current.name));
            var ConfigWidget = function(type, name){
              return {
                type:type,
                name:name,
                page:1
              }
            }
            Statistics.get(ConfigWidget(scope.type,scope.name))
            .then(function(response){
              $log.debug('GraphDirective','success',response)
            },function(response){
              $log.debug('GraphDirective','error',response)

            })
            /* TODO change this wit an API service
            scope.labels = ["January", "February", "March", "April", "May", "June", "July"];
            scope.series = ['Series A', 'Series B'];
            scope.data = [
              [65, 59, 80, 81, 56, 55, 40],
              [28, 48, 40, 19, 86, 27, 90]
            ];
            */
            scope.pointClick = function (points, evt) {
              console.log(points, evt);

            };
            var openWidgetMenu = function($mdOpenMenu, ev){
              console.log('Open Widget menu');
              //originatorEv = ev;
              $mdOpenMenu(ev);
            }
            scope.openWidgetMenu = openWidgetMenu;

          },
          post: function postLink(scope, iElement, iAttrs, controller) {
            var templateSrc = 'scripts/modules/statistics.fil3chain/templates/'+
            scope.type+'/'+scope.name+'.html';
            $log.debug('GraphDirective','post');
            console.log(scope,  templateSrc);
            $http.get(templateSrc)
            .then(function(template){
              $log.debug('GraphDirective','post','template load success');
              var compiled = $compile(template.data)(scope);
              if(scope.type==='number'){
                compiled.css('max-height','8em');
              }
              angular.element(iElement).replaceWith(compiled);
            },function(error){
              $log.error('GraphDirective','post','template load error',error);
            })
          }
        }
      }
    };
  };
})();
