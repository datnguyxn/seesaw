$(document).ready(function (e) {
    const btnPerson = $('.btn-person')
    const btnCheck = $('#btn-check')

    btnCheck.on('click', (e) => {
        e.preventDefault()
        if (localStorage.getItem("token") !== null) {
            window.location.href = '/user/account-setting'
        } else {
            window.location.href = '/login'
        }
    })

    btnPerson.on('click', (e) => {
        e.preventDefault()
        if (localStorage.getItem("token") !== null) {
            localStorage.clear()
            window.location.href = '/login'
        } else {
            window.location.href = '/'
        }
    })
})
