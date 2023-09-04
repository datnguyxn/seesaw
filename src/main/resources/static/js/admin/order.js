// js to get all products data
const productTable = $('#orderTable');
const tbody = $('#orderTable tbody');

$(document).ready(function () {
    ajaxAllOrder();
});

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

function ajaxAllOrder(
    page = 0,
    size = 10
) {
    let tbody = $('#orderTable tbody');
    let html = "";
    let index = 1;
    $.ajax({
        url: `/api/orders/list?page=${page}&size=${size}`,
        type: "GET",
        dataType: "json",
        contentType: "application/json",
        async: false,
        success: function (data) {
            const {
                totalPages,
                number
            } = data;

            const paginationTemplate = renderPagnigation(totalPages, number);
            $('.pagination').html(paginationTemplate);
            registerPaginationEvent();
            $.each(data.content, function (key, value) {
                html +=
                    `
                        <tr id="${value.id}">
                                <td>${index++}</td>
                                <td>
                                    <p>${value.name}</p>
                                </td>
                                <td>
                                    <p>${value.email}</p>
                                </td>
                                <td>
                                    <p>${value.phone}</p>
                                </td>
                                <td>
                                    <p>${value.address}</p>
                                </td>
                                <td>
                                    <p class="fw-bold">${value.total_amount} VNĐ</p>
                                </td>
                                <td>
                                    <p >${value.status}</p>
                                </td>
                                <td>
                                    <p >${value.created_at}</p>
                                </td>
                                <td>
                                    <button class="detailBtn btn btn-link p-1 m-1">Detail
                                    </button>
                                </td>
                                <td>
                                    <button class="deleteBtn btn btn-light p-1 m-1"><i class='bx bx-dots-vertical-rounded'></i></button>
                                </td>
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

function activeOrderDetails() {

}