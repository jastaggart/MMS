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

      <div class="submit">
        <button>Log In</button>
      </div>
    </form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      email: "",
      password: "",
      userType: ""
    };
  },

  methods: {
    handleSubmit() {
      // call axios methods for get for owner, employee and visitor
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
