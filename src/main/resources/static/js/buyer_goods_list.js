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
                            })
                    }
                })
        }
    })
});
