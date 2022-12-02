<template>
    <div>
        <h1>My Loans</h1>
        <div>
            <p v-if="this.failureMessage" style="text-align:center; padding-top: 20px;">
                <b>{{ this.failureMessage }}</b> <br>
                Browse all of our loanable artworks <a href = "http://127.0.0.1:8087/?#/VisitorArtworks" >here</a>! 
            </p>
            <table v-else>
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
            <p>
                Thank you for your interest in loaning our museum's artworks! <br>
                To submit another loan request, navigate to our Artworks page <a href = "http://127.0.0.1:8087/?#/VisitorArtworks" >here</a>. 
            </p>
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
            failureMessage: ""
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
        getStatus: function(loan) {
            if (loan.isApproved) {
                if (currentDate > loanEndDate) {
                    loan.loanStatus = "Completed";
                }
                else loan.loanStatus = "Accepted";
            }
            else if (loan.loanApprover == null) {
                loan.loanStatus = "Pending"
            }
            else /*if (loan.loanApprover != null && !loan.isApproved)*/{
                loan.loanStatus = "Rejected"
            }
            return loan.loanStatus
        }
    }
}   
</script>

<style scoped>

h1 {
    text-align: center;
    padding: 2rem;
}
a{
    color: hsl(189, 53%, 44%);
}
p {
    text-align:center; 
    padding-top: 40px;
    font-size: 1.1rem;
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
td,th {
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
</style>
