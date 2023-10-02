# Task 4 Groups config 
## Configuring Keycloak
### Step 1. Create groups

Go to `Groups` tab located in left menu under `Manage` block. Click new and type name of group.<br>
![img.png](docs/images/img.png)

Name can be any, but for app we need to define it with this name format. `Name of department`Group.<br>
Create groups for all departments names. here is the list of supported departments: `HR, Sales, Marketing, Finance, IT`.<br>
After creating return to `Groups` tab, and you should see all your Groups.<br>
![img_2.png](docs/images/img_2.png)

### Step 2. Create groups mapping
To add groups to the JWT token body you need to create mapping as for department.<br>
Go to `Clients` -> `your-client` -> `Mappers` and click `Create`.<br>
Enter name `groupsMapper` and select in `Mapper Type` value `Groups membership`. Enter `Token Claim Name` value `groups`.<br>
Value in `Token Claim Name` defines under what name in JWT token group mappings will be stored. <br>
Do this configs for slider:
- Full group path - OFF. Don`t need for this app as we are interested only in group name. 
- Add to ID token - ON.
- Add to access token - ON.
- Add to userinfo - OFF.

![img_6.png](docs/images/img_6.png)

### Step 3. Add groups to user
Go to `Users` -> `View all users` -> select your user -> `Groups`. <br>
![img_3.png](docs/images/img_3.png)

In right column select group and click `Join`. <br>
Group will be assigned to user and you will see it in right column. <br>
Add several of them, but not all. Two will be fine. <br>
![img_4.png](docs/images/img_4.png)

### Step 4. Verify that groups are added to JWT token.
Perform logging with configured user and acquire token. Go to https://jwt.io/ and pass it to `Encoded` column. <br>
If all was configured correctly you will see `groups` key and added to user groups. <br>
![img_7.png](docs/images/img_7.png)

## Update Spring Boot App
In Spring Boot App manager user should see employees from departments that are related to groups. <br>
Example: Manager user have groups `HRGroup` and `SalesGroup`. After performing request user should see all employees that have department `HR` or `Sales`. <br>
To do this in code we will implement new endpoint and JPA function. Also, new function will be added to `SecurityContextHelper` class. <br>
You can do this by your own and use steps below as hits.

### Step 1. get groups from token.
To acquire `groups` values lets update `SecurityContextHelper` class. <br>

     public static List<String> getGroups() {
        final var jwtAuthenticationContext = SecurityContextHolder.getContext().getAuthentication();
        final var credentials = (Jwt)jwtAuthenticationContext.getCredentials();
        final var groups = (List<String>) credentials.getClaims().get("groups");

        return groups
                .stream()
                .map(group ->
                        group
                        .replace("Group", "")
                        .trim()
                )
                .toList();
    }
As you can see groups is retrieved from token by `groups` key. Note that value is array, as user can have multiply groups defined. <br>
After retrieving we transform each group name from token to expected and returns it as list. <br>

### Step 2. Add new endpoint and processing for it.
After configuring retrieving of groups, we can proceed with new endpoint implementation. <br>
Firstly, we will update `EmployeeJpa` class to consume employees by group. <br>

    @Query("SELECT e FROM Employee e WHERE e.employeeDepartment IN :groups")
    List<Employee> findAllByEmployeeDepartment(@Param("groups") List<String> groups);

As you can see in this DML employee is retrieved if his department matching one of defined in `groups` list value. <br>

The second part will be adding new logic to `EmployeeService` class. <br>

    public List<Employee> getEmployeesByGroup() {
        return employeeJpa.findAllByEmployeeDepartment(SecurityContextHelper.getGroups());
    }
Here we call retrieving function of database and get groups from `SecurityContextHelper`. <br>

And finally, add new endpoint to `EmployeeController` class. <br>

    @GetMapping("/search/group")
    public List<Employee> getEmployeesByGroup() {
        return employeeService.getEmployeesByGroup();
    }
To distinguish endpoints new subdirectory was added. <br>

### Step 3. Update Web Security config
Currently, if you try to call new endpoint from Postman you will get `Unauthorized 401` error. <br>
To avoid this we need to update `WebSecurity` class configuration. We need to define that there is access to new endpoint and only manager can access it. <br>
Add new endpoint path to existing `requestMatchers` for manager 
    
    .requestMatchers("/employees/search", "/employees/search/group")
    .hasAnyRole("manager")

or add new configuration step

    requestMatchers("/employees/search/group")
    .hasAnyRole("manager")

After this you should be able to perform request from Postman to you new endpoint. <br>
### Step 4. Testing with Postman
Go to Postman, acquire token and perform `Get All Employees By groups` request.<br>
You should see list of employees from departments that are defined in manager user groups. See example below.<br>
![img_5.png](docs/images/img_5.png)

