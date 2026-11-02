'use client'

import axios from "axios";
import Cookies from "js-cookie";
import { store } from "../redux/store";
import { logout } from "../redux/slices/authSlice";

const api = axios.create({
    baseURL: "http://localhost:8080"
});

//Envia automaticamente o token nas requisições.
api.interceptors.request.use(
    (config) => {
        const token = Cookies.get("token");

        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }

        return config;
    }
);

//Desconecta quando a API informa que a autenticação é inválida.
api.interceptors.response.use(
    (response) => {
        return response;
    },
    (error) => {
        if (error.response?.status === 401) {
            store.dispatch(logout());
        }

        return Promise.reject(error);
    }
);

export default api;