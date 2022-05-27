import { Profile } from "../profile.model";
import { LoginUser } from "../login-user.model";
export interface PostReset {
    success: boolean
    createdObject: LoginUser;
    // gotObject: Profile[]
}
