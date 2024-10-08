import axios from "axios";
import authServiceInstance from "./AuthService";

class ProjectService {
  async fetchProjects() {
    try {
      const response = await axios.get(
        `${
          process.env.REACT_APP_BASE_URL
        }/api/projects/${authServiceInstance.getCurrentUser()}`
      );
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
      const ownerId = authServiceInstance.getCurrentUser();
      const response = await axios.post(
        `${process.env.REACT_APP_BASE_URL}/api/projects/create-project`,
        { name, description, apiPrefix, ownerId }
      );
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
      const response = await axios.get(
        `${process.env.REACT_APP_BASE_URL}/api/projects/project/${projectId}`
      );
      if (response.status === 200) {
        return response.data;
      }

      return [];
    } catch (error) {
      console.error("Error in fetching projects:", error);
      return [];
    }
  }

  async deleteProject(projectId) {
    try {
      const response = await axios.delete( `${process.env.REACT_APP_BASE_URL}/api/projects/${projectId}` );
      if (response.status === 200) {
        return response.data;
      }

      return [];
    } catch (error) {
      console.error("Error in fetching projects:", error);
      return [];
    }
  }
}

const projectServiceInstance = new ProjectService();

export default projectServiceInstance;
