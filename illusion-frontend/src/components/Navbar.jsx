import React, { useState, useRef, useEffect } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import logoUrl from "./../assets/images/logo.png";
import { FaUserLarge, FaCircleUser, FaGear, FaArrowRightToBracket } from "react-icons/fa6";
import Text from "./Text";
import authServiceInstance from "../services/AuthService";
import Loader from "./Loader";

const Navbar = () => {
  const navigate = useNavigate();
  const [isDropdownOpen, setIsDropdownOpen] = useState(false);
  const [loading, setLoading] = useState(false);
  const location = useLocation();
  const dropdownRef = useRef(null);

  const toggleDropdown = () => {
    authServiceInstance.isAuthenticated() ? setIsDropdownOpen(prev => !prev) : navigate("/signin");
  };

  const handleLogout = () => {
    setLoading(true);
    authServiceInstance.signOut();
    location.pathname === "/" ? window.location.reload() : navigate("/");
    setLoading(false);
    setIsDropdownOpen(false);
  };

  const handleProfileSettings = () => {
    navigate("/profile");
    setIsDropdownOpen(false);
  };

  const handleClickOutside = (event) => {
    if (dropdownRef.current && !dropdownRef.current.contains(event.target)) {
      setIsDropdownOpen(false);
    }
  };

  useEffect(() => {
    document.addEventListener("mousedown", handleClickOutside);
    return () => {
      document.removeEventListener("mousedown", handleClickOutside);
    };
  }, []);

  return (
    <header className="fixed w-[calc(100%-4rem)] h-20 p-8 px-12 mx-8 mt-8 flex items-center justify-between backdrop-blur-3xl bg-slate-100 rounded-2xl">
      <Loader visibility={loading} />
      <img src={logoUrl} alt="Logo" className="w-52 h-auto cursor-pointer" onClick={() => navigate("/")} />
      <div className="relative" ref={dropdownRef}>
        <FaUserLarge className="w-7 h-7 hover:scale-125 duration-200 cursor-pointer" onClick={toggleDropdown} />

        {isDropdownOpen && (
          <div className="absolute right-0 z-10 w-fit mt-2 p-4 bg-white border border-gray-200 rounded-md shadow-lg">
            {/* todo: show user name */}
            {/* <Text>{}</Text> */}
            {[
              { icon: <FaCircleUser />, label: "Profile", onClick: handleProfileSettings },
              { icon: <FaGear />, label: "Settings", onClick: handleProfileSettings },
              { icon: <FaArrowRightToBracket />, label: "Logout", onClick: handleLogout }
            ].map(({ icon, label, onClick }, index) => (
              <div key={index} className="flex items-center mb-2 cursor-pointer hover:scale-110 duration-200 hover:text-gray-500" onClick={onClick}>
                {icon}
                <Text className="ml-2">{label}</Text>
              </div>
            ))}
          </div>
        )}
      </div>
    </header>
  );
};

export default Navbar;
