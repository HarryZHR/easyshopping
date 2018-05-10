var colorNum = 0;
//选择颜色
function selectColor(check) {
    if(!check.is(":checked")){
        //如果是勾选状态，点击删除相关的颜色
        var coloId = check.parent().parent().attr("id");
        var colorTr = check.parent().parent();
        var standardTr = $("#"+coloId+"_color").parent();
        colorTr.remove();
        // 删除对应的规格表中的颜色
        standardTr.remove();
        // 如果是非勾选状态，点击增加相关颜色的选项
    }else{
        colorNum++;
        var trHtml = "<div id='"+ colorNum +"' class='tr_goods_color row'>" +
            "<div class=\"col-xs-3\">" +
            "<input type=\"checkbox\" name=\"goods_color\" class=\"input_check_color\" onclick=\"selectColor($(this))\"/>&nbsp" +
            ";<input type=\"text\" size=\"10\" class=\"input_goods_color\" placeholder=\"颜色\" onblur=\"updColor($(this))\"/><br/>" +
            "</div>" +
            "<div class=\"col-xs-1\">" +
            "<img src=\" \" class=\"img_goods_color\"/>" +
            "</div>" +
            "<div class=\"col-xs-2\">" +
            "<input id='color_"+ colorNum +"' type=\"file\" class=\"input_up_file\" accept=\"image/gif,image/jpeg,image/jpg,image/png\" onchange=\"upColorImg($(this))\"/>" +
            "<input type=\"button\" class=\"btn btn-info input_up_css\" value=\"上传图片\">" +
            "</div>" +
            "</div>";
        $("#table_goods_color").find(".tr_goods_color:last").after(trHtml);
        var color = check.next().val();
        if(color === ""){
            color = "颜色";
        }
        var colorImg = check.parent().next().find(".img_goods_color").attr("src");
        var colorId = check.parent().parent().attr("id");
        // 产生颜色相应的表格
        var html2 = "<div class='row color_tr'>" +
            "<div class='col-xs-3 color_content' id="+ colorId + '_color'+">" +
            "<span class=\"span_color\">"+ color +"</span>\n" +
            "<input type='hidden' class='hidden_color_color_img' value='"+ colorImg +"' />"+
            "</div>" +
            "<div class='col-xs-9 padding count_count'>" +
            "<div class='price_count_tr' style='height: 0'></div>" +
            "</div>" +
            "</div>";
        $("#table_price_count").find(".color_tr:last").after(html2);
        // 如果尺寸被勾选，产生颜色对应的尺寸表格
        $(".input_check_size").each(function () {
            if($(this).is(":checked")){
                var size = $(this).next().text();
                var html3 = "<div class='row standard_info price_count_tr "+size+"'>" +
                    "<div class=\"col-xs-4\">\n" +
                    "<input type=\"hidden\" class='hidden_size' value=" + size + ">\n" +
                    "<input type=\"hidden\" class=\"hidden_color\" value=" + color + ">\n" +
                    "<input type=\"hidden\" class='hidden_color_img "+colorId+"_colorImg' value=" + colorImg + ">\n" +
                    "<span>" + size + "</span>\n" +
                    "</div>\n" +
                    "<div>\n" +
                    "<div>\n" +
                    "<div class=\"col-xs-4\">\n" +
                    "<input type=\"text\" class='standard_price' size=\"5\"/>\n" +
                    "</div>\n" +
                    "<div class=\"col-xs-4\">\n" +
                    "<input type=\"text\" class='standard_count' size=\"5\"/>\n" +
                    "</div>\n" +
                    "</div>\n" +
                    "</div>\n" +
                    "</div>";
                $("#table_price_count").find(".count_count:last").append(html3);
            }
        });
    }
    updateStandardTr();
}

// 上传颜色分类的图片
function upColorImg(img) {
    var formData = new FormData();
    var imgId = img.attr("id");
    var colorId = imgId.split("_")[1];
    var fileObj = document.querySelector("#"+imgId).files[0];
    var colorImg = img.parent().prev().find("img");
    var $color = $("#"+colorId+"_color");
    formData.append("file", fileObj);
    $.ajax({
        url: 'up_img' ,
        type: 'POST',
        data: formData,
        async: false,
        cache: false,
        contentType: false,
        processData: false,
        success: function (returndata) {
            colorImg.attr("src",returndata);
            $color.find(".hidden_color_color_img").val(returndata);
            $color.next().find(".hidden_color_img").each(function () {
                $(this).val(returndata);
            });
        },
        error: function () {
            alert("fail");
        }
    });
}

//修改表格中相应位置的颜色
function updColor(input) {
    var colorId = input.parent().parent().attr("id");
    var color = input.val();
    if(color === ""){
        color = "颜色";
    }
    var $color = $("#"+colorId+"_color");
    $color.find(".span_color").text(color);
    $("."+colorId+"_colorImg").each(function () {
        $(this).prev().val(color);
    });
}



// 删除上传的细节图片
function deleteImg(deleteBtn) {
    deleteBtn.prev().remove();
    deleteBtn.next().val("");
    deleteBtn.parent().remove();
    deleteBtn.remove();
}

// 给生成都规格表赋予name属性
function updateStandardTr() {
    var standardTr = 0;
    $(".standard_info").each(function () {
        $(this).find(".hidden_color").prop("name","standards["+standardTr+"].color");
        $(this).find(".hidden_color_img").prop("name","standards["+standardTr+"].colorImg");
        $(this).find(".hidden_size").prop("name","standards["+standardTr+"].size");
        $(this).find(".standard_price").prop("name","standards["+standardTr+"].price");
        $(this).find(".standard_count").prop("name","standards["+standardTr+"].count");
        standardTr++;
    });
}

function updateColorAndImg() {
    $(".standard_info").each(function () {
        var color = $(this).parent().parent().find(".span_color").text();
        var colorImg = $(this).parent().parent().find(".hidden_color_color_img").val();
        $(this).find(".hidden_color").val(color);
        $(this).find(".hidden_color_img").val(colorImg);
    });
}

$(function () {
    updateStandardTr();
    // 勾选尺寸
    $(".input_check_size").on("click",function () {
        // 如果尺寸是选中状态，删掉相应的尺寸
        if(!$(this).is(":checked")){
            var sizeId = $(this).next().text();
            $("#table_price_count").find("."+sizeId).each(function () {
                $(this).remove();
            });

        //尺寸没有选中，增加相应的尺寸
        }else {
            var size = $(this).next().text();
            var html4 = "<div class='row standard_info price_count_tr "+size+"'>\n" +
                "<div class=\"col-xs-4\">\n" +
                "<input type=\"hidden\" class='hidden_size' value=" +size+ " />\n" +
                "<input type='hidden' class='hidden_color' />" +
                "<input type='hidden' class='hidden_color_img' />" +
                "<span>" + size + "</span>\n" +
                "</div>\n" +
                "<div class=\"col-xs-4\">\n" +
                "<input class='standard_price' type=\"text\" size=\"5\"/>\n" +
                "</div>\n" +
                "<div class=\"col-xs-4\">\n" +
                "<input class='standard_count' type=\"text\" size=\"5\"/>\n" +
                "</div>\n" +
                "</div>";
            $("#table_price_count").find(".count_count").find(".price_count_tr:last").after(html4);
            updateColorAndImg();
        }
        updateStandardTr();
    });
    // 上传主图
    $(".input_up_goods_main").on("change",function () {
        var formData = new FormData();
        var fileObj = $(this).get(0).files[0]; //document.querySelector("#").files[0];
        formData.append("file", fileObj);
        var file = $(this);
        $.ajax({
            url: 'up_img' ,
            type: 'POST',
            data: formData,
            async: false,
            cache: false,
            contentType: false,
            processData: false,
            success: function (returndata) {
                file.next().next().css("display","block");
                file.next().css("display","none");
                file.prev().prop("src",returndata);
                file.prev().css("display","block");
                file.css("display","none");
                file.next().next().next().val(returndata);
            },
            error: function (returndata) {
                alert("fail");
            }
        });
    });
    // 删除主图
    $(".img_delete").on("click",function () {
        $(this).parent().find(".img_goods_main").css("display","none");
        $(this).parent().find(".input_up_goods_main").css("display","block");
        $(this).parent().find(".i_add_img").css("display","block");
        $(this).css("display","none");
        $(this).parent().find(".input_src").prop("src","");
    });
    // 上传细节图
    $("#input_up_goods_detail").on("change",function () {
        var formData = new FormData();
        var files = $("#input_up_goods_detail").get(0).files;
        for (var i = 0; i < files.length; i++){
            formData.append("files",files[i]);
        }
        $.ajax({
            url: "up_img_multi",
            type: "POST",
            data: formData,
            async: false,
            cache: false,
            contentType: false,
            processData: false,
            success: function (returndata) {
                var url = returndata.split("_");
                for (var i = 0 ; i < url.length - 1; i++){
                    $("#div_goods_detail_img").append("<div class=\'div_goods_detail_img_img\'><img src=\'"+ url[i] +"\' class=\'img_goods_detail\' /><img src=\'/img/delete.png\' class=\'img_delete_detail\' onclick='deleteImg($(this))'/> <input type=\'hidden\' name=\'input_img_detail\' value=\'"+ url[i] +"\'/></div>");
                }
                $(".img_delete_detail").css("display","inline");
            },
            error: function () {
                alert("fail");
            }
        });
    });
    // 判断颜色的id
    $(".tr_goods_color").each(function () {
        colorNum++;
    });
    colorNum--;
    $(".new_color").attr("id",colorNum);
    $(".new_up_file").attr("id","color_"+colorNum);

    // 主图上传的设置
    $(".img_goods_main").each(function () {
        if(!$(this).attr("src")){
            $(this).parent().find(".img_delete").css("display","none");
            $(this).css("display","none");
        }else {
            $(this).parent().find(".input_up_goods_main").css("display","none");
            $(this).parent().find(".i_add_img").css("display","none");
        }
    });

    // 详情图上传的设置
    $(".img_goods_detail").each(function () {
        if(!$(this).attr("src")){
            $(this).parent().find(".img_delete_detail").css("display","none");
            $(this).css("display","none");
        }
    });

});