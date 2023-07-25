$(document).ready(function() {
    const myChart = $("#myChart");
    const emailList = getAllUser();
    console.log(emailList);

    new Chart(myChart, {
        type: 'pie',
        data: {
            labels: ["email"],
            datasets: [{
                label: '# of Votes',
                data: emailList,
                borderWidth: 1
            }]
        },
        options: {
            responsive: true,
            title: {
                display: true,
                text: 'Chart.js Pie Chart'
            },
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    })

    function getAllUser() {
        let emailList = []
        $.ajax({
            url: "/api/user/get-all-user",
            type: "POST",
            contentType: "application/json",
            success: function (data) {
                console.log(data);
                data.forEach(function (item) {
                    emailList.push(item.email);
                })
            },
            error: function (error) {
                console.log(error);
            }
        })
        return emailList;
    }
})