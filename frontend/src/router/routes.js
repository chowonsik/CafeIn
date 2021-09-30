const routes = [
  {
    path: "/",
    component: () => import("layouts/MainLayout.vue"),
    children: [{ path: "", component: () => import("pages/Index.vue") }],
  },
  {
    path: "/users",
    component: () => import("layouts/EmptyLayout.vue"),
    children: [
      { path: "login", component: () => import("pages/user/LoginPage.vue") },
      { path: "signup", component: () => import("pages/user/SignupPage.vue") },
      { path: "delete", component: () => import("pages/user/DeletePage.vue") },
    ],
  },
  {
    path: "/cafes",
    component: () => import("layouts/EmptyLayout.vue"),
    children: [
      // { path: }
    ],
  },
  {
    path: "/service",
    component: () => import("layouts/SubLayout.vue"),
    children: [
      // { path: }
    ],
  },
  {
    path: "/mycafe",
    component: () => import("layouts/MainLayout.vue"),
    children: [{ path: "", component: () => import("pages/MyCafe.vue") }],
  },
  {
    path: "/tag",
    component: () => import("layouts/SubLayout.vue"),
    children: [{ path: "", component: () => import("pages/TagRecommend.vue") }],
  },
  {
    path: "/cafedetail",
    component: () => import("layouts/EmptyLayout.vue"),
    children: [{ path: "", component: () => import("pages/CafeDetail.vue") }],
  },

  // Always leave this as last one,
  // but you can also remove it
  {
    path: "/:catchAll(.*)*",
    component: () => import("pages/Error404.vue"),
  },
];

export default routes;
