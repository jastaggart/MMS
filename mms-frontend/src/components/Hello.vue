<template>
  <div class="background">
    <div class="jumbotron">
      <h1 class="display-4">Welcome to <b class = "museum-name">Marwans' Museum</b></h1>
      <p class="lead">There are plenty of good reasons to visit Marwan's Museum! With so many artworks spanning so many different periods, plan your visit in advance to make the most of your experience. Why not pick one of our visitor trails to follow a theme-based tour? </p>
        <span class = "museum-info"><p> We are open daily from 9:00 AM to 9:00 PM</p>   <p>|</p>      <p>Tickets Price = 25$</p></span>
    </div>
  </div>
</template>

<script></script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style>
.background {
  background-image: url("../assets/m6.png");
  background-size: cover;
  width: 100%;
  height: 100vh;
}

.jumbotron {
    width: 60%;
    height: 38%;
    margin: auto;
    position: relative;
    top: 180px;
}

.museum-name {
    text-decoration: underline;
    font-style: italic;
}

.lead {
    padding-top: 1rem;
}

.museum-info  {
    padding-top: 2rem;
    display: flex;
    flex-direction: row;
    justify-content: flex-start;
}

.museum-info p  {
    margin-right: 10px;
    font-weight: bold;
}




</style>
