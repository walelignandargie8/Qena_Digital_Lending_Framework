@ui @regression

Feature: Access Management

  @login
  Scenario: Create User Group
    When I login as "Superadmin" user role
    Then I verify user is logged in
    When I navigate to "Access Management" page
    Then I verify "Access Management" page