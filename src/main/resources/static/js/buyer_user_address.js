$(function () {
     $(".set_default").on("click",function () {
         var addressId = $(this).parent().find(".address_id").val();
         var $set = $("#all_address").find(".set_default");
         var $this = $(this);
         if($(this).text() === '设为默认'){
             $.get("buyer_set_address_default",
                 {"addressId":addressId},
                 function (data) {
                     if(data === "success"){
                         $set.each(function () {
                             $(this).text('设为默认');
                         });
                         $this.text('默认地址');
                     }else {
                         window.location.href = "buyer_login";
                     }
                 });
         }
     });
     $(".add_address").on("click",function () {
         $(".div_add_address").css("display","block");
     });
     $(".address_edit").on("click",function () {
         $(".div_add_address").css("display","block");
         var province = $(this).parent().parent().find(".span_province").text();
         var city = $(this).parent().parent().find(".span_city").text();
         var district = $(this).parent().parent().find(".span_district").text();
         var street = $(this).parent().parent().find(".span_street").text();
         var receiver = $(this).parent().parent().find(".span_receiver").text();
         var tel = $(this).parent().parent().find(".span_tel").text();
         var addressId = $(this).parent().find(".address_id").val();
         var $province = $("#province");
         var $city = $("#city");
         var $district = $("#district");
         var $street = $("#addressStreet");
         var $receiver = $("#receiver");
         var $tel = $("#tel");
         $street.text(street);
         $receiver.val(receiver);
         $tel.val(tel);
         $("#addressId").val(addressId);
         $province.find("option").removeAttr("selected");
         $city.find("option").removeAttr("selected");
         $district.find("option").removeAttr("selected");
         $province.find("option").each(function(){
             if($(this).val() === province){
                 $(this).attr('selected',true);
                 $(this).parent().change();
             }
         });
         $city.find("option").each(function(){
             if($(this).val() === city){
                 $(this).attr('selected',true);
                 $(this).parent().change();
             }
         });
         $district.find("option").each(function(){
             if($(this).val() === district){
                 $(this).attr('selected',true);
             }
         });
     });
     $(".address_delete").on("click",function () {
         var address = $(this).parent().parent();
         var addressId = $(this).parent().find(".address_id").val();
         if(confirm("确认删除该条地址？")){
             $.get("buyer_delete_address",
                 {"addressId":addressId},
                 function (data) {
                     if(data === "success"){
                         address.remove();
                     }else {
                         window.location.href = "buyer_login";
                     }
                 });
         }
     });

});