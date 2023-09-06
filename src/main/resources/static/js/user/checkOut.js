$(document).ready(function () {
    const TOKEN = localStorage.getItem("token");
    const email = $("#email");
    const firstName = $("#firstName");
    const lastName = $("#lastName");
    const address = $("#address");
    const phone = $("#contact");
    const gender = $("#gender")
    const errorText = $(".error-text")
    const saveMe = $("#save-me")
    const formCheckOut = $("#formCheckOut")

    if (TOKEN == null) {
        window.location.href = "/auth/login";
    }

    saveMe.on("change", function (e) {
        if(saveMe.is(":checked")) {
            saveUser(email, firstName, lastName, address, phone, gender, errorText)
        } else {
            console.log("Not save")
        }
    })

    let id = getUserByToken(TOKEN, email, firstName, lastName, address, phone, gender, errorText)

    let productList = [];
    $.ajax({
        url: "/api/cart-detail/list?cart_id=" + getCartId(TOKEN),
        type: "GET",
        async: true,
        contentType: "application/json",
        timeout: 1000,
        success: function (data) {
            console.log(data.products)
            // productList = data.products
            // console.log(productList)
            data.products.forEach(product => {
                productList.push({
                    id: product.id,
                    quantity: product.quantity
                })
            })
        },
        error: function (e) {
            console.log(e)
            console.log("error");
        }
    })


    // addProductToOrder(id, TOKEN, email, firstName, lastName, address, phone, gender, errorText, getProductOfCart(TOKEN))
    formCheckOut.on("submit", function (e) {
        e.preventDefault()
        console.log(id)
        addProductToOrder(id, TOKEN, email, firstName, lastName, address, phone, gender, errorText, productList)
        alert("Check out success")
        deleteCartDetail(TOKEN)
        window.location.href = "/order"
    })

    // getProductOfCart(TOKEN)

})

function deleteCartDetail(token) {
    $.ajax({
        url: `/api/cart-detail/delete-all?cart_id=${getCartId(token)}`,
        type: "DELETE",
        success: function (data) {
            console.log(data)
        },
        error: function (error) {
            console.log(error)
        }
    })
}

function getUserByToken(token, email, firstName, lastName, address, phone, gender, errorText) {
    let id;
    $.ajax({
        url: "/api/user/get-user",
        type: "POST",
        data: token,
        async: false,
        contentType: "application/json",
        headers: {
            Authorization: "Bearer " + token,
        },
        success: function (data) {
            console.log(data)
            id = data.id
            email.val(data.email)
            firstName.val(data.firstname)
            lastName.val(data.lastname)
            address.val(data.address)
            phone.val(data.contact)
            gender.val(data.gender)
        },
        error: function (error) {
            console.log(error)
            console.log(error.responseJSON.message)
            errorText.text(error.responseJSON.message)
        }
    })
    return id;
}

function saveUser(email, firstName, lastName, address, phone, gender, errorText) {
    $.ajax({
        url: "/api/user/update",
        type: "PUT",
        data: JSON.stringify({
            firstname: firstName.val(),
            lastname: lastName.val(),
            email: email.val(),
            gender: gender.val(),
            contact: phone.val(),
            address: address.val()
        }),
        contentType: "application/json",
        success: function (data) {
            console.log(data)
            alert("Save information success")
        },
        error: function (error) {
            console.log(error)
            errorText.text(error.responseJSON.message)
        }
    })
}

function addProductToOrder(id, token, email, firstName, lastName, address, phone, gender, errorText, productList) {
    console.log(productList)
    $.ajax({
        url: "/api/orders/add",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify({
            firstName: firstName.val(),
            lastName: lastName.val(),
            email: email.val(),
            phone: phone.val(),
            address: address.val(),
            user_id: id,
            products: productList
        }),
        success: function (data) {
            console.log(data)
        },
        error: function (error) {
            console.log(error)
            errorText.text(error.responseJSON.message)
        }
    })
}


function getCartId(token) {
    let cart_id;
    $.ajax({
        url: "/api/user/get-cart" ,
        type: "POST",
        data: token,
        async: false,
        contentType: "application/json",
        success: function (data) {
            cart_id = data;
        },
        error: function (e) {
            console.log(e)
            console.log("error");
        }
    })
    return cart_id;
}