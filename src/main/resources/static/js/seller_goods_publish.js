var i=0;
//选择颜色
function selectColor(check) {
    if(!check.is(":checked")){
        //如果是勾选状态，点击删除相关的颜色
        var coloId = check.parent().parent().attr("id");
        check.parent().parent().remove();
        $("#table_price_count").find("tr").each(function () {
            var idArr = $(this).attr("id").split("_");
            if(idArr[0] === coloId){
                $(this).remove();
            }
        });
        // 如果是非勾选状态，点击增加相关颜色的选项
    }else{
        i++;
        var trHtml = "<tr id="+ i +" class=\'tr_goods_color\'>" +
            "<td>" +
            "<input type=\'checkbox\' name=\'goods_color\' class=\'input_check_color\' onclick=\'selectColor($(this))\'/>" +
            "<input type=\'text\' size=\'10\' class=\'input_goods_color\' placeholder=\'颜色\' onblur=\'updColor($(this))\'/><br/> " +
            "<img src=\' \' class=\'img_goods_color\'/>" +
            "<input type=\'file\' id=\'color"+ i +"\' value=\'上传图片\' class=\'input_up_file\' accept=\'image/gif,image/jpeg,image/jpg,image/png\' onchange=\'upColorImg($(this))\'/>" +
            "<input type='button' class='btn btn-info input_up_css' value='上传图片'>" +
            "</td>" +
            "</tr>";
        $("#table_goods_color tr:last").after(trHtml);
        var color = check.next().val();
        if(color === ""){
            color = "颜色";
        }
        var colorImg = check.next().next().next()[0].src;
        var colorId = check.parent().parent().attr("id");
        // 如果尺寸被勾选，产生相应的表格
        var j = 0;
        $(".input_check_size").each(function () {
            if($(this).is(":checked")){
                var size = $(this).next().text();
                var trHtml2 = "<tr id=" + colorId + "_" + size +">" +
                    "<td><input type=\'hidden\' name=\'color\' value=\'" + color + "\' class=\'hidden_color\'/><input name=\'colorImg\' type=\'hidden\' value=\' "+ colorImg +" \'  class=\'hidden_color_img\'/><span class=\'span_color\'>" + color + "</span></td>" +
                    "<td><input type=\'hidden\' name=\'size\' value=\'" + size + "\'/><span>" + size + "</span></td>" +
                    "<td><input type=\'text\' name=\'price\' size=\'5\'></td>" +
                    "<td><input type=\'text\' name=\'count\' size=\'5\'></td>" +
                    "</tr>";
                j++;
                $("#table_price_count tr:last").after(trHtml2);
            }
        });
    }
}

//修改表格中相应位置的颜色
function updColor(input) {
    var colorId = input.parent().parent().attr("id");
    var color = input.val();
    $("#table_price_count").find("tr").each(function () {
        var idArr = $(this).attr("id").split("_");
        if(idArr[0] === colorId){
            $(this).children().find(".span_color").html(color);
            $(this).children().find(".hidden_color").val(color);
        }
    });
}

// 上传颜色分类的图片
function upColorImg(img) {
    var formData = new FormData();
    var imgId = img.attr("id");
    var fileObj = document.querySelector("#"+imgId).files[0];
    var colorId = img.parent().parent().attr("id");
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
            $(img).prev().attr("src",returndata);
            $("#table_price_count").find("tr").each(function () {
                var idArr = $(this).attr("id").split("_");
                if(idArr[0] === colorId){
                    $(this).children().find(".hidden_color_img").val(returndata);
                }
            });
        },
        error: function (returndata) {
            alert("fail");
        }
    });
}

// 删除上传的细节图片
function deleteImg(deleteBtn) {
    deleteBtn.prev().remove();
    deleteBtn.next().val("");
    deleteBtn.parent().remove();
    deleteBtn.remove();
}

$(function () {
    // 勾选尺寸
    $(".input_check_size").on("click",function () {
        // 如果尺寸是选中状态，删掉相应的尺寸
        if(!$(this).is(":checked")){
            var sizeId = $(this).next().text();
            $("#table_price_count").find("tr").each(function () {
                var idArr = $(this).attr("id").split("_");
                if(idArr[1] === sizeId){
                    $(this).remove();
                }
            })
            //尺寸没有选中，增加相应的尺寸
        }else {
            var size = $(this).next().text();
            var j = 0;
            $(".input_check_color").each(function () {
                if($(this).is(":checked")){
                    
                    var color = $(this).next().val();
                    var colorImg = $(this).next().next().next()[0].src;
                    var colorImgArr = colorImg.split("/");
                    var colorImgStr = colorImgArr[colorImgArr.length - 2] + "/" + colorImgArr[colorImgArr.length - 1];
                    if(color === ""){
                        color = "颜色";
                    }
                    var colorId = $(this).parent().parent().attr("id");
                    var trHtml2 = "<tr id=" + colorId + "_" + size +">" +
                        "<td><input type=\'hidden\' name=\'color\' value=\'" + color + "\' class=\'hidden_color\'/><input name=\'colorImg\' type=\'hidden\' value=\' "+ colorImgStr +" \'  class=\'hidden_color_img\'/><span class=\'span_color\'>" + color + "</span></td>" +
                        "<td><input type=\'hidden\' name=\'size\' value=\'" + size + "\'/><span>" + size + "</span></td>" +
                        "<td><input name=\'price\' type=\'text\' size=\'5\'></td>" +
                        "<td><input name=\'count\' type=\'text\' size=\'5\'></td>" +
                        "</tr>";
                    j++;
                    $("#table_price_count tr:last").after(trHtml2);
                }
            })
        }
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
                    $("#div_goods_detail_img").append("<div class=\'div_goods_detail_img_img\'><img src=\'"+ url[i] +"\' class=\'img_goods_detail\' /><img src=\'/img/delete.png\' class=\'img_delete_detail\' th:src=\'@{/img/delete.png}\' onclick='deleteImg($(this))'/> <input type=\'hidden\' name=\'input_img_detail\' value=\'"+ url[i] +"\'/></div>");
                }
                $(".img_delete_detail").css("display","inline");
            },
            error: function (data) {
                alert("fail");
            }
        });
    });
    $("")



});