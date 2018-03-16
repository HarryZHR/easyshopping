$(function () {
    $("#curr_pwd").on("blur",function () {
        var text = $(this).parent().next();
        if(!$(this).val()){
            text.removeClass("user-pwd-true").addClass("user-pwd-error").text("请输入原始密码");
        }else {
            $.get("buyer_update_check_pwd",
                {"pwd":$(this).val()},
                function (data) {
                    if(data === "true"){
                        text.removeClass("user-pwd-error").text("").addClass("user-pwd-true");
                    }else if(data === "false"){
                        text.removeClass("user-pwd-true").addClass("user-pwd-error").text("密码输入错误");
                    }else {
                        window.location.href = "buyer_login";
                    }
                });
        }
    }).on("focus",function () {
        $(this).parent().next().removeClass().text("");
        $(".pwd-success-tip").text("");
    });
    $("#new_pwd").on("blur",function () {
        var text = $(this).parent().next();
        if(!$(this).val()){
            text.removeClass("user-pwd-true").addClass("user-pwd-error").text("请输入新密码");
        }else if(!(/[a-zA-Z0-9\x21-\x7e]{6,14}/.test($(this).val()))){
            text.removeClass("user-pwd-true").addClass("user-pwd-error").text("密码由英文或者数字组成，长度为6位－20位");
        }else {
            text.removeClass("user-pwd-error").text("").addClass("user-pwd-true");
        }
    }).on("focus",function () {
        $(this).parent().next().removeClass().text("");
        $(".pwd-success-tip").text("");
    });
    $("#sub_pwd").on("blur",function () {
        var text = $(this).parent().next();
        if(!$(this).val()){
            text.removeClass("user-pwd-true").addClass("user-pwd-error").text("请确认新密码");
        }else if($("#new_pwd").val() !== $("#sub_pwd").val()){
            text.removeClass("user-pwd-true").addClass("user-pwd-error").text("两次密码不一致");
        }else {
            text.removeClass("user-pwd-error").text("").addClass("user-pwd-true");
        }
    }).on("focus",function () {
        $(this).parent().next().removeClass().text("");
        $(".pwd-success-tip").text("");
    });
    $(".btn_pwd_sub").on("click",function () {
        var $currPwd = $("#curr_pwd");
        var $newPwd = $("#new_pwd");
        var $subPwd = $("#sub_pwd");
        var flag = true;
        var text = $(this).next();
        var $curPwdTip = $currPwd.parent().next();
        if(!$currPwd.val()){
            $curPwdTip.removeClass("user-pwd-true").addClass("user-pwd-error").text("请输入原始密码");
            flag = false;
        }else if(!$newPwd.val()){
            $newPwd.parent().next().removeClass("user-pwd-true").addClass("user-pwd-error").text("请输入新密码");
            flag = false;
        }else if(!$subPwd.val()){
            $subPwd.parent().next().removeClass("user-pwd-true").addClass("user-pwd-error").text("请确认新密码");
            flag = false;
        }
        if(flag){
            $.get("buyer_update_pwd",
                {"pwd":$newPwd.val()},
                function (data) {
                    if(data === "success"){
                        text.text("修改成功");
                    }else {
                        window.location.href = "buyer_login";
                    }
                });
        }
    });
});