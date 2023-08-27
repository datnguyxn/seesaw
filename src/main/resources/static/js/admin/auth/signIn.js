const signInFormAdmin = $("#formSignInAdmin");
const rememberMe = $("#remember-me");
const email = $("#email");
const password = $("#password");
let errorText = $(".error");

if (localStorage.getItem('emailLogin') !== null && localStorage.getItem('password') !== null) {
    email.val(localStorage.getItem('emailLogin'))
    password.val(localStorage.getItem('password'))
    rememberMe.prop("checked", true)
}

signInFormAdmin.on("submit", function (e) {
    e.preventDefault()
    // const data = JSON.stringify({email, password});
    $.ajax({
        url: "/api/v1/auth/login",
        type: "POST",
        data: JSON.stringify({email: email.val(), password: password.val()}),
        contentType: "application/json",
        success: function (data) {
            const {access_token, refresh_token} = data;
            localStorage.setItem('token', access_token);
            localStorage.setItem('refreshToken', refresh_token);
            window.location.href = "/admin/dashboard"
        },
        error: function (error) {
            console.log(error)
            errorText.text(error.responseJSON.message)
            if (error.responseJSON.message === "Bad credentials") {
                errorText.text("Email or password is incorrect")
            }
        }
    })
})