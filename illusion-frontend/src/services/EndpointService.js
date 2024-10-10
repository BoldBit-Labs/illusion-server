import axios from "axios";

class EndpointService {
  async fetchEndpoints(projectId) {
    try {
      const response = await axios.get(`${process.env.REACT_APP_BASE_URL}/api/endpoints/${projectId}`);
      if (response.status === 200) {
        return response.data;
      }

      return [];
    } catch (error) {
      console.error("Error in fetching endpoints: ", error);
      return [];
    }
  }

  async createEndpoint(postData) {
    try {
      const response = await axios.post(
        `${process.env.REACT_APP_BASE_URL}/api/endpoints/create-endpoint`, 
        {
          projectId: postData.projectId,
          path: postData.fullPath,
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

  // async getProject(projectId) {
  //   try {
  //     const response = await axios.get(
  //       `${process.env.REACT_APP_BASE_URL}/api/projects/project/${projectId}`
  //     );
  //     if (response.status === 200) {
  //       return response.data;
  //     }

  //     return [];
  //   } catch (error) {
  //     console.error("Error in fetching projects:", error);
  //     return [];
  //   }
  // }

  // async deleteProject(projectId) {
  //   try {
  //     const response = await axios.delete( `${process.env.REACT_APP_BASE_URL}/api/projects/${projectId}` );
  //     if (response.status === 200) {
  //       return response.data;
  //     }

  //     return [];
  //   } catch (error) {
  //     console.error("Error in fetching projects:", error);
  //     return [];
  //   }
  // }
}

const endpointServiceInstance = new EndpointService();

export default endpointServiceInstance;
