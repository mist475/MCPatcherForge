package mist475.mcpatcherforge.mixins;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.prupe.mcpatcher.Config;
import com.prupe.mcpatcher.MCPatcherUtils;

import cpw.mods.fml.relauncher.FMLLaunchHandler;

// Adapted from Hodgepodge
public enum Mixins {

    BASE_MOD(new Builder("Base mod (can't be disabled, sorry)").setSide(Side.CLIENT)
        .setPhase(Phase.EARLY)
        .setApplyIf(() -> true)
        .addTargetedMod(TargetedMod.VANILLA)
        .addMixinClasses(
            "base.MixinBlockGrass",
            "base.MixinBlockMycelium",

            "base.MixinAbstractTexture",
            "base.MixinTextureAtlasSprite",

            "base.MixinSimpleReloadableResourceManager",

            "base.MixinMinecraft",

            "renderpass.MixinEntityRenderer",
            "renderpass.MixinRenderBlocks",
            "renderpass.MixinRenderGlobal",
            "renderpass.MixinWorldRenderer")

    ),

    CUSTOM_COLOURS(new Builder("Custom colors").setSide(Side.CLIENT)
        .setPhase(Phase.EARLY)
        .setApplyIf(() -> Config.getBoolean(MCPatcherUtils.CUSTOM_COLORS, "enabled", true))
        .addTargetedMod(TargetedMod.VANILLA)
        .addMixinClasses(
            addPrefix(
                "cc.",
                "block.material.MixinMapColor",

                "block.MixinBlock",
                "block.MixinBlockDoublePlant",
                "block.MixinBlockGrass",
                "block.MixinBlockLeaves",
                "block.MixinBlockLilyPad",
                "block.MixinBlockLiquid",
                "block.MixinBlockOldLeaf",
                "block.MixinBlockRedstoneWire",
                "block.MixinBlockReed",
                "block.MixinBlockStem",
                "block.MixinBlockTallGrass",
                "block.MixinBlockVine",

                "client.particle.MixinEntityAuraFX",
                "client.particle.MixinEntityBubbleFX",
                "client.particle.MixinEntityDropParticleFX",
                "client.particle.MixinEntityPortalFX",
                "client.particle.MixinEntityRainFX",
                "client.particle.MixinEntityRedDustFX",
                "client.particle.MixinEntitySplashFX",
                "client.particle.MixinEntitySuspendFX",

                "client.renderer.entity.MixinRenderWolf",
                "client.renderer.entity.MixinRenderXPOrb",

                "client.renderer.tileentity.MixinTileEntitySignRenderer",

                "client.renderer.MixinEntityRenderer",
                "client.renderer.MixinItemRenderer",
                "client.renderer.MixinRenderBlocks",
                "client.renderer.MixinRenderGlobal",

                "entity.MixinEntityList",

                "item.crafting.MixinRecipesArmorDyes",

                "item.MixinItemArmor",
                "item.MixinItemBlock",
                "item.MixinItemMonsterPlacer",

                "potion.MixinPotion",
                "potion.MixinPotionHelper",

                "world.MixinWorld",
                "world.MixinWorldProvider",
                "world.MixinWorldProviderEnd",
                "world.MixinWorldProviderHell"))),

    CUSTOM_ITEM_TEXTURES(new Builder("Custom Item Textures").setSide(Side.CLIENT)
        .setPhase(Phase.EARLY)
        .setApplyIf(() -> Config.getBoolean(MCPatcherUtils.CUSTOM_ITEM_TEXTURES, "enabled", true))
        .addTargetedMod(TargetedMod.VANILLA)
        .addMixinClasses(
            addPrefix(
                "cit.",
                "client.renderer.entity.MixinRenderBiped",
                "client.renderer.entity.MixinRenderEntityLiving",
                "client.renderer.entity.MixinRenderItem",
                "client.renderer.entity.MixinRenderPlayer",
                "client.renderer.entity.MixinRenderSnowball",
                "client.renderer.MixinItemRenderer",
                "item.MixinItem",
                "nbt.MixinNBTTagCompound",
                "nbt.MixinNBTTagList"))),

    CONNECTED_TEXTURES(new Builder("Connected Textures").setSide(Side.CLIENT)
        .setPhase(Phase.EARLY)
        .setApplyIf(() -> Config.getBoolean(MCPatcherUtils.CONNECTED_TEXTURES, "enabled", true))
        .addTargetedMod(TargetedMod.VANILLA)
        .addMixinClasses("ctm.MixinRenderBlocks")),

    EXTENDED_HD(new Builder("Extended hd").setSide(Side.CLIENT)
        .setPhase(Phase.EARLY)
        .setApplyIf(() -> Config.getBoolean(MCPatcherUtils.EXTENDED_HD, "enabled", true))
        .addTargetedMod(TargetedMod.VANILLA)
        .addMixinClasses(
            addPrefix("hd.", "MixinFontRenderer", "MixinTextureClock", "MixinTextureCompass", "MixinTextureManager"))),

    RANDOM_MOBS(new Builder("Random Mobs").setSide(Side.CLIENT)
        .setPhase(Phase.EARLY)
        .setApplyIf(() -> Config.getBoolean(MCPatcherUtils.RANDOM_MOBS, "enabled", true))
        .addTargetedMod(TargetedMod.VANILLA)
        .addMixinClasses(
            addPrefix(
                "mob.",
                "MixinRender",
                "MixinRenderEnderman",
                "MixinRenderFish",
                "MixinRenderLiving",
                "MixinRenderMooshroom",
                "MixinRenderSheep",
                "MixinRenderSnowMan",
                "MixinRenderSpider",
                "MixinRenderWolf",
                "MixinEntityLivingBase"))),

    SKY(new Builder("Sky").setSide(Side.CLIENT)
        .setPhase(Phase.EARLY)
        .setApplyIf(() -> Config.getBoolean(MCPatcherUtils.BETTER_SKIES, "enabled", true))
        .addTargetedMod(TargetedMod.VANILLA)
        .addMixinClasses(addPrefix("sky.", "MixinEffectRenderer", "MixinRenderGlobal"))),

    CC_NO_CTM(new Builder("Custom colors, no connected textures").setSide(Side.CLIENT)
        .setPhase(Phase.EARLY)
        .setApplyIf(
            () -> !Config.getBoolean(MCPatcherUtils.CONNECTED_TEXTURES, "enabled", true)
                && Config.getBoolean(MCPatcherUtils.CUSTOM_COLORS, "enabled", true))
        .addTargetedMod(TargetedMod.VANILLA)
        .addMixinClasses("cc_ctm.MixinRenderBlocksNoCTM")),

    CTM_AND_CC(new Builder("Connected textures and Custom Colors enabled").setSide(Side.CLIENT)
        .setPhase(Phase.EARLY)
        .setApplyIf(
            () -> Config.getBoolean(MCPatcherUtils.CONNECTED_TEXTURES, "enabled", true)
                && Config.getBoolean(MCPatcherUtils.CUSTOM_COLORS, "enabled", true))
        .addTargetedMod(TargetedMod.VANILLA)
        .addMixinClasses("ctm_cc.MixinRenderBlocks")),

    CTM_NO_CC(new Builder("Connected textures, no custom colours").setSide(Side.CLIENT)
        .setPhase(Phase.EARLY)
        .setApplyIf(
            () -> Config.getBoolean(MCPatcherUtils.CONNECTED_TEXTURES, "enabled", true)
                && !Config.getBoolean(MCPatcherUtils.CUSTOM_COLORS, "enabled", true))
        .addTargetedMod(TargetedMod.VANILLA)
        .addMixinClasses("ctm_cc.MixinRenderBlocksNoCC")),

    CTM_OR_CC(new Builder("Connected textures or Custom Colors enabled").setSide(Side.CLIENT)
        .setPhase(Phase.EARLY)
        .setApplyIf(
            () -> Config.getBoolean(MCPatcherUtils.CONNECTED_TEXTURES, "enabled", true)
                || Config.getBoolean(MCPatcherUtils.CUSTOM_COLORS, "enabled", true))
        .addTargetedMod(TargetedMod.VANILLA)
        .addMixinClasses("ctm_cc.MixinTextureMap")),

    ;

    public final String name;
    public final List<String> mixinClasses;
    private final Supplier<Boolean> applyIf;
    public final Phase phase;
    private final Side side;
    public final List<TargetedMod> targetedMods;
    public final List<TargetedMod> excludedMods;

    private static class Builder {

        private final String name;
        private final List<String> mixinClasses = new ArrayList<>();
        private Supplier<Boolean> applyIf;
        private Side side = Side.BOTH;
        private Phase phase = Phase.LATE;
        private final List<TargetedMod> targetedMods = new ArrayList<>();
        private final List<TargetedMod> excludedMods = new ArrayList<>();

        public Builder(String name) {
            this.name = name;
        }

        public Builder addMixinClasses(String... mixinClasses) {
            this.mixinClasses.addAll(Arrays.asList(mixinClasses));
            return this;
        }

        public Builder setPhase(Phase phase) {
            this.phase = phase;
            return this;
        }

        public Builder setSide(Side side) {
            this.side = side;
            return this;
        }

        public Builder setApplyIf(Supplier<Boolean> applyIf) {
            this.applyIf = applyIf;
            return this;
        }

        public Builder addTargetedMod(TargetedMod mod) {
            this.targetedMods.add(mod);
            return this;
        }

        public Builder addExcludedMod(TargetedMod mod) {
            this.excludedMods.add(mod);
            return this;
        }
    }

    Mixins(Builder builder) {
        this.name = builder.name;
        this.mixinClasses = builder.mixinClasses;
        this.applyIf = builder.applyIf;
        this.side = builder.side;
        this.targetedMods = builder.targetedMods;
        this.excludedMods = builder.excludedMods;
        this.phase = builder.phase;
        if (this.targetedMods.isEmpty()) {
            throw new RuntimeException("No targeted mods specified for " + this.name);
        }
        if (this.applyIf == null) {
            throw new RuntimeException("No ApplyIf function specified for " + this.name);
        }
    }

    private boolean shouldLoadSide() {
        return (side == Side.BOTH || (side == Side.SERVER && FMLLaunchHandler.side()
            .isServer())
            || (side == Side.CLIENT && FMLLaunchHandler.side()
                .isClient()));
    }

    private boolean allModsLoaded(List<TargetedMod> targetedMods, Set<String> loadedCoreMods, Set<String> loadedMods) {
        if (targetedMods.isEmpty()) return false;

        for (TargetedMod target : targetedMods) {
            if (target == TargetedMod.VANILLA) continue;

            // Check coremod first
            if (!loadedCoreMods.isEmpty() && target.coreModClass != null
                && !loadedCoreMods.contains(target.coreModClass)) return false;
            else if (!loadedMods.isEmpty() && target.modId != null && !loadedMods.contains(target.modId)) return false;
        }

        return true;
    }

    private boolean noModsLoaded(List<TargetedMod> targetedMods, Set<String> loadedCoreMods, Set<String> loadedMods) {
        if (targetedMods.isEmpty()) return true;

        for (TargetedMod target : targetedMods) {
            if (target == TargetedMod.VANILLA) continue;

            // Check coremod first
            if (!loadedCoreMods.isEmpty() && target.coreModClass != null
                && loadedCoreMods.contains(target.coreModClass)) return false;
            else if (!loadedMods.isEmpty() && target.modId != null && loadedMods.contains(target.modId)) return false;
        }

        return true;
    }

    public boolean shouldLoad(Set<String> loadedCoreMods, Set<String> loadedMods) {
        return (shouldLoadSide() && applyIf.get()
            && allModsLoaded(targetedMods, loadedCoreMods, loadedMods)
            && noModsLoaded(excludedMods, loadedCoreMods, loadedMods));
    }

    @SuppressWarnings("SimplifyStreamApiCallChains")
    private static String[] addPrefix(String prefix, String... values) {
        return Arrays.stream(values)
            .map(s -> prefix + s)
            .collect(Collectors.toList())
            .toArray(new String[values.length]);
    }

    enum Side {
        BOTH,
        CLIENT,
        SERVER
    }

    public enum Phase {
        EARLY,
        LATE,
    }
}
