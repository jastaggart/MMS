<template>

<div class="container">
  
  <div  class="row">
    <div v-for="artwork in Artworks" class="col-sm-4">
      <div class = "image">
        <img class = "image-img" :src="require(`../assets/${artwork.name}.png`)" alt = "artwork"/>
        <div class ="image-overlay">
          <div class = "image-title"> {{ artwork.name }}</div>
          <div class = "artist"><p><b>Artist:</b></p>{{ artwork.artist }}</div>
          <div class = "status"><p><b>Display Status:</b></p>{{ artwork.status }}</div>
          <div class = "loan-availability"><p><b>Available For Loan:</b></p> {{ artwork.availableForLoan }}</div>
      </div>
      </div>
    </div>
  </div>
</div>




  <!-- <div cclass="container text-center" style="display: flex; flex-wrap: wrap;">
    <div v-for="artwork in Artworks" class="col-sm-2 card">
      <img :src="require(`../assets/${artwork.name}.png`)" class="card-img-top"/>
      <div class="card-body">
        <h5 class="card-title">{{ artwork.name }}</h5>

        <b-card-text class = "artist">
          <p><b>Artist:</b></p>{{ artwork.artist }}
        </b-card-text>

        <b-card-text class = "status">
            <p><b>Display Status:</b></p>{{ artwork.status }}
        </b-card-text>

        <b-card-text class = "loan-availability">
            <p><b>Available For Loan:</b></p> {{ artwork.availableForLoan }}
        </b-card-text>
      </div>
    </div>
  </div> -->
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
  name: "artworks",
  data() {
    return {
      Artworks: []
    };
  },
  created: function() {
    // Initializing artworks from backend
    AXIOS.get("/artworks")
      .then(response => {
        this.Artworks = response.data;
      })
      .catch(e => {
        this.errorVisitor = e;
      });
  }
};
</script>

<style>

/* .card {
    margin: 1.5rem;
    padding: 0;
    border-radius: 20px; 
    height: 30rem;
    box-shadow: 8px 8px 8px rgb(174, 181, 182) ;
}

.card-img-top {
    width: 100%;
    height: 60%;
    border-radius: 0;
}

.artist, .status, .loan-availability {
    display: flex;
    justify-content:flex-start;
    margin: 1px;
}

.artist p, .status p, .loan-availability p {
    margin-right: 5px;
} */

.container {
  display: flex;
  margin-top: 20px;

}

.image {
  position: relative;
  width: 350px;
  height: 300px;
  border-radius: 0;
  object-fit: cover;
  vertical-align: bottom;
  float: left;
  object-fit:scale-down;
  margin: 30px;
}

img {
  border-radius: 0;
  width: 100%;
  height: 100%;
}

.image-overlay {
   position: absolute;
   top: 0;
   left: 0;
   width: 100%;
   height:100%;
   background: rgba(0, 0, 0, 0.6);
   color: #ffffff;
   display: flex;
   flex-direction: column;
   align-items: center;
   justify-content: center;
   opacity: 0;
}

.image-overlay:hover {
  opacity: 1;
}

.image-title {
  font-size: 2em;
  font-weight: bold;
  margin-bottom: 0.25em;
  text-align: center;
}

.artist, .status, .loan-availability {
    display: flex;
    justify-content:flex-start;
    margin: 0;
    padding: 0;
}

.artist p, .status p, .loan-availability p {
    margin-right: 5px;
} 


</style>
