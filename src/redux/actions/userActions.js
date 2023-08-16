export const join = (emailid, password) => {
    return {
        type: 'JOIN',
        payload: {
            emailid,
            password,
        }
    };
}
export const sendEmail = () => {
    return {
        type: 'SEND_EMAIL',
    };
}
export const login = (emailid, password) => {
    return {
        type: 'LOGIN',
        payload: {
            emailid,
            password,
        }
    };
};
export const logout = () => {
    // 로그아웃 액션 객체를 반환
    return {
        type: 'LOGOUT'
    };
};
