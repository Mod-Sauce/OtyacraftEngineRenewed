package org.modsauce.otyacraftenginerenewed.client.callpoint.impl;

import com.google.common.collect.ImmutableList;
import org.modsauce.otyacraftenginerenewed.client.callpoint.ClientCallPoint;
import org.modsauce.otyacraftenginerenewed.client.callpoint.ClientCallPointManager;
import org.modsauce.otyacraftenginerenewed.client.callpoint.LayerRegister;
import org.modsauce.otyacraftenginerenewed.client.callpoint.ModelRegister;
import org.modsauce.otyacraftenginerenewed.util.OEUtils;

import java.util.List;
import java.util.function.Consumer;

public class ClientCallPointManagerImpl implements ClientCallPointManager {
  public static final ClientCallPointManagerImpl INSTANCE = new ClientCallPointManagerImpl();
  private final List<ClientCallPoint> points = ImmutableList.copyOf(OEUtils.getCallPoints("otyacraftengine_client", ClientCallPoint.Sign.class, ClientCallPoint.class));
  private final ClientCallPoint allCallPoint = new ClientCallPoint() {
    @Override
    public void onModelRegistry(ModelRegister register) {
      allConsumer(n -> n.onModelRegistry(register));
    }

    @Override
    public void onLayerRegistry(LayerRegister register) {
      allConsumer(n -> n.onLayerRegistry(register));
    }
  };

  @Override
  public ClientCallPoint call() {
    return allCallPoint;
  }

  private void allConsumer(Consumer<ClientCallPoint> callPointConsumer) {
    for (ClientCallPoint point : points) {
      callPointConsumer.accept(point);
    }
  }
}
