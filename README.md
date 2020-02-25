# sharing-ideas 山软智库前端 ——by shadowingszy

> front-master 分支为上线的稳定版

> front-dev 分支为开发版

## 目录结构

|-- README.md 说明文档<br/>
|-- babel.config.js babel 设置<br/>
|-- package-lock.json package-lock<br/>
|-- package.json 依赖库配置<br/>
|-- public public 目录，放置静态文件以及 SPA 入口<br/>
| |-- img 图片<br/>
| |-- preview 预览知识文档所需要的 js 库<br/>
| |-- index.html 主页面<br/>
| |-- preview.html 预览页面<br/>
|-- src 源代码<br/>
|   |-- App.vue 页面主容器<br/>
|   |-- assets 资源文件<br/>
|   |-- components 一些公用的组件<br/>
|   |-- main.js vue 入口以及 vue 根实例<br/>
|   |-- pages pages 目录，放置页面组件<br/>
|   |   |-- about 关于智库 tab<br/>
|   |   |-- article 智库专栏 tab<br/>
|   |   |-- knowledge 知识见解 tab<br/>
|   |   |-- resources 文件资源 tab<br/>
|   |   |-- user 用户 tab<br/>
|   |-- app app 目录，放置全局的文件<br/>
|   |-- |-- apis 封装的 api 目录<br/>
|   |-- |-- modules 封装数据的模块<br/>
|   |-- router vue-router 配置<br/>
|   |-- store vuex 配置<br/>
|   |-- tools.js 全局的工具函数<br/>
|-- vue.config.js vue-cli-webpack 打包配置<br/>

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

### 使用 ESLint 修正代码

```
npm run lint
```

## 更新日志

- 2019.10.28 -- by wuchvi

  整理代码并上传到仓库

- 2019.11.13 --by wuchvi

  修改 tab 判断逻辑和路由守卫，用 getCookie('token')代替 status.isLogin,删除 status.isLogin 和 status.tabIndex

  增加 论坛 模块（仅增加模块，无内容）

- 2019.11.23 --by wuchvi

  修改路由，分管理模块和普通用户模块，新建管理页面

- 2019.11.26 --by wuchvi

  完善管理模块，但未和后台对接
  使用 mathjax 转换公式，mathjax 文件是 public/tex-svg.js，配置文件是 mathjax.config.js

- 2019.12.01 --by wuchvi

  完成管理界面的上传图片和预览功能

- 2019.12.07 --by wuchvi

  管理模块完成，增加管理员权限，增加智库周记

- 2020.01.27 --by wuchvi

  开始重构代码，新建 app 文件夹放置全局方法，新建 HTTP 类处理请求

- 2020.02.01 --by wuchvi

  知识见解页面重构基本完成，将知识见解页面数据封装成 Course 类，将 api 封装

- 2020.02.07 --by wuchvi

  将 tool.js 改成 functions.js，并挂载到 vue.prototype 作为全局方法，替换部分 http 请求为封装后的 api
  
- 2020.02.22 --by wuchvi

  增加文件审核模块
  
- 2020.02.24 --by wuchvi

  增加 修改密码，修改文件资源列表加载  
  
- 2020.02.25 --by wuchvi

  增加axios拦截器，优化http.js
