package org.modsauce.otyacraftenginerenewed.client.entity;

public record PlayerNameByUUIDResult(String name, boolean loading) {
    public boolean isFailure() {
        return name == null && !loading();
    }

    public boolean isSuccess() {
        return name != null && !loading();
    }
}
