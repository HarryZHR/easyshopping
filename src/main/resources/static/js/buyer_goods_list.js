$(function() {
    // 点击加载更多
    $(".span_load_more").on("click",function(){
        var $currPage = $(this).prev();
        var currPage = parseInt($currPage.val()) + 1;
        console.log("当前页码："+currPage);
        $(this).html('加载中...');
        $.get("buyer_load_goods",
            {"currPage":currPage},
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
    })
});
