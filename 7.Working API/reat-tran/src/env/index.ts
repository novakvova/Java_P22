const API_BASE_URL = import.meta.env.VITE_API_BASE_URL;
const GOOGLE_AUTH_KEY = import.meta.env.VITE_GOOGLE_AUTH;

const IMAGE_BASE_URL = API_BASE_URL + '/' + 'uploads/';

const APP_ENV = {
    API_BASE_URL,
    GOOGLE_AUTH_KEY,
    IMAGE_BASE_URL,
}

export { APP_ENV };