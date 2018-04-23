$(function () {
    $(".img_order_del").on("click",function () {
        if(confirm("是否确认删除该条订单？")){
            var orderId = $(this).next().val();
            window.location.href = "del_order?orderId="+orderId;
            $.get("del_order",
                {"orderId":orderId},
                function (data) {
                    if(data === "success"){
                        window.location.reload();
                    }else if (data === "buyer_login"){
                        window.location.href = "buyer_login";
                    }
                });
        }
    });
    $(".div_order").on("mouseover",function () {
        $(this).find(".order_seller").css("border","1px solid #bdbbbb").css("border-bottom","1px solid #ececec");
        $(this).find(".order_bottom").css("border","1px solid #bdbbbb").css("border-top",0);
    }).on("mouseout",function () {
        $(this).find(".order_seller").css("border","1px solid #ececec");
        $(this).find(".order_bottom").css("border","1px solid #ececec").css("border-top",0);
    });
    $(".pay_now").on("click",function () {
        var orderId = $(this).parent().parent().parent().parent().parent().find(".hiddenOrderId").val();
        $.get("buyer_pay_now",
            {"orderId":orderId},
            function (data) {
                $(".div-right").html(data);
        });
    });
    $(".submit_receive").on("click",function () {
        var orderId = $(this).parent().parent().parent().parent().parent().find(".hiddenOrderId").val();
        if(confirm('是否确认收到商品？')){
            $.get("buyer_submit_receive",
                {"orderId":orderId},
                function (data) {
                    if(data === "success"){
                        window.location.reload();
                    }else {
                        window.location.href = "buyer_login";
                    }
                })
        }
    });
    $(".complain_seller").on("click",function () {
        var orderItemId = $(this).parent().find(".orderItemId").val();
        $("#orderItemId").val(orderItemId);
        $(this).attr("id","clickBtn");
    });
    $("#sub_complain").on("click",function () {
        var content = $("#complain_content").val();
        var orderItemId = $("#orderItemId").val();
        var select = $("input[name='type']:checked").val();
        if(select === undefined){
            $("#complain_tip").text("请选择投诉类型");
        }else if(content === "" || content === null){
            $("#complain_tip").text("请描述问题");
        }else {
            var img = '';
            $(".hidden_complain_img").each(function () {
                console.log($(this).val());
                img += $(this).val() + "_";
            });
            $.get("buyer_complain",
                {"orderItemId":orderItemId,"content":content,"type":select,"img":img},
                function (data) {
                    if(data === "success"){
                        $("input[type='radio']").each(function () {
                            $(this).attr("checked",false);
                        });
                        $("#complain_content").val('');
                        $(".up_img").html('');
                        $('#complain').modal('hide');
                        $("#clickBtn").remove();
                    }else {
                        window.location.href = "buyer_login";
                    }
                });
        }
    });
    $("input[name='type']").on("focus",function () {
        $("#complain_tip").text('');
    });
    $("#complain_content").on("focus",function () {
        $("#complain_tip").text('');
    });
    $(".input_upload_img").on("change",function () {
        var formData = new FormData();
        var files = $(".input_upload_img").get(0).files;
        for (var i = 0; i < files.length; i++){
            formData.append("files",files[i]);
        }
        var num = 0;
        $(".complain_img").each(function () {
            num ++;
        });
        if(files.length + num > 5){
            $("#complain_tip").text('图片超过上限');
        }else {
            $.ajax({
                url: "up_img_multi",
                type: "POST",
                data: formData,
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                success: function (returndata) {
                    var url = returndata.split("_");
                    for (var i = 0 ; i < url.length - 1; i++){
                        $(".up_img").append("<img src=\'"+ url[i] +"\' class=\'complain_img\' /> <input type=\'hidden\' class='hidden_complain_img' value=\'"+ url[i] +"\'/><img src='/img/delete.png' class='img_delete_detail' onclick='deleteImg($(this))'/>");
                    }
                    $(".img_delete_detail").css("display","inline");
                },
                error: function (data) {
                    alert("fail");
                }
            });
        }
    }).on("focus",function () {
        $("#complain_tip").text('');
    });
});

// 删除上传的细节图片
function deleteImg(deleteBtn) {
    deleteBtn.prev().remove();
    deleteBtn.prev().remove();
    deleteBtn.remove();
}