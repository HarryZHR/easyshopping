$(function () {
    //没有参与活动的取消键不可点击
    $(".goods_activity").each(function () {
        console.log($(this).val());
       if($(this).val() === '0'){
           console.log(66);
           $(this).parent().find(".cancel").css("backgroundColor","#B0B0B0");
           $(this).parent().find(".cancel").css("border","1px solid #B0B0B0");
           $(this).parent().find(".cancel").prop("disabled",true);
       }
    });
    // 点击参与活动
   $(".activity").on("click",function () {
       var goodsId = $(this).parent().find(".goodsId").val();
       $("#div_activity").css("display","block");
       $(".input_goods_id").val(goodsId);
   });
   // 点击具体参加的活动
   $(".div_in_activity").on("click",function () {
       var activityName = $(this).prop("id").charAt($(this).prop("id").length - 1);
       var goodsId = $(this).parent().find(".input_goods_id").val();
       $.get("seller_attend_activity",
           {"activityName":activityName,"goodsId":goodsId},
           function (returndata) {
                if(returndata === "true"){
                    $("#div_activity").css("display","none");
                    $(".goodsId").each(function () {
                        if($(this).val() === goodsId){
                            $(this).parent().find(".cancel").css("backgroundColor","#d43f3a");
                            $(this).parent().find(".cancel").css("border","1px solid #d43f3a");
                            $(this).parent().find(".cancel").prop("disabled",false);
                            if(activityName === '1'){
                                $(this).parent().parent().find(".td_activity_name").html("活动一");
                            }else if(activityName === '2'){
                                $(this).parent().parent().find(".td_activity_name").html("活动二");
                            }else if(activityName === '3'){
                                $(this).parent().parent().find(".td_activity_name").html("活动三");
                            }
                        }
                    });
                }
           });
   });
   // 取消活动
   $(".cancel").on("click",function () {
        if(confirm("是否确认取消活动？")){
            var goodsId = $(this).parent().find(".goodsId").val();
            $.get("seller_attend_activity",
                {"activityName":0,"goodsId":goodsId},
                function (returndata) {
                    if(returndata === "true"){
                        $("#div_activity").css("display","none");
                        $(".goodsId").each(function () {
                            if($(this).val() === goodsId){
                                $(this).parent().parent().find(".td_activity_name").html("暂无参与活动");
                                $(this).parent().parent().find(".cancel").css("backgroundColor","#B0B0B0");
                                $(this).parent().parent().find(".cancel").css("border","1px solid #B0B0B0");
                                $(this).parent().parent().find(".cancel").prop("disabled","true");
                            }
                        });
                    }
                });
        }
   });
    $("#span_close").on("click",function () {
        $(this).parent().css("display","none");
    });
});