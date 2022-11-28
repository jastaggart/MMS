<template>
  <nav class="navbar navbar-expand-lg navbar-dark bg-info">
    <router-link to="/">
        <a class="navbar-brand" href="#">Marwan's Museum</a>
    </router-link>
    <button
      class="navbar-toggler"
      type="button"
      data-toggle="collapse"
      data-target="#navbarNav"
      aria-controls="navbarNav"
      aria-expanded="false"
      aria-label="Toggle navigation"
    >
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
      <ul class="navbar-nav">

        <router-link v-if="userType != 'visitor' && userType != 'owner' && userType != 'employee'" to="LogIn">
            <li class="nav-item">
                <a class="nav-link">Log In</a>
            </li>
        </router-link>

        <router-link v-if="userType != 'visitor' && userType != 'owner' && userType != 'employee'" to="Register">
            <li class="nav-item">
                <a class="nav-link" >Register</a>
            </li>
        </router-link>

        <router-link v-if="userType != 'visitor' && userType != 'owner' && userType != 'employee'" to="Artworks">
          <li class="nav-item">
            <a class="nav-link">View Artworks</a>
          </li>     
        </router-link>

        <router-link v-if="userType == 'visitor'" to="VisitorLoans">
            <li class="nav-item">
                <a class="nav-link" >My Loans</a>
            </li>
        </router-link>

        <router-link v-if="userType == 'visitor'" to="VisitorArtworks">
            <li class="nav-item">
                <a class="nav-link">Browse Artworks</a>
            </li>
        </router-link>

        <router-link v-if="userType == 'visitor'" to="VisitorPasses">
            <li class="nav-item">
                <a class="nav-link">Buy Passes</a>
            </li>
        </router-link>

        <router-link v-if="userType == 'employee' || userType == 'owner'" to="ManageArtworks">
            <li class="nav-item">
                <a class="nav-link">Manage Artworks</a>
            </li>
        </router-link>

        <router-link v-if="userType == 'employee' || userType == 'owner'" to="ManageLoans">
            <li class="nav-item">
                <a class="nav-link">Manage Loans</a>
            </li>
        </router-link>

        <router-link v-if="userType == 'owner'" to="ManageShifts">
            <li class="nav-item">
                <a class="nav-link">Manage Shifts</a>
            </li>
        </router-link>

        <router-link v-if="userType == 'owner'" to="ManageEmployees">
            <li class="nav-item">
                <a class="nav-link">Manage Employees</a>
            </li>
        </router-link>

        <div v-if="userType == 'employee' || userType == 'owner' || userType == 'visitor'" class="navigation">
          <a  @click.preventDefault="logout()" class="button" href="">
            <img src="https://www.citypng.com/public/uploads/preview/png-login-logout-white-icon-11641484341czkekai5wp.png">
            <div class="logout">LOGOUT</div>
          </a>
        </div>

      </ul>
    </div>
  </nav>
</template>

<script>
export default {
  name: "Navbar",
  data() {
    return {
      userType: sessionStorage.getItem('userType'),
      username: sessionStorage.getItem('username'),
      email: sessionStorage.getItem('email'),
      password: sessionStorage.getItem('password'),
      visitorID: sessionStorage.getItem('visitorID'),
      employeeID: sessionStorage.getItem('employeeID'),
    };
  },

  methods: {
    logout: function() {
      window.sessionStorage.removeItem('userType');
      window.sessionStorage.removeItem('username');
      window.sessionStorage.removeItem('email');
      window.sessionStorage.removeItem('password');
      window.sessionStorage.removeItem('visitorID');
      window.sessionStorage.removeItem('employeeID');
      this.$router.push('/');
      window.location.reload();
    }
  }
}
</script>

<style>
a {
    text-decoration: none;
}
a:hover {
  color: white;
  text-decoration: none;
  cursor: pointer;
}


.navigation {
  position: absolute;
  right: 0;
  background-color: black;
  margin-right: 11px;
  border-radius: 10px;
}

img {
  width: 25px;
  border-radius: 50px;
  float: left;
}

.logout {
  font-size: .8em;
  font-family: 'Oswald', sans-serif;
	position: relative;
  right: -18px;
  bottom: -4px;
  overflow: hidden;
  letter-spacing: 3px;
  opacity: 0;
  transition: opacity .45s;
  -webkit-transition: opacity .35s;
  
}

.button {
	text-decoration: none;
	float: right;
  padding: 4px;
  margin: 4px;
  color: white;
  width: 26px;
  background-color: black;
  transition: width .35s;
  -webkit-transition: width .35s;
  overflow: hidden
}

.button:hover {
  width: 120px;
}

a:hover .logout{
  opacity: .9;
}

a {
  text-decoration: none;
}

</style>
