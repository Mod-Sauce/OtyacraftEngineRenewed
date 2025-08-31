package org.modsauce.otyacraftenginerenewed.server.event;

import dev.architectury.event.Event;
import dev.architectury.event.EventFactory;
import dev.architectury.event.events.common.LifecycleEvent;

public interface ServerEvent {
  Event<LifecycleEvent.ServerState> SERVER_SAVING = EventFactory.createLoop();
}
