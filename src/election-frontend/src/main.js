import './assets/main.css';
import './assets/tailwind.css';

import {createApp} from 'vue';
import App from './App.vue';
import router from './router/index.js';

import PrimeVue from 'primevue/config';
import Aura from '@primevue/themes/aura';
import "primeicons/primeicons.css";

const app = createApp(App);

app.use(PrimeVue, {
    theme: {
        preset: Aura
    }
});
app.use(router);

app.mount('#app');
