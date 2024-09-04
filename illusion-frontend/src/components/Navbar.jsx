import React from "react";
import { useNavigate } from "react-router-dom";
import logoUrl from "./../assets/images/logo.png";
import { FaUserLarge } from "react-icons/fa6";
import authServiceInstance from "../services/AuthService";

function Navbar() {
  const navigate = useNavigate();

  const handleUserIconClick = () => {
    navigate("/signin");
  };

  const handleLogoClick = () => {
    navigate("/");
  };

  const handleProjectsClick = () => {
    navigate("/projects");
  };
  return (
    <header className="fixed w-[calc(100%-4rem)] h-20 p-8 px-12 mx-8 mt-8 flex items-center justify-between backdrop-blur-3xl bg-slate-100 rounded-2xl">
      <img src={logoUrl} alt="Logo" className="w-52 h-auto" onClick={handleLogoClick} />
      <div className="flex">
        {authServiceInstance.isAuthenticated() && (
          <div className="pr-12">
            <div className="hover:scale-125 duration-200 font-semibold cursor-pointer" onClick={handleProjectsClick}>
              Projects
            </div>
          </div>
        )}
        <FaUserLarge className="w-7 h-7 hover:scale-125 duration-200" onClick={handleUserIconClick} />
      </div>
    </header>
  );
}

export default Navbar;
