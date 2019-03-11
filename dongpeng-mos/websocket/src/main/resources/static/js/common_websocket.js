/**
 * 该组件使用需要引用以下3个js文件
 * <script src="https://cdn.bootcss.com/jquery/3.3.1/jquery.min.js"></script>
 * <script src="https://cdn.bootcss.com/sockjs-client/1.1.4/sockjs.min.js"></script>
 * <script src="https://cdn.bootcss.com/stomp.js/2.3.3/stomp.min.js"></script>
 * @type {null}
 */

;! function(window) {
    "use strict";

    var sock = null;
    var stompClient = null;
//地址
    var host="http://172.20.15.82:15000";
//var host="http://192.168.111.188:14000/websocket";
    var endpoint="/message";
    var topicBroker="/topic";
    var queueBroker="/queue";
    var userDestinationPrefix="/user";
    var appDestinationPrefixes="/dongpeng";
    var queueSub= queueBroker+"/message";
    var errorSub= queueBroker+"/error";
    var publicSub= topicBroker+"/public";
    var sendMsgUrl= appDestinationPrefixes+"/sendMsg";
    var sendMsgPublicUrl= appDestinationPrefixes+"/sendPublicMsg";
    var userId;
    var queueSubCallback;
    var errorSubCallback;
    var publicSubCallback;

    var wsFunction={};
    //websocket连接初始化
    wsFunction.init=function (errorCallback){
        if (window.WebSocket){
            wsFunction.websocketConfig();
        } else {
            if(wsFunction.isFunction(errorCallback)){
                errorCallback();
            }else{
                alert("错误","你的浏览器不支持websocket技术通讯.");
            }
        }
    }

    //websocket配置
    wsFunction.websocketConfig=function() {
        //连接endpoint,对应后台WebSoccketConfig的配置
        sock = new SockJS(host+endpoint);
        wsFunction.sockHandle();
        wsFunction.stompConfig();
    }
    //stomp配置
    wsFunction.stompConfig=function () {
        //使用STOMP子协议的websocket客户端
        stompClient = Stomp.over(sock);
        stompClient.connect({"userId":userId}, function(frame) {

            //这里的订阅地址和controller的convertAndSendToUser地址一致，多出的user必须的，指的是用户之间发送消息
            stompClient.subscribe(userDestinationPrefix+"/"+userId+queueSub, function (message) {
                //处理响应消息
                var json = JSON.parse(message.body);
                console.log(json);

                if(wsFunction.isFunction(queueSubCallback)){
                    queueSubCallback(json);
                }

            });

            //订阅处理错误的消息地址,接收错误消息
            stompClient.subscribe(userDestinationPrefix+"/"+userId+errorSub, function (message) {
                console("发送失败:"+message);
                if(wsFunction.isFunction(errorSubCallback)){
                    errorSubCallback(json);
                }
            });

            //这里的订阅地址指的是广播消息
            stompClient.subscribe(publicSub, function (message) {
                //处理响应消息
                var json = JSON.parse(message.body);
                console.log(json);
                if(wsFunction.isFunction(publicSubCallback)){
                    publicSubCallback(json);
                }else{
                    console.log("publicSubCallback is not a function")
                }

            });
        });
    }

    //发送消息到指定用户
    wsFunction.sendMessageToUser=function (type,id,name,body,fromUser,toUser) {
        //拼接成json传到后台
        var data = {
            type:type,
            id:id,
            name:name,
            body:body,
            fromUser:fromUser,
            toUser:toUser
        };
        //发送
        stompClient.send(sendMsgUrl,{},JSON.stringify(data) );
    }

    //广播消息
    wsFunction.sendMessageToPublic=function (type,id,name,body) {
        //拼接成json传到后台
        var data = {
            type:type,
            id:id,
            name:name,
            body:body
        };
        //发送
        stompClient.send(sendMsgPublicUrl,{},JSON.stringify(data) );
    }

    //websocket状态监控
    wsFunction.sockHandle=function () {
        sock.onopen = function () {
            console.log("------连接成功------");
        };
        sock.onmessage = function (event) {
            console.log('-------Received: ' + event.data);
        };
        sock.onclose = function (event) {
            console.log('--------Info: connection closed.------');
        };
        //连接发生错误
        sock.onerror = function () {
            console.log("连接错误", "网络超时或通讯地址错误.");
            //disconnect();
        } ;
    }

    //关闭websocket
    wsFunction.disconnect=function () {
        if (sock != null) {
            sock.close();
            sock = null;
        }
    }

    //设置当前用户id
    wsFunction.setUserId=function (id){
        userId=id;
    }
    //获取当前用户id
    wsFunction.getUserId=function (){
        return userId;
    }
    //设置收到用户消息callback
    wsFunction.setQueueSubCallback=function (callback){
        queueSubCallback=callback;
    }
    //设置发送消息错误callback
    wsFunction.setErrorSubCallback=function (callback){
        errorSubCallback=callback;
    }
    //设置收到广播消息callback
    wsFunction.setPublicSubCallback=function (callback){
        publicSubCallback=callback;
    }

    /**
     * 判断是否为函数
     * @param funName
     * @returns {boolean}
     */
    wsFunction.isFunction=function (funName){
        return typeof funName === "function";
    }


    // websocket核心函数
    window.WS =wsFunction;
    window.WS.version = '1.0.0';
}(window);

