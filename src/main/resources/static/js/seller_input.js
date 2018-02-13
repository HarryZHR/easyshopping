$(function () {
    $("#storeName").on("blur",function () {
        if ($(this).val() === "") {
            $("#storeNameError").html('店名不能为空').removeClass("disappear");
        }
    }).focus(function () {
        $("#errorTip").addClass("disappear");
        $("#storeNameError").addClass("disappear");
    });
    $("#hostName").on("blur",function () {
        var hostName = $(this).val();
        if (hostName === "") {
            $("#hostNameError").html('店主名不能为空').removeClass("disappear");
        } else if (!(/^([\u4E00-\u9FA5]{2,})$/).test(hostName)) {
            $("#hostNameError").html('店主名不合法').removeClass("disappear");
        }
    }).focus(function () {
        $("#errorTip").addClass("disappear");
        $("#hostNameError").addClass("disappear");
    });
    $("#pwd").on("blur",function () {
        var pwd = $("#pwd").val();
        if (pwd === "") {
            $("#pwdError").html('密码不能为空').removeClass("disappear");
        } else if (!(/[a-zA-Z0-9\x21-\x7e]{6,14}/.test(pwd))) {
            $("#pwdError").html('密码由英文或者数字组成，长度为6位－20位').removeClass("disappear");
        }
    }).focus(function () {
        $("#errorTip").addClass("disappear");
        $("#pwdError").addClass("disappear");
    });

    $("#secondPwd").on("blur",function () {
        var secondPwd = $("#secondPwd").val();
        var pwd = $("#pwd").val();
        if (secondPwd === "") {
            $("#secondPwdError").html('确认密码不能为空').removeClass("disappear");
        } else if (pwd !== secondPwd) {
            $("#secondPwdError").html('两次密码输入不一致').removeClass("disappear");
        }
    }).focus(function () {
        $("#secondPwdError").addClass("disappear");
        $("#errorTip").addClass("disappear");
    });
    $("#form_seller").on("submit",function () {
        if ($("#storeName").val() === "" || $("#hostName").val() === "" || $("#pwd").val() === "" || $("#secondPwd").val() === "") {
            $("#errorTip").removeClass("disappear");
            return false;
        }
    })
});
