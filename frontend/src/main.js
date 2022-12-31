import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'

require('@/assets/main.scss');

createApp(App)
    .use(store)
    .use(router)
    .use(function(req, res) {
        res.setHeader('Strict-Transport-Security', 'max-age=31536000');
    })
    .mount('#app')
