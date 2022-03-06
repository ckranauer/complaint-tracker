import { UserDto } from "../user/userDto";


export interface UserResponse {
    timeStamp: Date;
    statusCode: number;
    status: string;
    reason: string;
    message: string;
    developerMessage: string;
    data: { users?: UserDto[], user?: UserDto}
}