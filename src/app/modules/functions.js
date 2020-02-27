//本文件定义了一些工具方法。

import katex from "katex";
import showdown from "showdown";

// 路由跳转的定制方法
const routerChange = (pageUrl, _this) => {
    _this.$router.push(pageUrl);
    _this.$emit("change", pageUrl);
    console.log("跳转到" + pageUrl);
};

// 生成节流函数
const throttle = (fn, duration) => {
    let preTime = Date.now();
    return () => {
        let doTime = Date.now();
        if (doTime - preTime > duration) {
            console.log("节流函数执行");
            fn();
            preTime = doTime;
        }
    };
};

/**
 * 匹配根域名
 * @param href
 * @returns {RegExpMatchArray | Promise<Response | undefined> | * | {protocol: string | *, hostname: string | *, search: string | *, port: string | *, host: string | *, hash: string | *, pathname: string | *}}
 */
const getLocation = href => {
    const match = href.match(
        // eslint-disable-next-line no-useless-escape
        /^(https?\:)\/\/(([^:\/?#]*)(?:\:([0-9]+))?)([\/]?[^?#]*)(\?[^#]*|)(#.*|)$/
    );
    return (
        match && {
            protocol: match[1],
            host: match[2],
            hostname: match[3],
            port: match[4],
            pathname: match[5],
            search: match[6],
            hash: match[7]
        }
    );
};

/**
 * @desc 设置cookie
 * @param cname 名称
 * @param cvalue 值
 * @param expire 过期时间
 */
const setCookie = function (cname, cvalue, expire) {
    let d = new Date();
    d.setTime(d.getTime() + expire * 1000);
    let expires = "expires=" + d.toUTCString();
    document.cookie = cname + "=" + cvalue + "; " + expires + ";path=/";
};

/**
 * @desc 获取cookie
 * @param cname
 * @returns {string}
 */
const getCookie = cname => {
    var name = cname + "=";
    var ca = document.cookie.split(";");
    for (var i = 0; i < ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == " ") c = c.substring(1);
        if (c.indexOf(name) != -1) return c.substring(name.length, c.length);
    }
    return "";
};

/**
 * @desc 清除cookie
 * @param name
 */
const clearCookie = function (name) {
    setCookie(name, "", -1);
};

/**
 * 翻译markdown字符串中的公式
 * @param str
 * @returns {void|string|*}
 */
const parse = str => {
    return parse$(parse$$(str));
};

/**
 * 编译markdown字符串中的多行公式
 * @param str
 * @returns {void | string | *}
 */
const parse$$ = str => {
    const strArray = str.split("");
    let dollarFlag = false; // 是否匹配到了$
    let mathStr = "";
    for (let a = 0; a < strArray.length - 1; a++) {
        let testStr = strArray[a] + strArray[a + 1];
        if (testStr === "$$" && !dollarFlag) {
            dollarFlag = true;
        } else if (testStr === "$$" && dollarFlag) {
            dollarFlag = false;
            mathStr = mathStr.substr(1, mathStr.length);
            let parseMathStr = parseMath(
                mathStr,
                mathStr.includes("\\tag{") ? true : false
            );
            str = str.replace("$$" + mathStr + "$$", "<p>" + parseMathStr + "</p>");
            mathStr = "";
        } else if (testStr !== "$$" && dollarFlag) {
            mathStr += strArray[a];
        }
    }

    return str;
};

/**
 * 编译markdown中的单行公式
 * @param str
 * @returns {void | string | *}
 */
const parse$ = str => {
    const strArray = str.split("");
    let dollarFlag = false; // 是否匹配到了$
    let mathStr = "";
    for (let a = 0; a < strArray.length; a++) {
        let testStr = strArray[a];
        if (testStr === "$" && !dollarFlag) {
            dollarFlag = true;
        } else if (testStr === "$" && dollarFlag) {
            dollarFlag = false;
            let parseMathStr = parseMath(
                mathStr,
                mathStr.includes("\\tag{") ? true : false
            );
            str = str.replace("$" + mathStr + "$", parseMathStr);
            mathStr = "";
        } else if (testStr !== "$" && dollarFlag) {
            mathStr += strArray[a];
        }
    }

    return str;
};

/**
 * 将公式字符串转换为HTML
 * @param mathStr
 * @returns {*}
 */
const parseMath = (mathStr, flag = false) => {
    return katex.renderToString(mathStr, {
        throwOnError: false,
        displayMode: flag,
        output: "html",
        strict: "ignore "
    });
};

/**将section从markdown转化成html
 *@param section
 *@return {Array}
 */
const markdown2Html = sectionView => {
    let converter = new showdown.Converter();
    converter.setOption("tables", "true");
    converter.setOption("simpleLineBreaks", "true");

    // 将markdown转换为html
    sectionView.sectionNameHtml = converter.makeHtml(
        parse(sectionView.sectionName)
    );
    sectionView.knowledgeViews.map(knowledge => {
        knowledge.knowledgeNameHtml = converter.makeHtml(
            parse(knowledge.knowledgeName)
        );
        knowledge.paragraphs.map(paragraph => {
            let paragraphContentHtml = converter.makeHtml(
                parse(paragraph.paragraphContent)
            );
            if (paragraphContentHtml.includes("<pre")) {
                paragraphContentHtml = paragraphContentHtml.replace(
                    "<pre",
                    '<pre class="code-content"'
                );
                // console.log("===", paragraphContentHtml);
            } else if (paragraphContentHtml.includes("<img")) {
                paragraphContentHtml = paragraphContentHtml.replace(
                    "<img",
                    '<img class="img-content"'
                );
                // console.log("====", paragraphContentHtml);
            }
            paragraph.paragraphContentHtml = paragraphContentHtml;
        });
    });
    return sectionView;
};

/**根据cookies判断是否登陆 */
const isLogin = () => {
    return getCookie("token");
};

/**
 * 滚动到底部时执行fn
 * @param fn
 */
const lazyLoading = (fn) => {
    let scrollTop =
        document.documentElement.scrollTop || document.body.scrollTop
    let clientHeight = document.documentElement.clientHeight
    let scrollHeight = document.documentElement.scrollHeight
    if (scrollTop + clientHeight + 0.2 >= scrollHeight) {
        fn()
    }
}

export {
    routerChange,
    throttle,
    getLocation,
    setCookie,
    getCookie,
    clearCookie,
    markdown2Html,
    isLogin,
    lazyLoading
};
