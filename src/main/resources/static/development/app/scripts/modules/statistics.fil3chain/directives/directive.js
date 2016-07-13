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
  module.directive('filechain', Fil3chainDirective);
  Fil3chainDirective.$inject = [
    '$log',
    '$compile',
    '$http',
    '$state',
    'Statistics',
    'StatisticsConfig'
  ];
  function Fil3chainDirective($log, $compile, $http, $state, Statistics, StatisticsConfig) {
    return{
      restrict:'A',
      //template:'<svg id="tree-container" style="width:100%"></svg>',
      link: function(scope, element, attrs){
        $log.debug('Fil3chainDirective',scope, element, attrs)
        var json ={
          "name": "flare",
          "children": [{
            "name": "analytics",
            "children": [{
              "name": "cluster",
              "children": [{
                "name": "AgglomerativeCluster",
                "size": 3938
              }, {
                "name": "CommunityStructure",
                "size": 3812
              }, {
                "name": "HierarchicalCluster",
                "size": 6714
              }, {
                "name": "MergeEdge",
                "size": 743
              }]
            }]
          }]
        }
        $log.debug('Fil3chainDirective','element chart:', element[0])
        angular.element(element[0]).css('width','100%');
        angular.element(element[0]).css('height','100%');
        scope.$watch(
          "data",
          function handleDataChange( newValue, oldValue ) {
            if(newValue){
              console.log( 'Fil3chainDirective:', newValue );
              //var json = JSON.parse(newValue);
              angular.element(element[0]).empty();
              initTree(element[0], newValue );
            }
          }
        );
        var ConfigWidget = function(type, name, page){
          return {
            type:type,
            name:name,
            page:page
          }
        }
        scope.pagination = function(page){
          if(!page)page = 1;
          Statistics.get(ConfigWidget(scope.type,scope.name,page))
          .then(function(response){
            $log.debug('Fil3chainDirective','success',response)
            scope.data = response.data
          },function(response){
            $log.debug('Fil3chainDirective','error',response)

          })
        }
      }
    }
  }
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
            scope.refreshWidget = function(){
              Statistics.get(ConfigWidget(scope.type,scope.name,scope.page))
              .then(function(response){
                $log.debug('GraphDirective','success',response)
                scope.data = response.data
              },function(response){
                $log.debug('GraphDirective','error',response)

              })
            };
            scope.refreshWidget();
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
