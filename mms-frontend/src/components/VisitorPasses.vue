<template>
  <div>
    <h2>Purchase a Pass</h2>

    <main class="formContainer">
      <div id="purchasepass">
        <label>Pass Date:</label>
        <input type="text" v-model="newDate" placeholder="mm-dd-yyyy" />
        <p v-if="this.failiureMessage" style="color: red">
          {{ this.failiureMessage }}
        </p>
        <p v-if="this.successfullMessage" style="color: green">
          {{ this.successfullMessage }}
        </p>
        <button @click="createPass(newDate)">Purchase Pass</button>
      </div>
      <div id="passes">
        <h2>My Passes</h2>
        <table>
          <tr v-for="pass in passes" :key="pass.date">
            <td>{{ pass.date }}</td>
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
var backendUrl ="http://" + config.dev.backendHost + ":" + config.dev.backendPort;

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { "Access-Control-Allow-Origin": frontendUrl }
});


export default {
  name: "Pass",
  data() {
    return {
      passes: [],
      passID: "",
      passDate: "",
      visitorID: "",
      failiureMessage: "",
      successfullMessage: ""
    };
  },
  created: function() {
    // Initializing pass from backend
    AXIOS.get("/pass/visitor/" + window.sessionStorage.getItem("visitorID"))
      .then(response => {
        this.passes = response.data;
      })
      .catch(e => {
        this.errorPass = e;
      });
  },

  methods: {
    createPass: function(passDate) {
      AXIOS.post("/pass", {
        visitorID: window.sessionStorage.getItem("visitorID"),
        passDate: passDate
      })
        .then(response => {
          this.passes.push(response.data);
          this.successfullMessage = "Pass created";
          this.passDate = passDate;
          this.failiureMessage = "";
          this.visitorID = window.sessionStorage.getItem("visitorID");
        })
        .catch(e => {
          if (e.response.status == 400 || e.response.status == 500) {
            this.failiureMessage = "Please use the following format: dd-mm-yyyy";
            this.successfullMessage = "";
          }
        });
    }
  }
};
</script>

<style></style>
