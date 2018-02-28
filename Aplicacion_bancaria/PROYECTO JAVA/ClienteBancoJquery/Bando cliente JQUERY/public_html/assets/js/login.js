$("#login").submit(function (event) {
    $.ajax({
        url: API_REST_URL + "/login",
        type: "POST",
        data: loginFormToJson(),
        beforeSend : function()
        {
            $("#loading").show();
        },
        success: function (result) {
            console.log(result);
        },
        complete: function()
        {
            $("#loading").hide();
        }
    });
    event.preventDefault();
});
function loginFormToJson() {

  var returnArray = [],
      data = JSON.stringify($("#login").serializeArray());
      
  for (var i = 0; i < formArray.length; i++){
    returnArray[formArray[i]['name']] = formArray[i]['value'];
  }
  return returnArray;
}