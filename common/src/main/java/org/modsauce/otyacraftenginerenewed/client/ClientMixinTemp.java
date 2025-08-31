package org.modsauce.otyacraftenginerenewed.client;

import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public class ClientMixinTemp {
  public static ThreadLocal<Boolean> SKIP_TRANSANDROT_MODELPART = ThreadLocal.withInitial(() -> false);
}
