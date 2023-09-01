$(document).ready(function () {
    // Back to shopping
    $('.checkout-button').on( "click", function() {
        window.location.href =  "/checkout";
    } )

    // render cart detail
    $.ajax({
        url: "/cart-detail/list?cart_id=" + getCartId(),
        type: "GET",
        async: true,
        contentType: "application/json",
        success: function (data) {
            console.log(data);
            $('.checkout--total-price').text(data.price)
            data.products.forEach(product => {
                $('.cart--detail').append(`
                    <div class="row d-flex align-items-center gap-2 m-0">
                            <div class="col-md-2">
                                <img src="${product.image}" alt="error">
                            </div>
                            <div class="col-md-4 cart__item--name">
                                <h4>${product.name}</h4>
                                <p class="cart__item--price">
                                    ${product.price}
                                </p>
                            </div>
                            <div class="col-md-4 quantity p-0">
                                <button class="minus-btn btn  disabled" type="button">-</button>
                                <input id="quantity" type="text" value="${product.quantity}" readonly>
                                <button class="btn plus-btn" type="button">+</button>
                            </div>
                            <div class="col cart--remove-item" data-id="${product.id}">
                                <u class="">Remove</u>
                            </div>
                        </div>
                `)
            })
            updateQuantity()
            removeItem()
        },
        error: function (e) {
            console.log(e)
            console.log("error");
        }
    })

    function getCartId() {
        const TOKEN = localStorage.getItem("token");
        let cart_id;
        // console.log(TOKEN)
        $.ajax({
            url: "/api/user/get-cart" ,
            type: "POST",
            data: TOKEN,
            async: false,
            contentType: "application/json",
            success: function (data) {
                // console.log(data);
                cart_id = data;
            },
            error: function (e) {
                console.log(e)
                console.log("error");
            }
        })
        // console.log(cart_id)
        return cart_id;
    }

    // Remove item from cart
    function removeItem() {
        $('.cart--remove-item').on( "click", function() {
            $.ajax({
                url: "/cart-detail/delete?product_id=" + $(this).data('id') + "&cart_id=" + getCartId(),
                type: "POST",
                async: true,
                contentType: "application/json",
                success: function () {
                    console.log("success");
                    window.location.href =  "/cart";
                },
                error: function (e) {
                    console.log(e)
                    console.log("error");
                }
            })
        })
    }
    // Update item quantity
    function updateQuantity() {

    }

})