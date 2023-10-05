package com.prupe.mcpatcher.mal.biome;

import java.util.BitSet;

import net.minecraft.client.Minecraft;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.biome.BiomeGenBase;

import com.prupe.mcpatcher.MCLogger;
import com.prupe.mcpatcher.MCPatcherUtils;
import com.prupe.mcpatcher.mal.resource.PropertiesFile;

public class BiomeAPI {

    private static final MCLogger logger = MCLogger.getLogger(MCPatcherUtils.CUSTOM_COLORS);
    private static final BiomeAPI instance = new BiomeAPI();

    public static final int WORLD_MAX_HEIGHT = 255;
    public static final boolean isColorHeightDependent = instance.isColorHeightDependent();

    private static boolean biomesLogged;
    private static BiomeGenBase lastBiome;
    private static int lastI;
    private static int lastK;

    BiomeAPI() {}

    public static void parseBiomeList(String list, BitSet bits) {
        logBiomes();
        if (MCPatcherUtils.isNullOrEmpty(list)) {
            return;
        }
        for (String s : list.split(list.contains(",") ? "\\s*,\\s*" : "\\s+")) {
            BiomeGenBase biome = findBiomeByName(s);
            if (biome != null) {
                bits.set(biome.biomeID);
            }
        }
    }

    public static BitSet getHeightListProperty(PropertiesFile properties, String suffix) {
        int minHeight = Math.max(properties.getInt("minHeight" + suffix, 0), 0);
        int maxHeight = Math.min(properties.getInt("maxHeight" + suffix, WORLD_MAX_HEIGHT), WORLD_MAX_HEIGHT);
        String heightStr = properties.getString("heights" + suffix, "");
        if (minHeight == 0 && maxHeight == WORLD_MAX_HEIGHT && heightStr.isEmpty()) {
            return null;
        } else {
            BitSet heightBits = new BitSet(WORLD_MAX_HEIGHT + 1);
            if (heightStr.isEmpty()) {
                heightStr = minHeight + "-" + maxHeight;
            }
            for (int i : MCPatcherUtils.parseIntegerList(heightStr, 0, WORLD_MAX_HEIGHT)) {
                heightBits.set(i);
            }
            return heightBits;
        }
    }

    public static BiomeGenBase findBiomeByName(String name) {
        logBiomes();
        if (name == null) {
            return null;
        }
        name = name.replace(" ", "");
        if (name.isEmpty()) {
            return null;
        }
        for (BiomeGenBase biome : BiomeGenBase.getBiomeGenArray()) {
            if (biome == null || biome.biomeName == null) {
                continue;
            }
            if (name.equalsIgnoreCase(biome.biomeName) || name.equalsIgnoreCase(biome.biomeName.replace(" ", ""))) {
                if (biome.biomeID >= 0 && biome.biomeID < BiomeGenBase.getBiomeGenArray().length) {
                    return biome;
                }
            }
        }
        return null;
    }

    public static IBlockAccess getWorld() {
        return Minecraft.getMinecraft().theWorld;
    }

    public static int getBiomeIDAt(IBlockAccess blockAccess, int i, int j, int k) {
        BiomeGenBase biome = getBiomeGenAt(blockAccess, i, j, k);
        return biome == null ? BiomeGenBase.getBiomeGenArray().length : biome.biomeID;
    }

    public static BiomeGenBase getBiomeGenAt(IBlockAccess blockAccess, int i, int j, int k) {
        if (lastBiome == null || i != lastI || k != lastK) {
            lastI = i;
            lastK = k;
            lastBiome = instance.getBiomeGenAt_Impl(blockAccess, i, j, k);
        }
        return lastBiome;
    }

    public static float getTemperature(BiomeGenBase biome, int i, int j, int k) {
        return instance.getTemperaturef_Impl(biome, i, j, k);
    }

    public static float getTemperature(IBlockAccess blockAccess, int i, int j, int k) {
        return getTemperature(getBiomeGenAt(blockAccess, i, j, k), i, j, k);
    }

    public static float getRainfall(BiomeGenBase biome, int i, int j, int k) {
        return biome.getFloatRainfall();
    }

    public static float getRainfall(IBlockAccess blockAccess, int i, int j, int k) {
        return getRainfall(getBiomeGenAt(blockAccess, i, j, k), i, j, k);
    }

    public static int getGrassColor(BiomeGenBase biome, int i, int j, int k) {
        return instance.getGrassColor_Impl(biome, i, j, k);
    }

    public static int getFoliageColor(BiomeGenBase biome, int i, int j, int k) {
        return instance.getFoliageColor_Impl(biome, i, j, k);
    }

    public static int getWaterColorMultiplier(BiomeGenBase biome) {
        return biome == null ? 0xffffff : biome.getWaterColorMultiplier();
    }

    private static void logBiomes() {
        if (!biomesLogged) {
            biomesLogged = true;
            for (int i = 0; i < BiomeGenBase.getBiomeGenArray().length; i++) {
                BiomeGenBase biome = BiomeGenBase.getBiomeGenArray()[i];
                if (biome != null) {
                    int x = (int) (255.0f * (1.0f - biome.temperature));
                    int y = (int) (255.0f * (1.0f - biome.temperature * biome.rainfall));
                    logger.config(
                        "setupBiome #%d id=%d \"%s\" %06x (%d,%d)",
                        i,
                        biome.biomeID,
                        biome.biomeName,
                        biome.waterColorMultiplier,
                        x,
                        y);
                }
            }
        }
    }

    protected BiomeGenBase getBiomeGenAt_Impl(IBlockAccess blockAccess, int i, int j, int k) {
        return blockAccess.getBiomeGenForCoords(i, k);
    }

    protected float getTemperaturef_Impl(BiomeGenBase biome, int i, int j, int k) {
        return biome.getFloatTemperature(i, j, k);
    }

    protected int getGrassColor_Impl(BiomeGenBase biome, int i, int j, int k) {
        return biome.getBiomeGrassColor(i, j, k);
    }

    protected int getFoliageColor_Impl(BiomeGenBase biome, int i, int j, int k) {
        return biome.getBiomeFoliageColor(i, j, k);
    }

    protected boolean isColorHeightDependent() {
        return true;
    }
}
