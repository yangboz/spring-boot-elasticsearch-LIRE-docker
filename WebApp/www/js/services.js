angular.module('starter.services', [])
    //@see: http://www.masnun.com/2013/08/28/rest-access-in-angularjs-using-ngresource.html
//PersonService
    .factory('PersonService', function ($resource, CONFIG_ENV) {
        var data = $resource(CONFIG_ENV.api_endpoint + 'person/create');
        return data;
    })
//CameraService
    .factory('CameraService', function ($resource, CONFIG_ENV) {
        var data = $resource(CONFIG_ENV.api_endpoint + 'camera');
        return data;
    })
    //QueryService
    .factory('QueryService', function ($resource, CONFIG_ENV) {
        var data = $resource(CONFIG_ENV.api_endpoint + 'query/:index/:from/:size/:q',{index:"@index",from:"@from",size:"@size",q:"@q"});
        return data;
    })
//SearchService
    .factory('SearchExistedService', function ($resource, CONFIG_ENV) {
        var data = $resource(CONFIG_ENV.api_endpoint + 'searchExisted/:index/:item/:id',{index:"@index",item:"@item",id:"@id"});
        return data;
    })
    //TODO:IndexService
//SearchInfoService
//    .factory('SearchService', function ($resource, CONFIG_ENV) {
//        var data = $resource(CONFIG_ENV.api_endpoint + 'search/',{}, {'query': {method: 'GET', isArray: false }} );
//        return data;
//    })
    .factory('MappingService', function ($resource, CONFIG_ENV) {
        var data = $resource(CONFIG_ENV.api_endpoint + 'mapping/' +
            '', {}, {
            update: {
                method: 'PUT'
            }
        });
        return data;
    })
    .factory('SettingService', function ($resource, CONFIG_ENV) {
        var data = $resource(CONFIG_ENV.api_endpoint + 'setting/' +
            '', {}, {
            update: {
                method: 'PUT'
            }
        });
        return data;
    })
//SearchFaceService
    .factory('SearchFaceService', function ($resource, CONFIG_ENV) {
        var data = $resource(CONFIG_ENV.api_endpoint + 'search/face/:id', {id: "@id"}, {});
        return data;
    })

//ESClientService
.factory('ESClientService', function (esFactory) {
    return esFactory({
        host: 'http://localhost:9200',
        apiVersion: '2.3',
        log: 'trace'
    });
})

//@see http://stackoverflow.com/questions/16627860/angular-js-and-ng-swith-when-emulating-enum
    .factory('Enum', [function () {
        var service = {
            getUUID: function () {
                // http://www.ietf.org/rfc/rfc4122.txt
                var s = [];
                var hexDigits = "0123456789abcdef";
                for (var i = 0; i < 36; i++) {
                    s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
                }
                s[14] = "4";  // bits 12-15 of the time_hi_and_version field to 0010
                s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1);  // bits 6-7 of the clock_seq_hi_and_reserved to 01
                s[8] = s[13] = s[18] = s[23] = "-";

                var uuid = s.join("");
                return uuid;
            }
            , getTimestamp: function () {
                var now = new Date;
                var utc_timestamp = Date.UTC(now.getUTCFullYear(), now.getUTCMonth(), now.getUTCDate(),
                    now.getUTCHours(), now.getUTCMinutes(), now.getUTCSeconds(), now.getUTCMilliseconds());
                return utc_timestamp;
            }
        };
        return service;
    }])
///@see: http://forum.ionicframework.com/t/ionicloading-in-http-interceptor/4599/7
    .factory('TrendicityInterceptor',
    function ($injector, $q, $log) {

        var hideLoadingModalIfNecessary = function () {
            var $http = $http || $injector.get('$http');
            if ($http.pendingRequests.length === 0) {
                $injector.get('$ionicLoading').hide();
            }
        };

        return {
            request: function (config) {
                $injector.get('$ionicLoading').show();

                // Handle adding the access_token or auth request.

                return config;
            },
            requestError: function (rejection) {
                hideLoadingModalIfNecessary();
                return $q.reject(rejection);
            },
            response: function (response) {
                hideLoadingModalIfNecessary();
                return response;
            },
            responseError: function (rejection) {
                hideLoadingModalIfNecessary();
                //http status code check
                $log.error("detected what appears to be an Instagram auth error...", rejection);
                if (rejection.status == 400) {
                    rejection.status = 401; // Set the status to 401 so that angular-http-auth inteceptor will handle it
                }
                return $q.reject(rejection);
            }
        };
    }
);