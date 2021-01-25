import { Profile } from "../../profile/profile";

export interface Message{
    id: string,
    message: string;
    profile: Profile;
    createdOn: string
}