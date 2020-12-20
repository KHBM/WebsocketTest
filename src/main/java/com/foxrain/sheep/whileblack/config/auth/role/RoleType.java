package com.foxrain.sheep.whileblack.config.auth.role;

public enum RoleType {

  USER("USER"),
  ADMIN("ADMIN"),
  NONE("NONE")
  ;

  private final String PREFIX = "ROLE_";
  private String roleName;

  RoleType(String roleName) {
    this.roleName = roleName;
  }

  public String getRoleName() {
    return PREFIX + this.roleName;
  }

}
