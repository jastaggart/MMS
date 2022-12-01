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
                //OR put mark as completed button in employee page and when this occurs, change status
                // if (currentDate.compareTo(loan.getEndDate()) != 1 && (currentDate.compareTo(loan.getStartDate()) != -1)) {
                //     loan.loanStatus = "Completed"
                // }
                loan.loanStatus = "Accepted"
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
    padding: 20px;
}

table {
    width: 50%;
    position: relative;
    margin: auto;
}

td,th {
    border: 1px solid #dddddd;
    text-align: center;
    padding: 15px;
}
</style>
