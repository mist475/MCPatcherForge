package com.prupe.mcpatcher.renderpass;

import net.minecraft.block.Block;

import com.prupe.mcpatcher.mal.block.RenderPassAPI;

public class RenderPassMap {

    static final RenderPassMap instance = new RenderPassMap();

    private static final int[] MAP = new int[] { 0, 0, 0, 1, 2, 3 };

    public static int map18To17(int pass) {
        return pass > 1 ? instance.MCPatcherToVanilla(pass) : pass;
    }

    public static int map17To18(int pass) {
        return pass <= 1 ? instance.vanillaToMCPatcher(pass) : pass;
    }

    protected int vanillaToMCPatcher(int pass) {
        for (int i = 0; i < MAP.length; i++) {
            if (MAP[i] == pass) {
                return i;
            }
        }
        return -1;
    }

    protected int MCPatcherToVanilla(int pass) {
        return pass >= 0 && pass < MAP.length ? MAP[pass] : -1;
    }

    protected int getDefaultRenderPass(Block block) {
        return vanillaToMCPatcher(block.getRenderBlockPass());
    }

    protected int getCutoutRenderPass() {
        return RenderPassAPI.SOLID_RENDER_PASS;
    }
}
