$(function () {
    initView();
});
$(window).resize(function () {
  initView();
});
function initView() {
    var $buyerName = $("#ul_buyer_name");
    var $buyerLike = $("#ul_buyer_like");
    var $cartTop = $("#cart_top");
    var $orderTop = $("#order_top");
    var $sellerLogin = $("#seller_login_top");
    var $loginTop = $("#login_top");
    var $registerTop = $("#register_top");
    var windowWidth = $(window).width() * 0.78;
    var homePageWidth = $("#home_page_top").width();
    var loginTopWidth = $loginTop.width();
    var registerTopWidth = $registerTop.width();
    var buyerNameWidth = $buyerName.width();
    var buyerLikeWidth = $buyerLike.width();
    var cartTopWidth = $cartTop.width();
    var orderTopWidth = $orderTop.width();
    var sellerLoginWidth = $sellerLogin.width();
    /*console.log(windowWidth);
    console.log(homePageWidth);
    console.log(buyerLikeWidth);
    console.log(buyerNameWidth);
    console.log(cartTopWidth);*/
 /*   console.log("---"+$("#div_navbar").width());*/
    /*if($("#currBuyer").val() != null){
        var space = (windowWidth - homePageWidth - buyerNameWidth -buyerLikeWidth - cartTopWidth - orderTopWidth - sellerLoginWidth) / 5;
        /!*console.log(space);*!/
        if(space > 0){
            $("#buyer_top").css("margin-left",space);
            $buyerLike.css("margin-left",space);
            $cartTop.css("margin-left",space);
            $orderTop.css("margin-left",space);
            $sellerLogin.css("margin-left",space);
        }
        if(space <= 0){
            $("#buyer_top").css("margin-left",0);
            $buyerLike.css("margin-left",0);
            $cartTop.css("margin-left",0);
            $orderTop.css("margin-left",0);
            $sellerLogin.css("margin-left",0);
        }
    }*/
    $buyerName.on("mouseover",function () {
        $("#ul_buyer").css("display","block");
    }).on("mouseout",function () {
        $("#ul_buyer").css("display","none");
    });
    $buyerLike.on("mouseover",function () {
        $("#ul_like").css("display","block");
    }).on("mouseout",function () {
        $("#ul_like").css("display","none");
    })
}