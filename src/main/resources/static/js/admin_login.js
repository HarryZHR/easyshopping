$(function () {
    $("#input_login").on("click",function () {
        var loginId = $("#loginId").val();
        var pwd = $("#pwd");
        $.get("admin_login_check",
            {"loginId":loginId,"pwd":pwd},
            function (data) {

            });
    });
});