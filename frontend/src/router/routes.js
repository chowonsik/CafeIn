const routes = [
  {
    path: "/",
    component: () => import("layouts/MainLayout.vue"),
    children: [
      { path: "", component: () => import("pages/Index.vue") },
      { path: "mycafe", component: () => import("pages/MyCafe.vue") },
    ],
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
    path: "/",
    component: () => import("layouts/EmptyLayout.vue"),
    children: [
      { path: "myreview", component: () => import("pages/MyReviewPage") },
      { path: "cafe/detail", component: () => import("pages/CafeDetail.vue") },
    ],
  },
  {
    path: "/",
    component: () => import("layouts/SubLayout.vue"),
    children: [
      { path: "profile", component: () => import("pages/user/ProfilePage.vue") },
      { path: "profile/edit", component: () => import("pages/user/EditUserPage.vue") },
      { path: "tag", component: () => import("pages/TagRecommend.vue") },
      { path: "nearcafe", component: () => import("pages/NearCafe.vue") },
      { path: "curation", component: () => import("pages/CafeCuration.vue") }
    ],
  },
  // Always leave this as last one,
  // but you can also remove it
  {
    path: "/:catchAll(.*)*",
    component: () => import("pages/Error404.vue"),
  },
];

export default routes;
