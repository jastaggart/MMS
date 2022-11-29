<template>
  <div>
    <div class="greeting">
      <h1>Register</h1>
      <p>
        Please fill out the form below to create a Visitor account and register
        for the Museum Management System
      </p>
    </div>
    <form>
      <label>Username:</label>
      <input type="username" required v-model="username" />

      <label>Email:</label>
      <input type="email" required v-model="email" />

      <label>Password:</label>
      <input type="password" required v-model="password" />

      <!-- <p class = "registration-confirmation">Account created for {{username}} with email {{email}}. Proceed to login.</p> -->

      <p v-if="this.successfullMessage" style="color: green">
          {{ this.successfullMessage }}
        </p>
        <p v-if="this.failiureMessage" style="color: red">
          {{ this.failiureMessage }}
        </p>

      <div class="submit">
        <button
          @click.preventDefault="createVisitor(username, email, password)"
        >
          Create account
        </button>
      </div>
      
    </form>
    
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
  name: "Register",
  data() {
    return {
      visitors: [],
      username: "",
      email: "",
      password: "",
      failiureMessage: "",
      successfullMessage: ""
    };
  },
  created: function() {
    // Initializing persons from backend
    AXIOS.get("/visitors")
      .then(response => {
        this.visitors = response.data;
      })
      .catch(e => {
        this.errorVisitor = e;
      });
  },

  methods: {
    createVisitor: function(username, email, password) {
      AXIOS.post("/visitor", {
        username: username,
        email: email,
        password: password
      })
        .then(response => {
          this.visitors.push(response.data);
          this.successfullMessage = "Account created for " + this.username  + " with email " + this.email + ". Proceed to login.";
          this.username = "";
          this.email = "";
          this.password = "";
          this.failiureMessage = "";
        })
        .catch(e => {
          if (e.response.status == 400) {
            this.failiureMessage = e.response.data;
            this.successfullMessage = "";
          }
        });
    }
  }
};
</script>

<style scoped>
.greeting {
  padding-top: 20px;
  text-align: center;
  margin: auto;
}

.greeting p {
  font-style: italic;
}

form {
  max-width: 640px;
  margin: 30px auto;
  background: white;
  text-align: left;
  padding: 40px;
  border-radius: 10px;
}

label {
  color: black;
  display: inline-block;
  margin: 25px 0 15px;
  font-size: 0.6rem;
  text-transform: uppercase;
  letter-spacing: 1px;
  font-weight: bold;
}

input,
select {
  display: block;
  padding: 10px 6px;
  width: 100%;
  box-sizing: border-box;
  border: none;
  border-bottom: 1px solid #ddd;
  color: #555;
}

button {
  background: #0b6dff;
  border: 0;
  padding: 10px 20px;
  margin-top: 20px;
  color: white;
  border-radius: 20px;
}
.submit {
  text-align: center;
}
</style>
