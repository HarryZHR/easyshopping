$(function () {
    var $checkbox = $("input[type='checkbox']");
    $checkbox.each(function () {
        $(this).prop("checked",false);
    });
    var cartItemSize = parseInt($("#cart_item_size").val());
    if(cartItemSize === 0){
        $("#div_null_cart").css("display","block");
        $("#div_full_cart").css("display","none");
    }else {
        $("#div_null_cart").css("display","none");
        $("#div_full_cart").css("display","block");
    }
    $checkbox.on("change",function () {
        updateTotal();
        var flag = false;
        var selectFlag = true;
        $("input[type='checkbox']").each(function () {
            if($(this).is(":checked")){
               flag = true;
            }
        });
        if(flag){
            $("#to_pay").css("background-color","#ef2f23").prop("disabled",false);
        }else {
            $("#to_pay").css("background-color","#ddd").prop("disabled",true);
        }
        var cartItemNum = 0;
        $(".div_cart_item_seller").each(function () {
            var $div_cart_item_seller = $(this);
            var checkFlag = true;
            $div_cart_item_seller.find(".div_cart_item_tr").each(function () {
                if(!$(this).find(".checkbox").is(":checked")){
                    checkFlag = false;
                }
            });
            if(checkFlag){
                $div_cart_item_seller.find(".seller_check").prop("checked",true);
            }
            $(this).find("input[type='checkbox']").each(function () {
                if(!$(this).is(":checked")){
                    selectFlag = false;
                }
            });
        }).find(".checkbox").each(function () {
            if($(this).is(":checked")){
                cartItemNum++;
            }
        });
        if(selectFlag){
            $(".all_check").each(function () {
                $(this).prop("checked",true);
            });
        }
        $("#span_cart_item_num").html(cartItemNum);
    });
    $(".all_check").on("click",function () {
        if(this.checked){
            $("input[type='checkbox']").prop("checked",true);
        }else {
            $("input[type='checkbox']").prop("checked",false);
        }
    });
    $(".seller_check").on("click",function () {
        var $cart_in_seller = $(this).parent().parent().parent().parent();
        if(this.checked){
            $cart_in_seller.find(":checkbox").prop("checked",true);
            } else {
            $cart_in_seller.find(":checkbox").prop("checked",false);
            $(".all_check").prop("checked",false);
        }
    });
    $(".checkbox").on("click",function () {
        var $cart_in_seller = $(this).parent().parent().parent().parent();
        if(!this.checked){
            $cart_in_seller.find(".seller_check").prop("checked",false);
            $(".all_check").prop("checked",false);
        }
    });
    // 数量框输入控制
    $(".sy_num").on("keypress",function(event) {
        var keyCode = event.which;
        return (keyCode >= 48 && keyCode <=57);
    }).focus(function() {
        this.style.imeMode='disabled';
    }).blur(function () {
        var cartId = $(this).parent().parent().parent().find(".checkbox").val();
        var countNow = parseInt($(this).parent().parent().parent().find(".count").val());
        var input = parseInt($(this).parent().parent().parent().find(".sy_num").val());
        if(input - countNow > 0){
            $(this).parent().parent().parent().find(".sy_num").val(countNow);
            updateCartNum(cartId,countNow);
            updateSubtotal($(this),countNow);
            updateTotal();
        }else {
            updateCartNum(cartId,input);
            updateSubtotal($(this),input);
            updateTotal();
        }
    });

    // 库存加减
    $(".sy_minus").on("click",function(){
        var cartId = $(this).parent().parent().parent().find(".checkbox").val();
        var num = $(this).parent().parent().parent().find(".sy_num").val();
        if(num > 1){
            num--;
        }
        updateCartNum(cartId,num);
        $(this).parent().parent().parent().find(".sy_num").val(num);
        updateSubtotal($(this),num);
        updateTotal();
    });
    $(".sy_plus").on("click",function(){
        var cartId = $(this).parent().parent().parent().find(".checkbox").val();
        var countNow = parseInt($(this).parent().parent().parent().find(".count").val());
        var num = $(this).parent().parent().parent().find(".sy_num").val();
        num++;
        if(num >= countNow){
            $(this).parent().parent().parent().find(".sy_num").val(countNow);
            updateCartNum(cartId,countNow);
            updateSubtotal($(this),countNow);
            updateTotal();
        }else{
            updateCartNum(cartId,num);
            $(this).parent().parent().parent().find(".sy_num").val(num);
            updateSubtotal($(this),num);
            updateTotal();
        }
    });
    $(".a_delete").on("click",function () {
        var cartId = $(this).parent().parent().find(".checkbox").val();
        var cartItemTr = $(this).parent().parent();
        var cartSellerTr = $(this).parent().parent().parent();
        var goodsNum = parseInt($("#allGoodsNum").text().toString());
        if(cartSellerTr.find(".div_cart_item_tr").length === 1){
            $.get("delete_cart_item",
                {"cartId":cartId},
                function (data) {
                    if(data === "success"){
                        cartSellerTr.remove();
                        $("#allGoodsNum").html(goodsNum - 1);
                    }else if(data === "zero"){
                        $("#div_null_cart").css("display","block");
                        $("#div_full_cart").css("display","none");
                    }
                });
        }else {
            $.get("delete_cart_item",
                {"cartId":cartId},
                function (data) {
                    if(data === "success"){
                        cartItemTr.remove();
                        $("#allGoodsNum").html(goodsNum - 1);
                    }else if(data === "zero"){
                        $("#div_null_cart").css("display","block");
                        $("#div_full_cart").css("display","none");
                    }
                });
        }
    });
    $("#a_select_delete").on("click",function () {
        var cartItemIdStr = "";
        var goodsNum = parseInt($("#allGoodsNum").text().toString());
        $(".checkbox").each(function () {
            if($(this).is(":checked")){
                cartItemIdStr += $(this).val() + "_";
            }
        });
        $.get("delete_select_cart_item",
            {"cartItemIdStr":cartItemIdStr},
            function (data) {
                if(data === "success"){
                    $("input[type='checkbox']").each(function () {
                        if($(this).is(":checked")){
                            $(this).parent().parent().parent().remove();
                        }
                    });
                    $("#allGoodsNum").html(goodsNum - cartItemIdStr.split("_").length + 1);
                }else if(data === "zero"){
                    $("#div_null_cart").css("display","block");
                    $("#div_full_cart").css("display","none");
                }
            })
    });
    $("#to_pay").on("click",function () {
        var cartItemIdStr = "";
        $(".checkbox").each(function () {
            if($(this).is(":checked")){
                cartItemIdStr += $(this).val() + "_";
            }
        });
        $.get("buyer_submit_order",
            {"cartItemIdStr":cartItemIdStr},
            function (data) {
                if(data === "buyer_login"){
                    window.location.href = "buyer_login";
                }else {
                    $("#div_mid").html(data);
                }
            });
    });
});
function updateCartNum(cartId,num) {
    $.get("update_cart_num",
        {"cartId":cartId,"num":num},
        function (data) {
        });
}
function updateSubtotal(btn,buyCount) {
    var subTotal = btn.parent().parent().parent().find(".div_subtotal");
    var realPrice = parseInt(btn.parent().parent().parent().find(".span_real_price").text().toString());
    subTotal.html((buyCount * realPrice).toFixed(2));
}
function updateTotal() {
    var total = 0.00;
    $(".div_subtotal").each(function () {
        if($(this).parent().find(".checkbox").is(":checked")){
            total += Number(parseFloat($(this).text().toString()).toFixed(2));
        }
    });
    $("#total").html("￥"+total.toFixed(2));
}