import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/components/Login'
import Home from '@/components/Home'
import AddMovie from '@/components/AddMovie'
import EditMovie from '@/components/EditMovie'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path:"/",
      name:"Login",
      component:Login
    }
    ,
    {
      path: '/home',
      name: 'Home',
      component: Home,
      meta:{
        reqiredAuth:true
      }
    },
    {
      path: '/new',
      name: 'AddMovie',
      component: AddMovie,
      meta:{
        reqiredAuth:true
      }
    },
    {
      path: '/edit/:id',
      name: 'EditMovie',
      component: EditMovie,
      meta:{
        reqiredAuth:true
      }
    }
  ]
})
