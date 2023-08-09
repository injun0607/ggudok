export const join = (emailid, password) => {
    return {
        type: 'JOIN',
        payload: {
            emailid,
            password,
        // 기타 로그인 정보
        }
    };
}
export const login = (emailid, password) => {
    // 로그인 정보를 받아와서 로그인 액션 객체를 반환
    return {
        type: 'LOGIN',
        payload: {
            emailid,
            password,
        // 기타 로그인 정보
        }
    };
};
export const logout = () => {
    // 로그아웃 액션 객체를 반환
    return {
        type: 'LOGOUT'
    };
};
