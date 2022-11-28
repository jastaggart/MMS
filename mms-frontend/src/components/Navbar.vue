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
</style>
