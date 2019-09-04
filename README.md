# sharing-ideas（山软智库前端）
## ——by shadowingszy

## 未完成的工作
- 知识见解详情页面——收藏、笔记功能的实现
- 知识见解详情页面——移动端适配

## 目录结构
- ├── README.md                   说明文档
- ├── babel.config.js                 
- ├── package-lock.json           
- ├── package.json                
- ├── public                      public目录，放置静态文件以及SPA入口
- ├── src                         源代码
- │   ├── App.vue                 页面主容器
- │   ├── assets                  资源文件
- │   ├── components              一些公用的组件
- │   ├── main.js                 vue入口以及vue根实例
- │   ├── pages<br/>                   
- │   │   ├── about               关于智库tab
- │   │   ├── article             智库专栏tab
- │   │   ├── knowledge           知识见解tab
- │   │   ├── resources           文件资源tab
- │   │   └── user                用户tab
- │   ├── router                  vue-router配置
- │   ├── store                   vuex配置
- │   └── tools.js                全局的工具函数
- └── vue.config.js               vue-cli-webpack打包配置


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
