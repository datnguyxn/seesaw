// js to get all products data
const products = $('.products');
const type = $('#type').val();

$(document).ready(function () {
    renderCategoriesFilter();

    $('.product_card').on('click', function (e) {
        window.location.href = `/product-detail?id=${$(this).data('id')}`;
    })
});

function renderCategoriesFilter() {
    let html = '';
    let filterCategories = $('.filter-categories');
    if(type === 'all') {
        $('.choice.all').addClass('active');
    } else {
        $('.choice.all').removeClass('active');
    }

    $.ajax({
        url: '/api/categories/list',
        type: 'GET',
        success: function (data) {
            data.content.forEach(function (category) {
                html += `<a class="choice ${type=== category.id ? "active":"" } text-decoration-none text-dark" data-id="${category.id}" href="/products?type=${category.id}">${category.name}</a>`
            })
            filterCategories.append(html);
        },
        error: function (error) {
            console.log(error.responseText);
        }
    })
}


