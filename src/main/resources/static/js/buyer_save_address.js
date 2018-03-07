$(function () {
    $('#distpicker').distpicker({
        autoSelect: false
    });
    $("#subAdd").on("click",function(){
        var $add_error = $("#add_error");
        var province = $("#province").val();
        var city = $("#city").val();
        var district = $("#district").val();
        var street = $("#addressStreet").val();
        var receiver = $("#receiver").val();
        var tel = $("#tel").val();
        if(province === "" || city === "" || district === ""){
            $add_error.html('请选择正确的市区').css("display","inline");
        }else if(street.length < 5 || street.length > 100){
            $add_error.html('请按要求填写街道').css("display","inline");
        }else if(!receiver){
            $add_error.html('请填写收件人').css("display","inline");
        }else if(!(/^([\u4E00-\u9FA5]{2,})$/).test(receiver)){
            $add_error.html('请填写真实的姓名').css("display","inline");
        }else if(!tel){
            $add_error.html('请填写手机号码').css("display","inline");
        }else if(!(/^1[3|5][0-9]\d{4,8}$/.test(tel))){
            $add_error.html('手机号码不合法').css("display","inline");
        }else {
            $.get("buyer_save_address",
                {"province":province,"city":city,"district":district,
                    "street":street,"receiver":receiver,"tel":tel},
                function (data) {
                    if(data === "success"){
                        window.location.reload();
                    }else {
                        $add_error.html('地址添加失败').css("display","inline");
                    }
                });
        }
    });
    $("#province").on("focus",function(){
        $("#add_error").css("display","none");
    });
    $("#city").on("focus",function(){
        $("#add_error").css("display","none");
    });
    $("#district").on("focus",function(){
        $("#add_error").css("display","none");
    });
    $("#addressStreet").on("focus",function(){
        $("#add_error").css("display","none");
    });
    $("#receiver").on("focus",function(){
        $("#add_error").css("display","none");
    });
    $("#tel").on("focus",function(){
        $("#add_error").css("display","none");
    });
    $("#cancelAdd").on("click",function(){
        $(".div_tr").parent().parent().css("display","none");
    });
});
