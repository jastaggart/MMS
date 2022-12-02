<template>
    <div>
        <h2 class="manageShiftHeader">Manage Shifts</h2>

        <div class="filters">
            <!-- <div class="filter-by-shiftID">
                <label>Filter by Shift ID:</label>
                <input @change="filterShiftByShiftID(shiftID)" v-model="shiftID" />
                <p style="color: red">
                    {{ this.failureMessage1 }}
                </p>
            </div> -->
            <div class="filter-by-employeeID">
                <label>Employee ID:</label>
                <input list="employees" @change="filterShiftsByEmployeeID(employeeID)" v-model="employeeID">
                <datalist id="employees">
                    <option v-for="employee in Employees" :key="employee.staffMemberID" :value="employee.staffMemberID">
                        {{ employee.username }}</option>
                </datalist>
                <p v-if="this.failureMessage2" style="color: red">
                    {{ this.failureMessage2 }}
                </p>
            </div>

            <div class="filter-by-date">
                <label>Date:</label>
                <input type="date" @change="filterShiftsByDate(date)" v-model="date">
                <p v-if="this.failureMessage3" style="color: red">
                    {{ this.failureMessage3 }}
                </p>
            </div>

            <div class="all-shifts">
                <button @click.preventDefault="getAllShifts()">Reset</button>
            </div>
        </div>


        <div id="Manage-Shift-body">

            <table id="shift-table">
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
                    <td>{{ shift.endHour }}</td>
                </tr>
            </table>
        </div>


        <hr>

        <div class="create">
            <h2> Add New Shift </h2>
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
            //variables for display
            Shifts: [],
            Employees: [],

            //variables for filter methods
            ShiftsAll: [],
            ShiftsBySID: [],
            ShiftsByEID: [],
            ShiftsByDate: [],
            filteredShifts: [],

            //output messages for display
            failureMessage1: "",
            failureMessage2: "",
            failureMessage3: "",
            failureMessage5: "",
            successMessage5: "",

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
            .catch(e => {
                console.log("Error in GET /shifts:");
                console.log(e);
            });
        AXIOS.get('/staffMembers')
            .then(response => {
                console.log(response)
                this.Employees = response.data
                this.Employees.splice(0, 1);
                console.log(this.Employees);
            })
            .catch(e => {
                console.log('Error in GET /StaffMembers:')
                console.log(e)
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
        // filterShiftByShiftID: function (shiftID) {
        //     this.failureMessage1 = '';
        //     if (shiftID == "") return;
        //     AXIOS.get("/shift/shiftID/" + this.shiftID)
        //         .then(response => {
        //             this.Shifts = [];
        //             Shifts[0].shiftID = response.shiftID;
        //             Shifts[0].shiftAssigneeID = response.shiftAssigneeID;
        //             Shifts[0].employeeName = response.getUsername(response.shiftAssigneeID);
        //             Shifts[0].date = response.date;
        //             Shifts[0].startHour = response.startHour;
        //             Shifts[0].endHour = response.endHour;
        //             console.log(response.data);
        //             this.failureMessage1 = '';
        //         })
        //         .catch(e => {
        //             if (e.response.status == 404) {
        //                 this.failureMessage1 = "No shift with id " + shiftID; 
        //             }
        //             else {
        //                 this.failureMessage1 = "Invalid shift id"
        //             }
        //         });
        // },
        filterShiftsByEmployeeID: function (employeeID) {
            this.failureMessage2 = '';
            if (employeeID == "") return;
            AXIOS.get("/shift/employee/" + employeeID)
                .then(response => {
                    this.Shifts = response.data;
                    this.failureMessage2 = '';
                })
                .catch(e => {
                    if (e.response.status == 404) {//Employee with EmployeeID not found
                        this.failureMessage2 = "No employee with ID " + employeeID;
                    }
                    else {
                        this.failureMessage2 = "Invalid employee ID"
                    }
                });
        },
        filterShiftsByDate: function (date) {
            this.failureMessage3 = '';
            if (date == "") return;
            AXIOS.get("/shift/date/" + date)
                .then(response => {
                    this.Shifts = response.data;
                    this.failureMessage3 = '';
                })
                .catch(e => {
                    if (e.response.status == 404) {
                        this.failureMessage3 = "No shift on this date";//no shift on this date
                    }
                });
        },
        getAllShifts: function () {
                // this.shiftID = "",
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

tr:first-child{
    background-color: #eaf2f8;
}
tr:hover {
    background-color: #eaf2f8;
}
tr:first-child:hover{
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

/* .filter-by-shiftID {
    width: 220px;
    font-size: 20px;

} */

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




/* .filters {
  margin-top: 0;
  margin-bottom: 0;
  margin-left: 2px;
  margin-right: 0;
  display: flex;
  flex-direction: row;
  justify-content: center;
} */
/* .filters {
  display: flex;
  flex-direction: row;
  justify-content: center;
  margin-bottom: 15px;
}

.filters input {
  width: 25%;
  border: none;
  box-sizing: border-box;
  color: #555;
  border: 1px solid black;
} */
</style>

// <!-- @PutMapping(value={"/shift/modify/{shiftID}","/shift/modify/{shiftID}/"})
//   public ResponseEntity<ShiftResponseDto> modifyShift(@PathVariable int shiftID, @Valid @RequestBody ShiftRequestDto shiftRequest) {
//     ShiftResponseDto shiftDto = shiftService.modifyShift(shiftID, shiftRequest.getDate(), shiftRequest.getStartHour(), shiftRequest.getEndHour());
//     return new ResponseEntity<ShiftResponseDto>(shiftDto, HttpStatus.OK);
//   } -->