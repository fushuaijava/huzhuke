require.config({
	baseUrl: "static/js",
    paths: {
        jquery: 'lib/jquery',
        md5:'lib/md5',
        jlogin:'my/login',
        jsendsms:'my/sendsms'
    }
});
 
require(['jquery','jlogin','jsendsms'], function($,jlogin,jsendsms) {
    jsendsms.sendSms('15108977909');
});