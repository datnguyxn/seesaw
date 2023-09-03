// js to get all products data
const productTable = $('#productTable');
const tbody = $('#productTable tbody');

let myModal = new bootstrap.Modal(document.getElementById('editProductModal'), options)

// ajax request to get all products
$(document).ready(function () {
    ajaxAllProduct();
    ajaxAllCollectionsName();
    ajaxAllCategoryName();
    uploadImage();
    ajaxAddProduct();
    ajaxEditProduct();
    ajaxDeleteProduct();
});

function uploadImage() {
    let imageFile = $('#addProductModal #image_file');
    let imagePreview = $('#addProductModal #image_preview');
    let icon = $('#addProductModal .custum-file-upload .icon');
    let imagePath = $('#addProductModal .product_inputs #product_image_path');

    imageFile.on('change', function () {
        let file = this.files[0];
        if (file) {
            let reader = new FileReader();
            reader.onload = function (event) {
                imagePreview.attr('src', event.target.result);
                let imageText = imageFile.val().split('\\').pop();
                imagePath.val(imageText);
                imagePreview.css('display', 'block');
                icon.css('display', 'none');
            }
            reader.readAsDataURL(file);
        }
    })
}

function registerPaginationEvent() {
    const pageLinks = $('.page-link');
    pageLinks.on('click', function (e) {
        e.preventDefault();
        if($(this).parent().hasClass('disabled')) {
            return;
        }
        const page = $(this).data('page');
        ajaxAllProduct(page);
    });
}

function ajaxAllProduct(
    page = 0,
    size =5
) {
    let tbody = $('#productTable tbody');
    let html = "";
    let index = 1;
    $.ajax({
        url: `/api/products/list?page=${page}&size=${size}`,
        type: "GET",
        dataType: "json",
        contentType: "application/json",
        async: false,
        success: function (data) {
            const {
                totalPages,
                number
            } = data;
            console.log("totalPages: " + totalPages);
            console.log("number: " + number);

            const paginationTemplate = renderPagnigation(totalPages, number);
            $('.pagination').html(paginationTemplate);
            registerPaginationEvent();
            $.each(data.content, function (key, value) {
                html +=
                    `
                        <tr id="${value.id}">
                                <td>${index++}</td>
                                <td>
                                    <img src="${value?.image_path ? value.image_path : '/images/default_product_placeholder.png'}"
                                         alt="Glasses" width="200px" height="120px" style="object-fit: cover;">
                                </td>
                                <td style="min-width: 200px;">
                                    <p>${value.name}</p>
                                </td>
                                <td>
                                    <p>${value.brand}</p>
                                </td>
                                <td>
                                    <p>${value.collection_name}</p>
                                </td>
                                <td>
                                    <p>${value.category_name}</p>
                                </td>
                                <td>
                                    <p>${value.description}</p>
                                </td>
                                <td>
                                    <p class="fw-bold">${value.price} VNĐ</p>
                                </td>
                                <td>
                                    <p class="text-danger fw-bold text-center">${value.quantity}</p>
                                </td>
                                <td>
                                    <div class="d-flex flex-row gap-1">
                                        <button class="editBtn btn btn-primary p-1 m-1" data-bs-toggle="modal"
                                                data-bs-target="#editProductModal">Edit
                                        </button>
                                        <button class="deleteBtn btn btn-danger p-1 m-1" data-bs-toggle="modal"
                                                data-bs-target="#deleteProductModal">Delete
                                        </button>
                                    </div>
                            </tr>
                    `;
            })
            tbody.html(html);
        },
        error: function (error) {
            console.log("Error");
        }
    })

}

function ajaxAllCollectionsName() {
    let collectionSelect = $('.product_collection');
    let html = "";
    $.ajax({
        url: "/api/collections/list",
        type: "GET",
        dataType: "json",
        contentType: "application/json",
        async: false,
        success: function (data) {
            $.each(data.content, function (key, value) {
                html +=
                    `
                        <option id="${value.id}" value="${value.name}">${value.name}</option>
                    `;
            })
            $.each(collectionSelect, function (key, value) {
                $(value).append(html);
            });
        },
        error: function (error) {
            console.log("Error");
        }

    })
}

function ajaxAllCategoryName() {
    let categorySelect = $('.product_category');
    let html = "";
    $.ajax({
        url: "/api/categories/list",
        type: "GET",
        dataType: "json",
        contentType: "application/json",
        async: false,
        success: function (data) {
            $.each(data.content, function (key, value) {
                html +=
                    `
                        <option id="${value.id}" value="${value.name}">${value.name}</option>
                    `;
            })
            $.each(categorySelect, function (key, value) {
                $(value).append(html);
            });
        },
        error: function (error) {
            console.log("Error");
        }

    })
}

function ajaxAddProduct() {
    let addProductModal = $('#addProductModal');
    let saveBtn = $('#addProductModal .saveBtn');
    let closeBtn = $('#addProductModal .closeBtn');


    saveBtn.on('click', function () {
        const data = new FormData();
        let imageFile = $('#addProductModal #image_file');

        data.append("name", addProductModal.find('#product_name').val());
        data.append("brand", addProductModal.find('#product_brand').val());
        data.append("collection_id", addProductModal.find('#product_collection').find('option:selected').attr('id'));
        data.append("category_id", addProductModal.find('#product_category').find('option:selected').attr('id'));
        data.append("description", addProductModal.find('#product_description').val());
        data.append("price", addProductModal.find('#product_price').val());
        data.append("quantity", addProductModal.find('#product_quantity').val());
        if(imageFile[0].files[0] != null){
            data.append("image_path", imageFile[0].files[0]);
        }

        $.ajax({
            url: "/api/products/add",
            type: "POST",
            contentType: false,
            processData: false,
            enctype: 'multipart/form-data',
            cache: false,
            async: false,
            data: data,
            success: function (data) {
                console.log("Success Add Product");
                window.location.reload();
            },
            error: function (error) {
                console.log("Error Add Product");
            }
        })
    })

    $.each(closeBtn, function (key, value) {
        $(value).on('click', function () {
            clearInputModal('#editProductModal');
        });
    });
}

function ajaxEditProduct() {
    let editProductModal = $('#editProductModal');
    let saveBtn = $('#editProductModal .saveBtn');
    let closeBtn = $('#editProductModal .closeBtn');
    let allRow = $('#productTable tbody tr');
    let imagePreview2 = $('#image_preview2');
    let imageFile2 = $('#image_file2');
    let icon2 = $('.custum-file-upload .icon2');
    icon2.css('display', 'none');

    allRow.each(function (index, element) {
        $(element).find('button.editBtn').on('click', function () {
            let id = $(element).attr('id');
            let name = $(element).find('td:nth-child(3)').text().replace(/\s+/g, ' ').trim();
            let brand = $(element).find('td:nth-child(4)').text().replace(/\s+/g, ' ').trim();
            let collection = $(element).find('td:nth-child(5)').find('p').text().replace(/\s+/g, ' ').trim();
            let category = $(element).find('td:nth-child(6)').find('p').text().replace(/\s+/g, ' ').trim();
            let description = $(element).find('td:nth-child(7)').text().replace(/\s+/g, ' ').trim();
            let price = $(element).find('td:nth-child(8)').text().replace(/\s+/g, ' ').trim().replace(' VNĐ', '');
            let quantity = $(element).find('td:nth-child(9)').text().replace(/\s+/g, ' ').trim();
            let image = $(element).find('td:nth-child(2)').find('img').attr('src').replace(/\s+/g, ' ').trim();
            console.log(image)

            editProductModal.find('#product_id').val(id);
            editProductModal.find('#product_name').val(name);
            editProductModal.find('#product_brand').val(brand);
            editProductModal.find('#product_description').val(description);
            editProductModal.find('#product_price').val(price);
            editProductModal.find('#product_quantity').val(quantity);
            editProductModal.find('#product_image_path').val(image);
            editProductModal.find('#product_collection').find('option').each(function (index, element) {
                if ($(element).val() == collection) {
                    $(element).attr('selected', 'selected');
                }
            });
            editProductModal.find('#product_category').find('option').each(function (index, element) {
                if ($(element).val() == category) {
                    $(element).attr('selected', 'selected');
                }
            });

            const data = new FormData();
            let imageFile = $('#editProductModal #image_file2');
            data.append("name", editProductModal.find('#product_name').val());
            data.append("brand", editProductModal.find('#product_brand').val());
            data.append("collection_id", editProductModal.find('#product_collection').find('option:selected').attr('id'));
            data.append("category_id", editProductModal.find('#product_category').find('option:selected').attr('id'));
            data.append("description", editProductModal.find('#product_description').val());
            data.append("price", editProductModal.find('#product_price').val());
            data.append("quantity", editProductModal.find('#product_quantity').val());

            icon2.css('display', 'none');
            imagePreview2.css('display', 'block');
            imagePreview2.attr('src', image);
            imageFile2.on('change', function () {
                let file = this.files[0];
                if (file) {
                    let reader = new FileReader();
                    reader.onload = function (event) {
                        imagePreview2.attr('src', event.target.result);
                        let imageText = imageFile2.val().split('\\').pop();
                        editProductModal.find('#product_image_path').val(imageText);
                        imagePreview2.css('display', 'block');
                        icon2.css('display', 'none');
                    }
                    reader.readAsDataURL(file);
                }
            });


            saveBtn.on('click', function () {
                if(imageFile[0].files[0] != null){
                    data.append("image_path", imageFile[0].files[0]);
                }
                console.log(data)
                $.ajax({
                    url: "/api/products/update?id=" + id,
                    type: "PUT",
                    contentType: false,
                    processData: false,
                    enctype: 'multipart/form-data',
                    cache: false,
                    async: false,
                    data: data,
                    success: function (data) {
                        console.log("Success Update Product");
                        window.location.reload();
                    },
                    error: function (error) {
                        console.log(error.responseText)
                        console.log("Error Update Product");
                    }
                })
            })
        });
    });
}

function ajaxDeleteProduct() {
    let deleteBtn = $('#deleteProductModal .delete_button');
    let allRow = $('#productTable tbody tr');

    allRow.each(function (index, element) {
        $(element).find('button.deleteBtn').on('click', function () {
            let id = $(element).attr('id');
            deleteBtn.on('click', function () {
                console.log("Delete Product Confirm Click")
                $.ajax({
                    url: "/api/products/delete?id=" + id,
                    type: "DELETE",
                    dataType: "json",
                    contentType: "application/json",
                    async: false,
                    success: function (data) {
                        console.log("Success Delete Product");
                        window.location.reload();
                    },
                    error: function (error) {
                        console.log("Error Delete Product");
                    }
                })
            })
        });
    });
}

function clearInputModal(elementId) {
    let modal = $(elementId);
    let input = modal.find('input');
    input.val('');
    let select = modal.find('select');
    select.val('');
    let textarea = modal.find('textarea');
    textarea.val('');
    let imagePreview = modal.find('#image_preview');
    imagePreview.attr('src', '');
    imagePreview.css('display', 'none');
    let icon = modal.find('.custum-file-upload .icon');
    icon.css('display', 'block');
}