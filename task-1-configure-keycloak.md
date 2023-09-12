# Task 1 Configure Keycloak
## Configuring Keycloak
### Step 1. Logging into Keycloak
Go by this link http://localhost:8080/auth/ to Keycloak console<br/>
![img.png](docs/images/img.png)<br/>

Click `Administration Console` and you will be redirected to login page<br/>
![img_1.png](docs/images/img_1.png)<br/>

Admin user is already predefined, so enter: <br/>
`Username: admin` <br/>
`Password: admin` <br/>

### Step 2. Create Keycloak realm
On left side menu, hover over `Master` realm and click `Add realm` <br/>
![img_2.png](docs/images/img_2.png)<br/>

Enter name of realm and verify that `Enabled` is `On` <br/>
![img_3.png](docs/images/img_3.png) <br/>
After this you will be redirected to your realm page.<br/>
You should see at the top of menu name of your realm.<br/>
![img_4.png](docs/images/img_4.png)<br/>
### Step 3. Create and configure client
Go to `Clients` page. Look for button at menu. You will be redirected, and you will see base clients.<br/>
![img_5.png](docs/images/img_5.png) <br/>
Click Create button and fill `ClientID` field and click `Save`. <br/>
![img_6.png](docs/images/img_6.png) <br/>
After this you will be redirected to your client page. <br/>
Verify that `Enabled`, `Standard Flow Enabled`, `Direct Access Grants Enabled` and `Backchannel Logout Session Required` are turned on.  
All other sliders are disabled. <br/>
Change `Client Protocol` to `openid-connect` and `Access Type` to `confidential`. <br/>
Into `Valid Redirect URIs` enter URI that will handle URI for accessing code for token and click `+` to add it.
For valid redirect URI use application port `8081` and unused endpoints as  `/callback`.<br/>
Into `Web Origins` enter URI of your service and click `+` to add it.<br/>
Complete configuration looks like this.<br/>
![img_7.png](docs/images/img_7.png)<br/>
![img_8.png](docs/images/img_8.png)<br/>
After configuration click `Save`.
### Step 4. Create new role
To create new role go to `Roles` tab and click `Add Role` button.<br/>
![img_9.png](docs/images/img_9.png)<br/>
Enter role name and some description and click save. <br/>
![img_11.png](docs/images/img_11.png) <br/>
### Step 5. Create and configure User
Go to `Users` tab and click `Add user` button. Enter `Username` and all other information that you want and click `Save`.<br/>
![img_10.png](docs/images/img_10.png)<br/>
Go to `Credentials` tab and define `Password`. I propose to set `Temporary` slider to Off to prevent redefining password.
Click Set Password button and agree in popup window<br/>
![img_12.png](docs/images/img_12.png) <br/>
After this password is set up successfully. If you see after creation that fields are empty and `Temporary` is On, it's completely fine.<br/>
![img_13.png](docs/images/img_13.png) <br/>
Go to `Role Mappings `tab. <br/>
![img_14.png](docs/images/img_14.png)<br/>
Click on your role and click `Add selected`. <br/>
![img_15.png](docs/images/img_15.png) <br/>

### Step 6. Configure Custom Attributes
Go to `Users`, select your user, click `Attributes` tab.<br/>
![img_16.png](docs/images/img_16.png) <br/>
Here you will define custom attribute.<br/>
Enter key name department, value for it and click `Add`. Enter one of this `HR, Sales, Marketing, Finance, IT` to work with predefined data.<br/>
![img_17.png](docs/images/img_17.png)<br/>
Click Save to complete adding attribute. <br/>
Go to `Clients` and select your client, click `Mappers` and `Create button`.<br/>
In `Mapper Type` select `User Attribute`. Enter any mapper name.
In `User Attribute` and `Token Claim Name` enter name of attribute that you have define previously for your user.
Switch off all sliders except of `Add to access token` and click `Save`<br/>
![img_18.png](docs/images/img_18.png)<br/>

### Step 7. Acquire Access Token
Run Postman program, import collection using this link:
https://api.postman.com/collections/15826172-a989b383-a314-4aed-9990-56674dac1887?access_key=PMAT-01H7W1HH74ED4XJQ50NJ7DTJE7
and you can start testing application using existing collection. <br/>

`OAuthn2 token` folder commands are responsible for acquiring access token
`Service` folder commands are responsible for requests to Spring Boot app.

Firstly go to `Get code` request and change in URI fields:
* `realm` - name of your realm
* `client_id` - name of your client
* `redirect_uri` - redirect URI that you defined in client config

In `Get access token` request change all from previous and additionally `client_secret`.<br/>
Go to your realm -> Client -> Credentials and copy value in `Secret` field. If it is hidden click `Regenerate Secret`. <br/>
Also you can define your personal `state` value.<br/>

Now you can try to log in into using user that you have created.
To acquire token you need to copy `Get code` request, paste to you browser and perform Sign In process in Keycloak server.

Then after successful sign in, copy `code` part from URL.
Paste copied part into `Get access token` request and execute it in Postman.
In response, you will see data about access token that you have acquired.
If you got response with `access_token` field, you have logged in successfully.