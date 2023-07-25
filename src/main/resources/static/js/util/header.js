$(document).ready(function (e) {
    const btnPerson = $('.btn-person')
    const btnCheck = $('#btn-check')
    const checkToken = $('.check-token')
    const ulDisplay = $('.ul-display')
    const checkAuth = $('.check-auth')

    checkAuth.on('click', (e) => {
        e.preventDefault()
        if (localStorage.getItem("token") === null) {
            window.location.href = '/auth/login'
        } else {
            window.location.href = '/cart'
        }
    })

    checkToken.on('click', (e) => {
        e.preventDefault()
        if (localStorage.getItem("token") === null) {
            window.location.href = '/auth/login'
        } else {
           ulDisplay.removeClass('d-none')
        }
    })


    btnCheck.on('click', (e) => {
        e.preventDefault()
        if (localStorage.getItem("token") !== null) {
            window.location.href = '/user/account-setting'
        } else {
            window.location.href = '/auth/login'
        }
    })

    btnPerson.on('click', (e) => {
        e.preventDefault()
        if (localStorage.getItem("token") !== null) {
            localStorage.removeItem("token")
            localStorage.removeItem("refreshToken")
            window.location.href = '/auth/login'
        } else {
            window.location.href = '/'
        }
    })
})
