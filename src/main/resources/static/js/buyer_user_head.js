$(function () {
    $("#input_img_submit").on("mouseover",function () {
        $(".btn-upload").css("background-color","#ececec");
    }).on("mouseout",function () {
        $(".btn-upload").css("background-color","#dddddd");
    }).on("change",function () {
        var formData = new FormData();
        var fileObj = $(this).get(0).files[0];
        formData.append("file", fileObj);
        $.ajax({
            url: 'up_img' ,
            type: 'POST',
            data: formData,
            async: false,
            cache: false,
            contentType: false,
            processData: false,
            success: function (data) {
                $(".img_head_show").attr("src",data);
            },
            error: function (data) {
                alert("fail:" + data);
            }
        });
    });
    $(".btn-user-head-up").on("click",function () {
        $.get("buyer_update_head_img",
            {"headImg":$(".img_head_show").prop("src")},
            function (data) {
                if(data === "success"){
                    $(".up_success").text('保存成功');
                    $("#img_user_head").prop("src",$(".img_head_show").prop("src"));
                }else if(data === "null"){
                    $(".up_success").text('头像不能为空，保存失败');
                }else {
                    $(".up_success").text('保存失败');
                }
            });
    });
});