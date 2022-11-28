<template>
  <div>
    <div class="greeting">
      <h1>Log In</h1>
      <p>
        Please fill out the form below to log in to the Museum Management System
      </p>
    </div>
    <form @submit.prevent="handleSubmit">
      <label>Email:</label>
      <input type="email" required v-model="email" />

      <label>Password:</label>
      <input type="password" required v-model="password" />

      <label>Type Of User:</label>
      <select v-model="userType">
        <option value="owner">Owner</option>
        <option value="visitor">Visitor</option>
        <option value="employee">Employee</option>
      </select>

      <p v-if="this.failiureMessage" style="color: red">
        {{ this.failiureMessage }}
      </p>

      <div class="submit">
        <button>Log In</button>
      </div>
    </form>
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
  name: "Login",
  data() {
    return {
      email: "",
      password: "",
      userType: "",
      failiureMessage: ""
    };
  },

  methods: {
    handleSubmit() {
      if (this.userType == "visitor") {
        AXIOS.get("/visitor/email/" + this.email, {})
          .then(response => {
            console.log(response);
            if (response.data.password != this.password) {
              this.failiureMessage = "Invalid Password for " + this.email;
            } else {
              window.sessionStorage.setItem('userType', 'visitor');
              window.sessionStorage.setItem('password', this.password);
              window.sessionStorage.setItem('email', this.email);
              window.sessionStorage.setItem('visitorID', response.data.visitorId);
              this.email = "";
              this.password = "";
              this.userType = "";
              this.failiureMessage = "";
              this.$router.push('VisitorArtworks');
              window.location.reload();
            }
          })
          .catch(e => {
            if (e.response.status == 404) {
              this.failiureMessage = e.response.data;
            }
          });
      } 

      if (this.userType == "employee") {
        AXIOS.get("/staffMember/staffMemberEmail/" + this.email, {})
          .then(response => {
            console.log(response);
            if (response.data.password != this.password) {
              this.failiureMessage = "Invalid Password for " + this.email;
            } else {
              window.sessionStorage.setItem('userType', 'employee');
              window.sessionStorage.setItem('password', this.password);
              window.sessionStorage.setItem('email', this.email);
              window.sessionStorage.setItem('employeeID', response.data.staffMemberID);
              this.email = "";
              this.password = "";
              this.userType = "";
              this.failiureMessage = "";
              this.$router.push('ManageArtworks');
              window.location.reload();
            }
          })
          .catch(e => {
            if (e.response.status == 404) {
              this.failiureMessage = e.response.data;
            }
          });
      } 

      if (this.userType == "owner") {
        AXIOS.get("/staffMember/staffMemberEmail/" + this.email, {})
          .then(response => {
            console.log(response);
            if (response.data.password != this.password) {
              this.failiureMessage = "Invalid Password for " + this.email;
            } else if(this.email != "mmsOwner@gmail.com") {
              this.failiureMessage = "Invalid owner email."
            } else {
              window.sessionStorage.setItem('userType', 'owner');
              window.sessionStorage.setItem('password', this.password);
              window.sessionStorage.setItem('email', this.email);
              window.sessionStorage.setItem('employeeID', response.data.staffMemberID);
              this.email = "";
              this.password = "";
              this.userType = "";
              this.failiureMessage = "";
              this.$router.push('ManageArtworks');
              window.location.reload();
            }
          })
          .catch(e => {
            if (e.response.status == 404) {
              this.failiureMessage = e.response.data;
            }
          });
      } 

    }
  }
};
</script>

<style>
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
