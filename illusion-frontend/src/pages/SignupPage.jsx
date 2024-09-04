import React, { useState } from "react";
import AuthService from "../services/AuthService";
import { Link, useNavigate } from "react-router-dom";
import Input from "../components/Input";
import Button from "../components/Button";

function SignupPage() {
  const [fullNameValue, setFullNameValue] = useState("");
  const [emailValue, setEmailValue] = useState("");
  const [passwordValue, setPasswordValue] = useState("");
  const [isChecked, setIsChecked] = useState(false);
  const navigate = useNavigate();

  const submit = async (e) => {
    e.preventDefault();
    if (!isChecked) {
      alert("Please accept the terms and conditions to proceed.");
      return;
    }

    const success = await AuthService.signUp(fullNameValue, emailValue, passwordValue);
    if (success) {
      navigate("/");
      alert("Sign Up successfull");
    } else {
      alert("Sign Up failed");
    }
  };

  return (
    <div className="flex items-center justify-center h-screen">
      <div className="w-96 p-10 rounded-3xl backdrop-blur-3xl bg-slate-100 text-black">
        <form onSubmit={submit}>
          <div>
            <Input
              type="text"
              placeholder="Full name"
              value={fullNameValue}
              setValue={setFullNameValue}
            />
          </div>
          <div className="mt-6">
            <Input
              type="email"
              placeholder="Email"
              value={emailValue}
              setValue={setEmailValue}
            />
          </div>
          <div className="mt-6">
            <Input
              type="password"
              placeholder="********"
              value={passwordValue}
              setValue={setPasswordValue}
            />
          </div>
          <div className="mt-4 flex items-center">
            <input
              type="checkbox"
              id="terms"
              checked={isChecked}
              onChange={() => setIsChecked(!isChecked)}
              className="mx-1"
            />
            <Link to="/terms" className="text-blue-500 hover:underline ml-1">
              Terms and Conditions
            </Link>
          </div>
          <div className="mt-6 flex justify-between items-center">
            <Button text="Sign Up" />
            <div className="text-xs text-gray-500 mb-2">
              <span>Already have an account? </span>
              <Link to="/signin" className="hover:text-blue-500 duration-200">
                Sign In
              </Link>
            </div>
          </div>
        </form>
      </div>
    </div>
  );
}

export default SignupPage;
