package org.modsauce.otyacraftenginerenewed.client;

import net.irisshaders.iris.Iris;

public class IrisUtil {
    public static boolean enableShader(){
        return Iris.getIrisConfig().areShadersEnabled();
    }
}
