storageEngine = function () {

    function getStorageUrlOf(type) {
        switch (type) {
            case storageType.TASK:
                return 'TaskServlet';
            case storageType.USER:
                return 'UserServlet';
            case storageType.TEAM:
                return 'TeamServlet';
            default:
                return '#';
        }
    }

    return {
        init: function (successCallback) {
            successCallback(null);
            console.log('Init not required for remote db');
        },

        initObjectStore: function(type, successCallback) {
            successCallback();
            console.log('InitObjectStore not required for remote db');
        },

        save: function (type, obj, successCallback, errorCallback) {
            const url = getStorageUrlOf(type);
            $.post(url, {'data': obj})
                .done(successCallback)
                .fail(errorCallback);
        },

        findById: function (type, obj, successCallback, errorCallback) {
            const url = getStorageUrlOf(type);
            $.get(url, {'id': obj})
                .done(successCallback)
                .fail(errorCallback);
        },

        findAll: function (type, successCallback, errorCallback) {
            const url = getStorageUrlOf(type);
            console.log("fetching all of ", type, "at", url);
            $.get(url)
                .done(successCallback)
                .fail(errorCallback);
        }
    }
}();