(function(window){var svgSprite='<svg><symbol id="icon-weixinzhifu" viewBox="0 0 1024 1024"><path d="M405.106 628.723c-53.867 32.528-61.858-18.264-61.858-18.264l-67.512-170.643c-25.974-81.090 22.484-36.563 22.484-36.563s41.58 34.072 73.136 54.836c31.539 20.764 67.493 6.095 67.493 6.095l441.359-220.663c-81.429-109.755-215.947-181.52-368.231-181.52-248.527 0-449.975 190.982-449.975 426.58 0 135.519 66.705 256.131 170.605 334.3l-18.735 116.675c0 0-9.136 34.057 22.522 18.262 21.571-10.77 76.563-49.372 109.3-72.86 51.464 19.418 107.532 30.203 166.31 30.203 248.504 0 449.993-190.978 449.993-426.578 0-68.243-16.974-132.689-47.052-189.893-140.617 91.645-467.688 304.625-509.838 330.034v0 0z"  ></path></symbol></svg>';var script=function(){var scripts=document.getElementsByTagName("script");return scripts[scripts.length-1]}();var shouldInjectCss=script.getAttribute("data-injectcss");var ready=function(fn){if(document.addEventListener){if(~["complete","loaded","interactive"].indexOf(document.readyState)){setTimeout(fn,0)}else{var loadFn=function(){document.removeEventListener("DOMContentLoaded",loadFn,false);fn()};document.addEventListener("DOMContentLoaded",loadFn,false)}}else if(document.attachEvent){IEContentLoaded(window,fn)}function IEContentLoaded(w,fn){var d=w.document,done=false,init=function(){if(!done){done=true;fn()}};var polling=function(){try{d.documentElement.doScroll("left")}catch(e){setTimeout(polling,50);return}init()};polling();d.onreadystatechange=function(){if(d.readyState=="complete"){d.onreadystatechange=null;init()}}}};var before=function(el,target){target.parentNode.insertBefore(el,target)};var prepend=function(el,target){if(target.firstChild){before(el,target.firstChild)}else{target.appendChild(el)}};function appendSvg(){var div,svg;div=document.createElement("div");div.innerHTML=svgSprite;svgSprite=null;svg=div.getElementsByTagName("svg")[0];if(svg){svg.setAttribute("aria-hidden","true");svg.style.position="absolute";svg.style.width=0;svg.style.height=0;svg.style.overflow="hidden";prepend(svg,document.body)}}if(shouldInjectCss&&!window.__iconfont__svg__cssinject__){window.__iconfont__svg__cssinject__=true;try{document.write("<style>.svgfont {display: inline-block;width: 1em;height: 1em;fill: currentColor;vertical-align: -0.1em;font-size:16px;}</style>")}catch(e){console&&console.log(e)}}ready(appendSvg)})(window)