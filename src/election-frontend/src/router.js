import { createRouter, createWebHistory } from 'vue-router';
import HomePage from './views/HomePage.vue';
import LoginPage from './views/LoginPage.vue';
import RegisterPage from './views/RegisterPage.vue';
import AdminPage from './views/Adminpage.vue';
import ThreadsPage from "./views/ThreadsPage.vue";
import SingleThreadPage from "./views/SingleThreadPage.vue";

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
        path: '/register',
        name: 'register',
        component: RegisterPage,
    },
    {
        path: '/admin',
        name: 'admin',
        component: AdminPage
    },
    {   path: '/threads',
        name: 'threads',
        component: ThreadsPage
    },
    {   path: '/threads/:id',
        name: 'single-thread',
        component: SingleThreadPage
    }
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;
