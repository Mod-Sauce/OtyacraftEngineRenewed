package org.modsauce.otyacraftenginerenewed.client.event;

import dev.architectury.event.Event;
import dev.architectury.event.EventFactory;
import dev.architectury.event.events.client.ClientLifecycleEvent;

public interface MoreClientLifecycleEvents {
    Event<ClientLifecycleEvent.ClientLevelState> CLIENT_LEVEL_UNLOAD = EventFactory.createLoop();
}
