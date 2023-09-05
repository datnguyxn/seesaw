$(document).ready(function() {
    // get cart id by token
    function getUserId() {
        const TOKEN = localStorage.getItem("token");
        let user_id;
        // console.log(TOKEN)
        $.ajax({
            url: "/api/user/get-user" ,
            type: "POST",
            data: TOKEN,
            async: false,
            contentType: "application/json",
            success: function (data) {
                console.log(data);
                user_id = data.id;
            },
            error: function (e) {
                console.log(e)
                console.log("error");
            }
        })
        // console.log(cart_id)
        return user_id;
    }
    // render your order
    $.ajax({
        url: "/api/orders/get-orders-of-user?user_id=" + getUserId(),
        type: "GET",
        async: false,
        contentType: "application/json",
        success: function (data) {
            console.log(data);
            if (data.length === 0) {
                $('.order-item__list').text('No order')
            } else {
                data.forEach(product => {
                    $('.order-item__list').append(`
                    <div class="order--item row d-flex justify-content-between m-0 pt-2" style="cursor: pointer;" data-id="${product.id}">
              <div class="col-md-4 col-12">
                <p>${product.name}</p>
              </div>
              <div class="col-md-2 col p-0">
                <p class="cart__item--price">
                  ${product.total_amount}
                </p>
              </div>
              <div class="col-md-2 col">
                <p>${product.createdAt}</p>
              </div>
              <div class="col-md-2 col">
                <p>
                  ${product.status}
                </p>
              </div>
              <div class="col-md-2 col">
                <a href="/order-detail/order/${product.id}">Detail</a>
              </div>
              
            </div>
                `)
                })
                clickOrderDetail()
            }
        },
        error: function (e) {
            console.log(e)
            console.log("error");
        }
    })
    // click to detail order
    function clickOrderDetail() {
        $('.order--item').click(function() {
            window.location.href = '/order-detail?id=' + $(this).data('id')
        })
    }

})