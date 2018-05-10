$(function () {
    var colorNum = 0;
    $(".tr_goods_color").each(function () {
        colorNum++;
    });
    $(".new_color").attr("id",colorNum - 1);
});
/* 选择颜色 */
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