package mist475.mcpatcherforge.asm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import com.prupe.mcpatcher.Config;
import com.prupe.mcpatcher.MCPatcherUtils;

import cpw.mods.fml.relauncher.FMLLaunchHandler;
import mist475.mcpatcherforge.core.MCPatcherForgeCore;

/**
 * Adapted from Hodgepodge
 */
public enum AsmTransformers {

    RENDERBLOCKS("RenderBlocks transformer", () -> Config.getBoolean(MCPatcherUtils.CUSTOM_COLORS, "enabled", true),
        Side.CLIENT, "mist475.mcpatcherforge.asm.RenderBlocksTransformer"),
    WORLDRENDERER("WorldRenderer transformer", () -> true, Side.CLIENT,
        "mist475.mcpatcherforge.asm.WorldRendererTransformer");

    private final Supplier<Boolean> applyIf;
    private final Side side;
    private final String[] transformerClasses;

    AsmTransformers(@SuppressWarnings("unused") String description, Supplier<Boolean> applyIf, Side side,
        String... transformers) {
        this.applyIf = applyIf;
        this.side = side;
        this.transformerClasses = transformers;
    }

    private boolean shouldBeLoaded() {
        return applyIf.get() && shouldLoadSide();
    }

    private boolean shouldLoadSide() {
        return side == Side.BOTH || (side == Side.SERVER && FMLLaunchHandler.side()
            .isServer())
            || (side == Side.CLIENT && FMLLaunchHandler.side()
                .isClient());
    }

    public static String[] getTransformers() {
        final List<String> list = new ArrayList<>();
        for (AsmTransformers transformer : values()) {
            if (transformer.shouldBeLoaded()) {
                MCPatcherForgeCore.log.info("Loading transformer {}", (Object[]) transformer.transformerClasses);
                list.addAll(Arrays.asList(transformer.transformerClasses));
            } else {
                MCPatcherForgeCore.log.info("Not loading transformer {}", (Object[]) transformer.transformerClasses);
            }
        }
        return list.toArray(new String[0]);
    }

    private enum Side {
        BOTH,
        CLIENT,
        SERVER
    }
}
