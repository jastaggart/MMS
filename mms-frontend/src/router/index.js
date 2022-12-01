import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import LogIn from '@/components/LogIn'
import Register from '@/components/Register'
import VisitorLoans from '@/components/VisitorLoans'
import VisitorArtworks from '@/components/VisitorArtworks'
import VisitorPasses from '@/components/VisitorPasses'
import Artworks from '@/components/Artworks'
import ManageArtworks from '@/components/ManageArtworks'
import ManageLoans from '@/components/ManageLoans'
import ManageShifts from '@/components/ManageShifts'
import ManageEmployees from '@/components/ManageEmployees'
import ManagePasses from '@/components/ManagePasses'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Home',
      component: Hello
    }, {
      path: '/login',
      name: 'LogIn',
      component: LogIn    
    }, {
      path: '/register',
      name: 'Register',
      component: Register   
    }, {
      path: '/visitorLoans',
      name: 'VisitorLoans',
      component:  VisitorLoans
    }, {
      path: '/visitorArtworks',
      name: 'VisitorArtworks',
      component:  VisitorArtworks
    }, {
      path: '/visitorPasses',
      name: 'VisitorPasses',
      component:  VisitorPasses
    }, {
      path: '/artworks',
      name: 'Artworks',
      component:  Artworks
    }, {
      path: '/manageArtworks',
      name: 'ManageArtworks',
      component:  ManageArtworks
    }, {
      path: '/manageLoans',
      name: 'ManageLoans',
      component:  ManageLoans
    }, {
      path: '/manageShifts',
      name: 'ManageShifts',
      component:  ManageShifts
    }, {
      path: '/manageEmployees',
      name: 'ManageEmployees',
      component:  ManageEmployees
    }, {
      path: '/managePasses',
      name: 'ManagePasses',
      component:  ManagePasses
    }
  ]
})


