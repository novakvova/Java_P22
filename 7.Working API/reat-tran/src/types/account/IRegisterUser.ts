export interface IRegisterUser {
    name: string;
    lastName: string;
    phone: string;
    email: string;
    password: string;
    imageFile?: File | null;
}
