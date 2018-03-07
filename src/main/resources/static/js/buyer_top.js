$(function () {
    initView();
});
$(window).resize(function () {
  initView();
});
function initView() {
    var width = 0;
    var num = 0;
    var space = 0;
    var $right = $(".right_nav");
    var $currBuyer = $("#currBuyer");
    var $buyerName = $("#ul_buyer_name");
    $right.each(function () {
        width += $(this).width() + 40 ;
        num++;
    });
    if($currBuyer.val() === null || $currBuyer.val() === ""){
        space = ($(window).width() * 0.78 - $("#a_login").width() - $("#a_home").width() - width) / num;
    }else {
        space = ($(window).width() * 0.78 - $buyerName.width() - $("#a_home").width() - width) / num;
    }
    if(space > 10){
        $right.css("margin-left",space);
    }else {
        $right.css("margin-left","10px");
    }
    $buyerName.on("mouseover",function () {
        $("#ul_buyer").css("display","block");
    }).on("mouseout",function () {
        $("#ul_buyer").css("display","none");
    });
    $("#ul_buyer_like").on("mouseover",function () {
        $("#ul_like").css("display","block");
    }).on("mouseout",function () {
        $("#ul_like").css("display","none");
    });
}