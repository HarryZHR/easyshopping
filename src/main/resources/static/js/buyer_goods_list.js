$(function() {
    // 点击加载更多
    $(".span_load_more").on("click",function(){
        var $currPage = $(this).parent().find(".hidden_curr_page");
        var key = $(this).parent().find(".hidden_key").val();
        var type = $(this).parent().find(".hidden_type").val();
        var low = $(this).parent().find(".hidden_low").val();
        var high = $(this).parent().find(".hidden_high").val();
        var operate = $(this).parent().find(".hidden_operate").val();
        var currPage = parseInt($currPage.val()) + 1;
        console.log("当前页码："+currPage);
        if($(this).text() === "加载更多"){
            $(this).html('加载中...');
            $.get("buyer_load_goods",
                {"currPage":currPage,"key": key,"type": type,"low": low,"high": high,"operate": operate},
                function (data) {
                    if(data === "true"){
                        $.ajax({
                            url: "buyer_load_more",
                            type: "get",
                            success: function (returnData) {
                                $(".div_more").append(returnData);
                                $(".span_load_more").html('加载更多');
                                $currPage.val(currPage);
                            }
                        });
                    }else if(data === "false"){
                        $.get("buyer_load_more",
                            function (returnData) {
                                $(".div_more").append(returnData);
                                $(".span_load_more").html('已经到底了');
                            });
                    }
                });
        }
    });
    $("#li_sort").on("mouseover",function () {
        $("#ul_dropdown").css("display","block");
    }).on("mouseout",function () {
        $("#ul_dropdown").css("display","none");
    });
    $("#highToLow").on("click",function () {
        sort("highToLow");
    });
    $("#lowToHigh").on("click",function () {
        sort("lowToHigh");
    });
    $("#sub_price").on("click",function () {
        sort("");
    });
});
// 将商品按价格排序
function sort(operate) {
    var type = $(".type").val();
    var key = $(".key").val();
    var low = $(".low").val();
    var high = $(".high").val();
    console.log(low +"--"+high)
    $.get("buyer_goods_category_key",
        {"operate":operate,"type":type,"key":key,"low":low,"high":high},
        function (data) {
            if(data === "success"){
                window.location.href = "buyer_goods_type_key";
            }
        });
}