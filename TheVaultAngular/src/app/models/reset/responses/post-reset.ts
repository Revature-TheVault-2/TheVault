import { resetPassword } from "../reset-password.model";

export interface PostReset {
    success: boolean;
    createdObject: resetPassword[];
}
