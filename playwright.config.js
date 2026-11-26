const {defineConfig}=require('@playwright/test');
module.exports=defineConfig({testDir:'./e2e',workers:1,reporter:[['list'],['html',{open:'never'}]],use:{baseURL:'http://127.0.0.1:8080',trace:'retain-on-failure',screenshot:'only-on-failure'},webServer:{command:'java -jar target/helpdesk-1.0.0.jar',url:'http://127.0.0.1:8080/api/health',timeout:60000,reuseExistingServer:false}});
