const routes = [
  {
    path: "/",
    component: () => import("layouts/MainLayout.vue"),
    children: [
      { path: "", component: () => import("pages/Index.vue") },
    ],
  },
  {
    path: "/users",
    component: () => import("layouts/EmptyLayout.vue"),
    children: [
      { path: "login", component: () => import("pages/user/LoginPage.vue") },
      { path: "signup", component: () => import("pages/user/SignupPage.vue") },
      { 
        path: "delete",
        component: () => import("pages/user/DeletePage.vue"),
        meta: { auth: true },
      },
    ],
  },
  {
    path: "/",
    component: () => import("layouts/EmptyLayout.vue"),
    children: [
      { 
        path: "myreview",
        component: () => import("pages/MyReviewPage"),
        meta: { auth: true }
      },
      { path: "cafes/:id", component: () => import("pages/CafeDetail.vue") },
    ],
  },
  {
    path: "/",
    component: () => import("layouts/MyLayout.vue"),
    children: [
      {
        path: "profile",
        component: () => import("pages/user/ProfilePage.vue"),
        meta: { auth: true },
      },
      {
        path: "profile/edit",
        component: () => import("pages/user/EditUserPage.vue"),
        meta: { auth: true },
      },
    ]
  },
  {
    path: "/",
    component: () => import("layouts/NearLayout.vue"),
    children: [
      { path: "nearcafe", component: () => import("pages/NearCafe.vue") },
    ]
  },
  {
    path: "/",
    component: () => import("layouts/TagLayout.vue"),
    children: [
      { 
        path: "mycafe",
        component: () => import("pages/MyCafe.vue"),
        meta: { auth: true },
      },
    ]
  },
  {
    path: "/",
    component: () => import("layouts/SubLayout.vue"),
    children: [
      { path: "tag", component: () => import("pages/TagRecommend.vue") },
      { 
        path: "curation",
        component: () => import("pages/CafeCuration.vue"),
        meta: { auth: true },
      },
      {
        path: "search/:cafeName",
        component: () => import("pages/CafeSearch.vue")
      },
      {
        path: "tag/:tagname",
        component: () => import("pages/TagListPage.vue"),
      },
    ],
  },

  {
    path: "/:catchAll(.*)*",
    component: () => import("pages/Error404.vue"),
  },
];

export default routes;
