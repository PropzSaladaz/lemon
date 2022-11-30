import { createRouter, createWebHistory } from 'vue-router'
import HomeLoggedIn from '../views/HomeLoggedIn.vue'
import Vehicles from '../views/Vehicles.vue'

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeLoggedIn,
    props: true,
  },
  {
    path: '/about',
    name: 'about',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
  },
  {
    path: '/vehicles',
    name: 'vehicles',
    component: Vehicles,
    props: true,
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
