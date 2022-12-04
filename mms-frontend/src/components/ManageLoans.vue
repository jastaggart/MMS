<template>
    <div>
        <h1>Manage Loans</h1>
        <p v-if="this.failureMessage" style="text-align:center; padding-top: 20px;">
            <b>{{ this.failureMessage }}</b> <br>
        </p>
        <div class="layout" v-else>

            <select id="loanTitles" class="loanTitles" @change="displayInfo()">
                <option value="requests" >Loan Requests</option>
                <option value="actives">Active Loans</option>
                <option value="history">All Loans</option>
            </select>

            <p id="requests" v-if="this.noRequestsMessage" style="text-align:center; padding-top: 20px;">
                <br><b>{{ this.noRequestsMessage }}</b>
            </p>
            <table id="requests" v-else>
                <tr>
                    <th>Loan Id</th>
                    <th>Visitor Id</th>
                    <th>Email</th>
                    <th>Artwork</th>
                    <th>Loan Fee</th>
                    <th>Start Date</th>
                    <th>End Date</th>
                    <th>Action</th>
                </tr>
                <tr v-for="loanRequest in LoanRequests">
                    <td>{{ loanRequest.loanID }}</td>
                    <td>{{ loanRequest.loanRequestor.visitorId }}</td>
                    <td>{{loanRequest.loanRequestor.email}}</td>
                    <td>{{ loanRequest.artwork.name }}</td>
                    <td>{{ loanRequest.loanFee }}</td>
                    <td>{{ loanRequest.startDate.substring(0, 10) }}</td>
                    <td>{{ loanRequest.endDate.substring(0, 10) }}</td>
                    <td>
                        <button @click="accept(loanRequest)" id="Accept">Accept</button>
                        <button @click="reject(loanRequest)" id="Reject">Reject</button>
                    </td>
                </tr>
            </table>

            <p id="actives" v-if="this.noActivesMessage" style="text-align:center; padding-top: 20px;">
                <br><b>{{ this.noActivesMessage }}</b> <br>
            </p>
            <table id="actives" v-else>
                <tr>
                    <th>Loan Id</th>
                    <th>Visitor Id</th>
                    <th>Email</th>
                    <th>Artwork</th>
                    <th>Loan Fee</th>
                    <th>Start Date</th>
                    <th>End Date</th>
                    <th>Status</th>
                </tr>
                <tr v-for="activeLoan in ActiveLoans">
                    <td>{{ activeLoan.loanID }}</td>
                    <td>{{ activeLoan.loanRequestor.visitorId }}</td>
                    <td>{{activeLoan.loanRequestor.email}}</td>
                    <td>{{ activeLoan.artwork.name }}</td>
                    <td>{{ activeLoan.loanFee }}</td>
                    <td>{{ activeLoan.startDate.substring(0, 10) }}</td>
                    <td>{{ activeLoan.endDate.substring(0, 10) }}</td>
                    <td>
                        <button @click="complete(activeLoan)" id="Completed">Mark As Completed</button>
                    </td>
                </tr>
            </table>
            <table id="history">
                <tr>
                    <th>Loan Id</th>
                    <th>Visitor Id</th>
                    <th>Email</th>
                    <th>Artwork</th>
                    <th>Loan Fee</th>
                    <th>Start Date</th>
                    <th>End Date</th>
                    <th>Status</th>
                </tr>
                <tr v-for="loan in Loans">
                    <td>{{ loan.loanID }}</td>
                    <td>{{ loan.loanRequestor.visitorId }}</td>
                    <td>{{loan.loanRequestor.email}}</td>
                    <td>{{ loan.artwork.name }}</td>
                    <td>{{ loan.loanFee }}</td>
                    <td>{{ loan.startDate.substring(0, 10) }}</td>
                    <td>{{ loan.endDate.substring(0, 10) }}</td>
                    <td>{{ getStatus(loan) }}</td>
                </tr>
            </table>
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
            LoanRequests: [],
            ActiveLoans: [],
            LoanHistory: [],
            loanID: "",
            startDate: "",
            endDate: "",
            loanFee: "",
            isApproved: "",
            loanStatus: "",
            loanApprover: "",
            failureMessage: "",
            noRequestsMessage: "",
            noActivesMessage: "",
            noHistoryMessage: ""
        };
    },
    created: function () {
        AXIOS.get("/loans")
            .then(response => {
                this.Loans = response.data;

                //populate LoanRequests array
                this.LoanRequests = this.Loans.filter(loan => loan.loanApprover == null);
                if (this.LoanRequests.length == 0) {
                    this.noRequestsMessage = "There are currently no pending loan requests."
                }
                //populate ActiveLoans array
                var loan = "";
                var currentDate = new Date();
                for (let i = 0; i < this.Loans.length; i++) {
                    loan = this.Loans[i];
                    var loanStartDate = new Date(loan.startDate.substring(0, 10));
                    var loanEndDate = new Date(loan.endDate.substring(0, 10));
                    if (loan.isApproved && loanStartDate <= currentDate && currentDate <= loanEndDate) {
                        this.ActiveLoans.push(loan);
                    }
                }
                if (this.ActiveLoans.length == 0) {
                    this.noActivesMessage = "There are currently no active loans."
                }

            })
            .catch(e => {
                if (e.response.status == 404) {
                    this.failureMessage = "No loans found.";
                }
            });
    },
    methods: {
        getStatus: function (loan) {
            var currentDate = new Date()
            var loanEndDate = new Date(loan.endDate.substring(0, 10));
            if (loan.isApproved) {
                if (currentDate > loanEndDate) {
                    loan.loanStatus = "Completed";
                }
                else loan.loanStatus = "Accepted";
            }
            else if (loan.loanApprover == null) {
                loan.loanStatus = "Pending";
            }
            else /*if (loan.loanApprover != null && !loan.isApproved)*/ {
                loan.loanStatus = "Rejected";
            }
            return loan.loanStatus;
        },
        accept: function (loanRequest) {
            AXIOS.put("/loans/approve/" + loanRequest.loanID + "/" + window.sessionStorage.getItem("employeeID"));
            const acceptButton = document.getElementById("Accept");
            setTimeout(() => {
                window.location.reload()
            }, "350")
        },
        reject: function (loan) {
            AXIOS.put("/loans/reject/" + loan.loanID + "/" + window.sessionStorage.getItem("employeeID"));
            const rejectButton = document.getElementById("Reject");
            setTimeout(() => {
                window.location.reload()
            }, "350")
        },
        complete: function (activeLoan) {
            activeLoan.loanStatus = "Completed";
            AXIOS.put("/loans/close/" + activeLoan.loanID)
            setTimeout(() => {
                window.location.reload()
            }, "350")
        },
        displayInfo: function () {
            var selectedItem = document.getElementById("loanTitles").value;
            if (selectedItem == "requests") {
                document.getElementById("requests").style.display = "block";
                document.getElementById("actives").style.display = "none";
                document.getElementById("history").style.display = "none";
            }
            else if (selectedItem == "actives") {
                document.getElementById("actives").style.display = "block";
                document.getElementById("requests").style.display = "none";
                document.getElementById("history").style.display = "none";
            }
            else {
                document.getElementById("requests").style.display = "none";
                document.getElementById("actives").style.display = "none";
                document.getElementById("history").style.display = "block";
            }
        }
    }
}   
</script>

<style scoped>
h1 {
    text-align: center;
    padding: 30px;
}

button {
    background: hsl(189, 55%, 52%);
    border: 0;
    padding: 10px 20px;
    margin-top: 5px;
    color: white;
    border-radius: 20px;
    min-width: 90px;
}

button:hover {
    background-color: hsl(189, 67%, 58%);
}

table {
    width: 70%;
    padding: 3rem;
    align-items: center;
    justify-content: center;
    text-align: center;
    margin: auto;
}

td,
th {
    border: 1px solid #dddddd;
    text-align: center;
    padding: 1rem;
    font-size: 1rem;
}

tr {
    border-bottom: 1px solid #dddddd;
}

tr:nth-of-type(even) {
    background-color: #f3f3f3;
}

.layout {
    padding: 2em;
}

#loanTitles:hover {
    background-color: hsl(189, 67%, 58%);
}

select {
    font-size: 1.8rem;
    color: white;
    width: 21rem;
    height: 3.6rem;
    display: flex;
    position: relative;
    border-radius: 4px;
    box-shadow: 2.5px 4px 1.5px rgb(26, 26, 26);
    margin: auto;
    border: none;
    outline: none;
    -webkit-appearance: button;
    appearance: button;
    cursor: pointer;
    background: url(data:image/svg+xml;base64,PHN2ZyBpZD0iTGF5ZXJfMSIgZGF0YS1uYW1lPSJMYXllciAxIiB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZpZXdCb3g9IjAgMCA0Ljk1IDEwIj48ZGVmcz48c3R5bGU+LmNscy0ye2ZpbGw6IzQ0NDt9PC9zdHlsZT48L2RlZnM+PHRpdGxlPmFycm93czwvdGl0bGU+PHBvbHlnb24gY2xhc3M9ImNscy0yIiBwb2ludHM9IjEuNDEgNC42NyAyLjQ4IDMuMTggMy41NCA0LjY3IDEuNDEgNC42NyIvPjxwb2x5Z29uIGNsYXNzPSJjbHMtMiIgcG9pbnRzPSIzLjU0IDUuMzMgMi40OCA2LjgyIDEuNDEgNS4zMyAzLjU0IDUuMzMiLz48L3N2Zz4=) no-repeat 95% 50%;
    background-color: hsl(189, 55%, 52%);
    padding-left: 1.5rem;
}

p {
    font-size: 1.2em;
    color: black;
    top: 5em;
    bottom: 1em;
}

#requests {
    display: block;
}

#actives {
    display: none;
}

#history {
    display: none;
}
</style>


