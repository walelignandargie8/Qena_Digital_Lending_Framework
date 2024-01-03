@ui @regression

Feature: Tests for CXS Login

  @login
  Scenario: Verify invalid credentials
    When I login as "Admin" user role
    Then I verify error message displayed for 'Wrong Username Password'
