// Ionic Starter App

// angular.module is a global place for creating, registering and retrieving Angular modules
// 'starter' is the name of this angular module example (also set in a <body> attribute in index.html)
// the 2nd parameter is an array of 'requires'
// 'starter.controllers' is found in controllers.js
angular.module('app.filter.similarity',[])
    .filter('rangeFilter', function () {
        return function (items, attr, threshold) {
            if(items!=undefined) {
                // console.log("items:"+items);
                var range = [],
                    threshold = parseFloat(threshold);
                // console.log("threshold:" + threshold);
                for (var i = 0, l = items.length; i < l; ++i) {
                    var item = items[i];
                    if (item[attr] >= 2*threshold*0.01) {
                        range.push(item);
                    }
                }
            }
            return range;
        };
    });

angular.module('starter', ['ionic', 'starter.controllers', 'starter.services', 'nvd3', 'ngCordova', 'angularFileUpload','ngResource','ionic-zoom-view','app.filter.similarity'])
    //Support RESTful PATCH
    //@see: http://stackoverflow.com/questions/20305615/configure-angularjs-module-to-send-patch-request
    .config(['$httpProvider', function ($httpProvider) {
        $httpProvider.defaults.headers.patch = {
            'Content-Type': 'application/json;charset=utf-8'
        }
        //@see: http://forum.ionicframework.com/t/ionicloading-in-http-interceptor/4599/7
        $httpProvider.interceptors.push('TrendicityInterceptor');
    }])
////$log configure
    .config(['$logProvider', function ($logProvider) {
        $logProvider.debugEnabled(true);
        //TODO:https://github.com/ThomasBurleson/angularjs-logDecorator
    }])
///ENV_config
    .constant('CONFIG_ENV', {
        'api_endpoint_base': 'http://192.168.0.8:8084/api/',
        'api_endpoint': 'http://192.168.0.8:8084/api/es/image/',
        'api_version': '0.0.1',
        'debug': false
        , 'UPLOAD_FOLDER': 'uploads/'//for image file upload
    })
    .run(function ($ionicPlatform) {
        $ionicPlatform.ready(function () {
            // Hide the accessory bar by default (remove this to show the accessory bar above the keyboard
            // for form inputs)
            if (window.cordova && window.cordova.plugins.Keyboard) {
                cordova.plugins.Keyboard.hideKeyboardAccessoryBar(true);
            }
            if (window.StatusBar) {
                // org.apache.cordova.statusbar required
                StatusBar.styleDefault();
            }
        });
    })

    .config(function ($stateProvider, $urlRouterProvider) {
        $stateProvider

            .state('app', {
                url: "/app",
                abstract: true,
                templateUrl: "templates/menu.html",
                controller: 'SettingCtrl'
            })

            .state('app.browse', {
                url: "/browse",
                views: {
                    'menuContent': {
                        templateUrl: "templates/browse.html",
                    }
                }
            })
            .state('app.dashboard', {
                url: "/dashboard",
                views: {
                    'menuContent': {
                        templateUrl: "templates/dashboard.html",

                    }
                }
            });
        // if none of the above states are matched, use this as the fallback
        $urlRouterProvider.otherwise('/app/dashboard');
    });

