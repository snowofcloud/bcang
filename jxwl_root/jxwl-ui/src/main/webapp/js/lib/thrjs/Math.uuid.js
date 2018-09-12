/*
 * GDYY
 * Description: GDYY project by handler to liumm
 * Author: liumm
 * Version: 1.0.0
 * Last Update : 2016-05-31 09:58:23
 */
!function(){var a="0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".split("");Math.uuid=function(b,c){var d,e=a,f=[];if(c=c||e.length,b)for(d=0;b>d;d++)f[d]=e[0|Math.random()*c];else{var g;for(f[8]=f[13]=f[18]=f[23]="-",f[14]="4",d=0;36>d;d++)f[d]||(g=0|16*Math.random(),f[d]=e[19==d?3&g|8:g])}return f.join("")},Math.uuidFast=function(){for(var b,c=a,d=new Array(36),e=0,f=0;36>f;f++)8==f||13==f||18==f||23==f?d[f]="-":14==f?d[f]="4":(2>=e&&(e=33554432+16777216*Math.random()|0),b=15&e,e>>=4,d[f]=c[19==f?3&b|8:b]);return d.join("")},Math.uuidCompact=function(){return"xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx".replace(/[xy]/g,function(a){var b=16*Math.random()|0,c="x"==a?b:3&b|8;return c.toString(16)})}}();