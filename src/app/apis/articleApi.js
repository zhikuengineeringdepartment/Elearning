import Http from "../modules/Http";

let http = new Http();

export const queryArticleTypes = (params, fn) => http.get('/specialColumn/getAll', params, fn)

export const queryAllArticles = (params, fn) => http.get("/specialColumnArticle/getAll", params, fn);

export const queryArticles = (params, fn) => http.get("/specialColumnArticle/getAllByType", params, fn);