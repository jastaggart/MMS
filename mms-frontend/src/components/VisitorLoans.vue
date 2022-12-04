<template>
    <div>
        <h1>My Loans</h1>
        <div>
            <p v-if="this.failureMessage" style="text-align:center; padding-top: 20px;">
                <b>{{ this.failureMessage }}</b> <br>
                Browse all of our loanable artworks <a href="http://127.0.0.1:8087/?#/VisitorArtworks">here</a>!
            </p>

            <div v-else>

                <p>
                    Thank you for your interest in loaning our museum's artworks! <br>
                    To submit another loan request, navigate to our Artwork Collection <a
                        href="http://127.0.0.1:8087/?#/VisitorArtworks">here</a>.
                </p>
                <select id="statusOptions" @change.prevent="filterLoansByStatus()">
                    <option value="Filter" selected>--Filter By Status--</option>
                    <option value="Pending">Pending</option>
                    <option value="Accepted/Rejected">Accepted/Rejected</option>
                    <option value="Completed">Completed</option>
                </select>

                <table id="myLoans">
                    <tr>
                        <th>Artwork</th>
                        <th>Loan Fee</th>
                        <th>Start Date</th>
                        <th>End Date</th>
                        <th>Status</th>
                    </tr>
                    <tr v-for="loan in Loans">
                        <td>{{ loan.artwork.name }}</td>
                        <td>{{ loan.loanFee }}</td>
                        <td>{{ loan.startDate.substring(0, 10) }}</td>
                        <td>{{ loan.endDate.substring(0, 10) }}</td>
                        <td>{{ getStatus(loan) }}</td>
                    </tr>
                </table>
                <p ><br><b>{{this.noneFoundMessage}}</b></p>
            </div>
        </div>
    </div>
</template>

<script>
import axios from "axios";
const config = require("../../config");
var frontendUrl = "http://" + config.dev.host + ":" + config.dev.port;
var backendUrl =
    "http://" + config.dev.backendHost + ":" + config.dev.backendPort;
var AXIOS = axios.create({
    baseURL: backendUrl,
    headers: { "Access-Control-Allow-Origin": frontendUrl }
});

export default {
    name: "visitorLoans",
    data() {
        return {
            Loans: [],
            loanID: "",
            startDate: "",
            endDate: "",
            loanFee: "",
            isApproved: "",
            loanStatus: "",
            loanApprover: "",
            failureMessage: "",
            noneFoundMessage: ""
        };
    },
    created: function () {
        AXIOS.get("/loan/visitorID/" + window.sessionStorage.getItem("visitorID"))
            .then(response => {
                this.Loans = response.data;
            })
            .catch(e => {
                if (e.response.status == 404) {
                    this.failureMessage = "You currently have not applied for any loans.";
                }
            });
    },
    methods: {
        getStatus: function (loan) {
            var currentDate = new Date();
            var loanEndDate = new Date(loan.endDate.substring(0, 10));
            if (loan.isApproved) {
                if (currentDate > loanEndDate) {
                    loan.loanStatus = "Completed";
                }
                else loan.loanStatus = "Accepted";
            }
            else if (loan.loanApprover == null) {
                loan.loanStatus = "Pending"
            }
            else /*if (loan.loanApprover != null && !loan.isApproved)*/ {
                loan.loanStatus = "Rejected"
            }
            return loan.loanStatus
        },
        filterLoansByStatus: function () {
            var rowStatus, row;
            var table = document.getElementById("myLoans")
            var rows = table.getElementsByTagName("tr");
            var selectedStatus = document.getElementById("statusOptions").value;
            console.log(selectedStatus);
            var count = 0;
            for (let i = 0; i < rows.length; i++) {
                row = rows[i];
                rowStatus = row.getElementsByTagName("td")[4];
                console.log(rowStatus);
                if (rowStatus) {
                    var value = rowStatus.textContent;
                    console.log(value);
                    if (selectedStatus == "Filter") {
                        rows[i].style.display = "";
                    }
                    else if (value == selectedStatus || selectedStatus.includes(value)) {
                        rows[i].style.display = "";
                        count += 1;
                    }
                    else rows[i].style.display = "none";
                }
            }
            console.log(count);
            if (count == 0) {
                this.noneFoundMessage = "No " + selectedStatus + " loans found.";
            }
            else this.noneFoundMessage = "";
        }
    }
}   
</script>

<style scoped>
h1 {
    text-align: center;
    padding: 2rem;
}

a {
    color: hsl(189, 53%, 44%);
}

p {
    text-align: center;
    font-size: 1.2rem;
}

table {
    width: 50%;
    position: relative;
    margin: auto;
    box-shadow: 0 0 20px rgba(0, 0, 0, 0.15);
    padding-top: 2rem;
}

th {
    background-color: hsl(189, 53%, 47%);
    color: white;
}

td,
th {
    border: 1px solid #dddddd;
    text-align: center;
    padding: 15px;
    font-size: 1rem;
}

tr {
    border-bottom: 1px solid #dddddd;
}

tr:nth-of-type(even) {
    background-color: #f3f3f3;
}

tr:last-of-type {
    border-bottom: 2px solid hsl(189, 53%, 47%);
}


select {
    font-size: 1.1rem;
    width: 13rem;
    height: 2.5rem;
    display: flex;
    position: relative;
    border-radius: 4px;
    margin: auto;
    margin-bottom: 15px;
    margin-top: 30px;
    border: none;
    outline: none;
    cursor: pointer;
}

input {
    width: 75%;
    border: none;
    box-sizing: border-box;
    border-bottom: 1px solid #ddd;
    color: #555;
    border: 1px solid black;
}
</style>
