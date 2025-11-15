import axios from 'axios';
import axiosRetry from 'axios-retry';

const api = axios.create({
  baseURL: 'http://localhost:8080/api',
});

axiosRetry(api, { retries: 3 });

export { api };
