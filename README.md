# sharing-ideas 山软智库前端 ——by shadowingszy

## 未完成的工作
- 知识见解详情页面——收藏、笔记功能的实现
- 知识见解详情页面——移动端适配

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
