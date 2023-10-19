package mist475.mcpatcherforge.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.gtnewhorizon.gtnhmixins.IEarlyMixinLoader;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import mist475.mcpatcherforge.asm.AsmTransformers;
import mist475.mcpatcherforge.asm.mappings.Namer;
import mist475.mcpatcherforge.mixins.Mixins;

/**
 * Adapted from Hodgepodge
 */
@IFMLLoadingPlugin.MCVersion("1.7.10")
@IFMLLoadingPlugin.TransformerExclusions("mist475.mcpatcherforge.asm")
public class MCPatcherForgeCore implements IFMLLoadingPlugin, IEarlyMixinLoader {

    public static final Logger log = LogManager.getLogger("MCPatcher");

    private String[] transformerClasses;

    @Override
    public String getMixinConfig() {
        return "mixins.mcpatcherforge.early.json";
    }

    @Override
    public List<String> getMixins(Set<String> loadedCoreMods) {
        final List<String> mixins = new ArrayList<>();
        final List<String> notLoading = new ArrayList<>();
        for (Mixins mixin : Mixins.values()) {
            if (mixin.phase == Mixins.Phase.EARLY) {
                if (mixin.shouldLoad(loadedCoreMods, Collections.emptySet())) {
                    mixins.addAll(mixin.mixinClasses);
                } else {
                    notLoading.addAll(mixin.mixinClasses);
                }
            }
        }
        log.info("Not loading the following EARLY mixins: {}", notLoading.toString());
        return mixins;
    }

    @Override
    public String[] getASMTransformerClass() {
        if (transformerClasses == null) {
            Namer.initNames();
            transformerClasses = AsmTransformers.getTransformers();
        }
        return transformerClasses;
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {}

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}
