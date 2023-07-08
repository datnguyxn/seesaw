// const signInForm = document.getElementById("formSignIn");

const signInForm = $("#formSignIn");
let errorText = $(".error");
$(document).ready(function () {
    signInForm.on("submit", function (e){
        e.preventDefault()
        const email = $("#exampleInputEmail1").val();
        const password = $("#exampleInputPassword1").val();
        const data = JSON.stringify({email, password});
        $.ajax({
            url: "/api/v1/auth/login",
            type: "POST",
            data: data,
            contentType: "application/json",
            success: function (data) {
                const {access_token, refresh_token} = data;
                localStorage.setItem('token', access_token);
                localStorage.setItem('refreshToken', refresh_token);
                window.location.href = "/";
            },
            error: function (error) {
                console.log(error)
                errorText.text("Email or password is incorrect")
            }
        })
    })
})
