const TOKEN = localStorage.getItem('token');

// js to get all products data
const userTable = $('#userTable');
const tbody = $('#userTable tbody');

var myModal = new bootstrap.Modal(document.getElementById('editModal'), options)
let deleteModal = $('#deleteModal');

// ajax request to get all products
$(document).ready(function () {
    ajaxGetAllUser();
    editUser();
    deleteUser();
});

function editUser() {
    let editModal = $('#editModal');
    let saveBtn = $('#editModal .saveBtn');
    let closeBtn = $('#editModal .closeBtn');
    let allRow = $('#userTable tbody tr');

    allRow.each(function (index, element) {
        $(element).find('button.editBtn').on('click', function () {
            let firstName = $(element).find('td:nth-child(3)').text();
            let lastName = $(element).find('td:nth-child(4)').text();
            let email = $(element).find('td:nth-child(5)').text();
            let contact = $(element).find('td:nth-child(6)').text();
            let gender = $(element).find('td:nth-child(7)').text();
            let address = $(element).find('td:nth-child(8)').text();

            editModal.find('#firstname').val(firstName);
            editModal.find('#lastname').val(lastName);
            editModal.find('#email').val(email);
            editModal.find('#gender').val(contact);
            editModal.find('#contact').val(gender);
            editModal.find('#address').val(address);
            console.log("firstName : " + firstName + ", lastName: " + lastName + ", email: " + email
                + ", gender: " + gender + ", contact: " + contact + ", address: " + address);

            saveBtn.on('click', function () {
                $.ajax({
                    url: "/api/user/update",
                    type: "POST",
                    dataType: "json",
                    contentType: "application/json",
                    header: {
                        authorization: "Bearer " + TOKEN,
                    },
                    async: false,
                    data: JSON.stringify({
                        "firstname": editModal.find('#firstname').val(),
                        "lastname": editModal.find('#lastname').val(),
                        "email": editModal.find('#email').val(),
                        "gender": editModal.find('#gender').val(),
                        "contact": editModal.find('#contact').val(),
                        "address": editModal.find('#address').val()
                    }),
                    success: function (data) {
                        console.log("Success Edit User");
                        window.location.reload();
                    },
                    error: function (error) {
                        console.log("Error Edit User");
                    }
                })
            });
        });
    })
}

function ajaxGetAllUser() {
    $.ajax({
        url: "/api/user/get-all-user",
        type: "POST",
        dataType: "json",
        contentType: "application/json",
        async: false,
        success: function (data) {
            let html = "";
            let i = 0;
            $.each(data, function (key, value) {
                html += `<tr>
                            <td> ${++i} </td>
                            <td>${value.id}</td>
                            <td>${value.firstname}</td>
                            <td>${value.lastname}</td>
                            <td>${value.email}</td>
                            <td>${value.gender}</td>
                            <td>${value.contact}</td>
                            <td>${value.address}</td>
                            <td class='d-flex gap-1 nowrap'>
                                <button type='button' class='editBtn btn btn-primary border-0 p-1' data-bs-toggle=\"modal\" data-bs-target=\"#editModal\">Edit</button>
                                <button type=\"button\" class=\"deleteBtn btn btn-danger border-0 p-1\" data-bs-toggle=\"modal\" data-bs-target=\"#deleteModal\">Delete</button>
                            </td>
                        </tr>
                    `;
            });
            tbody.append(html);
        },
        error: function (error) {
            console.log("Error");
        }
    })

}

// $(document).ready(function () {
//
//     var table = $('#userTable').DataTable({
//
//         buttons: ['copy', 'csv', 'excel', 'print']
//
//     });
//
//
//     table.buttons().container()
//         .appendTo('#userTable_wrapper .col-md-6:eq(0)');
//
// });

function deleteUser() {
    let deleteModal = $('#deleteModal');
    let deleteBtn = $('#deleteModal  .delete-button');
    let closeBtn = $('#deleteModal .close-button');
    let allRow = $('#userTable tbody tr');

    allRow.each(function (index, element) {
        $(element).find('button.deleteBtn').on('click', function () {
            let email = $(element).find('td:nth-child(5)').text();
            deleteBtn.on('click', function () {
                console.log("email : " + email)
                $.ajax({
                    url: "/api/user/delete-user",
                    type: "POST",
                    dataType: "json",
                    contentType: "application/json",
                    async: false,
                    data: JSON.stringify({
                        "email": email
                    }),
                    success: function (data) {
                        console.log("Success Delete User");
                        window.location.reload();
                    },
                    error: function (error) {
                        console.log("Error Delete User");
                    }
                })
            });
        });
    })
}




