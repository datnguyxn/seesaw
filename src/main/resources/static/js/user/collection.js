$(document).ready(function () {
    $('.product-item').on('click', function (e) {
        window.location.href = `/product-detail?id=${$(this).data('id')}`;
    })
})