@ui @regression

Feature: Tests for CXS Login

  @login
  Scenario : create user group
    When I login as "superadmin" user role
    Then I verify user is logged in

