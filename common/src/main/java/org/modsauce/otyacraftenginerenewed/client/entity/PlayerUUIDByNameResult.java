package org.modsauce.otyacraftenginerenewed.client.entity;

import java.util.UUID;

public record PlayerUUIDByNameResult(UUID uuid, boolean loading) {
  public boolean isFailure() {
    return uuid == null && !loading();
  }

  public boolean isSuccess() {
    return uuid != null && !loading();
  }
}
