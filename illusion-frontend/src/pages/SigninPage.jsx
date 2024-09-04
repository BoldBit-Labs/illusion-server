import React, { useState } from "react";
import AuthService from "../services/AuthService";
import { Link, useNavigate } from "react-router-dom";
import Input from "../components/Input";
import Button from "../components/Button";

function SigninPage() {
  const [emailValue, setEmailValue] = useState("");
  const [passwordValue, setPasswordValue] = useState("");
  const navigate = useNavigate();

  const submit = async (e) => {
    e.preventDefault();

    const success = await AuthService.signIn(emailValue, passwordValue);
    if (success) {
      navigate("/");
    } else {
      alert("Invalid email or password. Please try again.");
    }
  };

  return (
    <div className="flex items-center justify-center h-screen">
      <div className="w-96 p-10 rounded-3xl backdrop-blur-3xl bg-slate-100 text-black">
        <form onSubmit={submit}>
          <Input
            type="email"
            placeholder="Email"
            value={emailValue}
            setValue={setEmailValue}
          />
          <div className="mt-6">
            <Input
              type="password"
              placeholder="********"
              value={passwordValue}
              setValue={setPasswordValue}
            />
          </div>
          <div className="mt-6 flex justify-between items-center">
            <Button text="Sign In" />
            <div className="text-xs text-gray-500">
              <div className="mb-2">
                <span>Create account? </span>
                <Link to="/signup" className="hover:text-blue-500 duration-200">
                  Sign Up
                </Link>
              </div>
              <Link to="/forgotpassword" className="hover:text-blue-500 duration-200">
                Forgot password?
              </Link>
            </div>
          </div>
        </form>
      </div>
    </div>
  );
}

export default SigninPage;
