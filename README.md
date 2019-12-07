# sharing-ideas 山软智库前端 ——by shadowingszy




>front-master分支为上线的稳定版
>front-dev分支为开发版




## 目录结构
|-- README.md                   说明文档<br/>
|-- babel.config.js             babel设置<br/>
|-- package-lock.json           package-lock<br/>
|-- package.json                依赖库配置<br/>
|-- public                      public目录，放置静态文件以及SPA入口<br/>
|   |-- img                     图片<br/>
|   |-- preview                 预览知识文档所需要的js库<br/>
|   |-- index.html              主页面<br/>
|   |-- preview.html            预览页面<br/>
|-- src                         源代码<br/>
|   |-- App.vue                 页面主容器<br/>
|   |-- assets                  资源文件<br/>
|   |-- components              一些公用的组件<br/>
|   |-- main.js                 vue入口以及vue根实例<br/>
|   |-- pages                   pages目录，放置页面组件<br/>
|   |   |-- about               关于智库tab<br/>
|   |   |-- article             智库专栏tab<br/>
|   |   |-- knowledge           知识见解tab<br/>
|   |   |-- resources           文件资源tab<br/>
|   |   |-- user                用户tab<br/>
|   |-- router                  vue-router配置<br/>
|   |-- store                   vuex配置<br/>
|   |-- tools.js                全局的工具函数<br/>
|-- vue.config.js               vue-cli-webpack打包配置<br/>


### 前端依赖库补全
```
npm install
```

### 启动前端页面+热更新
```
npm run serve
```

### 前端页面打包
```
npm run build
```

### 进行测试
```
npm run test
```

### 使用ESLint修正代码
```
npm run lint
```

## 更新日志
+ 2019.10.28	-- by wuchvi

    整理代码并上传到仓库 
	
+ 2019.11.13    --by wuchvi

    修改tab判断逻辑和路由守卫，用getCookie('token')代替status.isLogin,删除status.isLogin和status.tabIndex

    增加 论坛 模块（仅增加模块，无内容）

+ 2019.11.23    --by wuchvi

    修改路由，分管理模块和普通用户模块，新建管理页面

+ 2019.11.26    --by wuchvi

    完善管理模块，但未和后台对接
    使用mathjax转换公式，mathjax文件是public/tex-svg.js，配置文件是mathjax.config.js

+ 2019.12.01    --by wuchvi

    完成管理界面的上传图片和预览功能

+ 2019.12.07    --by wuchvi

    管理模块完成，增加管理员权限，增加智库周记
