<template>
    <div id="Manage-Employee-body">
        <h2 id="employee-header">Employee List</h2>

        <div id="filter-employee-layout">
            <div id="filter-by-id">
                <label>Filter by Id:</label>
                <input required v-model="Employee" />
            </div>

            <div id="filter-by-username">
                <label>Filter by Username:</label>
                <input required v-model="Employee" />
            </div>
        </div>

        <table id="employee-table">
            <tr class="top-row">
                <th>Id</th>
                <th>Email</th>
                <th>Username</th>
                <th>Password</th>
                <th>Add/Delete</th>
            </tr>
            <tr v-for="e in employees" :key="e.staffMemberId">
                <td>{{ e.staffMemberID }}</td>
                <td contenteditable="true">{{ e.email }}</td>
                <td contenteditable="true">{{ e.username }}</td>
                <td contenteditable="true">{{ e.password }}</td>
                <td><button class="add-delete-employee-button">Delete</button></td>
            </tr>
            <tr id="last-row">
                <td></td>
                <td><input type="text" placeholder="Email" v-model="newEventDate"></td>
                <td><input type="text" placeholder="Username" v-model="newEventStartTime"></td>
                <td><input type="text" placeholder="Password" v-model="newEventEndTime"></td>
                <td><button class="add-delete-employee-button">Add</button></td>
            </tr>
        </table>
        <div id="save-discard-layout">
            <button id="save-employee-changes-button">Save Changes</button>
            <button id="cancel-employee-changes-button">Cancel</button>
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
            failiureMessage: ""
        };
    },
    created() {
        AXIOS.get('/staffMembers')
            .then(response => {
                console.log(response)
                this.employees = response.data
            })
            .catch(e => {
                console.log('Error in GET /StaffMembers:')
                console.log(e)
            })
    }
}
</script>

<style>
#Manage-Employee-body {
    display: flex;
    flex-direction: column;

}

#employee-header {
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
    border: 2px solid black;
    align-items: center;
    justify-content: center;
    text-align: center;
    margin: auto;
    padding: 20px;
}

.top-row {
    background-color: gray;
}

.add-delete-employee-button:hover,
#cancel-employee-changes-button:hover {
    background-color: lightgray;
}

#save-discard-layout {
    display: flex;
    flex-direction: row;
    align-items: flex-end;
    margin: auto;
    padding-top: 20px;
}

#save-employee-changes-button {
    background-color: #54B4D3;
    color: white;
    margin: 25px;
}

#save-employee-changes-button:hover {
    background-color: #3d7f95;
}

#cancel-employee-changes-button {
    background-color: white;
    color: red;
    margin: 25px;
}
</style>
