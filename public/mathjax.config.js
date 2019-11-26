/**mathjax配置文件 */

window.MathJax = {
  //关闭js加载过程信息
  showProcessingMessages: false,
  //不显示信息
  messageStyle: "none",
  //禁用右键菜单
  options: {
    renderActions: {
      addMenu: [0, "", ""]
    }
  },
  //行内公式选择符
  tex: {
    inlineMath: [
      ["$", "$"],
      ["\\(", "\\)"]
    ]
  },
  //段内公式选择符
  displayMath: [
    ["$$", "$$"],
    ["\\[", "\\]"]
  ],
  svg: {
    fontCache: "global"
  }
};

(function() {
  var script = document.createElement("script");
  script.src = "./tex-svg.js";
  script.async = true;
  document.head.appendChild(script);
})();
