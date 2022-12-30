import { createRouter, createWebHistory } from 'vue-router'
import WelcomePage from '../views/WelcomePage.vue'
import LoginForm from '../components/LoginForm.vue'
import Vehicles from '../views/Vehicles.vue'
import Localization from '../views/Localization.vue'
import Reservation from '../views/Reservation.vue'

const routes = [
  {
    path: '/',
    name: 'home',
    component: WelcomePage,
    props: true,
  },
  {
    path: '/user/login',
    name: 'login',
    component: LoginForm,
    props: true,  
  },
  {
    path: '/user/vehicles',
    name: 'vehicles',
    component: Vehicles,
    props: true,
  },
  {
    path: '/user/localization',
    name: 'localization',
    component: Localization,
    props: true,
  },
  {
    path: '/user/reservation',
    name: 'reservation',
    component: Reservation,
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
  
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
