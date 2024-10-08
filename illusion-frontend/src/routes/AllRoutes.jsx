import React from "react";
import {
  Route,
  createBrowserRouter,
  createRoutesFromElements,
  RouterProvider,
} from "react-router-dom";
import MainLayout from "../layouts/MainLayout";
import HomePage from "../pages/HomePage/HomePage";
import NotFoundPage from "../pages/NotFoundPage";
import SigninPage from "../pages/authPages/SigninPage";
import SignupPage from "../pages/authPages/SignupPage";
import Dashboard from "../pages/HomePage/dashboard/Dashboard";
import authServiceInstance from "../services/AuthService";
import NewProject from "../pages/projectsPage/NewProject";
import Project from "../pages/projectsPage/Project";

const router = createBrowserRouter(
  createRoutesFromElements(
    <Route path="/" element={<MainLayout />}>
      <Route index element={<ProtectedRoute Component1={Dashboard} Component2={HomePage} />} />
      <Route path="/signin" element={<ProtectedRoute Component1={Dashboard} Component2={SigninPage} />} />
      <Route path="/signup" element={<ProtectedRoute Component1={Dashboard} Component2={SignupPage} />} />
      <Route path="/create-project" element={<ProtectedRoute Component1={NewProject} Component2={NotFoundPage} />} />
      <Route path="/projects/:projectId" element={<ProtectedRoute Component1={Project} Component2={NotFoundPage} />} />

      <Route path="*" element={<NotFoundPage />} />
    </Route>
  )
);

function AllRoutes() {
  return <RouterProvider router={router} />;
}

export default AllRoutes;

function ProtectedRoute({ Component1, Component2 }) {
  return authServiceInstance.isAuthenticated() ? <Component1 /> : <Component2 />;
}