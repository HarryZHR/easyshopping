$(function () {
    $("#input_login").on("click",function () {
        var loginId = $("#loginId").val();
        var pwd = $("#pwd").val();
        $.get("admin_login_check",
            {"loginId":loginId,"pwd":pwd},
            function (data) {
                if(data === "success"){
                    console.log(11)
                    window.location.href = "admin_index";
                }
            });
    });
});