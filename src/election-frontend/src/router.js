import { createRouter, createWebHistory } from 'vue-router';
import HomePage from "./views/HomePage.vue";
import LoginPage from "./views/LoginPage.vue";
import AdminPage from "./views/AdminPage.vue"; // Import the AdminPage component

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
        path: '/admin',
        name: 'admin',
        component: AdminPage,
        // meta: { requiresAuth: true }
    }
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;
