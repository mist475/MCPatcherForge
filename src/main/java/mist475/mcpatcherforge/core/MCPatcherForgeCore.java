package mist475.mcpatcherforge.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.gtnewhorizon.gtnhmixins.IEarlyMixinLoader;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import mist475.mcpatcherforge.mixins.Mixins;

@IFMLLoadingPlugin.MCVersion("1.7.10")
public class MCPatcherForgeCore implements IFMLLoadingPlugin, IEarlyMixinLoader {

    private static final Logger log = LogManager.getLogger("MCPatcher");
    public static final SortingIndex index = MCPatcherForgeCore.class
        .getAnnotation(IFMLLoadingPlugin.SortingIndex.class);

    private String[] transformerClasses;

    public static int getSortingIndex() {
        return index != null ? index.value() : 0;
    }

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

    public enum AsmTransformers {

        ;

        private final String name;
        private final Supplier<Boolean> applyIf;
        private final List<String> asmTransformers;

        AsmTransformers(String name, Supplier<Boolean> applyIf, List<String> asmTransformers) {
            this.name = name;
            this.applyIf = applyIf;
            this.asmTransformers = asmTransformers;
        }

        public boolean shouldBeLoaded() {
            return applyIf.get();
        }
    }

    @Override
    public String[] getASMTransformerClass() {
        if (transformerClasses == null) {
            transformerClasses = Arrays.stream(AsmTransformers.values())
                .map(asmTransformer -> {
                    if (asmTransformer.shouldBeLoaded()) {
                        log.info("Loading MCPatcher forge transformers {}", asmTransformer.name);
                        return asmTransformer.asmTransformers;
                    } else {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .flatMap(List::stream)
                .toArray(String[]::new);
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
