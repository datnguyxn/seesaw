// js to get all products data
const productTable = $('#collectionTable');
const tbody = $('#collectionTable tbody');

// ajax request to get all products
$(document).ready(function () {
    ajaxAllCollection();
    ajaAddCollection();
    ajaxEditCollection();
    ajaxDeleteCollection();
    uploadImage();
});

function uploadImage() {
    let imageFile = $('#addCollectionModal #image_file');
    let imagePreview = $('#addCollectionModal #image_preview');
    let icon = $('#addCollectionModal .custum-file-upload .icon');

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
        ajaxAllCollection(page);
    });
}

function ajaxAllCollection(
    page = 0,
    size = 10
) {
    let tbody = $('#collectionTable tbody');
    let html = "";
    let index = 1;

    $.ajax({
        url: `/api/collections/list?page=${page}&size=${size}`,
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
                            <td>${value.name}</td>
                            <td>${value.description}</td>
                            <td>
                                    <div class="d-flex flex-row gap-1">
                                        <button class="editBtn btn btn-primary p-1 m-1" data-bs-toggle="modal"
                                                data-bs-target="#editCollectionModal">Edit
                                        </button>
                                        <button class="deleteBtn btn btn-danger p-1 m-1" data-bs-toggle="modal"
                                                data-bs-target="#deleteCollectionModal">Delete
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

function ajaAddCollection() {
    let addCollectionModal = $('#addCollectionModal');
    let saveBtn = $('#addCollectionModal .saveBtn');
    let closeBtn = $('#addCollectionModal .closeBtn');
    const data = new FormData();
    let imageFile = $('#addCollectionModal #image_file');

    saveBtn.click(function () {
        data.append("name", addCollectionModal.find('#collection_name').val());
        data.append("description", addCollectionModal.find('#collection_description').val());
        if(imageFile[0].files[0] != null){
            data.append("image", imageFile[0].files[0]);
        }

        $.ajax({
            url: "/api/collections/add",
            method: "POST",
            contentType: false,
            processData: false,
            enctype: 'multipart/form-data',
            cache: false,
            async: false,
            data: data,
            success: function (data) {
                console.log("Add collection success");
                window.location.reload();
            },
            error: function (error) {
                console.log("error");
                console.log(error.responseText);
            }
        })
    });

}

function ajaxEditCollection() {
    let editCollectionModal = $('#editCollectionModal');
    let saveBtn = $('#editCollectionModal .saveBtn');
    let allRow = $('#collectionTable tbody tr');
    const data = new FormData();
    let imageFile2 = $('#editCollectionModal #image_file2');
    let imagePreview2 = $('#editCollectionModal #image_preview2');
    let icon2 = $('#editCollectionModal .custum-file-upload .icon');

    $.each(allRow, function (key, value) {
        let editBtn = $(value).find('.editBtn');
        editBtn.click(function () {
            let id = $(value).attr('id');
            let name = $(value).find('td:nth-child(3)').text();
            let description = $(value).find('td:nth-child(4)').text();
            let image = $(value).find('td:nth-child(2) img').attr('src');
            console.log("image: " + image)
            editCollectionModal.find('#collection_id').val(id);
            editCollectionModal.find('#collection_name').val(name);
            editCollectionModal.find('#collection_description').val(description);

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
                data.append("name", editCollectionModal.find('#collection_name').val());
                data.append("description", editCollectionModal.find('#collection_description').val());
                if(imageFile2[0].files[0] != null){
                    console.log("imageFile2[0].files[0] exist")
                    data.append("image", imageFile2[0].files[0]);
                }

                $.ajax({
                    url: "/api/collections/update?id=" + id,
                    method: "PUT",
                    contentType: false,
                    processData: false,
                    enctype: 'multipart/form-data',
                    cache: false,
                    async: false,
                    data: data,
                    success: function (data) {
                        console.log("Update collection success");
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

function ajaxDeleteCollection() {
    let saveBtn = $('#deleteCollectionModal .delete_button');
    let allRow = $('#collectionTable tbody tr');
    $.each(allRow, function (key, value) {
        let deleteBtn = $(value).find('.deleteBtn');
        deleteBtn.click(function () {
            let id = $(value).attr('id');
            saveBtn.click(function () {
                $.ajax({
                    url: "/api/collections/delete?id=" + id,
                    method: "DELETE",
                    success: function (data) {
                        console.log("Delete collection success");
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