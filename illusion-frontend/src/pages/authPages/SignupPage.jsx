import React, { useState } from "react";
import AuthService from "../../services/AuthService";
import { Link, useNavigate } from "react-router-dom";
import Input from "../../components/Input";
import Button from "../../components/Button";
import Label from "../../components/Label";
import Text from "../../components/Text";

function SignupPage() {
  const [fullName, setFullName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [isChecked, setIsChecked] = useState(false);
  const navigate = useNavigate();

  const submit = async (e) => {
    e.preventDefault();
    if (!isChecked) {
      alert("Please accept the terms and conditions to proceed.");
      return;
    }

    const success = await AuthService.signUp(fullName, email, password);
    if (success) {
      navigate("/");
      alert("Sign Up successfull");
    } else {
      alert("Sign Up failed");
    }
  };

  return (
    <div className="flex items-center justify-center h-screen">
      <div className="w-1/3 p-10 rounded-3xl backdrop-blur-3xl bg-slate-100 text-black">
        <form onSubmit={submit}>
          <Label htmlFor="name">Name</Label>
          <Input id="name" placeholder="Demo Singh" value={fullName} onChange={(e) => setFullName(e.target.value)} className="mt-2 mb-6" />

          <Label htmlFor="email">Email</Label>
          <Input id="email" type="email" placeholder="example@mail.com" value={email} onChange={(e) => setEmail(e.target.value)} className="mt-2 mb-6" />

          <Label htmlFor="password">Password</Label>
          <Input id="password" type="password" placeholder="********" value={password} onChange={(e) => setPassword(e.target.value)} className="mt-2" />

          <div className="mt-4 flex items-center">
            <input type="checkbox" id="terms" checked={isChecked} onChange={() => setIsChecked(!isChecked)} className="mx-1" />
            <Link to="/terms" className="text-blue-500 hover:underline ml-1">Terms and Conditions</Link>
          </div>

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
