<template>
<div>
  <h4 class = "page-description">Marwan's Museum boasts a wonderful collection of artworks spanning some of the most beautiful periods in art history. 
    Please enjoy browsing our virutal collection of artworks by some of the great artists of our time such as Picasso, Monet, 
    Van Gogh, Rembrandt, Cézanne and many more! To request to take out an item on loan, please sign in.
  </h4>

  <div class = "filters">
    <div class = "filter-by-roomID">
      <label>Filter by Room:</label>
      <select @change.prevent="filterArtworksByRoomID(roomID)" v-model="roomID">
        <option value=1>1 (Storage)</option>
        <option value=2>2</option>
        <option value=3>3</option>
        <option value=4>4</option>
        <option value=5>5</option>
        <option value=6>6</option>
        <option value=7>7</option>
        <option value=8>8</option>
        <option value=9>9</option>
        <option value=10>10</option>
        <option value=11>11</option>
      </select>

      <p v-if="this.failiureMessage1" style="color: red">
        {{ this.failiureMessage1 }}
      </p>
    </div>

    <div class ="filter-by-artist">
      <label>Artist:</label>
      <input @change.prevent="filterArtworksByArtist(artist)" type="artist" required v-model="artist" />
      <p v-if="this.failiureMessage2" style="color: red">
        {{ this.failiureMessage2 }}
      </p>
    </div>
    
    <button @click.preventDefault="getAllArtworks" class = "all-artworks">All artworks</button>
    
  </div>


  
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
          <div class = "room-id"><p><b>Room Id:</b></p> {{ artwork.roomRoomID }}</div>
      </div>
      </div>
    </div>
  </div>
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
  
  name: "artworks",
  data() {
    return {
      Artworks: [],
      roomID: '',
      artist: '',
      failiureMessage1: '',
      failiureMessage2: ''
    };
  },
  created: function() {
    // Initializing artworks from backend
    AXIOS.get("/artworks")
      .then(response => {
        this.Artworks = response.data;
      });
  }, 
  methods: {
    filterArtworksByRoomID: function(roomID) {
      AXIOS.get("/artwork/roomID/" + roomID)
      .then(response => {
        this.Artworks = response.data;
        this.failiureMessage1 = '';
      })
      .catch(e => {
        if(e.response.status == 404) {
          this.failiureMessage1 = "No artworks in room with roomID " + roomID;
        }
      });
    }, filterArtworksByArtist: function(artist) {
      AXIOS.get("/artwork/artist/" + artist)
      .then(response => {
        this.Artworks = response.data;
        this.artist = '';
        this.failiureMessage2= '';
      })
      .catch(e => {
        if(e.response.status == 404) {
          this.failiureMessage2= "No artworks by " + artsit + " currently in the museum"
        }
      });
    }, getAllArtworks: function() {
      AXIOS.get("/artworks")
      .then(response => {
        this.Artworks = response.data;
      })
    }
  }
};
</script>

<style scoped>

.filters {
  margin-top: 0;
  margin-bottom: 0;
  margin-left: 2rem;
  margin-right: 0;
  display: flex;
  flex-direction: row;
  justify-content: center;
}

.filters select{
  width: 36%;
  border: none;
  box-sizing: border-box;
  border-bottom: 1px solid #ddd;
  color: #555;
}

.filters input {
  width: 75%;
  border: none;
  box-sizing: border-box;
  border-bottom: 1px solid #ddd;
  color: #555;
  border: 1px solid black;
}

.filter-by-roomID {
  margin-right: 3rem;
  font-size: 20px;
  font-family: Baskerville;

}

.filter-by-artist {
  margin-left: 3rem;
  font-family: Baskerville;
  font-size: 20px;

}

.page-description {
  margin-top: 2rem;
  margin-bottom: 2rem;
  font-family: Baskerville;
  max-width: 75%;
  text-align: center;
  margin-left: auto;
  margin-right: auto;
}

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
  box-shadow: 6px 8px 4px rgb(26, 26, 26);

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

.artist, .status, .loan-availability, .room-id {
    display: flex;
    justify-content:flex-start;
    margin: 0;
    padding: 0;
}

.artist p, .status p, .loan-availability p, .room-id  p {
    margin-right: 5px;
} 

/* CSS */
.all-artworks {
  appearance: button;
  background-color: #54B4D3;
  border: solid transparent;
  border-radius: 16px;
  border-width: 0 0 4px;
  box-sizing: border-box;
  color: #FFFFFF;
  cursor: pointer;
  display: inline-block;
  font-family: Baskerville;
  font-size: 15px;
  font-weight: 700;
  letter-spacing: .8px;
  margin: 0;
  overflow: visible;
  text-align: center;
  text-transform: uppercase;
  touch-action: manipulation;
  transform: translateZ(0);
  transition: filter .2s;
  user-select: none;
  -webkit-user-select: none;
  vertical-align: middle;
  white-space: nowrap;
  width: 170px;
  height: 36px;
  margin-left: 6rem;
}

.all-artworks:after {
  background-clip: padding-box;
  background-color: #54B4D3;
  border: solid transparent;
  border-radius: 16px;
  border-width: 0 0 4px;
  bottom: -4px;
  content: "";
  left: 0;
  position: absolute;
  right: 0;
  top: 0;
  z-index: -1;
}

.all-artworks:main, .all-artworks:focus {
  user-select: auto;
}

.all-artworks:hover:not(:disabled) {
  filter: brightness(1.1);
  -webkit-filter: brightness(1.1);
}

.all-artworks:disabled {
  cursor: auto;
}

</style>
