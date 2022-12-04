
<template>
    <div>
        <div v-if="currentUserType == 'employee'">
            <h2 class=" viewShiftHeader">View Shifts</h2>
            <div class="filters">
                <div class="filter-by-date">
                    <label>Date:</label>
                    <input type="date" @change="filterShiftsByDateEmployee(date)" v-model="date">
                    <p v-if="this.failureMessageEmployee" style="color: red">
                        {{ this.failureMessageEmployee }}
                    </p>
                </div>
            </div>

            <div id="View-Shift-body">

                <table id="shift-table-E">
                    <tr class="topRow">
                        <th>Shift ID</th>
                        <th>Employee ID</th>
                        <th>Date</th>
                        <th>Start Time</th>
                        <th>End Time</th>
                    </tr>
                    <tr v-for="shift in EmployeeShifts" :key="shift.shiftID">
                        <td>{{ shift.shiftID }}</td>
                        <td>{{ shift.shiftAssigneeID }}</td>
                        <td>{{ shift.date }}</td>
                        <td>{{ shift.startHour }}</td>
                        <td>{{ shift.endHour }}</td>
                    </tr>
                </table>
            </div>
        </div>

        <div v-if="currentUserType == 'owner'">
            <h2 class=" manageShiftHeader">Manage Shifts</h2>

            <div class="filters">
                <div class="filter-by-shiftID">
                    <label>Filter by Shift ID:</label>
                    <input @change="filterShiftsByInput" v-model="shiftID" />
                    <p style="color: red">
                        {{ this.failureMessage1 }}
                    </p>
                </div>

                <div class="filter-by-employeeID">
                    <label>Employee ID:</label>
                    <input list="employees" @change.prevent="filterShiftsByInput" v-model="employeeID">
                    <datalist id="employees">
                        <option v-for="employee in Employees" :key="employee.staffMemberID"
                            :value="employee.staffMemberID">
                            {{ employee.username }}</option>
                    </datalist>
                    <p v-if="this.failureMessage2" style="color: red">
                        {{ this.failureMessage2 }}
                    </p>
                </div>

                <div class="filter-by-date">
                    <label>Date:</label>
                    <input type="date" @change="filterShiftsByInput" v-model="date">
                    <p v-if="this.failureMessage3" style="color: red">
                        {{ this.failureMessage3 }}
                    </p>
                </div>

                <div class="all-shifts">
                    <button @click.preventDefault="getAllShifts()">Reset</button>
                </div>
            </div>


            <div id="Manage-Shift-body">

                <table id="shift-table" style="margin-bottom:50px">
                    <tr class="topRow">
                        <th>Shift ID</th>
                        <th>Employee ID</th>
                        <th>Employee Name</th>
                        <th>Date</th>
                        <th>Start Time</th>
                        <th>End Time</th>
                    </tr>
                    <tr v-for="shift in Shifts" :key="shift.shiftID">
                        <td>{{ shift.shiftID }}</td>
                        <td>{{ shift.shiftAssigneeID }}</td>
                        <td>{{ getUsername(shift.shiftAssigneeID) }}</td>
                        <td>{{ shift.date }}</td>
                        <td>{{ shift.startHour }}</td>
                        <td>{{ shift.endHour }}
                            <div class="hide">
                                <button type='button' id="modify" class="modify"
                                    @click="shiftToModify = shift; showPopup = true;">M</button>
                                <button type='button' id="delete" class="delete"
                                    @click.row="deleteShift(shift)">X</button>
                            </div>
                        </td>

                    </tr>
                </table>

                <vs-popup id="modifyShift" title="Modify Shift" :active.sync="showPopup">
                    <div style="margin-bottom:20px"><label style="font-size:15px">Select New Employee:</label>
                        <input style="width:80px" list="employees" v-model="modifyEmployeeID">
                        <datalist id="employees">
                            <option v-for="employee in Employees" :key="employee.staffMemberID"
                                :value="employee.staffMemberID">
                                {{ employee.username }}</option>
                        </datalist>
                        <button @click="reassignShift(shiftToModify.shiftID, modifyEmployeeID)"
                            style="float:right">Reassign</button>
                    </div>
                    <div>
                        <label style="font-size:15px">Date/time:</label>
                        <input type="date" v-model="modifyShiftDate">
                        <input type="time" step="1" v-model="modifyShiftStartHour">
                        <input type="time" step="1" v-model="modifyShiftEndHour">
                        <button style="float:right;width:100px"
                            @click="modifyShift(shiftToModify.shiftID, modifyShiftDate, modifyShiftStartHour, modifyShiftEndHour)">Modify</button>

                    </div>
                    <p v-if="failureMessage7" style="color:red"> {{ failureMessage7 }} </p>
                    <p v-if="failureMessage6" style="color:red"> {{ failureMessage6 }} </p>
                    <p v-if="successMessage7" style="color:green"> {{ successMessage7 }} </p>
                    <p v-if="successMessage6" style="color:green"> {{ successMessage6 }} </p>
                </vs-popup>

            </div>

            <hr>

            <div class="create">
                <h2> Add New Shift </h2>
                <p style="font-size:20px">Enter the employee id, date, start time and end time to create new shift.</p>
                <p style="color:red;"> {{ this.failureMessage5 }} </p>
                <p style="color:green;"> {{ this.successMessage5 }} </p>
                <table>
                    <tr>
                        <td>
                            <input placeholder="Employee ID" list="employees" v-model="shiftAssigneeID">
                            <datalist id="employees">
                                <option v-for="employee in Employees" :key="employee.staffMemberID"
                                    :value="employee.staffMemberID">
                                    {{ employee.username }}
                                </option>
                            </datalist>
                        </td>
                        <td><input type="date" placeholder="Date" v-model="shiftDate"></td>
                        <td><input type="time" step="1" placeholder="Start Time" v-model="shiftStartHour"></td>
                        <td><input type="time" step="1" placeholder="End Time" v-model="shiftEndHour"></td>
                        <td><button
                                @click.preventDefault="createShift(shiftAssigneeID, shiftDate, shiftStartHour, shiftEndHour)">Create</button>
                        </td>
                    </tr>
                </table>
            </div>
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
    name: "ManageShift",
    data() {
        return {
            currentUserType: window.sessionStorage.getItem('userType'),
            currentEmployeeID: window.sessionStorage.getItem('employeeID'),

            showPopup: false,
            shiftToModify: "",

            //variables for display owner
            Shifts: [],
            Employees: [],

            //variables for display and filter employee
            EmployeeShifts: [],
            EmployeeShifts_Constant: [],
            EmployeeShiftsDateFilter: [],

            //variables for filter methods owner
            ShiftsAll: [],
            ShiftsBySID: [],
            ShiftsByEID: [],
            ShiftsByDate: [],
            filteredShifts: [],

            //output messages for display
            failureMessageEmployee: "",
            failureMessage1: "",
            failureMessage2: "",
            failureMessage3: "",
            failureMessage5: "",
            successMessage5: "",
            failureMessage6: "", //modify shift
            failureMessage7: "", //reassign shift
            successMessage6: "",
            successMessage7: "",

            //user input for filters
            shiftID: "",
            employeeID: "",
            date: "",

            //user input for shift create
            shiftAssigneeID: "",
            shiftDate: "",
            shiftStartHour: "",
            shiftEndHour: "",
        };
    },

    created: function () {
        AXIOS.get("/shifts")
            .then(response => {
                this.Shifts = response.data;
            })
        AXIOS.get("/shift/employee/" + this.currentEmployeeID)
            .then(response => {
                this.EmployeeShifts = response.data;
                this.EmployeeShifts_Constant = response.data;
            })
        AXIOS.get('/staffMembers')
            .then(response => {
                this.Employees = response.data
                this.Employees.splice(0, 1);
            })

    },

    methods: {
        getUsername: function (staffMemberID) {
            for (let i = 0; i < this.Employees.length; i++) {
                if (this.Employees[i].staffMemberID == staffMemberID) {
                    return this.Employees[i].username;
                }
            }
        },
        modifyShift: function (shiftID, modifyShiftDate, modifyShiftStartHour, modifyShiftEndHour) {
            if (modifyShiftDate == undefined) modifyShiftDate = "";
            if (modifyShiftStartHour == undefined) modifyShiftStartHour = "";
            if (modifyShiftEndHour == undefined) modifyShiftEndHour = "";

            AXIOS.put("/shift/modify/" + shiftID, {
                date: modifyShiftDate,
                startHour: modifyShiftStartHour,
                endHour: modifyShiftEndHour,
                shiftAssignerID: 1, //doesnt matter
                shiftAssigneeID: 1 //doesnt matter
            })
                .then(response => {
                    this.successMessage6 = "Shift modified."
                    location.reload();
                })
                .catch(e => {
                    this.failureMessage6 = "Start time is not before end time."
                });

        },
        reassignShift: function (shiftID, employeeID) {
            if (shiftID != "") {
                AXIOS.put("/shift/reassign/" + shiftID + "/" + employeeID)
                    .then(response => {
                        this.successMessage7 = "Shift reassigned."
                        location.reload();
                    })
                    .catch(e => {
                        if (e.response.status == 404) {
                            this.failureMessage7 = "No shift assigned to employee with this id";
                        }
                        else {
                            this.failureMessage7 = "Invalid employee id";
                        }
                    });
            }
        },
        deleteShift: function (shift) {
            AXIOS.delete("/shift/delete/" + shift.shiftID)
                .then(response => {
                    location.reload();
                })
                .catch(e => {
                });
        },
        filterShiftsByDateEmployee: function (date) {
            this.failureMessageEmployee = '';
            if (date != '') {
                AXIOS.get("/shift/date/" + date)
                    .then(response => {
                        this.EmployeeShiftsDateFilter = response.data;
                        this.EmployeeShifts = this.EmployeeShiftsDateFilter.filter(a => this.EmployeeShifts_Constant.some(b => a.shiftID === b.shiftID));
                        if (this.EmployeeShifts.length == 0) {
                            this.EmployeeShifts = this.EmployeeShifts_Constant;
                            this.failureMessageEmployee = "No shift on this date";
                        }
                    })
                    .catch(e => {
                        if (e.response.status == 404) {
                            this.EmployeeShifts = this.EmployeeShifts_Constant;
                            this.failureMessageEmployee = "No shift on this date";
                        }
                    });
            }
            else this.EmployeeShifts = this.EmployeeShifts_Constant;
        },
        filterShiftsByInput: async function () {
            if (this.shiftID == '' && this.employeeID == '' && this.date == '') {
                this.getAllShifts();
            }
            else {

                this.failureMessage1 = "";
                this.failureMessage2 = "";
                this.failureMessage3 = "";

                const resp1 = await AXIOS.get("/shifts").catch(e => {
                    if (e.response.status == 404) {
                    }
                });
                try {
                    this.ShiftsAll = resp1.data;
                } catch (e) {
                    this.ShiftsAll = [];
                }

                if (this.shiftID != '') {
                    const resp2 = await AXIOS.get("/shift/shiftID/" + this.shiftID).catch(e => {
                        if (e.response.status == 404) {
                            this.failureMessage1 = "No shift with this id";
                        }
                        else {
                            this.failureMessage1 = "Invalid shift id";
                        }
                    });
                    try {
                        this.ShiftBySID = [];
                        this.ShiftBySID.push(resp2.data);
                    } catch (e) {
                        this.ShiftBySID = this.ShiftsAll;
                    }
                }
                else this.ShiftBySID = this.ShiftsAll;

                if (this.employeeID != '') {
                    const resp3 = await AXIOS.get("/shift/employee/" + this.employeeID).catch(e => {
                        if (e.response.status == 404) {
                            this.failureMessage2 = "No shift assigned to employee";
                        }
                        else {
                            this.failureMessage2 = "Invalid employee id";
                        }
                    });
                    try {
                        this.ShiftsByEID = resp3.data;
                    } catch (e) {
                        this.ShiftsByEID = this.ShiftsAll;
                    }
                }
                else this.ShiftsByEID = this.ShiftsAll;

                if (this.date != '') {
                    const resp4 = await AXIOS.get("/shift/date/" + this.date).catch(e => {
                        if (e.response.status == 404) {
                            this.failureMessage3 = "No shift on this date";
                        }
                    });
                    try {
                        this.ShiftsByDate = resp4.data;
                    } catch (e) {
                        this.ShiftsByDate = this.ShiftsAll;
                    }
                }
                else this.ShiftsByDate = this.ShiftsAll;

                this.filteredShifts = this.ShiftBySID.filter(a => this.ShiftsByEID.some(b => a.shiftID === b.shiftID));
                this.Shifts = this.filteredShifts.filter(a => this.ShiftsByDate.some(b => a.shiftID === b.shiftID))

            }
        },
        getAllShifts: function () {
            this.shiftID = "",
            this.employeeID = "",
            this.date = "",
            this.failureMessage2 = "";
            this.failureMessage3 = "";

            AXIOS.get("/shifts")
                .then(response => {
                    this.Shifts = response.data;
                })
                .catch(e => {
                    if (e.response.status == 404) {
                    }
                });
        },
        createShift: function (shiftAssignee, shiftDate, shiftStartHour, shiftEndHour) {
            this.failureMessage5 = "";
            this.successMessage5 = "";
            if (shiftAssignee == "" || shiftDate == "" || shiftStartHour == "" || shiftEndHour == "") {
                this.failureMessage5 = "Missing input";
            }
            else {
                AXIOS.post('/shift', {
                    date: shiftDate,
                    startHour: shiftStartHour,
                    endHour: shiftEndHour,
                    shiftAssignerID: 1,
                    shiftAssigneeID: shiftAssignee
                })
                    .then(response => {
                        this.successMessage5 = "New shift created.";
                        location.reload();
                    })
                    .catch(error => {
                        this.failureMessage5 = "Invalid input.";
                    })
            }
        },
    }
};
</script>

<style scoped>
table,
td {
    width: 800px;
    margin: 30px auto;
    text-align: left;
    padding: 15px;
    border: 1px solid black;
}

#shift-table,
td {
    width: 1200px;
    margin: 30px auto;
    text-align: left;
    padding: 15px;
    border: 1px solid black;
}

#shift-table-E,
td {
    width: 1200px;
    margin: 30px auto;
    text-align: left;
    padding: 15px;
    border: 1px solid black;
}

tr:first-child {
    background-color: #eaf2f8;
}

tr:hover {
    background-color: #eaf2f8;
}

tr:first-child:hover {
    background-color: #eaf2f8;
}

.create {
    margin: 30 px auto;
}

h2 {
    padding: 20px;
    text-align: center;
    margin: auto;
}

.create h2 {
    font-size: 20pt;
    font-family: Baskerville;
}

p {
    font-size: 15px;
    text-align: center;
    margin: auto;
    font-family: Baskerville;
}

button {
    font-family: Baskerville;
    appearance: button;
    background-color: #54b4d3;
    color: #ffffff;
    border: solid transparent;
    border-radius: 16px;
    box-sizing: border-box;
    cursor: pointer;
    display: inline-block;
    font-size: 15px;
    font-weight: 700;
    letter-spacing: 0.8px;
    margin: 0;
    overflow: visible;
    text-align: center;
    text-transform: uppercase;
    touch-action: manipulation;
    transform: translateZ(0);
    transition: filter 0.2s;
    user-select: none;
    -webkit-user-select: none;
    vertical-align: middle;
    white-space: nowrap;
    width: 100px;
    height: 36px;
}

.topRow {
    background-color: grey;
}

label {
    margin-right: 5px;
    font-family: Baskerville;
    font-size: 20px;
}

.filters {
    margin: auto;
    display: flex;
    flex-direction: row;
    justify-content: center;
}

.filters input {
    width: 60px;
    border: none;
    box-sizing: border-box;
    border-bottom: 1px solid #ddd;
    color: #555;
    border: 1px solid black;
}

.filter-by-shiftID {
    width: 220px;
    font-size: 20px;

}

.filter-by-employeeID {
    width: 190px;
    font-size: 20px;

}

.filter-by-date {
    width: 200px;
    font-size: 20px;

}

.filter-by-date input {
    width: 140px;
    font-size: 20px;
}

.hide button {
    font-family: Arial;
    background: none;
    border: none;
    color: #087498;
    font-size: 15px;
    cursor: pointer;
    width: 20px;
    height: 20px;
}

.hide button:hover {
    color: #58acc5;
}

.hide {
    display: none;
    float: right;
}

tr:hover .hide {
    display: block;
    float: right;
}
</style>