import React, { useState } from "react";
import AuthService from "../../services/AuthService";
import { useNavigate } from "react-router-dom";
import Input from "../../components/Input";
import Button from "../../components/Button";
import Label from "../../components/Label";
import Text from "../../components/Text";
import Loader from "../../components/Loader";

function SigninPage() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();

    setLoading(true);
    const success = await AuthService.signIn(email, password);
    setLoading(false);

    if (success) {
      navigate("/");
    } else {
      alert("Invalid email or password. Please try again.");
    }
  };

  return (
    <div className="flex items-center justify-center h-screen">
      {loading && <Loader />}
      <div className="w-1/3 p-10 rounded-3xl backdrop-blur-3xl bg-slate-100 text-black">
        <form onSubmit={handleSubmit}>
          <Label htmlFor="email">Email</Label>
          <Input id="email" type="email" placeholder="example@mail.com" value={email} onChange={(e) => setEmail(e.target.value)} className="mt-2 mb-6" />

          <Label htmlFor="password">Password</Label>
          <Input id="password" type="password" placeholder="********" value={password} onChange={(e) => setPassword(e.target.value)} className="mt-2" />

          <div className="mt-6 flex justify-between items-center">
            <Button type="submit" text="Sign In" />
            <div>
              <Text size="small" className={"flex "} >
                Create account?
                <Text tag="p" className={"cursor-pointer text-blue-500 hover:scale-125 duration-200 pl-2"} onClick={() => navigate('/signup')}>
                  Sign Up
                </Text>
              </Text>
              <Text tag="p" size="small" className={"cursor-pointer text-blue-500 hover:scale-125 duration-200"} onClick={() => navigate('/forgotpassword')}>
                Forgot password?
              </Text>
            </div>
          </div>
        </form>
      </div>
    </div>
  );
}

export default SigninPage;
