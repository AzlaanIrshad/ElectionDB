import { createRouter, createWebHistory } from 'vue-router';
import HomePage from '@/pages/HomePage.vue';
import LoginPage from '@/pages/LoginPage.vue';
import RegisterPage from '@/pages/RegisterPage.vue';
import AdminPage from '@/pages/AdminPage.vue';
import ThreadsPage from "@/pages/ThreadsPage.vue";
import SingleThreadPage from "@/pages/SingleThreadPage.vue";
import CreateThread from "@/pages/CreateThread.vue";
import FaqPage from "@/pages/FaqPage.vue";

// General authentication guard
function authGuard(to, from, next) {
    const token = localStorage.getItem('token');
    if (token) {
        next();
    } else {
        next({ name: 'login' });
    }
}

// Admin-specific guard
function adminGuard(to, from, next) {
    const token = localStorage.getItem('token');
    if (token) {
        const payload = JSON.parse(atob(token.split('.')[1]));
        if (payload.role === 'ADMIN') {
            next();
        } else {
            next({ name: 'home' });
        }
    } else {
        next({ name: 'login' });
    }
}

const routes = [
    { path: '/', name: 'home', component: HomePage },
    { path: '/login', name: 'login', component: LoginPage },
    { path: '/register', name: 'register', component: RegisterPage },
    { path: '/admin', name: 'admin', component: AdminPage, beforeEnter: adminGuard },
    { path: '/threads', name: 'threads', component: ThreadsPage },
    { path: '/threads/:id', name: 'single-thread', component: SingleThreadPage },
    { path: '/create-thread', name: 'create-thread', component: CreateThread, beforeEnter: authGuard },
    { path: '/faq', name: 'faq', component: FaqPage },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;
