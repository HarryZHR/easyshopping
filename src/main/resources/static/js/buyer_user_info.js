$(function () {
    $("#user_real_name").on("blur",function () {
        var realName = $(this).val();
        if(!(/^([\u4E00-\u9FA5]{2,})$/).test(realName) && realName !== ""){
            $(this).next().addClass("user-info-error").text('请输入真实姓名');
        }
    }).on("focus",function () {
        $(this).next().removeClass("user-info-error").text('');
    });
    $("#user_nick_name").on("blur",function () {
        var nickName = $(this).val();
        var error = $(this).next();
        if(!nickName){
            $(this).next().removeClass("user-info-true").addClass("user-info-error").text('昵称不能为空');
        }else if(!(/^[a-zA-Z\u4E00-\u9FA5][a-zA-Z0-9_\u4E00-\u9FA5]{0,15}$/.test(nickName))){
            $(this).next().removeClass("user-info-true").addClass("user-info-error").text('昵称不合法');
        }else {
            $.get("getBuyerByNickName",
                {"nickName":nickName,"buyerId":$("#hidden_buyer_id").val()},
                function (data) {
                    if(data === "false"){
                        error.removeClass("user-info-true").addClass("user-info-error").text('该昵称已经被占用');
                    }else if(data === "true"){
                        error.removeClass("user-info-error").addClass("user-info-true");
                    }else if(data === "buyer_login"){
                        window.location.href = "buyer_login";
                    }
                });
        }
    }).on("focus",function () {
        $(this).next().removeClass("user-info-error").text('');
    });
    $(".sub-btn").on("click",function () {
        var realName = $("#user_real_name").val();
        var nickName = $("#user_nick_name").val();
        var gender = $("input[type='radio']:checked").val();
        var birthday = $("#user_birthday").val();
        var location = $("#user_location").val();
        var remark = $(".user-info-remark").val();
        var flag = true;
        if(!(/^([\u4E00-\u9FA5]{2,})$/).test(realName) && realName !== ""){
            flag = false;
        }else if(!nickName){
            flag = false;
        }else if(!(/^[a-zA-Z\u4E00-\u9FA5][a-zA-Z0-9_\u4E00-\u9FA5]{0,15}$/.test(nickName))){
            flag = false;
        }else {
            $.ajaxSetup({
                async : false //取消异步
            });
            $.get("getBuyerByNickName",
                {"nickName":nickName,"buyerId":$("#hidden_buyer_id").val()},
                function (data) {
                    if(data === "false"){
                        flag = false;
                    }
                });
        }

        if(flag){
            $.get("buyer_update_info",
                {"realName":realName,"nickName":nickName,"gender":gender,"birthday":birthday,"location":location,"remark":remark},
                function (data) {
                    if (data === "success"){
                        $(".save_tip").text('保存成功');
                    }else{
                        window.location.href="buyer_login";
                    }
                });
        }
    });
});
