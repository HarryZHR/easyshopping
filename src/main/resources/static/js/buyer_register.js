var tel = "";
$(function() {
    $("#validate").prop("value","")
        .click(function () {
            $("#errorDisDiv").addClass("disappear");
    });
    $("#tel").on("click",function () {
        $("#errorDisDiv").addClass("disappear");
    });
    $("body").on("click","#getValidate",function(){
        tel = $("#tel").val();
        if(!tel){
            $("#errorContent").html('请输入手机号码');
            $("#errorDisDiv").removeClass("disappear");
        }else if(!(/^1[3|4|5|8][0-9]\d{4,8}$/.test(tel))){
            $("#errorContent").html('请填写正确的手机号码');
            $("#errorDisDiv").removeClass("disappear");
        }else{
            $(this).prop("disabled",true);
            $.get("validate_buyer_tel",{"tel":tel},
                function(data){
                    if(data === "false"){
                        $("#errorContent").html('该手机号码已经被注册');
                        $("#errorDisDiv").removeClass("disappear");
                    }else{
                        time($("#getValidate"));
                    }
                });
        }
    });
    $("#sub").off().on("click",function(){
        tel = $("#tel").val();
        if(!tel){
            $("#errorContent").html('请输入手机号码');
            $("#errorDisDiv").removeClass("disappear");
        }else if(!(/^1[3|4|5|8][0-9]\d{4,8}$/.test(tel))){
            $("#errorContent").html('请填写正确的手机号码');
            $("#errorDisDiv").removeClass("disappear");
        }else if($("#validate").val() === ""){
            $("#errorContent").html('请填写验证码');
            $("#errorDisDiv").removeClass("disappear");
        }else if(!($("#agreeBtn").is(':checked'))){
            $("#errorContent").html('请同意嗨易购注册条款');
            $("#errorDisDiv").removeClass("disappear");
        }else{
            $.get("buyer_validate_code",{"validate":$("#validate").val(),"tel":tel},
                function(data) {
                    if(data === "false"){
                        $("#errorContent").html('短信校验失败，请重试');
                        $("#errorDisDiv").removeClass("disappear");
                    }else if(data === "true"){
                        window.location.href="buyer_set_pwd";
                    }
                });
        }
    });
});

var wait=60;
function time(o) {
    if (wait === 0) {
        o.prop("disabled", false);
        o.css("background-color","#ff5777");
        o.val("获取验证码");
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