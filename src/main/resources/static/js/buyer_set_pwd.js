$(function(){
    var $oldPwd = $("#oldPwd");
    var $newPwd = $("#newPwd");
    $oldPwd.on("blur",function(){
        var pwd = $("#oldPwd").val();
        if(!pwd){
            $("#error_new_tip").html('请输入新密码').css("display","inline-block");
        }else if(!(/[a-zA-Z0-9\x21-\x7e]{6,14}/.test(pwd))){
            $("#error_new_tip").html('密码由英文或者数字组成，长度为6位－20位').css("display","inline-block");
        }
    }).on("focus",function () {
        $("#error_new_tip").css("display","none");
    });
    $newPwd.on("blur",function(){
        var pwd = $("#oldPwd").val();
        var pwd2 = $("#newPwd").val();
        if(pwd !== pwd2){
            $("#error_confirm_tip").html('两次密码输入不一致').css("display","inline-block");
        }
    });
    $newPwd.on("focus",function () {
        $("#error_confirm_tip").css("display","none");
    });
    $("#setPwdForm").submit(function(){
        var flag = true;
        var pwd = $("#oldPwd").val();
        var pwd_confirm = $("#newPwd").val();
        $.ajaxSetup({
            async: false
        });
        $.get("check_buyer_set_pwd",
            function(data){
                if(data === "false"){
                    $("#span_error_tip").css("display","inline-block");
                    flag = false;
                }
            });
        if(flag){
            if(!pwd){
                $("#error_new_tip").html('请输入新密码').css("display","inline-block");
                return false;
            }else if(!(/[a-zA-Z0-9\x21-\x7e]{6,14}/.test(pwd))){
                $("#error_new_tip").html('密码由英文或者数字组成，长度为6位－20位').css("display","inline-block");
                return false;
            }else if(pwd !== pwd_confirm){
                $("#error_confirm_tip").html('两次密码输入不一致').css("display","inline-block");
                return false;
            }
        }
        return flag;
    })
});
