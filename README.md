# Task 4 Groups config 
## Configuring Keycloak
### Step 1. Create groups

Go to `Groups` tab located in left menu under `Manage` block. Click new and type name of group.<br>
![img.png](img.png)

Name can be any, but for app we need to define it with this name format. `Name of department`Department.<br>
Create groups for all department names. here is the list of supported departments: `HR, Sales, Marketing, Finance, IT`.<br>
After creating return to `Groups` tab, and you should see all your Groups.<br>
![img_1.png](img_1.png)

### Step 2. Create groups mapping
To add groups to the JWT token body you need to create mapping as for department.<br>
Go to `Clients` -> `your-client` -> `Mappers` and click `Create`.<br>
Enter name `departmentMapper` and select in `Mapper Type` value `Groups membership`. Enter `Token Claim Name` value `departments`.<br>
Value in `Token Claim Name` defines under what name in JWT token group mappings will be stored. <br>
Do these configs for slider:
- Full group path - OFF. Don`t need for this app as we are interested only in group name. 
- Add to ID token - ON.
- Add to access token - ON.
- Add to userinfo - OFF.
- 
![img_2.png](img_2.png)

### Step 3. Add groups to user
Go to `Users` -> `View all users` -> select your user -> `Groups`. <br>
![img_3.png](img_3.png)

In right column select group and click `Join`. <br>
Department will be assigned to user, and you will see it in right column. <br>
Add several of them, but not all. Two will be fine. <br>
![img_4.png](img_4.png)

### Step 4. Verify that groups are added to JWT token.
Perform logging with configured user and acquire token. Go to https://jwt.io/ and pass it to `Encoded` column. <br>
If all was configured correctly you will see `departments` key and added to user groups. <br>
![img_5.png](img_5.png)

## Update Spring Boot App
In Spring Boot App manager user should see employees from departments that are related to defined for manager groups of departments. <br>
Example: Manager user have groups `HRDepartment` and `SalesDepartment`. After performing request user should see all employees that have department `HR` or `Sales`. <br>
To do this in code we will implement new endpoint and JPA function. Also, new function will be added to `SecurityContextHelper` class. <br>
You can do this by your own and use steps below as hits.

### Step 1. get groups from token.
To acquire `departments` values lets update `SecurityContextHelper` class. <br>

     public static List<String> getDepartments() {
        final var jwtAuthenticationContext = SecurityContextHolder.getContext().getAuthentication();
        final var credentials = (Jwt)jwtAuthenticationContext.getCredentials();
        final var groups = (List<String>) credentials.getClaims().get("departments");

        return groups
                .stream()
                .map(group ->
                        group
                        .replace("Department", "")
                        .trim()
                )
                .toList();
    }
As you can see groups is retrieved from token by `departments` key. Note that value is array, as user can have multiply departments defined. <br>
After retrieving we transform each group name from token to expected and returns it as list. <br>

### Step 2. Add new endpoint and processing for it.
After configuring retrieving of departments, we can proceed with new endpoint implementation. <br>
Firstly, we will update `EmployeeJpa` class to consume employees by departments. <br>

    @Query("SELECT e FROM Employee e WHERE e.employeeDepartment IN :departments")
    List<Employee> findAllByEmployeeDepartment(@Param("departments") List<String> departments);

As you can see in this DML employee is retrieved if his department matching one of defined in `departments` list value. <br>

The second part will be adding new logic to `EmployeeService` class. <br>

    public List<Employee> getEmployeesByDepartments() {
        return employeeJpa.findAllByEmployeeDepartment(SecurityContextHelper.getDepartments());
    }
Here we call retrieving function of database and get departments from `SecurityContextHelper`. <br>

And finally, add new endpoint to `EmployeeController` class. <br>

    @GetMapping("/search/department")
    public List<Employee> getEmployeesByDepartments() {
        return employeeService.getEmployeesByDepartments();
    }
To distinguish endpoints new subdirectory was added. <br>

### Step 3. Update Web Security config
Currently, if you try to call new endpoint from Postman you will get `Unauthorized 401` error. <br>
To avoid this we need to update `WebSecurity` class configuration. We need to define that there is access to new endpoint and only manager can access it. <br>
Add new endpoint path to existing `requestMatchers` for manager 
    
    .requestMatchers("/employees/search", "/employees/search/departments")
    .hasAnyRole("manager")

or add new configuration step

    .requestMatchers("/employees/search/departments")
    .hasAnyRole("manager")

After this you should be able to perform request from Postman to you new endpoint. <br>
### Step 4. Testing with Postman
Go to Postman, acquire token and perform `Get All Employees By departments` request.<br>
You should see list of employees from departments that are defined in manager user departments. See example below.<br>
![img_6.png](img_6.png)

