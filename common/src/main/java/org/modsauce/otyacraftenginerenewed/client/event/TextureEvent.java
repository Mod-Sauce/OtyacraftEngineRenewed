package org.modsauce.otyacraftenginerenewed.client.event;

import dev.architectury.event.Event;
import dev.architectury.event.EventFactory;
import dev.architectury.event.EventResult;
import org.jetbrains.annotations.NotNull;

public interface TextureEvent {
    Event<CheckTextureURL> CHECK_TEXTURE_URL = EventFactory.createEventResult();
    Event<SwapTextureURL> SWAP_TEXTURE_URL = EventFactory.createLoop();

    interface CheckTextureURL {
        EventResult onCheckURL(@NotNull String url);
    }

    interface SwapTextureURL {
        void onSwapURL(String baseUrl, TextureURLSwapper swapper);
    }

    interface TextureURLSwapper {
        void setURL(String url);

        String getURL();
    }
}
