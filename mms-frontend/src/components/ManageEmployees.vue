<template>
    <div id="Manage-Employee-body">
        <h2 id="employee-header">Employee List</h2>

        <div id="filter-employee-layout">
            <div id="filter-by-id">
                <label>Find Employee by Id:</label>
                <input @change="filterEmployeeById(employeeID)" v-model="employeeID" />
                <p v-if="this.failureMessage1" style="color: red">
                    {{ this.failureMessage1 }}
                </p>
            </div>

            <div id="filter-by-username">
                <label>Find Employee by Username:</label>
                <input @change="filterEmployeeByUsername(employeeUsername)" v-model="employeeUsername" />
                <p v-if="this.failureMessage2" style="color: red">
                    {{ this.failureMessage2 }}
                </p>
            </div>
        </div>

        <table id="employee-table">
            <tr class="top-row">
                <th>Id</th>
                <th>Email</th>
                <th>Username</th>
                <th>Password</th>
                <th>Delete</th>
            </tr>
            <tr v-for="e in employees" :key="e.staffMemberId">
                <td>{{ e.staffMemberID }}</td>
                <td>{{ e.email }}</td>
                <td>{{ e.username }}</td>
                <td>{{ e.password }}</td>
                <td><button v-bind:disabled="employeeBtnDisabled" @click="deleteEmployee(e.staffMemberID)"
                        class="add-delete-employee-button">Delete</button>
                </td>
            </tr>
        </table>

        <div class="create-employee-layout">
            <h2> Add New Employee </h2>
            <p id="enter-data-text"> Enter the email, username and password of the employee to add. </p>
            <table>
                <tr>
                    <td><input type="text" placeholder="email" v-model="newEmail"></td>
                    <td><input type="text" placeholder="username" v-model="newUsername"></td>
                    <td><input type="text" placeholder="password" v-model="newPassword"></td>
                    <td><button v-bind:disabled="employeeBtnDisabled"
                            @click="createEmployee(newEmail, newUsername, newPassword)">Create</button>
                    </td>
                </tr>
            </table>
            <p v-if="this.failureMessage3" style="color: red">
                {{ this.failureMessage3 }}
            </p>
        </div>
    </div>


</template>

<script>
import axios from 'axios'
const config = require('../../config')

const frontendUrl = config.dev.host + ':' + config.dev.port;
var backendUrl = "http://" + config.dev.backendHost + ":" + config.dev.backendPort;

var AXIOS = axios.create({
    baseURL: backendUrl,
    headers: { "Access-Control-Allow-Origin": frontendUrl }
});

export default {
    name: "Employee",
    data() {
        return {
            employees: [],
            id: "",
            username: "",
            email: "",
            password: "",
            successfulMessage: "",
            failureMessage1: "",
            failureMessage2: "",
            failureMessage3: ""
        };
    },
    created() {
        AXIOS.get('/staffMembers')
            .then(response => {
                console.log(response)
                this.employees = response.data;
            })
            .catch(e => {
                console.log('Error in GET /StaffMembers:')
                console.log(e)
            })
    },
    methods: {
        filterEmployeeById: function (staffMemberID) {
            AXIOS.get("/staffMember/staffMemberId/" + staffMemberID)
                .then(response => {
                    this.employees = [response.data]
                    this.failureMessage1 = "";
                    this.failureMessage2 = "";
                    this.failureMessage3 = "";
                })
                .catch(e => {
                    if (e.response.status == 404) {
                        this.failureMessage1 = "No StaffMember found for this id.";
                        this.failureMessage2 = "";
                        this.failureMessage3 = "";
                    }
                });
        },
        filterEmployeeByUsername: function (staffMemberUsername) {
            AXIOS.get("/staffMember/staffMemberName/" + staffMemberUsername)
                .then(response => {
                    this.employees = [response.data]
                    this.failureMessage1 = "";
                    this.failureMessage2 = "";
                    this.failureMessage3 = "";
                })
                .catch(e => {
                    if (e.response.status == 404) {
                        this.failureMessage1 = "";
                        this.failureMessage2 = "No StaffMember found with this username.";
                        this.failureMessage3 = "";
                    }
                });
        },
        createEmployee: function (newEmail, newUsername, newPassword) {
            AXIOS.post('/staffMember', {
                email: newEmail,
                username: newUsername,
                password: newPassword
            })
                .then(response => {
                    console.log(response)
                    this.employees.push(response.data)
                    this.newEmail = ''
                    this.newUsername = ''
                    this.newPassword = ''
                    this.failureMessage1 = "";
                    this.failureMessage2 = "";
                    this.failureMessage3 = "";
                })
                .catch(error => {
                    this.failureMessage1 = "";
                    this.failureMessage2 = "";
                    this.failureMessage3 = "StaffMember already exists.";
                })
        }
    },
    deleteEmployee: function (staffMemberID) {
        AXIOS.delete('/staffMember/delete/' + staffMemberID)
            .then(response => {
                this.employees = [response.data]
                this.failureMessage = "";
            })
            .catch(e => {
                if (e.response.status == 404) {
                    this.failureMessage = "No StaffMember found with this id.";
                }
            });
    }

};
</script>

<style scoped>
#Manage-Employee-body {
    display: flex;
    flex-direction: column;

}

#employee-header,
h2 {
    padding: 20px;
    text-align: center;
    margin: auto;
}

#filter-employee-layout {
    display: flex;
    flex-direction: row;
    margin: auto;
    padding: 20px;
}

#filter-by-id,
#filter-by-username {
    padding: 0px 10px;
}

table,
th,
td {
    justify-content: center;
    text-align: center;
    margin: auto;
    padding: 8px;

    max-width: 800px;
    margin: 30px auto;
    background: #eaf2f8;
    text-align: left;
    border: 1px solid black;
}

button {
    background: #54B4D3;
    border: 0;
    padding: 10px 10px;
    margin-top: 5px;
    color: white;
    border-radius: 20px;
    min-width: 100px;
}

button:hover {
    background: #38788e;
}

.create-employee-layout {
    margin: auto;
}

#enter-data-text {
    padding: 10px;
    text-align: center;
    margin: auto;
}
</style>
