package mist475.mcpatcherforge.asm.mappings;

public class NamerObf extends Namer {

    public void setNames() {
        setNames1_7_10();
    }

    public void setNames1_7_10() {
        Names.renderBlocks_ = c("blm");
        Names.block_ = c("aji");
        Names.iBlockAccess_ = c("ahl");

        Names.renderBlocks_colorBlueTopRight = f(Names.renderBlocks_, "aB", "F");
        Names.renderBlocks_blockAccess = f(Names.renderBlocks_, "a", Names.iBlockAccess_.desc);

        Names.renderBlocks_renderStandardBlockWithAmbientOcclusion = m(
            Names.renderBlocks_,
            "a",
            "(" + Names.block_.desc + "IIIFFF)Z");
    }
}
