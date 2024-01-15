@ui @regression

Feature: Login Tests

  @login
  Scenario: Verify valid credentials
    When I login as "superadmin" user role
    Then I verify user is logged in
