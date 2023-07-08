const formSignUp = $("#formSignUp")
let errorText = $(".error")
$(document).ready(function () {
    formSignUp.on("submit", function (e){
        e.preventDefault()
        const firstname = $("#firstName").val();
        const lastname = $("#lastName").val();
        const email = $("#exampleInputEmail1").val();
        const password = $("#exampleInputPassword1").val();
        const confirmPassword = $("#confirmPassword").val();
        const gender = $("#gender").val();
        const contact = $("#phoneNumber").val();
        const data = JSON.stringify({firstname, lastname, email, password, confirmPassword, gender, contact});
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
                errorText.text("error")
            }
        })
    })
});