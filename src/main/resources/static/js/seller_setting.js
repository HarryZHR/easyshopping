$(function () {
    // 省市级联的选择
    $("#distpicker").distpicker({
      autoSelect: false
    });
    // 省市级联赋值
    var province = $("#province_val").val();
    var city = $("#city_val").val();
    var district = $("#district_val").val();
    var $province_option = $("#province option");
    $province_option.removeAttr("selected");
    $("#city option").removeAttr("selected");
    $("#district option").removeAttr("selected");
    $province_option.each(function(){
        if($(this).val() === province){
            $(this).attr('selected',true);
            $(this).parent().change();
        }
    });
    $("#city option").each(function(){
        console.log($(this).val());
        if($(this).val() === city){
            $(this).attr('selected',true);
            $(this).parent().change();
        }
    });
    $("#district option").each(function(){
        if($(this).val() === district){
            $(this).attr('selected',true);
        }
    });
    var $province = $("#province");
    var $city = $("#city");
    var $district = $("#district");
  $(".input_seller_icon").on("change",function () {
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
              $("#img_seller_icon").prop("src",returndata);
              $("#input_seller_icon").val(returndata);
          },
          error: function (data) {
              alert("fail");
          }
      });
  }).on("focus",function () {
      $("#span_save_tip").html('');
  });
  var $span_name_error = $("#span_name_error");
  $("#seller_name").on("blur",function () {
     if($(this).val() === ""){
         $span_name_error.css("display","block").html("店铺名称不能为空");
     }
  }).on("focus",function () {
      $("#span_save_tip").html('');
      $span_name_error.css("display","none");
  });
  $("#textarea_introduce").on("focus",function () {
      $("#span_save_tip").html('');
  });
  $province.on("focus",function () {
      $("#span_save_tip").html('');
  });
  $city.on("focus",function () {
      $("#span_save_tip").html('');
  });
  $district.on("focus",function () {
      $("#span_save_tip").html('');
  });
  $("#street").on("focus",function () {
      $("#span_save_tip").html('');
  });
    $("input[class='goods_from']").each(function () {
       $(this).on("focus",function () {
            $("#span_save_tip").html('');
       });
    });
  $("#input_seller_save").on("click",function () {
    var storeName = $("#seller_name").val();
    var seller_icon = $("#input_seller_icon").val();
    var seller_introduce = $("#textarea_introduce").val();
    var seller_province = $("#province").val();
    var seller_city = $("#city").val();
    var seller_district = $("#district").val();
    var seller_street = $("#street").val();
    var goods_from = $("input[class='goods_from']:checked").val();
    $.get("seller_update",
        {"storeName":storeName,"icon":seller_icon,
            "introduce":seller_introduce,"sellerProvince":seller_province,
            "sellerCity":seller_city,"sellerDistrict":seller_district,
            "sellerStreet":seller_street,"supply":goods_from},
        function (data) {
            if(data === "true"){
                $("#span_save_tip").html("保存成功");
            }
        });
  });
});
