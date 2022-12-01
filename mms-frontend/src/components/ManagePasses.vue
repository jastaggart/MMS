<template>
  <div class="main">
    <h2 class="managePassHeader">Purchased Passes</h2>
    <div class="filter">
      <label class="label">Filter passes by date: </label>
      <input
        @change.prevent="filterPassesByDate(passDate)"
        type="artist"
        required
        v-model="passDate"
        class="dateInput"
        placeholder="YYYY-MM-DD"
      />
      <button class="button" @click.preventDefault="showAllPasses()">
        Show All Passes
      </button>
    </div>
    <p class="failMessage" v-if="this.failureMessage1" style="color: red">
      {{ this.failureMessage1 }}
    </p>
    <p class="failMessage" v-if="this.failureMessage2" style="color: red">
      {{ this.failureMessage2 }}
    </p>
    <table id="pass-table">
      <tr class="topRow">
        <th>Pass ID</th>
        <th>Visitor ID</th>
        <th>Email</th>
        <th>Username</th>
        <th>Pass Date</th>
      </tr>
      <tr v-for="pass in Passes" :key="pass.passID">
        <td>{{ pass.passID }}</td>
        <td>{{ pass.visitorID }}</td>
        <td>{{ getEmail(pass.visitorID) }}</td>
        <td>{{ getUsername(pass.visitorID) }}</td>
        <td>{{ pass.passDate.substring(0, 10) }}</td>
      </tr>
    </table>
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
  name: "ManagerPass",
  data() {
    return {
      Passes: [],
      email: "",
      username: "",
      emailMade: false,
      usernameMade: false,
      visitors: [],
      passDate: "",
      failureMessage1: "",
      failureMessage2: ""
    };
  },
  created() {
    AXIOS.get("/passes")
      .then(response => {
        this.Passes = response.data;
        this.email = "";
        this.username = "";
      })
      .catch(e => {
        console.log("Error in GET /Passes:");
        console.log(e);
      });

    AXIOS.get("/visitors")
      .then(response => {
        this.visitors = response.data;
        console.log(this.visitors);
      })
      .catch(e => {
        console.log(e);
      });
  },
  methods: {
    getEmail: function(visitorID) {
      for (let i = 0; i < this.visitors.length; i++) {
        if (this.visitors[i].body.visitorId == visitorID) {
          return this.visitors[i].body.email;
        }
      }
    },
    getUsername: function(visitorID) {
      for (let i = 0; i < this.visitors.length; i++) {
        if (this.visitors[i].body.visitorId == visitorID) {
          return this.visitors[i].body.username;
        }
      }
    },
    filterPassesByDate: function(passDate) {
      AXIOS.get("/pass/date/" + passDate)
        .then(response => {
          this.Passes = response.data;
          this.failureMessage1 = "";
          this.failureMessage2 = "";
        })
        .catch(e => {
          if (e.response.status == 404) {
            this.failureMessage1 = "No passes found for this date.";
          } else  {
            this.failureMessage2 = "Please enter a valid date using this format: YYYY-MM-DD";
          }
        });
    },
    showAllPasses: function() {
      AXIOS.get("/passes")
        .then(response => {
          this.Passes = response.data;
          this.email = "";
          this.username = "";
          this.failureMessage1 = "",
          this.failureMessage2 = ""
        })
        .catch(e => {
          console.log(e);
        });
    }
  }
};
</script>

<style scoped>
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

.managePassHeader {
  padding: 20px;
  text-align: center;
  margin: auto;
}

.filter {
  display: flex;
  flex-direction: row;
  justify-content: center;
  margin-bottom: 15px;
}

.button {
  appearance: button;
  background-color: #54b4d3;
  border: solid transparent;
  border-radius: 16px;
  border-width: 0 0 4px;
  box-sizing: border-box;
  color: #ffffff;
  cursor: pointer;
  display: inline-block;
  font-family: Baskerville;
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
  width: 165px;
  height: 36px;
  margin-left: 7px;
}

.topRow {
  background-color: grey;
}

.label {
  margin-right: 5px;
  font-family: Baskerville;
  font-size: 20px;
}

.button {
  margin-left: 3rem;
}

.dateInput {
  margin-right: 3rem;
}

.failMessage {
  text-align: center;
}
</style>
