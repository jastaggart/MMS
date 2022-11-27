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
        <router-link to="LogIn">
            <li class="nav-item">
                <a class="nav-link">Log In</a>
            </li>
        </router-link>

        <router-link to="Register">
            <li class="nav-item">
                <a class="nav-link" href="#">Register</a>
            </li>
        </router-link>
        <li class="nav-item">
          <a class="nav-link" href="#">Museum Info</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="#">View Artworks</a>
        </li>          
      </ul>
    </div>
  </nav>
</template>

<script></script>

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
