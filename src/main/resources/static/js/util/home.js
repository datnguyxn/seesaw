$(document).ready(function () {

    // render categories
    $.ajax({
        url: '/api/categories/list',
        method: 'GET',
        contentType: 'application/json',
        success: function (data) {
            // console.log(data);
            const template = data.content.slice(0, 3).map((category, index) => {
                return `
                    <div class="category-item col-md-3 col-sm-6 col-10" style="max-width: 20rem" data-id="${category.id}">
                        <p class="category-name fw-bold mt-3">${category.name}</p>
                    </div>             
                `;
            })
            $('.categories__items').append(template);
            categoryClick();
        },
        error: function (error) {
            console.log(error)
        }
    })

    //render collections
    $.ajax({
        url: '/api/collections/list',
        method: 'GET',
        contentType: 'application/json',
        success: function (data) {
            // console.log(data);
            const collection = data.content.map((collection) => {
                return `
                     <div class="collection-item col-md-3 col-sm-6 col-10" style="max-width: 20rem" data-id="${collection.id}">
                        <img
                                class=""
                                src="${collection.image}"
                                alt="Card image cap"
                        />
                        <p class="category-name fw-bold mt-3">${collection.name}</p>
                    </div>          
                `;
            })
            $('.collections__items').append(collection);
            collectionClick();
        },
        error: function (error) {
            console.log(error)
        }
    })

    // render products
    $.ajax({
        url: '/api/products/list',
        method: 'GET',
        contentType: 'application/json',
        success: function (data) {
            // console.log(data);
            const product = data.content.slice(0, 3).map((product, index) => {
                return `
                    <div class="product-item col-3" style="width: 20rem" data-id="${product.id}">
                        <img
                                class=""
                                src="${product.image_path}"
                                alt="Card image cap"
                        />
                        <div class="mt-3">
                            <h5 class="">${product.name}</h5>
                            <p class="">${product.price}</p>
                        </div>
                    </div>            
                `;
            })
            $('.product__items').append(product);
            productClick();
        },
        error: function (error) {
            console.log(error)
        }
    })

    // click category
    function categoryClick() {
        $('.category-item').on('click', function (e) {
            const id = $(this).data('id');
            console.log(id);
            window.location.href = `/products?id=${id}`;
        })
    }

    // click collection
    function collectionClick() {
        $('.collection-item').on('click', function (e) {
            const id = $(this).data('id');
            console.log(id);
            window.location.href = `/products/collection?id=${id}`;
        })
    }

    // click product
    function productClick() {
        $('.product-item').on('click', function (e) {
            // const id = $(this).data('id');
            // console.log(id);
            window.location.href = `/product-detail?id=${$(this).data('id')}`;
        })
    }

    // click more products
    $('.products__list--button').on('click', function (e) {
        window.location.href = '/products';
    })
});