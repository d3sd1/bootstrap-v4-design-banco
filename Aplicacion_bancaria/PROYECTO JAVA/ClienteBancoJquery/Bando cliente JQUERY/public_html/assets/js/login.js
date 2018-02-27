$("#login").submit(function (event) {
    $.ajax({
        url: "URL DE LA API",
        beforeSend : function()
        {
            $("#loading").show();
        },
        success: function (result) {
            
        },
        complete: function()
        {
            $("#loading").hide();
        }
    });
    event.preventDefault();
});