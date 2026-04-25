package dev.felnull.otyacraftengine;

import dev.felnull.otyacraftengine.client.debug.HighlightVoxelShapeType;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;

import java.util.Objects;

@Config(name = OtyacraftEngine.MODID)
@Config.Gui.Background(OtyacraftEngine.MODID + ":textures/gui/config_background.png")
public class OEConfig extends PartitioningSerializer.GlobalData {

    @ConfigEntry.Category("client")
    @ConfigEntry.Gui.TransitiveObject
    private ClientConfig clientConfig = new ClientConfig();

    @ConfigEntry.Category("client_debug")
    @ConfigEntry.Gui.TransitiveObject
    private DebugConfig debugConfig = new DebugConfig();

    public DebugConfig getDebugConfig() {
        return debugConfig;
    }

    public ClientConfig getClientConfig() {
        return clientConfig;
    }

    @Config(name = "client_debug")
    public static class DebugConfig implements ConfigData {
        private HighlightVoxelShapeType highlightVoxelShape = HighlightVoxelShapeType.OFF;

        private boolean showTagTooltip = false;

        private boolean showModNameTooltip = false;

        private boolean showWidgetData = false;

        public HighlightVoxelShapeType getHighlightVoxelShape() {
            return highlightVoxelShape;
        }

        public boolean isShowWidgetData() {
            return showWidgetData;
        }

        public boolean isShowTagTooltip() {
            return showTagTooltip;
        }

        public boolean isShowModNameTooltip() {
            return showModNameTooltip;
        }
    }

    @Config(name = "client")
    public static class ClientConfig implements ConfigData {
        @ConfigEntry.Gui.CollapsibleObject
        private URLTextureConfig urlTextureConfig = new URLTextureConfig();

        public URLTextureConfig getUrlTextureConfig() {
            return urlTextureConfig;
        }

        public static class URLTextureConfig {
            private int maxLoaderCount = 3;

            private String urlRegex = ".*";

            public int getMaxLoaderCount() {
                return Math.max(1, maxLoaderCount);
            }

            public String getUrlRegex() {
                if(Objects.equals(urlRegex, "https://(i.imgur.com|imgur.com)/.*"))
                    urlRegex = ".*";
                return urlRegex;
            }
        }
    }
}
