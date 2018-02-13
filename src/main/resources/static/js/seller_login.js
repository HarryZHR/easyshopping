$(function() {
    var $activePwd = $("#activePwd");
    var $tel = $("#tel");
    var $loginId = $("#loginId");
    $activePwd.val("");
    $loginId.on("click",function(){
        disappearError();
    });
    $("#pwd").on("click",function(){
        disappearError();
    });
    $("#phoneLoginDiv").on("click",function () {
        $("#phoneLoginSpan").addClass("showLine");
        document.getElementById("phoneLoginSpan").style.color="#ff4066";
        document.getElementById("commonLoginSpan").style.color="";
        $("#commonLoginSpan").removeClass().css("color","#333");
        $("#phoneFormDiv").removeClass();
        $("#commonFormDiv").addClass("disappear");
        disappearError();
    });
    $("#commonLoginDiv").on("click",function () {
        $("#commonLoginSpan").addClass("showLine");
        document.getElementById("commonLoginSpan").style.color="#ff4066";
        document.getElementById("phoneLoginSpan").style.color="";
        $("#phoneLoginSpan").removeClass();
        $("#commonFormDiv").removeClass();
        $("#phoneFormDiv").addClass("disappear");
        disappearError();

    });
    $("#commonLogin").on("click",function(){
        if(!$loginId.val()){
            $("#errorContent").html('请输入手机号码');
            $("#errorDisDiv").removeClass();
        }else if(!$("#pwd").val()){
            $("#errorContent").html('请输入密码');
            $("#errorDisDiv").removeClass();
        }else{
            $.get("seller_check_pwd",{"tel":$("#loginId").val(),"pwd":$("#pwd").val()},
                function(data) {
                    if(data === "false"){
                        $("#errorContent").html('用户名或者密码错误');
                        $("#errorDisDiv").removeClass();
                    }else if(data === "true"){
                        window.location.href="/seller_home";
                    }
                });
        }
    });
    $("#phoneLogin").on("click",function(){
        if(checkPhone()){
            if(!$activePwd.val()){
                $("#errorContent").html('请输入动态密码');
                $("#errorDisDiv").removeClass();
            }else{
                $.get("phoneLogin_LoginAction",{"loginId":$tel.val(),"pwd":$("#activePwd").val()},
                    function(data) {
                        if(data === "false"){
                            $("#errorContent").html('动态密码错误');
                            $("#errorDisDiv").removeClass();
                        }else if(data === "true"){
                            window.location.href="/seller_home";
                        }
                    })
            }
        }
    });
    $("#getActivePwd").on("click",function(){
        if(checkPhone()){
            $.get("getActivePwd_ValidateAction",{"tel":$tel.val()},
                function(data){
                    if(data === "false"){
                        $("#errorContent").html('您输入的手机号码还未注册');
                        $("#errorDisDiv").removeClass();
                    }else{
                        time($("#getActivePwd"));
//    				time(this);
                    }
                });
        }
    });
    $activePwd.on("click",function(){
        disappearError();
    });
    $tel.on("click",function(){
        disappearError();
    });

});

function checkPhone() {
    if(!$("#tel").val()){
        $("#errorContent").html('请输入手机号码');
        $("#errorDisDiv").removeClass();
    }else if(!(/^1[3|4|5|8][0-9]\d{4,8}$/.test($("#tel").val()))){
        $("#errorContent").html('请填写正确的手机号码');
        $("#errorDisDiv").removeClass();
    }else{
        return true;
    }
}
function disappearError() {
    $("#errorDisDiv").addClass("disappear");
}

var wait=60;
function time(o) {
    if (wait === 0) {
        o.prop("disabled", false);
        o.css("background-color","#ff5777");
        o.val("获取动态密码");
        wait = 60;
    } else {
        o.prop("disabled", true);
        o.css("background-color","#A3A3A3");
        o.val(' 重新发送(' + wait + ') ');
        wait--;
        setTimeout(function() {
                time(o)
            },
            1000)
    }
}