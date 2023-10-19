package mist475.mcpatcherforge.asm.mappings;

public class NamerSrg extends Namer {

    public void setNames() {
        setNamesSrg();
    }

    public void setNamesSrg() {
        Names.renderBlocks_ = c("net/minecraft/client/renderer/RenderBlocks");
        Names.block_ = c("net/minecraft/block/Block");
        Names.iBlockAccess_ = c("net/minecraft/world/IBlockAccess");

        Names.renderBlocks_colorBlueTopRight = f(Names.renderBlocks_, "field_147833_aA", "F");
        Names.renderBlocks_blockAccess = f(Names.renderBlocks_, "field_147845_a", Names.iBlockAccess_.desc);
        Names.renderBlocks_aoBrightnessXYZNNN = f(Names.renderBlocks_, "field_147832_R", "I");
        Names.renderBlocks_aoBrightnessYZNN = f(Names.renderBlocks_, "field_147825_U", "I");

        Names.renderBlocks_renderStandardBlockWithAmbientOcclusion = m(
            Names.renderBlocks_,
            "func_147751_a",
            "(" + Names.block_.desc + "IIIFFF)Z");
    }
}
