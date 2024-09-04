import axios from "axios";

class AuthService {
  async signIn(email, password) {
    try {
      const response = await axios.post(
        `${process.env.REACT_APP_BASE_URL}/api/user/signin`,
        { email, password }
      );
      if (response.status === 200) {
        localStorage.setItem("user", JSON.stringify(email));
        return true;
      }

      return false;
    } catch (error) {
      console.error("Error signing in:", error);
      return false;
    }
  }

  async signUp(fullName, email, password) {
    try {
      const response = await axios.post(
        `${process.env.REACT_APP_BASE_URL}/api/user/signup`,
        { fullName, email, password }
      );
      if (response.status === 200) {
        localStorage.setItem("user", JSON.stringify(email));
        return true;
      }

      return false;
    } catch (error) {
      console.error("Error in signup:", error);
      return false;
    }
  }

  signOut() {
    localStorage.removeItem("user");
  }

  getCurrentUser() {
    return JSON.parse(localStorage.getItem("user"));
  }

  isAuthenticated() {
    return !!this.getCurrentUser();
  }
  
  async fetchProjects(email) {
    try {
      const response = await axios.get(`${process.env.REACT_APP_BASE_URL}/api/projects`, {
        params: { email }
      });
      if (response.status === 200) {
        console.log(response.data);
        return response.data;
      }

      return [];
    } catch (error) {
      console.error("Error in fetching projects:", error);
      return [];
    }
  }
}

const authServiceInstance = new AuthService();

export default authServiceInstance;
