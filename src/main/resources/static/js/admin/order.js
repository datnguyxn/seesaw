// js to get all products data
const productTable = $('#orderTable');
const tbody = $('#orderTable tbody');

$(document).ready(function () {
    ajaxAllOrder();
    handleOrderSelect();
    clickOrderDetail();
    activeNabs();
});

function handleOrderSelect() {
    let editOrder = $('.editOrder');

    editOrder.each(function (index, item) {
        $(item).on('click', function (e) {
            let editOrderList = $(this).next();
            if (editOrderList.hasClass('active')) {
                editOrderList.removeClass('active');
            } else {
                let allEditOrderList = $('.editOrderList');
                allEditOrderList.removeClass('active');
                editOrderList.addClass('active');
            }
        })
    });

    let editOrderItem = $('.editOrderItem');
    editOrderItem.each(function (index, item) {
        $(item).on('click', function (e) {
            let status = $(this).text();
            let orderId = $(this).parent().parent().parent().attr('id');
            ajaxEditOrder(orderId, status);
        })
    });
}

function ajaxEditOrder(orderId, status) {
    $.ajax({
        url: `/api/orders/update-status/${orderId}`,
        type: "POST",
        dataType: "json",
        contentType: "application/json",
        data: status,
        async: false,
        success: function (data) {
            window.location.reload();
        },
        error: function (error) {
            console.log("Error");
            console.log(error.responseText);
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
        ajaxAllOrder(page);
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
                                    <a href="#" class="detailBtn link-primary text-decoration-underline p-1 m-1">Detail
                                    </a>
                                </td>
                                <td>
                                    <div class="editOrder p-1 m-1">
                                        <i class='bx bx-dots-vertical-rounded'></i>
                                    </div>
                                    <ul class="editOrderList">
                                        <li class="editOrderItem" data="1">Đang xét</li>
                                        <li class="editOrderItem" data="2">Đang giao</li>
                                        <li class="editOrderItem" data="3">Đã nhận</li>
                                        <li class="editOrderItem" data="4">Hủy</li>
                                    </ul>
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

function clickOrderDetail() {
    $('.detailBtn').click(function() {
        window.location.href = "/admin/order-detail/order/" + $(this).parent().parent().attr('id');
    })
}

function activeNabs() {
    let navTabs = $('.nav-tabs');
    let navItem = $('.nav-item');
    navItem.each(function (index, item) {
        $(item).on('click', function (e) {
            let navLink = $(this).find('.nav-link');
            if (navLink.hasClass('active')) {
                navLink.removeClass('active');
            } else {
                let allNavLink = $('.nav-link');
                allNavLink.each(function (index, item) {
                    $(item).removeClass('active');
                });
                navLink.addClass('active');
            }
        })
    });
}