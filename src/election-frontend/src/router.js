import { createRouter, createWebHistory } from 'vue-router';
import HomePage from './views/HomePage.vue';
import LoginPage from './views/LoginPage.vue';
import RegisterPage from './views/RegisterPage.vue'; // Import the RegisterPage component

const routes = [
    {
        path: '/',
        name: 'home',
        component: HomePage,
    },
    {
        path: '/login',
        name: 'login',
        component: LoginPage,
    },
    {
        path: '/register', // Add this new route for registration
        name: 'register',
        component: RegisterPage, // Reference to the RegisterPage component
    }
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;
