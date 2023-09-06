$(document).ready(function () {

    // ajax render product detail
    $.ajax({
        url: "/api/products/info",
        type: "GET",
        data: {
            id: window.location.href.split("=")[1],
        },
        async: true,
        contentType: "application/json",
        success: function (data) {
            console.log(data);
            id_product = data.id;
            const template = `
<div class="d-flex justify-content-center row m-5 min-vh-100">
    <div class="content__image col-md-4 col-10" >
                    <img src="${data.image_path}" alt="error">
                </div>
                <div class="content__information col-md-4 col-10 mx-2">
                    <div class="content__information--title">
                        <h2>${data.name}</h2>
                        <p class="content__information--title fw-light text-end">${data.price.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")} VND</p>
                    </div>
                    <div class="content__body">
                        <p class="content__information--description">
                            ${data.description}
                        </p>
                        <p class="content__information--collection">
                            in ${data.brand}
                        </p>
                        <div class="content__information--quantity">
                            <p>Quantity</p>
                            <div class="quantity">
                                <button class="minus-btn btn  disabled" type="button">-</button>
                                <input id="number-quantity" type="text" value="1" readonly>
                                <button class="btn plus-btn" type="button">+</button>
                            </div>
                        </div>
                        <div class="content__button--add">
                            <button class="addToCart--button collections__button mb-5 mt-2" role="button">
                                Add to cart
                            </button>
                        </div>
                    </div>
                </div>
</div>
                
            `;
            $(".productDetail__content").html(template);
            assignQuantityListener()
            addToCart()
            // getCartId()
        },
        error: function (error) {
            console.log(error);
        }
    })

    //add to cart
    function addToCart() {
        $(".addToCart--button").on('click', function (e) {
            e.preventDefault();
            console.log(id_product)
            console.log(getCartId())
            // console.log(typeof Number($("#number-quantity").val()))
            const TOKEN = localStorage.getItem("token");
            if (TOKEN == null) {
                window.location.href = "/auth/login"
            }

            console.log("click add to cart")
            $.ajax({
                url: "/api/cart-detail/add",
                type: "POST",
                data: JSON.stringify({
                    product_id: id_product,
                    cart_id: getCartId(),
                    quantity: Number($("#number-quantity").val())
                }),
                async: false,
                contentType: "application/json",
                success: function () {
                    console.log("success");
                    alert("Add to cart successfully!")
                    window.location.href = "/cart"
                },
                error: function (e) {
                    console.log(e)
                    console.log("error");
                }
            })
        });
    }

    // get cart id by token
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

    // assign quantity button
    function assignQuantityListener() {
        //setting default attribute to disabled
        document.querySelector(".minus-btn").setAttribute("disabled", "disabled");


        //plus button
        document.querySelector(".plus-btn").addEventListener("click", function() {
            //getting value of input
            var valueCount = document.getElementById("number-quantity").value;
            //input value increment by 1
            valueCount++;
            //setting increment input value
            document.getElementById("number-quantity").value = valueCount;

            //input value is > 1 using removeattribute and removeclass method
            if (valueCount > 1) {
                document.querySelector(".minus-btn").removeAttribute("disabled");
                document.querySelector(".minus-btn").classList.remove("disabled")
            }

        })

        // for minus button
        document.querySelector(".minus-btn").addEventListener("click", function() {
            var valueCount = document.getElementById("number-quantity").value;
            //input value decrement by 1
            valueCount--;
            document.getElementById("number-quantity").value = valueCount;
            console.log(valueCount);
            if (valueCount === 1) {
                document.querySelector(".minus-btn").setAttribute("disabled", "disabled")

            }
        })
    }


})