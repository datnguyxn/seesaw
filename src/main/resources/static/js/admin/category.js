// js to get all products data
const productTable = $('#categoryTable');
const tbody = $('#categoryTable tbody');

// ajax request to get all products
$(document).ready(function () {
    ajaxAllCategory();
    ajaAddCategory();
    ajaxEditCategory();
    ajaxDeleteCategory();
    uploadImage();
});

function uploadImage() {
    let imageFile = $('#addCategoryModal #image_file');
    let imagePreview = $('#addCategoryModal #image_preview');
    let icon = $('#addCategoryModal .custum-file-upload .icon');

    imageFile.on('change', function () {
        let file = this.files[0];
        if (file) {
            let reader = new FileReader();
            reader.onload = function (event) {
                imagePreview.attr('src', event.target.result);
                let imageText = imageFile.val().split('\\').pop();
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
        ajaxAllCategory(page);
    });
}

function ajaxAllCategory(
    page = 0,
    size = 2
) {
    let tbody = $('#categoryTable tbody');
    let html = "";
    let index = 1;

    $.ajax({
        url: `/api/categories/list?page=${page}&size=${size}`,
        method: "GET",
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
                            <td> ${index++} </td>
                            <td>
                                    <img src="${value?.image ? value.image : '/images/default_product_placeholder.png'}"
                                         alt="Glasses" width="200px" height="120px" style="object-fit: cover;">
                            </td>
                            <td>${value.id}</td>
                            <td>${value.name}</td>
                            <td>${value.description}</td>
                            <td>
                                    <div class="d-flex flex-row gap-1">
                                        <button class="editBtn btn btn-primary p-1 m-1" data-bs-toggle="modal"
                                                data-bs-target="#editCategoryModal">Edit
                                        </button>
                                        <button class="deleteBtn btn btn-danger p-1 m-1" data-bs-toggle="modal"
                                                data-bs-target="#deleteCategoryModal">Delete
                                        </button>
                                    </div>
                            </td>
                        </tr>
                    `;
            });
            tbody.html(html);
        },
        error: function (error) {
            console.log("error");
            console.log(error);
        }
    })
}

function ajaAddCategory() {
    let addCategoryModal = $('#addCategoryModal');
    let saveBtn = $('#addCategoryModal .saveBtn');
    let closeBtn = $('#addCategoryModal .closeBtn');

    saveBtn.click(function () {
        const data = new FormData();
        let imageFile = $('#addCategoryModal #image_file');
        data.append("name", addCategoryModal.find('#category_name').val());
        data.append("description", addCategoryModal.find('#category_description').val());
        if(imageFile[0].files[0] != null){
            data.append("image", imageFile[0].files[0]);
        }

        $.ajax({
            url: "/api/categories/add",
            method: "POST",
            contentType: false,
            processData: false,
            enctype: 'multipart/form-data',
            cache: false,
            async: false,
            data: data,
            success: function (data) {
                console.log("Add category success");
                window.location.reload();
            },
            error: function (error) {
                console.log("error");
                console.log(error.responseText);
            }
        })
    });

}

function ajaxEditCategory() {
    let editCategoryModal = $('#editCategoryModal');
    let saveBtn = $('#editCategoryModal .saveBtn');
    let allRow = $('#categoryTable tbody tr');
    const data = new FormData();
    let imageFile2 = $('#editCategoryModal #image_file2');
    let imagePreview2 = $('#editCategoryModal #image_preview2');
    let icon2 = $('#editCategoryModal .custum-file-upload .icon');

    $.each(allRow, function (key, value) {
        let editBtn = $(value).find('.editBtn');
        editBtn.click(function () {
            let id = $(value).attr('id');
            let name = $(value).find('td:nth-child(4)').text();
            let description = $(value).find('td:nth-child(5)').text();
            let image = $(value).find('td:nth-child(2) img').attr('src');
            editCategoryModal.find('#category_id').val(id);
            editCategoryModal.find('#category_name').val(name);
            editCategoryModal.find('#category_description').val(description);

            // show upload image
            icon2.css('display', 'none');
            imagePreview2.css('display', 'block');
            imagePreview2.attr('src', image);
            imageFile2.on('change', function () {
                console.log(this.files[0])
                let file = this.files[0];
                if (file) {
                    let reader = new FileReader();
                    reader.onload = function (event) {
                        imagePreview2.attr('src', event.target.result);
                        imagePreview2.css('display', 'block');
                        icon2.css('display', 'none');
                    }
                    reader.readAsDataURL(file);
                }
            });

            saveBtn.click(function () {
                console.log(editCategoryModal.find('#category_name').val());
                console.log(editCategoryModal.find('#category_description').val());
                console.log(imageFile2[0].files[0]);
                data.append("name", editCategoryModal.find('#category_name').val());
                data.append("description", editCategoryModal.find('#category_description').val());
                if(imageFile2[0].files[0] != null){
                    console.log("imageFile2[0].files[0] exist")
                    data.append("image", imageFile2[0].files[0]);
                }

                $.ajax({
                    url: "/api/categories/update?id=" + id,
                    method: "PUT",
                    contentType: false,
                    processData: false,
                    enctype: 'multipart/form-data',
                    cache: false,
                    async: false,
                    data: data,
                    success: function (data) {
                        console.log("Update category success");
                        window.location.reload();
                    },
                    error: function (error) {
                        console.log("error");
                        console.log(error.responseText);
                    }
                })
            });
        });
    })
};

function ajaxDeleteCategory() {
    let saveBtn = $('#deleteCategoryModal .delete_button');
    let allRow = $('#categoryTable tbody tr');
    $.each(allRow, function (key, value) {
        let deleteBtn = $(value).find('.deleteBtn');
        deleteBtn.click(function () {
            let id = $(value).attr('id');
            saveBtn.click(function () {
                $.ajax({
                    url: "/api/categories/delete?id=" + id,
                    method: "DELETE",
                    success: function (data) {
                        console.log("Delete category success");
                        window.location.reload();
                    },
                    error: function (error) {
                        console.log("error");
                        console.log(error.responseText);
                    }
                })
            });
        });
    });
}