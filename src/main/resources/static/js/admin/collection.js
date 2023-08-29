const TOKEN = localStorage.getItem('token');

// js to get all collection data
const productTable = $('#collectionTable');
const tbody = $('#collectionTable tbody');

// ajax request to get all collections
$(document).ready(function () {
    ajaxAllCollection();
    ajaAddCollection();
    ajaxEditCollection();
    ajaxDeleteCollection();
});

function ajaxAllCollection() {
    let tbody = $('#collectionTable tbody');
    let html = "";
    let index = 1;
    $.ajax({
        url: "/collections/list",
        method: "GET",
        dataType: "json",
        contentType: "application/json",
        header: {
            authorization: "Bearer " + TOKEN,
        },
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

    saveBtn.click(function () {
        let collection_name = addCollectionModal.find('#collection_name').val();
        let collection_description = addCollectionModal.find('#collection_description').val();
        $.ajax({
            url: "/collections/add",
            method: "POST",
            data: JSON.stringify(
                {
                    name: collection_name,
                    description: collection_description
                }
            ),
            contentType: "application/json",
            success: function (data) {
                console.log("Add collection success");
                window.location.reload();
            },
            error: function (error) {
                console.log("error");
                console.log(error);
            }
        })
    });

}

function ajaxEditCollection() {
    let editCollectionModal = $('#editCollectionModal');
    let saveBtn = $('#editCollectionModal .saveBtn');
    let allRow = $('#collectionTable tbody tr');

    $.each(allRow, function (key, value) {
        let editBtn = $(value).find('.editBtn');
        editBtn.click(function () {
            let id = $(value).attr('id');
            let name = $(value).find('td:nth-child(3)').text();
            let description = $(value).find('td:nth-child(4)').text();
            console.log(id);
            console.log(name);
            console.log(description);
            editCollectionModal.find('#collection_id').val(id);
            editCollectionModal.find('#collection_name').val(name);
            editCollectionModal.find('#collection_description').val(description);
            saveBtn.click(function () {
                $.ajax({
                    url: "/collections/update?id=" + id,
                    method: "PUT",
                    data: JSON.stringify(
                        {
                            name: editCollectionModal.find('#collection_name').val(),
                            description: editCollectionModal.find('#collection_description').val()
                        },
                    ),
                    contentType: "application/json",
                    success: function (data) {
                        console.log("Update collection success");
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

function ajaxDeleteCollection() {
    let saveBtn = $('#deleteCollectionModal .delete_button');
    let allRow = $('#collectionTable tbody tr');
    $.each(allRow, function (key, value) {
        let deleteBtn = $(value).find('.deleteBtn');
        deleteBtn.click(function () {
            let id = $(value).attr('id');
            saveBtn.click(function () {
                $.ajax({
                    url: "/collections/delete?id=" + id,
                    method: "DELETE",
                    success: function (data) {
                        console.log("Delete collection success");
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