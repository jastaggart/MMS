<template>
  <div class="main">
    <div class="banner">
      <h4>The artworks on display at Marwan's Museum are best enjoyed in person! Purchase a pass below and come visit on a day of your choosing.</h4>
    </div>
    <div class="purchasePassBox">
      <h2 class="purchasePassTitle">Purchase a Pass</h2>
      <div class="purchasePass">
        <input type="date" v-model="passDate" />
        <button class="button" @click.preventDefault="purchase(passDate)">
          Purchase
        </button>
        <p v-if="this.failiureMessage" style="color: red">
          {{ this.failiureMessage }}
        </p>
      </div>
    </div>
    <h2 class="purchasePassTitle">My Passes</h2>
    <div class="passesContainer">
      <div
        v-for="date in passDates"
        :key="date"
        class="card"
        style="width: 18rem;"
        :placeholder="'No passes have been purchased.'"
      >
        <img class="card-img-top" src="../assets/m6.png" alt="Card image cap" />
        <div class="card-body">
          <h5 class="card-title">{{ date.substring(0, 10) }}</h5>
          <p class="card-text">
            Passes on this day: {{ getNumberOfPasses(date) }}
          </p>
        </div>
      </div>
      <p class="emptyMessage" v-if="(Passes.length == 0)">No passes have been purchased.</p>
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
  name: "VisitorPass",
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

        this.passDates = [];
        for (let i = 0; i < this.Passes.length; i++) {
          this.passDates.push(this.Passes[i].passDate);
        }

        this.passDatesRepeating = this.passDates;
        this.passDates = [...new Set(this.passDates)];
      })
      .catch(e => {
        this.errorPass = e;
      });
  },

  methods: {
    createPass: async function(passDate) {
      const year = passDate.substring(0, 4);
      console.log(year);
      const month = passDate.substring(5, 7);
      console.log(month);
      const day = passDate.substring(8, 10);
      console.log(day);
      passDate = day + "-" + month + "-" + year;
      console.log(passDate);
      try {
        const resp = await AXIOS.post("/pass", {
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
              this.failiureMessage = "Please enter a valid date";
              this.successfullMessage = "";
              this.passDate = "";
            }
          });
      } catch (error) {
        console.log(error);
      }
    },
    showPasses: function() {
      // Initializing pass from backend
      AXIOS.get("/pass/visitor/" + window.sessionStorage.getItem("visitorID"))
        .then(response => {
          this.passDates = [];
          for (let i = 0; i < this.Passes.length; i++) {
            this.passDates.push(this.Passes[i].passDate);
            console.log(this.Passes[i].passDate);
          }

          this.passDatesRepeating = this.passDates;
          this.passDates = [...new Set(this.passDates)];
          console.log(this.passDates);
          console.log(this.passDatesRepeating);
        })
        .catch(e => {
          this.errorPass = e;
        });
    },
    getNumberOfPasses: function(date) {
      let count = 0;
      for (let i = 0; i < this.passDatesRepeating.length; i++) {
        if (this.passDatesRepeating[i] == date) {
          count = count + 1;
        }
      }
      return count;
    },
    purchase: async function(passDate) {
      const answer = await this.createPass(passDate);
      this.showPasses();
    }
  }
};
</script>

<style scoped>
.main {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.banner {
  margin-top: 2rem;
  margin-bottom: 2rem;
  font-family: Baskerville;
  color: white;
  max-width: 75%;
  text-align: center;
  margin-left: auto;
  margin-right: auto;
  background-color: grey;
  padding: 10px;
  border-radius: 25px;
  opacity: 0.75;
}

.purchasePassTitle {
  font-family: Baskerville;
  font-size: 48px;
  margin-top: 25px;
}

.purchasePassBox {
  padding: 10px;
  margin: 10px;
}

.passesContainer {
  display: flex;
  flex-flow: row wrap;
  max-width: 80%;
  padding: 10px;
  padding-right: 0px;
}

.card {
  margin: 6px;
  box-shadow: 6px 8px 4px rgb(26, 26, 26);
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
  width: 130px;
  height: 36px;
  margin-left: 7px;
}

.button:hover {
  box-shadow: 0 8px 16px 0 rgba(0, 0, 0, 0.2);
}

.emptyMessage {
  font-family: Baskerville;
  font-size: 20px;
  margin-top: 2rem;
  margin-bottom: 2rem;
  color: white;
  max-width: 100%;
  text-align: center;
  margin-left: auto;
  margin-right: auto;
  background-color: grey;
  padding: 10px;
  border-radius: 25px;
  opacity: 0.75;
}
</style>
