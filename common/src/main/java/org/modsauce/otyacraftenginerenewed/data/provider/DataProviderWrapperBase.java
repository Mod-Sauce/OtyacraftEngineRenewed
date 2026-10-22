package org.modsauce.otyacraftenginerenewed.data.provider;

import net.minecraft.data.DataGenerator;
import net.minecraft.resources.Identifier;
import org.modsauce.otyacraftenginerenewed.data.CrossDataGeneratorAccess;

public interface DataProviderWrapperBase {
  CrossDataGeneratorAccess getCrossGeneratorAccess();

  default Identifier modLoc(String id) {
    return Identifier.fromNamespaceAndPath(getCrossGeneratorAccess().getMod().getModId(), id);
  }

  default DataGenerator getGenerator() {
    return getCrossGeneratorAccess().getVanillaGenerator();
  }
}
