$(function () {
    $(".discount").on("click",function () {
        $("#div_discount").css("display","block");
        var goodsId = $(this).parent().find(".goodsId").val();
        $(".input_goods_id").val(goodsId);
    });
    $(".discount_submit").on("click",function () {
        var goodsId = $(".input_goods_id").val();
        var discount = $("#input_discount").val();
        $.get("seller_set_discount",
            {"goodsId":goodsId,"discount":discount},
            function (returndata) {
               if(returndata === "true"){
                   $("#div_discount").css("display","none");
                   $(".goodsId").each(function () {
                      if($(this).val() === goodsId){
                        $(this).parent().parent().find(".td_discount").html((discount * 1.0).toFixed(2));
                      }
                   });
               }
            });
    });
    $(".discount_cancel").on("click",function () {
        $(this).parent().css("display","none");
    });
    $("#span_close").on("click",function () {
        $(this).parent().css("display","none");
    });
});