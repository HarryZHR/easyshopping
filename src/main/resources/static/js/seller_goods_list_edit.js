$(function () {
   $(".delete").on("click",function () {
       var goodsId = $(this).parent().find(".goodsId").val();
       var tr = $(this).parent().parent();
       if(confirm("是否确认删除该商品？")){
           $.get("seller_delete_goods",
               {"goodsId":goodsId},
               function (returndata) {
                   if(returndata === "true"){
                        tr.remove();
                   }
               })
       }
   });
});