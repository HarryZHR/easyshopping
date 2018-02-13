$(function () {
   $("#front").on("change",function () {
       var formData = new FormData();
       var fileObj = $(this).get(0).files[0];
       formData.append("file",fileObj);
       $.ajax({
           url: 'up_img' ,
           type: 'POST',
           data: formData,
           async: false,
           cache: false,
           contentType: false,
           processData: false,
           success: function (returndata) {
               $("#img_front").prop("src",returndata);
               $("#input_front").val(returndata);
           },
           error: function (returndata) {
               alert("fail");
           }
       })
   });
   $("#back").on("change",function () {
       var formData = new FormData();
       var fileObj = $(this).get(0).files[0];
       formData.append("file",fileObj);
       $.ajax({
           url: 'up_img' ,
           type: 'POST',
           data: formData,
           async: false,
           cache: false,
           contentType: false,
           processData: false,
           success: function (returndata) {
               $("#img_back").prop("src",returndata);
               $("#input_back").val(returndata);
           },
           error: function (returndata) {
               alert("fail");
           }
       })
   });
   $("#hand").on("change",function () {
       var formData = new FormData();
       var fileObj = $(this).get(0).files[0];
       formData.append("file",fileObj);
       $.ajax({
           url: 'up_img' ,
           type: 'POST',
           data: formData,
           async: false,
           cache: false,
           contentType: false,
           processData: false,
           success: function (returndata) {
               $("#img_hand").prop("src",returndata);
               $("#input_hand").val(returndata);
           },
           error: function (returndata) {
               alert("fail");
           }
       })
   });
});