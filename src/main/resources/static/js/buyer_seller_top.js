$(function () {
    // 判断是否收藏店铺 上方和下方
    var $btn_like = $(".btn_like");
    var $span_like_seller = $("#span_like_seller");
    var buyer_like_seller = $("#buyerLikeSeller").val();
    if(buyer_like_seller === "like"){
        $btn_like.val('已收藏');
        $span_like_seller.text('已收藏');
    }else {
        $btn_like.val('+收藏');
        $span_like_seller.text('收藏店铺');
    }
    // 悬浮显示店铺信息
    $("#div_seller_intro").on("mouseover",function () {
        $("#div_seller_intro").css("display","block");
    }).on("mouseout",function () {
        $("#div_seller_intro").css("display","none");
    });
    $("#img_seller_icon").on("mouseover",function () {
        $("#div_seller_intro").css("display","block");
    });
    $("#span_seller_name").on("mouseover",function () {
        $("#div_seller_intro").css("display","block");
    });

    $btn_like.on("mouseover",function () {
        $("#div_seller_intro").css("display","none");
    }).on("click",function () {
        if($(this).val() === "+收藏"){
            // 收藏店铺
            buyer_like_sell()
        }else{
            // 取消收藏店铺
            buyer_dislike_seller()
        }
    });
    $span_like_seller.on("click",function () {
        if($(this).text() === "收藏店铺"){
            // 收藏店铺
            buyer_like_sell()
        }else{
            // 取消收藏店铺
            buyer_dislike_seller()
        }
    });
    
});
function buyer_like_sell() {
    $.get("buyer_like_seller",
        {"sellerId":$("#sellerId").val()},
        function (data) {
            if(data === "success"){
                $(".btn_like").val('已收藏');
                $("#span_like_seller").text('已收藏');
                $("#buyerLikeSeller").val("like");
            }else if(data === "fail"){
                window.location.href = "buyer_login";
            }
        });
}
function buyer_dislike_seller() {
    $.get("buyer_dislike_seller",
        {"sellerId":$("#sellerId").val()},
        function (data) {
            if(data === "success"){
                $(".btn_like").val('+收藏');
                $("#span_like_seller").text('收藏店铺');
                $("#buyerLikeSeller").val("dislike");
            }else if(data === "fail"){
                window.location.href = "buyer_login";
            }
        });
}