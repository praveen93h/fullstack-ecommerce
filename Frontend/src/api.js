import axios from 'axios';
const API_URL = process.env.REACT_APP_API_URL || 'http://localhost:8080/api';
const api = axios.create({ baseURL: API_URL });
api.interceptors.request.use(cfg => {
  const token = localStorage.getItem('token');
  if (token) cfg.headers.Authorization = `Bearer ${token}`;
  return cfg;
});
export default api;
