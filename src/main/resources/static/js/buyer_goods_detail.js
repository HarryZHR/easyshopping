$(function(){
    var $num_input = $("#sy_num_gid");
    // 判断是否收藏商品
    var $span_like_num = $("#span_like_num");
    var $div_like = $("#div_like");
    var buyer_like_goods = $("#buyerLikeGoods").val();
    if(buyer_like_goods === "like"){
        $span_like_num.css("color","#ef2f23");
        $div_like.css("border","1px solid #ef2f23");
    }else{
        $span_like_num.css("color","#666");
        $div_like.css("border","1px solid #ddd");
    }
    $("input[type='radio']").each(function () {
        $(this).prop("checked",false);
    });
    // 点击收藏商品
    $div_like.on("click",function () {
        var likeNum = parseInt($span_like_num.text());
        // 当前未收藏
        if($span_like_num.css("color") === "#666" || $span_like_num.css("color") === "rgb(102, 102, 102)"){
            $.get("buyer_like_goods",
                {"goodsId":$("#goodsId").val()},
                function (data) {
                    if(data === "success"){
                        $span_like_num.css("color","#ef2f23").html(likeNum + 1);
                        $div_like.css("border","1px solid #ef2f23");
                    }else {
                        window.location.href="buyer_login";
                    }
                });
        }else {
            // 当前收藏
            $.get("buyer_dislike_goods",
                {"goodsId":$("#goodsId").val()},
                function (data) {
                    if(data === "success"){
                        $span_like_num.css("color","#666").html(likeNum - 1);
                        $div_like.css("border","1px solid #ddd");
                    }else {
                        window.location.href="buyer_login";
                    }
                })
        }
    });

    // 放大镜
    $(".jqzoom").imagezoom();
    $("#ul_goods_image").find("li").find("a").on("click",function(){
        //增加点击的li的class:tb-selected，去掉其他的tb-selecte
        $(this).parents("li").addClass("tb-selected").siblings().removeClass("tb-selected");
        //赋值属性
        $(".jqzoom").attr('src',$(this).find("img").attr("mid")).attr('rel',$(this).find("img").attr("big"));
    });
    // 商品的尺寸选择
    $(".span_goods_size").on("click",function () {
        if(!$(this).hasClass("disable")){

            $(".span_goods_size").each(function () {
                $(this).css("border","1px solid #dddddd");
            });
            $(".img_goods_color_img").each(function () {
                $(this).removeClass("disable");
                $(this).prev().prop("disabled",false);
            });
            var color_flag = false;
            // 如果未选中
            if(!$(this).prev().prop("checked")){
                var size = $(this).prev().val();
                $(this).prev().prop("checked",true);
                $(this).css("border","2px solid #333");
                $("input[name='goods_color']").each(function () {
                    if($(this).is(":checked")){
                        color_flag = true;
                        $.get("get_standard_count",
                            {"goodsId":$("#goodsId").val(),"size":size, "color": $(this).val()},
                            function (data) {
                                $("#count").text(data);
                                if($num_input.val() > data){
                                    $num_input.val(data);
                                }
                            });
                    }
                });
                $.get("check_standard_count_by_size",
                    {"goodsId":$("#goodsId").val(),"size":size},
                    function (data) {
                        var sizeArr = data.split("_");
                        for (var i = 0; i < sizeArr.length; i++){
                            $("input[name='goods_color']").each(function () {
                                if(sizeArr[i] === $(this).val()){
                                    $(this).prop("disabled",true);
                                    $(this).next().addClass("disable");
                                }
                            });
                        }
                    });

                // 如果选中
            }else {
                $(this).css("border","1px solid #dddddd");
                $(this).prev().prop("checked",false);
                $("#count").text($("#all_count").val());
            }
            // 如果颜色有被选中
            if(color_flag && !$(this).is(":checked")){
                $(".input_submit").removeClass("input_inactive").addClass("input_active").prop("disabled",false);
            }else {
                $(".input_submit").removeClass("input_active").addClass("input_inactive").prop("disabled",true);
            }
        }
    });
    // 商品的颜色选择
    $(".img_goods_color_img").on("click",function () {
        if(!$(this).hasClass("disable")) {
            $(".img_goods_color_img").each(function () {
                $(this).css("border", "1px solid #dddddd");
            });
            $(".span_goods_size").each(function () {
                $(this).removeClass("disable");
                $(this).prev().prop("disabled", false);
            });
            var size_flag = false;
            // 如果未选中
            if (!$(this).prev().prop("checked")) {
                var color = $(this).prev().val();

                $(this).prev().prop("checked", true);
                $(this).css("border", "2px solid #333");
                $("input[name='goods_size']").each(function () {
                    if ($(this).is(":checked")) {
                        size_flag = true;
                        $.get("get_standard_count",
                            {"goodsId": $("#goodsId").val(), "color": color, "size": $(this).val()},
                            function (data) {
                                $("#count").text(data);
                                if ($num_input.val() > data) {
                                    $num_input.val(data);
                                }
                            });
                    }
                });
                $.get("check_standard_count_by_color",
                    {"goodsId": $("#goodsId").val(), "color": color},
                    function (data) {
                        var sizeArr = data.split("_");
                        for (var i = 0; i < sizeArr.length; i++) {
                            $("input[name='goods_size']").each(function () {
                                if (sizeArr[i] === $(this).val()) {
                                    $(this).prop("disabled", true);
                                    $(this).next().addClass("disable");
                                }
                            });
                        }
                    });

                // 如果选中
            } else {
                $(this).css("border", "1px solid #dddddd");
                $(this).prev().prop("checked", false);
                $("#count").text($("#all_count").val());
            }
            if (size_flag && !$(this).is(":checked")) {
                $(".input_submit").removeClass("input_inactive").addClass("input_active").prop("disabled", false);
            } else if (size_flag && $(this).is(":checked")) {
                $(".input_submit").removeClass("input_active").addClass("input_inactive").prop("disabled", true);
            }
        }
    });
    // 点击三个选项
    $("#li_goods_info").on("click",function () {
        $("#ul_goods_info_nav").find("li").removeClass("active").addClass("inactive");
        $(this).addClass("active");
        $("#div_goods_content > div").css("display","none");
        $(".div_goods_info").css("display","block");
        $("#ul_extra").css("display","inline-block");
    });
    $("#li_goods_reply").on("click",function () {
        $("#ul_goods_info_nav").find("li").removeClass("active").addClass("inactive");
        $(this).addClass("active");
        $("#div_goods_content > div").css("display","none");
        $(".div_goods_reply").css("display","block");
        $("#ul_extra").css("display","none");
    });
    $("#li_goods_category").on("click",function () {
        $("#ul_goods_info_nav").find("li").removeClass("active").addClass("inactive");
        $(this).addClass("active");
        $("#div_goods_content > div").css("display","none");
        $(".div_goods_category").css("display","block");
        $("#ul_extra").css("display","none");
    });
    // 滚动使右侧悬浮
    $(window).bind("scroll", function () {
        var extraTop = parseInt($(window).scrollTop());
        var $middle = $("#div_middle");
        var middleWidth = $middle.width() + $middle.offset().left + 1;
        if (extraTop >= 1000) {
            $("#div_extra").addClass("extra_hover").css("left",middleWidth);
        }else {
            $("#div_extra").removeClass("extra_hover");
        }
        var height2 = $("#div_goods_detail_image").offset().top;
        var height3 = $("#div_goods_category_in").offset().top;
        if(extraTop < height2){
            $("#ul_extra").find("li").removeClass('li_hover');
            $("#li_extra1").addClass('li_hover');
        }
        if(extraTop - parseInt(height2.toString()) >= 0 && extraTop - parseInt(height3.toString()) < 0){
            $("#ul_extra").find("li").removeClass('li_hover');
            $("#li_extra2").addClass('li_hover');
        }
        if(extraTop - parseInt(height3.toString()) + 1 >= 0){
            $("#ul_extra").find("li").removeClass('li_hover');
            $("#li_extra3").addClass('li_hover');
        }
    });
    $("#li_extra1").on("click",function () {
        $(window).scrollTop($("#div_goods_intro").offset().top);
    });
    $("#li_extra2").on("click",function () {
        $(window).scrollTop($("#div_goods_detail_image").offset().top);
    });
    $("#li_extra3").on("click",function () {
        $(window).scrollTop($("#div_goods_category_in").offset().top);
    });

    // 数量框输入控制
    $(".sy_num").on("keypress",function(event) {
        var keyCode = event.which;
        return (keyCode >= 48 && keyCode <=57);
    }).focus(function() {
        this.style.imeMode='disabled';
        $("#span_error_num").css("display","none");
    }).blur(function(){
        var countNow = parseInt($("#count").text().toString());
        var input = parseInt($(".sy_num").val());
        if(input >  countNow){
            $("#span_error_num").css("display","inline");
        }else{
            $("#span_error_num").css("display","none");
        }
    });

    // 库存加减
    $(".sy_minus").on("click",function(){
        var countNow = parseInt($("#count").text().toString());
        var input = parseInt($(".sy_num").val());
        var num = $num_input.val();
        if(num > 1){
            num--;
        }
        $num_input.val(num);
        if(input >  countNow){
            $("#span_error_num").css("display","inline");
        }else{
            $("#span_error_num").css("display","none");
        }
    });
    $(".sy_plus").on("click",function(){
        var countNow = parseInt($("#count").text().toString());
        var input = parseInt($(".sy_num").val());
        var num = $num_input.val();
        if(input >= countNow){
            $("#span_error_num").css("display","inline");
        }else{
            num++;
            $("#span_error_num").css("display","none");
        }
        $num_input.val(num);
    });

    // 选择商品信息关闭按钮
    $("#div_close_buy_info").find("span").on("click",colseSubmit);

    // 成功添加购物车的关闭
    $("#div_close_add_cart").find("span").on("click",function () {
        $("#add_cart_success").css("display","none");
    });

    $("#subOrderForm").submit(function () {
        // 如果尺寸和颜色没有选择
        if(!colorAndSizeCheck()) {
            openSubmit();
            $(".input_submit").addClass("input_inactive") .prop("disabled",true);
            $("#to_submit").addClass("buyer_buy").prop("type","submit");
            return false;
        }else{
            // 跳转生成订单的界面
            return true;
        }
    });

    // 上面的加入购物车
    $(".input_cart").on("click",clickCart);
    // 下面的购物车
    $(".span_cart").on("click",function () {
        $("html").animate({scrollTop:$("#div_goods_info_border").offset().top});
        clickCart();
    });
    $("#to_submit").on("click",function () {
        if($(this).hasClass("buyer_add_cart")){
            addCart();
        }
    });
    $("#toCart").on("click",function () {
        window.location.href = "buyer_cart";
    });

    // 加载更多的评价
    var goodsId = $("#goodsId").val();
    $(".reply_load_more").on("click",function () {
        var $currReplyPage = $(this).prev();
        var currReplyPage = parseInt($currReplyPage.val()) + 1;
        var replyType = $("#replyType").val();
        if($(this).text() === "加载更多"){
            $.get("buyer_list_reply",
                {"goodsId": goodsId,"currReplyPage":currReplyPage,"replyType":replyType},
                function (data) {
                    if(data === "true"){
                        $.ajax({
                            url: "buyer_goods_reply",
                            type: "get",
                            success: function (returnData) {
                                $("#div_goods_reply").append(returnData);
                                $(".reply_load_more").text('加载更多');
                                $currReplyPage.val(currReplyPage);
                            }
                        });
                    }else if(data === "false"){
                        $.get("buyer_goods_reply",
                            function (returnData) {
                                $("#div_goods_reply").append(returnData);
                                $(".reply_load_more").text('已经到底了');
                            })
                    }
                });
        }
    });
    $("#all_reply").on("click",function () {
        removeChecked();
        $(this).addClass("reply_checked");
        $("#replyType").val("");
        $.ajaxSetup({
            async:false
        });
        $.get("buyer_list_reply",
            {"goodsId": goodsId,"currReplyPage":1,"replyType":""},
            function () {
                $.get("buyer_goods_reply",
                    function (returnData) {
                        $("#div_goods_reply").html(returnData);
                    })
            });
        checkAllCount();
    });

    $("#good_reply").on("click",function () {
        removeChecked();
        $(this).addClass("reply_checked");
        $("#replyType").val("goodReply");
        $.ajaxSetup({
            async:false
        });
        $.get("buyer_list_reply",
            {"goodsId": goodsId,"currReplyPage":1,"replyType":"goodReply"},
            function () {
                $.get("buyer_goods_reply",
                    function (returnData) {
                        $("#div_goods_reply").html(returnData);
                    })
            });
        checkAllCount();
    });
    $("#multi_reply").on("click",function () {
        removeChecked();
        $(this).addClass("reply_checked");
        $("#replyType").val("multiReply");
        $.ajaxSetup({
            async:false
        });
        $.get("buyer_list_reply",
            {"goodsId": goodsId,"currReplyPage":1,"replyType":"multiReply"},
            function () {
                $.get("buyer_goods_reply",
                    function (returnData) {
                        $("#div_goods_reply").html(returnData);
                    })
            });
        checkAllCount();
    });
    $("#bad_reply").on("click",function () {
        removeChecked();
        $(this).addClass("reply_checked");
        $("#replyType").val("badReply");
        $.ajaxSetup({
            async:false
        });
        $.get("buyer_list_reply",
            {"goodsId": goodsId,"currReplyPage":1,"replyType":"badReply"},
            function () {
                $.get("buyer_goods_reply",
                    function (returnData) {
                        $("#div_goods_reply").html(returnData);
                    })
            });
        checkAllCount();
    });
    $("#img_reply").on("click",function () {
        removeChecked();
        $(this).addClass("reply_checked");
        $("#replyType").val("imgReply");
        $.ajaxSetup({
            async:false
        });
        $.get("buyer_list_reply",
            {"goodsId": goodsId,"currReplyPage":1,"replyType":"imgReply"},
            function () {
                $.get("buyer_goods_reply",
                    function (returnData) {
                        $("#div_goods_reply").html(returnData);
                    })
            });
        checkAllCount();
    });
    $("#into_seller").on("click",function () {
        $.get("buyer_goods_category_key",
            {"sellerId":$("#sellerId").val()},
            function (data) {
                if(data === "success"){
                    window.location.href = "buyer_seller_goods_list";
                }
            });
    });
    $(".a_category_seller").on("click",function () {
        var type = $(this).text();
        $.get("buyer_goods_category_key",
            {"sellerId":$("#sellerId").val(),"type":type},
            function (data) {
                if(data === "success"){
                    window.location.href = "buyer_seller_goods_list";
                }
            });
    });
});
// 比较当前显示的总记录数，决定要不要显示加载更多
function checkAllCount() {
    if(parseInt($("#replyAllCount").val()) <= 5){
        $(".reply_load_more").css("display","none");
    }else {
        $(".reply_load_more").css("display","block").text("加载更多");
    }
}
// 去掉当前所有的a标签的选中
function removeChecked() {
    $(".reply_select").each(function () {
        $(this).removeClass("reply_checked");
    });
}
// 判断颜色和尺寸是否都被选择
function colorAndSizeCheck() {
    var colorFlag = false;
    var sizeFlag = false;
    $("input[name='goods_color']").each(function () {
        if($(this).prop("checked")){
            colorFlag = true;
        }
    });
    $("input[name='goods_size']").each(function () {
        if($(this).prop("checked")){
            sizeFlag = true;
        }
    });
    return (colorFlag && sizeFlag);
}

// 购物信息界面弹窗
function openSubmit() {
    $(".div_buy_goods").css("display","block").css("top",$("#div_sell_num").offset().top - 265);
    $("#div_close_buy_info").css("display","block");
    $("#div_submit").css("display","block");
}
// 购物信息界面弹窗关闭
function colseSubmit() {
    $("#div_close_buy_info").css("display","none");
    $(".div_buy_goods").css("display","none");
    $("#div_submit").css("display","none");
}
function clickCart() {
    // 如果尺寸和颜色没有选择
    if (!colorAndSizeCheck()) {
        // 当前为游客状态
        if (!$("#currBuyer").val()) {
            window.location.href = "buyer_login";
        } else {
            openSubmit();
            $(".input_submit").addClass("input_inactive").prop("disabled", "true");
            $("#to_submit").addClass("buyer_add_cart");
        }
    } else {
        addCart();
    }
}
function addCart() {
    // 弹窗添加购物车成功
    var color = $("input[name='goods_color']:checked").val();
    var size = $("input[name='goods_size']:checked").val();
    var goodsId = $("#goodsId").val();
    var buyCount = $("#sy_num_gid").val();
    $.get("add_cart",
        {"color":color,"size":size,"goodsId":goodsId,"buyCount":buyCount},
        function (data) {
            if(data === "success"){
                $("#add_cart_success").css("display","block");
                colseSubmit();
            }else if(data === "fail"){
                window.location.href = "buyer_login";
            }
        });
}
