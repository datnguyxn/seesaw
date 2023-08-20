// js to get all products data
const productTable = $('#categoryTable');
const tbody = $('#categoryTable tbody');

// ajax request to get all products
$(document).ready(function () {
    ajaxAllCategory();
    ajaAddCategory();
    ajaxEditCategory();
    ajaxDeleteCategory();
});

function ajaxAllCategory() {
    let tbody = $('#categoryTable tbody');
    let html = "";
    let index = 1;
    $.ajax({
        url: "/categories/list",
        method: "GET",
        dataType: "json",
        contentType: "application/json",
        async: false,
        success: function (data) {
            $.each(data, function (key, value) {
                html +=
                    `
                        <tr id="${value.id}">
                            <td> ${index++} </td>
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
        let category_name = addCategoryModal.find('#category_name').val();
        let category_description = addCategoryModal.find('#category_description').val();
        $.ajax({
            url: "/categories/add",
            method: "POST",
            data: JSON.stringify(
                {
                    name: category_name,
                    description: category_description
                }
            ),
            contentType: "application/json",
            success: function (data) {
                console.log("Add category success");
                window.location.reload();
            },
            error: function (error) {
                console.log("error");
                console.log(error);
            }
        })
    });

}

function ajaxEditCategory() {
    let editCategoryModal = $('#editCategoryModal');
    let saveBtn = $('#editCategoryModal .saveBtn');
    let allRow = $('#categoryTable tbody tr');

    $.each(allRow, function (key, value) {
        let editBtn = $(value).find('.editBtn');
        editBtn.click(function () {
            let id = $(value).attr('id');
            let name = $(value).find('td:nth-child(3)').text();
            let description = $(value).find('td:nth-child(4)').text();
            console.log(id);
            console.log(name);
            console.log(description);
            editCategoryModal.find('#category_id').val(id);
            editCategoryModal.find('#category_name').val(name);
            editCategoryModal.find('#category_description').val(description);
            saveBtn.click(function () {
                $.ajax({
                    url: "/categories/update?id=" + id,
                    method: "PUT",
                    data: JSON.stringify(
                        {
                            name: editCategoryModal.find('#category_name').val(),
                            description: editCategoryModal.find('#category_description').val()
                        },
                    ),
                    contentType: "application/json",
                    success: function (data) {
                        console.log("Update category success");
                        window.location.reload();
                    },
                    error: function (error) {
                        console.log("error");
                        console.log(error);
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
                    url: "/categories/delete?id=" + id,
                    method: "DELETE",
                    success: function (data) {
                        console.log("Delete category success");
                        window.location.reload();
                    },
                    error: function (error) {
                        console.log("error");
                        console.log(error);
                    }
                })
            });
        });
    });
}