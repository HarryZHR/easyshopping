$(function () {
    // 流程的更新
    $("#two").addClass("round-active");
    $("#two_line").addClass("line-active");
    $("#two_text").addClass("content-active");
    $("#html_title").text("确认订单");
    // 判断是否有收货地址
    var addressSize = parseInt($("#addressSize").val());
    if(addressSize === 0){
        $("#div_input_address").css("display","block");
        $("#cancelAdd").prop("disabled",true);
    }else {
        $("#div_input_address").css("display","none");
    }
    $(".div_buyer_address_select").on("mouseover",function () {
        if(!$(this).find(":first").prop("checked")){
            $(this).removeClass("unselectedAddress");
            $(this).addClass("hoverAddress");
        }
    }).on("mouseout",function () {
        if(!$(this).find(":first").prop("checked")) {
            $(this).removeClass("hoverAddress");
            $(this).addClass("unselectedAddress");
        }
    }).on("click",function () {
        $(this).find(":first").prop("checked",true);
        $(".div_buyer_address_select").removeClass("selectAddress unselectedAddress hoverAddress").addClass("unselectedAddress");
        $(this).removeClass("unselectedAddress hoverAddress");
        $(this).addClass("selectAddress");
    });
    $("input[type='radio']").each(function(){
        if($(this).prop("checked")){
            $(this).parent().addClass("selectAddress");
        }else{
            $(this).parent().addClass("unselectedAddress");
        }
    });
    $("#use_new_address").on("click",function () {
        $("#div_input_address").css("display","block");
    });
    var totalMoney = 0;
    var totalNum = 0;
    $(".order_item_subtotal").each(function () {
        totalMoney += parseFloat($(this).text().toString());
    });
    $(".order_item_num").each(function () {
        totalNum += parseInt($(this).text().toString());
    });
    $("#total_money").html(totalMoney.toFixed(2));
    $("#total_num").html(totalNum);

    $("#save_order").on("click",function () {
        var addressId = null;
        $("input[type='radio']").each(function () {
            if($(this).is(":checked")){
                addressId = $(this).val();
            }
        });
        var orderAll = "";
        $(".div-row").each(function () {
            var remark = $(this).find(".input_remark").val();
            if(remark === null || remark === ""){
                remark = " ";
            }
            var orderOne = $(this).find(".seller_id").val() + ":" + remark;
            orderAll += orderOne +"_";
        });
        if(addressId === null){
            alert("请先选择收货地址！");
        }else {
            $.get("buyer_save_order",
                {"addressId":addressId,"orderAll":orderAll},
                function (data) {
                    if(data === "seller_buyer"){
                        window.location.href = "buyer_login";
                    }else {
                        $("#div_mid").html(data);
                    }
                });
        }
    });
});