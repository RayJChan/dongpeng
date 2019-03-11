/**
 * 浏览器通知
 */
;! function(window) {
    "use strict";

    var nFunction={};

    //浏览器通知初始化
    nFunction.init=function(){
        //首先检查浏览器是否支持
        if (!('Notification' in window)) {
            alert('浏览器不支持桌面提醒')
        }
        //如果没有拒绝Notification，就申请用户允许
        else if (Notification.permission !== 'granted') {

            Notification.requestPermission().then(function (permission) {
                if (permission === 'granted') {
                    console.log("允许")

                }else if(permission === 'denied'){
                    // 禁止
                    console.log("禁止")
                }
            })

        }
        console.log(Notification.permission)
    }

    /**
     * 创建一个通知
     * @param title
     * @param body
     * @param url
     * @param icon
     * @returns {Notification}
     */
    nFunction.newNotification=function (title, body,url,icon) {
        //默认显示15s
        title = title || '新的消息';
        var options ={
            body: body,
            data: {
                url: url
            },
            icon: icon || 'http://img1.gtimg.com/2016/pics/hv1/53/152/2109/137176538.jpg',
            tag : Math.ceil(Math.random()*10) ,//代表通知的一个识别标签，相同tag时只会打开同一个通知窗口。
            requireInteraction : true  //true，保证消息不会自动消失，直到用户手动点击或关闭。
        }

        var n = new Notification(title, options);
        n.onclick = function(){
            window.open(n.data.url, '_blank');      // 打开网址
            n.close();                              // 并且关闭通知
        }
        n.onerror=function(err){
            console.log("notify  error: "+err);
        }
        n.onshow=function(){
            console.log("notify show");
        }
        return n;
    }

    // notification核心函数
    window.N =nFunction;
    window.N.version = '1.0.0';
}(window);


