angular.module('starter.controllers', [])

    .controller('MainCtrl', function ($rootScope, $scope, $ionicModal, $timeout, $http, $log, CONFIG_ENV, $ionicLoading
        , $ionicSideMenuDelegate, SourceService) {
        //Always open left.
        ionic.Platform.ready(function () {
            $ionicSideMenuDelegate.toggleLeft();
        });
        //
        $http.defaults.headers.common['Accept'] = 'application/json';
        // Form data for the login modal
        $scope.loginData = {};

        // Create the search modal that we will use later
        $ionicModal.fromTemplateUrl('templates/modal_search.html', {
            scope: $scope
        }).then(function (modal) {
            $scope.searchModal = modal;
        });
        // Create the search modal that we will use later
        $ionicModal.fromTemplateUrl('templates/modal_index.html', {
            scope: $scope
        }).then(function (modal) {
            $scope.indexModal = modal;
        });


        // Triggered in the search modal to close it
        $scope.closeSearch = function () {
            $scope.searchModal.hide();
        };

        // Open the search modal
        $scope.openSearch = function () {
            $scope.searchModal.show();
            //Additional call.

        };
        // Open the index modal
        $scope.openIndex = function () {
            $scope.indexModal.show();
            //Additional call.

        };
        // Triggered in the index modal to close it
        $scope.closeIndex = function () {
            $scope.indexModal.hide();
        };

        // Function testing.
        //
        $rootScope.loadCameraInfos = function () {
            SourceService.get({}, function (response) {
                $log.debug("SourceService.get() success!", response);
                $scope.cameraInfoList = response.data;
                $log.info("$scope.cameraInfoList:", $scope.cameraInfoList);
            }, function (error) {
                // failure handler
                $log.error("SourceService.get() failed:", JSON.stringify(error));
            });
        }
        //Initialize here.
        // $rootScope.loadCameraInfos();
    })
    .controller('SearchCtrl', function ($rootScope, $scope, $log, $ionicLoading, $http, CONFIG_ENV, CameraService, SearchService) {
        $rootScope.newSearch = {};
        $rootScope.newSearch.index = "my_index";
        $rootScope.newSearch.item = "my_image_item";
        $rootScope.newSearch.threshold = 95;

        //
        $scope.searchBtnClickHandler = function () {
            $log.info("$rootScope.newSearchInfo.threshold:", $rootScope.newSearchInfo.threshold);
            //
            var anewSearch = new SearchService($rootScope.newSearch);
            anewSearch.$save(function (response) {
                $log.info("SearchService POST query success, response:", response.data);
                $rootScope.faceInfoList = response.data;
            }, function (error) {
                // failure handler
                $log.error("SearchService.post failed:", JSON.stringify(error));
            });
        }
    })
    .controller('IndexCtrl', function ($rootScope, $scope, $log, $ionicLoading, $http, CONFIG_ENV) {
        $rootScope.newIndex = {};
        $rootScope.newIndex.name = "my_index";
        $rootScope.newIndex.item = "my_image_item";
        //JSON object
        $rootScope.indexResult = {
            "_index": "my_index",
            "_type": "my_image_item",
            "_id": "AVhwraTmVlbP7cTwXwJg",
            "_version": "1",
            "created": true,
            "_shards": {
                "total": 2,
                "failed": 0,
                "successful": 1
            }
        }
        //
        $scope.indexBtnClickHandler = function () {
            var fdata = new FormData();
            fdata.append("file", $rootScope.imageFile);
            $log.info("POST formdata:",fdata);
//TODO:re-factory to stand-alone service;
            $http.post(CONFIG_ENV.api_endpoint + 'index/'+$rootScope.newIndex.name+'/'+$rootScope.newIndex.item+'/', fdata, {
                transformRequest: angular.identity,
                headers: {'Content-Type': undefined}
            }).then(function (response) {
                $rootScope.indexResult = response.data;
                $log.info("IndexService with file success:",$rootScope.indexResult);
            }, function (error) {
                // failure handler
                $log.error("IndexService failed:", JSON.stringify(error));
            });
        }
    })
    .controller('SettingCtrl', function ($scope, $http, $rootScope, $location, $ionicModal, $ionicLoading, $ionicNavBarDelegate,
                                         CONFIG_ENV, $log, $cordovaToast) {
        $scope.settings = {};
        $scope.settings.stompInterval = 5;
//Websocket/Stomp testing:
//        var client = Stomp.client(CONFIG_ENV.stomp_uri, CONFIG_ENV.stomp_protocol);
        //client.connect("", "",
        //    function () {
        //        client.subscribe("jms.topic.test",
        //            function (message) {
        //                $log.debug(message);
        //                $cordovaToast.show('Here is a message!', 'long', 'center').then(function (success) {
        //                });
        //            },
        //            {priority: 9}
        //        );
        //        client.send("jms.topic.test", {priority: 9}, "Pub/Sub over STOMP!");
        //    }
        //);
    })
    .controller('SearchImageUploadCtrl', function ($scope, $rootScope, $location, $log, $http, CONFIG_ENV, FileUploader, Enum) {
        //image upload related
        $scope.fromComputer = true;
        $scope.imageURI = null;//For update the display image view.
        // init variables
        $scope.data = {};
        $scope.obj;
        var pictureSource;   // picture source
        var destinationType; // sets the format of returned value
        var url;
        // get upload URL for FORM
        $scope.data.uploadurl = CONFIG_ENV.api_endpoint + "upload";
        $scope.data.uploadFolderURI = CONFIG_ENV.api_endpoint_base + CONFIG_ENV.UPLOAD_FOLDER;

        // on DeviceReady check if already logged in (in our case CODE saved)
        ionic.Platform.ready(function () {
            //console.log("ready get camera types");
            if (navigator.camera) {
                // website handling
                $scope.fromComputer = false;
                //pictureSource=navigator.camera.PictureSourceType.PHOTOLIBRARY;
                pictureSource = navigator.camera.PictureSourceType.CAMERA;
                destinationType = navigator.camera.DestinationType.FILE_URI;
            }
        });
        // take picture
        $scope.takePicture = function () {
            if ($scope.fromComputer) {
                $scope.takePictureFromComputer();
            } else {
                $scope.takePictureFromDevice();
            }
        }
        // take picture from mobile device
        $scope.takePictureFromDevice = function () {
            //console.log("got camera button click");
            var options = {
                quality: 50,
                destinationType: destinationType,
                sourceType: pictureSource,
                encodingType: 0
            };
            if (!navigator.camera) {
                // error handling
                return;
            }
            navigator.camera.getPicture(
                function (imageURI) {
                    //console.log("got camera success ", imageURI);
                    $scope.mypicture = imageURI;
                },
                function (err) {
                    //console.log("got camera error ", err);
                    // error handling camera plugin
                },
                options);
        };

        //TODO:@see: http://blog.nraboy.com/2014/09/use-android-ios-camera-ionic-framework/
        $scope.takeCamPicture = function () {
            var options = {
                quality: 75,
                destinationType: Camera.DestinationType.DATA_URL,
                sourceType: Camera.PictureSourceType.CAMERA,
                allowEdit: true,
                encodingType: Camera.EncodingType.JPEG,
                targetWidth: 300,
                targetHeight: 300,
                popoverOptions: CameraPopoverOptions,
                saveToPhotoAlbum: false
            };

            $cordovaCamera.getPicture(options).then(function (imageData) {
                $scope.imgURI = "data:image/jpeg;base64," + imageData;
            }, function (err) {
                // An error occured. Show a message to the user
            });
        }
        //@see: http://codepen.io/ajoslin/pen/qwpCB?editors=101
        $scope.fileName = 'nothing';
        $scope.imageFile;
        //@see: http://stackoverflow.com/questions/17922557/angularjs-how-to-check-for-changes-in-file-input-fields
        $scope.onFileChangeHandler = function () {
            $scope.imageFile = event.target.files[0];
            $log.debug("openFileDialog->file:", $scope.imageFile);
            $scope.fileName = $scope.imageFile.name;
            //$scope.$apply();
        }
        //
        $scope.takePictureFromComputer = function () {
            //console.log('fire! $scope.takePictureFromComputer()');
            ionic.trigger('click', {target: document.getElementById('id_file_search')});
        };
        //
        var uploader = $scope.uploader = new FileUploader({
            url: $scope.data.uploadurl + "?owner=" + $rootScope.username + "&name=" + Enum.getTimestamp()
        });

        // FILTERS
        uploader.filters.push({
            name: 'customFilter',
            fn: function (item /*{File|FileLikeObject}*/, options) {
                return this.queue.length < 10;
            }
        });

        // CALLBACKS

        uploader.onWhenAddingFileFailed = function (item /*{File|FileLikeObject}*/, filter, options) {
            console.info('onWhenAddingFileFailed', item, filter, options);
        };
        uploader.onAfterAddingFile = function (fileItem) {
            //console.info('onAfterAddingFile', fileItem);
            //$log.debug(uploader,uploader.queue);
            uploader.queue[0].upload();
        };
        uploader.onAfterAddingAll = function (addedFileItems) {
            //console.info('onAfterAddingAll', addedFileItems);
        };
        uploader.onBeforeUploadItem = function (item) {
            //console.info('onBeforeUploadItem', item);
        };
        uploader.onProgressItem = function (fileItem, progress) {
            //console.info('onProgressItem', fileItem, progress);
        };
        uploader.onProgressAll = function (progress) {
            //console.info('onProgressAll', progress);
        };
        uploader.onSuccessItem = function (fileItem, response, status, headers) {
            //console.info('onSuccessItem', fileItem, response, status, headers);
        };
        uploader.onErrorItem = function (fileItem, response, status, headers) {
            //console.info('onErrorItem', fileItem, response, status, headers);
        };
        uploader.onCancelItem = function (fileItem, response, status, headers) {
            //console.info('onCancelItem', fileItem, response, status, headers);
        };
        uploader.onCompleteItem = function (fileItem, response, status, headers) {
            //console.info('onCompleteItem', fileItem, response, status, headers);
            $log.info("Uploader->onCompleteItem.response:", response.body);
            //Update Invoice Image view
            //$scope.imgURI = $scope.data.uploadFolderURI + response.body;
            $scope.imgURI = $scope.data.uploadFolderURI + response.body;
            //$log.debug("$scope.imgURI:", $scope.imgURI, "id:", response.id);
            $rootScope.newSearch.fileName = response.body;
        };
        uploader.onCompleteAll = function () {
            //console.info('onCompleteAll');
        };

        //console.info('uploader', uploader);
    })
    .controller('IndexImageUploadCtrl', function ($scope, $rootScope, $location, $log, $http, CONFIG_ENV, FileUploader, Enum) {
        //image upload related
        $scope.fromComputer = true;
        $scope.imageURI = null;//For update the display image view.
        // init variables
        $scope.data = {};
        $scope.obj;
        var pictureSource;   // picture source
        var destinationType; // sets the format of returned value
        var url;
        // get upload URL for FORM
        $scope.data.uploadurl = CONFIG_ENV.api_endpoint + "upload";
        $scope.data.uploadFolderURI = CONFIG_ENV.api_endpoint_base + CONFIG_ENV.UPLOAD_FOLDER;

        // on DeviceReady check if already logged in (in our case CODE saved)
        ionic.Platform.ready(function () {
            //console.log("ready get camera types");
            if (navigator.camera) {
                // website handling
                $scope.fromComputer = false;
                //pictureSource=navigator.camera.PictureSourceType.PHOTOLIBRARY;
                pictureSource = navigator.camera.PictureSourceType.CAMERA;
                destinationType = navigator.camera.DestinationType.FILE_URI;
            }
        });
        // take picture
        $scope.takePicture = function () {
            if ($scope.fromComputer) {
                $scope.takePictureFromComputer();
            } else {
                $scope.takePictureFromDevice();
            }
        }
        // take picture from mobile device
        $scope.takePictureFromDevice = function () {
            //console.log("got camera button click");
            var options = {
                quality: 50,
                destinationType: destinationType,
                sourceType: pictureSource,
                encodingType: 0
            };
            if (!navigator.camera) {
                // error handling
                return;
            }
            navigator.camera.getPicture(
                function (imageURI) {
                    //console.log("got camera success ", imageURI);
                    $scope.mypicture = imageURI;
                },
                function (err) {
                    //console.log("got camera error ", err);
                    // error handling camera plugin
                },
                options);
        };

        //TODO:@see: http://blog.nraboy.com/2014/09/use-android-ios-camera-ionic-framework/
        $scope.takeCamPicture = function () {
            var options = {
                quality: 75,
                destinationType: Camera.DestinationType.DATA_URL,
                sourceType: Camera.PictureSourceType.CAMERA,
                allowEdit: true,
                encodingType: Camera.EncodingType.JPEG,
                targetWidth: 300,
                targetHeight: 300,
                popoverOptions: CameraPopoverOptions,
                saveToPhotoAlbum: false
            };

            $cordovaCamera.getPicture(options).then(function (imageData) {
                $scope.imgURI = "data:image/jpeg;base64," + imageData;
            }, function (err) {
                // An error occured. Show a message to the user
            });
        }
        //@see: http://codepen.io/ajoslin/pen/qwpCB?editors=101
        $scope.fileName = 'nothing';
        $rootScope.imageFile;
        //@see: http://stackoverflow.com/questions/17922557/angularjs-how-to-check-for-changes-in-file-input-fields
        $scope.onFileChangeHandler = function () {
            $rootScope.imageFile = event.target.files[0];
            $log.debug("openFileDialog->file:", $scope.imageFile);
            $scope.fileName = $rootScope.imageFile.name;
            //$scope.$apply();
        }
        //
        $scope.takePictureFromComputer = function () {
            //console.log('fire! $scope.takePictureFromComputer()');
            ionic.trigger('click', {target: document.getElementById('id_file_index')});
        };
        //
        var uploader = $scope.uploader = new FileUploader({
            url: $scope.data.uploadurl + "?owner=" + $rootScope.username + "&name=" + Enum.getTimestamp()
        });

        // FILTERS
        uploader.filters.push({
            name: 'customFilter',
            fn: function (item /*{File|FileLikeObject}*/, options) {
                return this.queue.length < 10;
            }
        });

        // CALLBACKS

        uploader.onWhenAddingFileFailed = function (item /*{File|FileLikeObject}*/, filter, options) {
            console.info('onWhenAddingFileFailed', item, filter, options);
        };
        uploader.onAfterAddingFile = function (fileItem) {
            //console.info('onAfterAddingFile', fileItem);
            //$log.debug(uploader,uploader.queue);
            // uploader.queue[0].upload();
        };
        uploader.onAfterAddingAll = function (addedFileItems) {
            //console.info('onAfterAddingAll', addedFileItems);
        };
        uploader.onBeforeUploadItem = function (item) {
            //console.info('onBeforeUploadItem', item);
        };
        uploader.onProgressItem = function (fileItem, progress) {
            //console.info('onProgressItem', fileItem, progress);
        };
        uploader.onProgressAll = function (progress) {
            //console.info('onProgressAll', progress);
        };
        uploader.onSuccessItem = function (fileItem, response, status, headers) {
            //console.info('onSuccessItem', fileItem, response, status, headers);
        };
        uploader.onErrorItem = function (fileItem, response, status, headers) {
            //console.info('onErrorItem', fileItem, response, status, headers);
        };
        uploader.onCancelItem = function (fileItem, response, status, headers) {
            //console.info('onCancelItem', fileItem, response, status, headers);
        };
        uploader.onCompleteItem = function (fileItem, response, status, headers) {
            //console.info('onCompleteItem', fileItem, response, status, headers);
            $log.info("Uploader->onCompleteItem.response:", response.body);
            //Update Invoice Image view
            //$scope.imgURI = $scope.data.uploadFolderURI + response.body;
            $scope.imgURI = $scope.data.uploadFolderURI + response.body;
            //$log.debug("$scope.imgURI:", $scope.imgURI, "id:", response.id);
            $rootScope.newSearchInfo.fileName = response.body;
        };
        uploader.onCompleteAll = function () {
            //console.info('onCompleteAll');
        };

        //console.info('uploader', uploader);
    })
;