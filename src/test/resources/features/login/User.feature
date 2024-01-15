@ui @regression

Feature: Tests for CXS Login

  @login
  Scenario: Verify invalid credentials
    When I login as "Superadmin" user role
    Then I verify user is logged in


