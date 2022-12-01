<template>
  <div>

    <div class="greeting">
      <h1>Manage Artworks</h1>
    </div>

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

    
    
    <vs-table
      v-model="selected"
      @selected="handleSelected"
      @dblSelection="doubleSelection"
      align=center
      :data="artworks">
      <template slot="thead">
        <vs-th>
          Name
        </vs-th>
        <vs-th>
          Artist
        </vs-th>
        <vs-th>
          Available for Loan
        </vs-th>
        <vs-th>
          Status
        </vs-th>
        <vs-th>
          Current Room
        </vs-th>
      </template>

      <template slot-scope="{data}">
        <vs-tr :data="tr" :key="indextr" v-for="(tr, indextr) in data" >
          <vs-td :data="data[indextr].name">
            {{data[indextr].name}}
          </vs-td>

          <vs-td :data="data[indextr].artist">
            {{data[indextr].artist}}
          </vs-td>

          <vs-td :data="data[indextr].availableForLoan">
            {{getAvailability(data[indextr].availableForLoan)}}
          </vs-td>

          <vs-td :data="data[indextr].status">
            {{data[indextr].status}}
          </vs-td>

          <vs-td :data="data[indextr].roomRoomID">
            {{getRoomName(data[indextr].roomRoomID)}}

            <div class="centerx">
                <button @click="showPopup=true" color="primary" type="border">Change</button>
                <vs-popup class="holamundo"  title="Switch Room" :active.sync="showPopup">
                  <label>Select New Room for {{selected.name}}:</label>
                  
                  <select @change.prevent="moveArtwork(selected.artworkID, rID)" v-model="rID">
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

                  <p v-if="switchErrorMsg" style="color: red">
                    {{ switchErrorMsg }}
                  </p>
                  <p v-if="switchSuccessMsg" style="color: green"> 
                    {{ switchSuccessMsg }}
                  </p>
                
          
                </vs-popup>
            </div>

          </vs-td>

          

        </vs-tr>
      </template>
    </vs-table>
    

    <div v-if="currentUserType=='owner'" class="create">
      <h2> Add New Artwork </h2>
      <p> Enter the name and artist of the artwork to add. </p>
      <table>
        <tr> 
          <td><input type="text" placeholder="Name" v-model="newName"></td>
          <td><input type="text" placeholder="Artist" v-model="newArtist"></td>
          <td><button v-bind:disabled="artworkBtnDisabled" @click="createArtwork(newName, newArtist)">Create</button></td>
        </tr>
      </table>
    </div>
    
  </div>
</template>

<script>

import Vue from 'vue';
import { vsPopup, vsTable } from 'vuesax';
import 'vuesax/dist/vuesax.css';

Vue.use(vsPopup);
Vue.use(vsTable);

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
  name: "ViewArtworks",
  data() {
    return {
      artworks: [],
      successfulMessage: "",
      failiureMessage: "",
      switchErrorMsg: "",
      switchSuccessMsg: "",
      currentUserType: window.sessionStorage.getItem('userType'),
      showPopup:false,
      selected:[]
    };
  },
  created: function () {
    // Initializing artworks from backend
    AXIOS.get("/artworks")
      .then(response => {
        console.log(response)
        this.artworks = response.data
      })
      .catch(e => {
        this.errorArtwork = e
        console.log('Error in GET /artworks:')
        console.log(e)
      });
  },

  methods: {
    getRoomName: function(roomRoomID) {
      if (roomRoomID == 1) {
        return "Storage";
      }
      return "Display Room #" + roomRoomID; 
    },
    getAvailability: function(availableForLoan) {
      if (availableForLoan) {
        return "Yes";
      }
      return "No"; 
    },
    moveArtwork: function(artworkID, roomID) {
      AXIOS.put("/artworks/move/" + artworkID + "/" + roomID)
      .then(response => {
        console.log(artworkID)
        console.log(response)
        this.switchErrorMsg = ''
        this.switchSuccessMsg = "Room was successfully switched."
        window.location.reload;
      })
      .catch(e => {
        if(e.response.status == 409) {
          this.switchErrorMsg = "Room is full.";
          this.switchSuccessMsg = ''
        }
      });
    },
    createArtwork: function(newName, newArtist) {
      AXIOS.post('/artwork', {
        name: newName,
        artist: newArtist
      })
      .then(response => { 
        console.log(response)
        this.artworks.push(response.data)
        this.newName = ''
        this.newArtist = ''
      })
      .catch(error => {
        console.log(error)
      })      
    },
    computed: { // conditions for disabling button
      artworkBtnDisabled() {
        return !this.newName.trim() || !this.newArtist.trim() 
      }
    },
    handleSelected(tr) {
      this.$vs.notify({
        
      })
    },
    filterArtworksByRoomID: function(roomID) {
      AXIOS.get("/artwork/roomID/" + roomID)
      .then(response => {
        this.artworks = response.data;
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
        this.artworks = response.data;
        this.artist = '';
        this.failiureMessage2= '';
      })
      .catch(e => {
        if(e.response.status == 404) {
          this.failiureMessage2= "No artworks by " + artist + " currently in the museum"
        }
      });
    }, getAllArtworks: function() {
      AXIOS.get("/artworks")
      .then(response => {
        this.artworks = response.data;
      })
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

.create {
  margin: 30 px auto;
}

h2, p {
  text-align: center;
}

table, th, td {
  max-width: 800px;
  margin: 30px auto;
  background:  #eaf2f8;
  text-align: left;
  padding: 15px;
  border: 1px solid black;
}

form {
  max-width: 640px;
  margin: 30px auto;
  background: white;
  text-align: left;
  padding: 40px;
  border-radius: 10px;
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
  background: #54B4D3;
  border: 0;
  padding: 10px 20px;
  margin-top: 5px;
  color: white;
  border-radius: 20px;
}

.filters {
  margin-top: 0;
  margin-bottom: 0;
  margin-left: 2rem;
  margin-right: 0;
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
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
  padding-bottom: 2rem;
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