package mist475.mcpatcherforge.asm;

import static mist475.mcpatcherforge.asm.ASMUtils.matchesNodeSequence;

import net.minecraft.launchwrapper.IClassTransformer;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FrameNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LineNumberNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import mist475.mcpatcherforge.Tags;
import mist475.mcpatcherforge.asm.mappings.Names;

public class RenderBlocksTransformer implements IClassTransformer {

    public RenderBlocksTransformer() {}

    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if ("net.minecraft.client.renderer.RenderBlocks".equals(transformedName)) {
            return patchRenderBlocks(basicClass);
        }

        return basicClass;
    }

    private static byte[] patchRenderBlocks(byte[] basicClass) {
        final Logger logger = LogManager.getLogger(Tags.MODNAME);
        final ClassReader classReader = new ClassReader(basicClass);
        final ClassNode classNode = new ClassNode();
        classReader.accept(classNode, 0);

        Pair<InsnList, InsnList> firstIfWrapper = getFirstRenderBlocksIfWrapper();
        Pair<AbstractInsnNode[], AbstractInsnNode[]> firstIfSequences = getFirstRenderBlocksSequences();
        // our second sequence occurs more than once
        boolean firstWrappedIfHandled = false;

        for (MethodNode methodNode : classNode.methods) {
            if (isRenderStandardBlockWithAmbientOcclusion(methodNode)) {
                logger.debug("found renderStandardBlockWithAmbientOcclusion");
                for (AbstractInsnNode node : methodNode.instructions.toArray()) {

                    if (matchesNodeSequence(node, firstIfSequences.getLeft())) {
                        methodNode.instructions.insert(
                            node.getNext()
                                .getNext()
                                .getNext()
                                .getNext()
                                .getNext(),
                            firstIfWrapper.getLeft());
                    }

                    if (!firstWrappedIfHandled && matchesNodeSequence(node, firstIfSequences.getRight())) {
                        methodNode.instructions.remove(
                            node.getNext()
                                .getNext());
                        methodNode.instructions.insert(node.getNext(), firstIfWrapper.getRight());
                        firstWrappedIfHandled = true;
                    }

                }
            }
        }
        final ClassWriter classWriter = new ClassWriter(0);
        classNode.accept(classWriter);
        return classWriter.toByteArray();
    }

    private static Pair<InsnList, InsnList> getFirstRenderBlocksIfWrapper() {
        final InsnList firstIfList = new InsnList();
        firstIfList.add(new VarInsnNode(Opcodes.ALOAD, 0));
        firstIfList.add(new VarInsnNode(Opcodes.ALOAD, 1));
        firstIfList.add(new VarInsnNode(Opcodes.ALOAD, 0));
        firstIfList.add(
            new FieldInsnNode(
                Opcodes.GETFIELD,
                Names.renderBlocks_blockAccess.clas,
                Names.renderBlocks_blockAccess.name,
                Names.renderBlocks_blockAccess.desc));
        firstIfList.add(new VarInsnNode(Opcodes.ILOAD, 2));
        firstIfList.add(new VarInsnNode(Opcodes.ILOAD, 3));
        firstIfList.add(new VarInsnNode(Opcodes.ILOAD, 4));
        firstIfList.add(new InsnNode(Opcodes.ICONST_0));
        firstIfList.add(new VarInsnNode(Opcodes.FLOAD, 9));
        firstIfList.add(new VarInsnNode(Opcodes.FLOAD, 10));
        firstIfList.add(new VarInsnNode(Opcodes.FLOAD, 11));
        firstIfList.add(new VarInsnNode(Opcodes.FLOAD, 12));
        firstIfList.add(
            new MethodInsnNode(
                Opcodes.INVOKESTATIC,
                "com/prupe/mcpatcher/cc/ColorizeBlock",
                "setupBlockSmoothing",
                "(" + Names.renderBlocks_.desc + Names.block_.desc + Names.iBlockAccess_.desc + "IIIIFFFF)Z",
                false));
        Label label85 = new Label();
        firstIfList.add(new JumpInsnNode(Opcodes.IFNE, new LabelNode(label85)));
        Label label86 = new Label();
        firstIfList.add(new LabelNode(label86));
        firstIfList.add(new LineNumberNode(4601, new LabelNode(label86)));

        final InsnList firstIfEnd = new InsnList();
        firstIfEnd.add(new LabelNode(label85));
        firstIfEnd.add(new LineNumberNode(4626, new LabelNode(label85)));
        firstIfEnd.add(new FrameNode(Opcodes.F_SAME, 0, null, 0, null));

        return Pair.of(firstIfList, firstIfEnd);
    }

    private static Pair<AbstractInsnNode[], AbstractInsnNode[]> getFirstRenderBlocksSequences() {

        FieldInsnNode searchNode1 = new FieldInsnNode(
            Opcodes.GETFIELD,
            Names.renderBlocks_aoBrightnessXYZNNN.clas,
            Names.renderBlocks_aoBrightnessXYZNNN.name,
            Names.renderBlocks_aoBrightnessXYZNNN.desc);
        VarInsnNode searchNode2 = new VarInsnNode(Opcodes.ALOAD, 0);
        FieldInsnNode searchNode3 = new FieldInsnNode(
            Opcodes.GETFIELD,
            Names.renderBlocks_aoBrightnessYZNN.clas,
            Names.renderBlocks_aoBrightnessYZNN.name,
            Names.renderBlocks_aoBrightnessYZNN.desc);
        VarInsnNode searchNode4 = new VarInsnNode(Opcodes.ILOAD, 20);

        InsnNode searchNode5 = new InsnNode(Opcodes.FMUL);
        FieldInsnNode searchNode6 = new FieldInsnNode(
            Opcodes.PUTFIELD,
            Names.renderBlocks_colorBlueTopRight.clas,
            Names.renderBlocks_colorBlueTopRight.name,
            Names.renderBlocks_colorBlueTopRight.desc);

        return Pair.of(
            new AbstractInsnNode[] { searchNode1, searchNode2, searchNode3, searchNode4 },
            new AbstractInsnNode[] { searchNode5, searchNode6 });
    }

    private static boolean isRenderStandardBlockWithAmbientOcclusion(MethodNode methodNode) {
        return Names.renderBlocks_renderStandardBlockWithAmbientOcclusion
            .equalsNameDesc(methodNode.name, methodNode.desc);
    }
}
