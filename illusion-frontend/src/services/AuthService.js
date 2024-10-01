import axios from "axios";

class AuthService {

  static REACT_APP_BASE_URL = "https://illusion-server.buzz";

  async signIn(email, password) {
    try {
      const response = await axios.post(
        `${AuthService.REACT_APP_BASE_URL}/api/user/signin`,
        { email, password }
      );
      if (response.status === 200) {
        localStorage.setItem("user", JSON.stringify(response.data));
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
      const response = await axios.post(`${AuthService.REACT_APP_BASE_URL}/api/user/signup`,
        { fullName, email, password }
      );
      if (response.status === 200) {
        localStorage.setItem("user", JSON.stringify(response.data));
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
  
  async fetchProjects() {
    try {
      const response = await axios.get(`${AuthService.REACT_APP_BASE_URL}/api/projects/${authServiceInstance.getCurrentUser()}`);
      if (response.status === 200) {
        return response.data;
      }

      return [];
    } catch (error) {
      console.error("Error in fetching projects:", error);
      return [];
    }
  }

  async createProject(name, description, apiPrefix) {
    try {
      const ownerId = this.getCurrentUser()
      const response = await axios.post(`${AuthService.REACT_APP_BASE_URL}/api/projects/create-project`, {name, description, apiPrefix, ownerId});
      if (response.status === 200) {
        return response.data;
      }

      return [];
    } catch (error) {
      console.error("Error in creating project:", error);
      return [];
    }
  }

  async getProject(projectId) {
    try {
      const response = await axios.get(`${AuthService.REACT_APP_BASE_URL}/api/projects/project/${projectId}`);
      if (response.status === 200) {
        return response.data;
      }

      return [];
    } catch (error) {
      console.error("Error in fetching projects:", error);
      return [];
    }
  }

  async createEndpoint(postData) {
    console.log("api data: ", postData);
    
    try {
      const response = await axios.post(
        `${AuthService.REACT_APP_BASE_URL}/api/endpoints/create-endpoint`, 
        {
          projectId: postData.projectId,
          name: postData.name,
          description: postData.description,
          schema: postData.schema,
          allowedMethods: postData.allowedMethods
        }
      );
  
      if (response.status === 200) {
        return response.data;
      }
  
      return null;
    } catch (error) {
      console.error("Error in creating Endpoint:", error);
      return null;
    }
  }
  
}

const authServiceInstance = new AuthService();

export default authServiceInstance;
