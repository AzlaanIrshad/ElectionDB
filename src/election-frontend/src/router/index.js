import {createRouter, createWebHistory} from 'vue-router';
import HomePage from '@/pages/HomePage.vue';
import LoginPage from '@/pages/LoginPage.vue';
import RegisterPage from '@/pages/RegisterPage.vue';
import AdminPage from '@/pages/AdminPage.vue';
import ThreadsPage from "@/pages/ThreadsPage.vue";
import SingleThreadPage from "@/pages/SingleThreadPage.vue";
import CreateThread from "@/pages/CreateThread.vue";
import FaqPage from "@/pages/FaqPage.vue";
import AboutPage from '@/pages/AboutPage.vue';
import DashboardPage from '@/pages/DashboardPage.vue';
import SinglePartyPage from "@/pages/SinglePartyPage.vue";
import ElectionStatistiekPage from "@/pages/ElectionStatistiekPage.vue";
import IntroductionPage from "@/components/IntroductionPage.vue";
import ElectionMap from "@/components/ElectionMap.vue";
import ElectionDonutChart from "@/components/ElectionDonutChart.vue";
import PartyBar from "@/components/PartyBar.vue";
import PartyLine from "@/components/PartyLine.vue";
import NotFoundPage from "@/pages/NotFoundPage.vue";
import CityStatistiekPage from "@/components/CityStatistiekPage.vue";
import ManhattanComparison from "@/components/ManhattanComparison.vue";

// General authentication guard
function authGuard(to, from, next) {
    const token = localStorage.getItem('token');
    console.log(`[AuthGuard] Navigating to ${to.name}`);
    if (token) {
        next();
    } else {
        console.log("[AuthGuard] No token found. Redirecting to login.");
        next({ name: 'login' });
    }
}

function adminGuard(to, from, next) {
    const token = localStorage.getItem('token');
    if (token) {
        const payload = JSON.parse(atob(token.split('.')[1]));
        if (payload.role === 'ADMIN') {
            next();
        } else {
            console.log("[AdminGuard] Unauthorized. Redirecting to home.");
            next({ name: 'home' });
        }
    } else {
        console.log("[AdminGuard] No token found. Redirecting to login.");
        next({ name: 'login' });
    }
}

const routes = [
    { path: '/', name: 'home', component: HomePage, meta: { breadcrumb: "Home" } },
    { path: '/login', name: 'login', component: LoginPage, meta: { breadcrumb: "Login" } },
    { path: '/register', name: 'register', component: RegisterPage, meta: { breadcrumb: "Registreren" } },
    {
        path: '/admin',
        name: 'admin',
        component: AdminPage,
        beforeEnter: adminGuard,
        meta: { breadcrumb: "Admin" }
    },
    { path: '/threads', name: 'threads', component: ThreadsPage, meta: { breadcrumb: "Discussies" } },
    {
        path: '/threads/:id',
        name: 'single-thread',
        component: SingleThreadPage,
        meta: { breadcrumb: "Thread Details" }
    },
    {
        path: '/create-thread',
        name: 'create-thread',
        component: CreateThread,
        beforeEnter: authGuard,
        meta: { breadcrumb: "Thread Aanmaken" }
    },
    { path: '/faq', name: 'faq', component: FaqPage, meta: { breadcrumb: "FAQ" } },
    { path: '/about', name: 'about', component: AboutPage, meta: { breadcrumb: "Over Ons" } },
    { path: '/dashboard', name: 'dashboard', component: DashboardPage, meta: { breadcrumb: "Dashboard" } },
    {
        path: '/parties/:id',
        name: 'single-party',
        component: SinglePartyPage,
        meta: { breadcrumb: "Partij Details" }
    },
    {
        path: '/election-statistiek',
        name: 'election-statistiek',
        component: ElectionStatistiekPage,
        meta: { breadcrumb: "Statistieken" },
        redirect: { name: 'introductie' },
        children: [
            {
                path: 'introductie',
                name: 'introductie',
                component: IntroductionPage,
                meta: { breadcrumb: "Introductie" }
            },
            {
                path: 'per-stemlocatie',
                name: 'per-stemlocatie',
                component: ElectionMap,
                meta: { breadcrumb: "Per Stemlocatie" },
                children: [
                    {
                        path: ':cityName',
                        name: 'city-statistieken-per-stemlocatie',
                        component: CityStatistiekPage,
                        meta: {
                            breadcrumb: (route) => route.params.cityName || "Stad Statistieken",
                        },
                    },
                ],
            },
            {
                path: 'per-verkiezing',
                name: 'per-verkiezing',
                component: ElectionDonutChart,
                meta: { breadcrumb: "Per Verkiezing" }
            },
            {
                path: 'zetels-per-jaar',
                name: 'zetels-per-jaar',
                component: PartyBar,
                meta: { breadcrumb: "Zetels Per Jaar" }
            },
            {
                path: 'zetels-per-partij',
                name: 'zetels-per-partij',
                component: PartyLine,
                meta: { breadcrumb: "Zetels Per Partij" }
            },
            {
                path: 'manhattan-vergelijking',
                name: 'manhattan-vergelijking',
                component: ManhattanComparison,
                meta: { breadcrumb: "Manhattan Vergelijking" },
            },
        ],
    },
    {
        path: '/:catchAll(.*)',
        name: 'not-found',
        component: NotFoundPage,
        meta: { breadcrumb: "Pagina Niet Gevonden" }
    },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;
