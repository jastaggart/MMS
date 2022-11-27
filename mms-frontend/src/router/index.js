import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import LogIn from '@/components/LogIn'
import Register from '@/components/Register'

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
    }
  ]
})


