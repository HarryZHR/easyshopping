$(function () {
    $(".img_order_del").on("click",function () {
        if(confirm("是否确认删除该条订单？")){
            var order = $(this).parent().parent().parent().parent();
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
});