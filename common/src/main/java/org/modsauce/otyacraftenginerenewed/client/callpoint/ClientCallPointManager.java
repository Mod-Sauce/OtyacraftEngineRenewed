package org.modsauce.otyacraftenginerenewed.client.callpoint;

import org.modsauce.otyacraftenginerenewed.client.callpoint.impl.ClientCallPointManagerImpl;

public interface ClientCallPointManager {
    static ClientCallPointManager getInstance() {
        return ClientCallPointManagerImpl.INSTANCE;
    }

    ClientCallPoint call();
}
