$(document).ready(function () {

    // click event to show search
    $('.search--button').on('click', () => {
        search();
    })

    // enter event to show search
    $('.search-box').on('keypress', (e) => {
        if (e.key === 'Enter') {
            e.preventDefault();
            search();
        }
    })

    //search function
    function search() {
        $.ajax({
            url: '/api/products/search',
            type: 'GET',
            data: {
                value: $('#search').val(),
            },
            success: function (data) {
                console.log(data);
                if ($('#search').val() === '') {
                    $('.product-list').html('Type your search, please!');
                    return;
                }
                if (data.length === 0) {
                    $('.product-list').append(`<p>No result</p>`)
                } else {
                    const result = data.map((product) => {
                        return `
                        <div class="product-item col-lg-3 col-md-6 mb-4" data-id="${product.id}">
                            <div class="product-img">
                                <a>
                                    <img src="${product.image_path}" alt="error">
                                </a>
                            </div>
                            <div class="product-content">
                                <h3 class="title">${product.name}</h3>
                                <div class="price">
                                    ${product.price}
                                </div>
                            </div>
                        </div>
                        `
                    })
                    $('.product-list').html(result);
                    assignProductListener();
                }

            },
            error: function(error) {
                console.log(error);
                console.log('error');
            }
        })
    }

    // assign product item
    function assignProductListener() {
        $('.product-item').on('click', function (e) {
            console.log("click")
            window.location.href = `/product-detail?id=${$(this).data('id')}`;
        })
    }
})