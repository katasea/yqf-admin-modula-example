function changeFrameHeight(frame) {
    frame.height = document.documentElement.clientHeight - 30 - 42 - 69;
}

//IE8 需要这句话，兼容性问题。
// document.getElementsByName("iframe0")[0].height = document.documentElement.clientHeight - 30 - 42 - 69;
/**
 * 提示框
 * flag = 0 info
 * flag = 1 success
 * flag = 2 warning
 * flag = 3 error
 */
function showMessage(flag, message, timeOut, title) {
    if (title == null || title == undefined) {
        title = '';
    }
    if (timeOut != null && timeOut != undefined) {
        timeOut = timeOut * 1000;
    }
    toastr.options = {
        closeButton: true,
        progressBar: true,
        showMethod: 'slideDown',
        timeOut: timeOut
    };
    if (flag == 0) {
        toastr.info(title, message);
    } else if (flag == 1) {
        toastr.success(title, message);
    } else if (flag == 2) {
        toastr.warning(title, message);
    } else {
        toastr.error(title, message);
    }
}

/**
 * 切换业务日期
 *
 * @param datestr
 */
function changeDate(datestr) {
    jQuery.post(getRootPath() + "/base/date", {date: datestr}, function (jsonData) {
        if (jsonData != null) {
            var flag = jsonData.flag;
            var message = jsonData.msg;
            if (flag == true) {
                showMessage(1, "切换日期成功！", 2, '');
            } else {
                showMessage(3, message, 3, '切换失败');
            }
        }
    }, "json");
}

function IEVersion() {
    var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串
    var isIE = userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1; //判断是否IE<11浏览器
    var isEdge = userAgent.indexOf("Edge") > -1 && !isIE; //判断是否IE的Edge浏览器
    var isIE11 = userAgent.indexOf('Trident') > -1 && userAgent.indexOf("rv:11.0") > -1;
    if (isIE) {
        var reIE = new RegExp("MSIE (\\d+\\.\\d+);");
        reIE.test(userAgent);
        var fIEVersion = parseFloat(RegExp["$1"]);
        if (fIEVersion == 7) {
            return 7;
        } else if (fIEVersion == 8) {
            return 8;
        } else if (fIEVersion == 9) {
            return 9;
        } else if (fIEVersion == 10) {
            return 10;
        } else {
            return 6;//IE版本<=7
        }
    } else if (isEdge) {
        return 'edge';//edge
    } else if (isIE11) {
        return 11; //IE11
    } else {
        return -1;//不是ie浏览器
    }
}

if (IEVersion() === 8) {
    $("#dependsOnIEVerionChangeCss").attr("class", "w-f-md");
}
if (IEVersion() < 8 && IEVersion() > 0) {
    alert("系统最低支持标准模式IE8,请升级浏览器或安装最新版本谷歌浏览器");
}

/**
 * 获取项目根路径
 * @returns {string}
 */
function getRootPath() {
    var strFullPath = window.document.location.href;
    var strPath = document.location.pathname;
    while(!(strPath.lastIndexOf("/") === strPath.indexOf("/"))) {
        strPath = strPath.substring(0,strPath.lastIndexOf("/"));
    }
    var pos = strFullPath.indexOf(strPath);
    var prePath = strFullPath.substring(0, pos);
    // var postPath = strPath.substring(0,strPath.substring(1).indexOf("/")+1);
    return prePath+strPath;
}
function getHostPath() {
    var strFullPath = window.document.location.href;
    var strPath = document.location.pathname;
    if (strPath == null || strPath == '/') {
        return strFullPath;
    } else {
        var pos = strFullPath.indexOf(strPath);
        var prePath = strFullPath.substring(0, pos);
        return prePath;
    }
}
function getHostWithoutPort() {
    var str = getHostPath();
    var strpre = str.substr(6);
    var pos = strpre.indexOf(':');
    if(pos == null || pos == -1) {
        pos = strpre.indexOf('/');
    }
    return str.substr(0,pos+6);
}

var myMLoading = null;
/**
 * 遮罩层显示
 */
function showMLoading(){
    myMLoading = $.confirm({
//            icon: 'fa fa-spinner fa-spin',
        icon: 'fa fa-spinner fa-pulse',
        title: '加载中...', // hides the title.
        cancelButton: false, // hides the cancel button.
        confirmButton: false, // hides the confirm button.
        closeIcon: false, // hides the close icon.
        content: false // hides content block.
    });
}
/**
 * 遮罩层隐藏
 */
function hideMLoading() {
    myMLoading.close();
}
