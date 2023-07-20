$(document).ready(function () {
    const formSignUp = $("#formSignUp")
    let errorText = $(".error")
    const email = $("#email");
    const password = $("#password");
    const confirmPassword = $("#confirmPassword");
    const gender = $("#gender");
    const contact = $("#contact");

    formSignUp.on("submit", function (e) {
        e.preventDefault()
        const data = JSON.stringify({
            email: email.val(),
            password: password.val(),
            confirmPassword: confirmPassword.val(),
            gender: gender.val(),
            contact: contact.val()
        });
        $.ajax({
            url: "/api/v1/auth/register",
            type: "POST",
            data: data,
            contentType: "application/json",
            success: function (data) {
                console.log(data)
                alert("Sign Up Success")
                window.location.href = "/login";
            },
            error: function (error) {
                console.log(error)
                errorText.text(error.responseJSON.message)
            }
        })
    })

    if (localStorage.getItem("email") !== null) {
        email.val(localStorage.getItem("email"));
        localStorage.removeItem("email");
    }
});