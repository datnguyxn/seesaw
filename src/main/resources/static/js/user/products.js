// js to get all products data
const products = $('.products');

//ajax request to get all products
$(document).ready(function () {
    $.ajax({
        url: "/products/list",
        type: "GET",
        dataType: "json",
        contentType: "application/json",
        success: function (data) {
            console.log(data);
        },
        error: function (error) {
            console.log("Error");
        }
    })
});


