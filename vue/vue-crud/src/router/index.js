import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/components/Home'
import AddMovie from '@/components/AddMovie'
import EditMovie from '@/components/EditMovie'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Home',
      component: Home
    },
    {
      path: '/new',
      name: 'AddMovie',
      component: AddMovie
    },
    {
      path: '/edit/:id',
      name: 'EditMovie',
      component: EditMovie
    }
  ]
})
