<template>
  <div>
    <h2>Purchase a Pass</h2>

    <main class="formContainer">
      <div id="purchasepass">
        <label>Pass Date:</label>
        <input type="date" v-model="passDate" placeholder="dd-mm-yyyy" />
        <p v-if="this.failiureMessage" style="color: red">
          {{ this.failiureMessage }}
        </p>
        <p v-if="this.successfullMessage" style="color: green">
          {{ this.successfullMessage }}
        </p>
        <button @click="createPass(passDate)">Purchase Pass</button>
      </div>
      <div id="passes">
        <h2>My Passes</h2>
        <button @click.preventDefault="created2">Click me</button>
        <div
          v-for="date in passDates"
          :key="date"
          class="card"
          style="width: 18rem;"
        >
          <img
            class="card-img-top"
            src="../assets/m6.png"
            alt="Card image cap"
          />
          <div class="card-body">
            <h5 class="card-title">{{getNumberOfPasses(date)}}</h5>
            <p class="card-text">
              {{ date.substring(0, 10) }}
            </p>
          </div>
        </div>
        <table>
          <tr v-for="pass in Passes" :key="pass.passDate">
            <td>{{ pass.passDate.substring(0, 10) }}</td>
          </tr>
        </table>
      </div>
    </main>
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
  name: "Pass",
  data() {
    return {
      Passes: [],
      passDates: [],
      passID: "",
      passDate: "",
      visitorID: "",
      failiureMessage: "",
      successfullMessage: "",
      newDate: "",
      passDatesRepeating: []
    };
  },
  created: function() {
    // Initializing pass from backend
    AXIOS.get("/pass/visitor/" + window.sessionStorage.getItem("visitorID"))
      .then(response => {
        this.Passes = response.data;
      })
      .catch(e => {
        this.errorPass = e;
      });

    this.passDates = []
    for (let i = 0; i < this.Passes.length; i++) {
      this.passDates.push(this.Passes[i].passDate);
    }

    this.passDatesRepeating = this.passDates
    this.passDates = [...new Set(this.passDates)];
    console.log(passDates);
  },

  methods: {
    createPass: function(passDate) {
      const year = passDate.substring(0, 4);
      console.log(year);
      const month = passDate.substring(5, 7);
      console.log(month);
      const day = passDate.substring(8, 10);
      console.log(day);
      passDate = day + "-" + month + "-" + year;
      console.log(passDate);
      AXIOS.post("/pass", {
        visitorID: window.sessionStorage.getItem("visitorID"),
        passDate: passDate
      })
        .then(response => {
          this.Passes.push(response.data);
          this.successfullMessage = "Pass created";
          this.passDate = passDate;
          this.failiureMessage = "";
          this.visitorID = window.sessionStorage.getItem("visitorID");
          this.passDate = "";
        })
        .catch(e => {
          if (e.response.status == 400 || e.response.status == 500) {
            this.failiureMessage =
              "Please use the following format: dd-mm-yyyy";
            this.successfullMessage = "";
            this.passDate = "";
          }
        });
      for (let i = 0; i < this.Passes.length; i++) {
        this.passDates.push(this.Passes[i].passDate);
      }

      this.passDates = [...new Set(this.passDates)];
    },
    created2: function() {
      // Initializing pass from backend
      AXIOS.get("/pass/visitor/" + window.sessionStorage.getItem("visitorID"))
        .then(response => {
          this.Passes = response.data;
        })
        .catch(e => {
          this.errorPass = e;
        });

      this.passDates = []
      for (let i = 0; i < this.Passes.length; i++) {
        this.passDates.push(this.Passes[i].passDate);
      }

      this.passDatesRepeating = this.passDates
      this.passDates = [...new Set(this.passDates)];
      console.log(passDates);
    },
    getNumberOfPasses: function(date) {
      let count = 0
      for (let i = 0; i < this.passDatesRepeating.length; i++) {
        if (this.passDatesRepeating[i] == date) {
          count = count + 1
        }
      }
      return count
    }
  }
};
</script>

<style></style>
