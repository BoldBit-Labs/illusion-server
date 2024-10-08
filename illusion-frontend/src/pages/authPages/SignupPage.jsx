import React, { useState } from "react";
import AuthService from "../../services/AuthService";
import { Link, useNavigate } from "react-router-dom";
import Input from "../../components/Input";
import Button from "../../components/Button";
import Label from "../../components/Label";
import Text from "../../components/Text";
import Loader from "../../components/Loader";

function SignupPage() {
  const [fullName, setFullName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [isChecked, setIsChecked] = useState(false);
  const [loading, setLoading] = useState(false);
  const [errors, setErrors] = useState({});
  const navigate = useNavigate();

  const submit = async (e) => {
    e.preventDefault();

    if (validateForm()) {
      setLoading(true);
      const success = await AuthService.signUp(fullName, email, password);
      setLoading(false);

      if (success) {
        navigate("/");
        // todo: change alert to popup or something else
        alert("Sign Up successfull");
      } else {
        alert("Sign Up failed");
      }
    }
  };

  const validateForm = () => {
    const tempErrors = {};
  
    // Validate fullName
    if (!fullName) {
      tempErrors.fullName = "Name is required";
    } else if (fullName.length < 3) {
      tempErrors.fullName = "Name must be at least 3 characters long";
    } else if (fullName.length > 30) {
      tempErrors.fullName = "Name must not exceed 30 characters";
    } else if (/[^a-zA-Z\s]/.test(fullName)) {
      tempErrors.fullName = "Name must contain only letters and spaces";
    }
  
    // Validate email
    if (!email) {
      tempErrors.email = "Email is required";
    } else if (!validateEmail(email)) {
      tempErrors.email = "Email is not valid";
    }
  
    // Validate password
    if (!password) {
      tempErrors.password = "Password is required";
    } else if (password.length < 8) {
      tempErrors.password = "Password must be at least 8 characters long";
    } else if (!/[A-Z]/.test(password)) {
      tempErrors.password = "Password must contain at least one uppercase letter";
    } else if (!/[a-z]/.test(password)) {
      tempErrors.password = "Password must contain at least one lowercase letter";
    } else if (!/[0-9]/.test(password)) {
      tempErrors.password = "Password must contain at least one number";
    } else if (!/[!@#$%^&*(),.?":{}|<>]/.test(password)) {
      tempErrors.password = "Password must contain at least one special character";
    }
  
    // Validate terms acceptance
    if (!isChecked) {
      tempErrors.terms = "Please accept the terms and conditions";
    }
  
    setErrors(tempErrors);
    return Object.keys(tempErrors).length === 0;
  };
  
  const validateEmail = (email) => {
    const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return re.test(email);
  };

  return (
    <div className="flex items-center justify-center h-screen">
      <Loader visibility={loading} />
      
      <div className="w-1/3 p-10 rounded-3xl backdrop-blur-3xl bg-slate-100 text-black">
        <form onSubmit={submit}>
          <Label htmlFor="fullName">Name</Label>
          <Input id="fullName" placeholder="Demo Singh" value={fullName} onChange={(e) => setFullName(e.target.value)} className="mt-2 mb-6" />
          {errors.fullName && <Text tag='p' className="text-red-500">{errors.fullName}</Text>}

          <Label htmlFor="email">Email</Label>
          <Input id="email" type="email" placeholder="example@mail.com" value={email} onChange={(e) => setEmail(e.target.value)} className="mt-2 mb-6" />
          {errors.email && <Text tag='p' className="text-red-500">{errors.email}</Text>}

          <Label htmlFor="password">Password</Label>
          <Input id="password" type="password" placeholder="********" value={password} onChange={(e) => setPassword(e.target.value)} className="mt-2" />
          {errors.password && <Text tag='p' className="text-red-500">{errors.password}</Text>}

          <div className="mt-4 flex items-center">
            <input type="checkbox" id="terms" checked={isChecked} onChange={() => setIsChecked(!isChecked)} className="mx-1" />
            <Link to="/terms" className="text-blue-500 hover:underline ml-1">Terms and Conditions</Link>
          </div>
          {errors.terms && <Text tag='p' className="text-red-500">{errors.terms}</Text>}

          <div className="mt-6 flex justify-between items-center">
            <Button type="submit" text="Sign Up" />
            <Text size="small" className={"flex "} >
              Already have an account?
              <Text tag="p" className={"cursor-pointer text-blue-500 hover:scale-125 duration-200 pl-2"} onClick={() => navigate('/signin')}>Sign In</Text>
            </Text>
          </div>
        </form>
      </div>
    </div>
  );
}

export default SignupPage;
