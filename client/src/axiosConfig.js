import axios from "axios";

axios.defaults.withCredentials = true;
axios.interceptors.request.use(cfg => {
    const token = localStorage.getItem("token");
    if (token) cfg.headers.Authorization = `Bearer ${token}`;
    return cfg;
});

export default axios;