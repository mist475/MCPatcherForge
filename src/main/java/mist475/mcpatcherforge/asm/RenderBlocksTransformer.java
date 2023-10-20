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

        AbstractInsnNode[] endIfSequence = getEndIfSequence();

        Pair<InsnList, InsnList> firstIfWrapper = getFirstRenderBlocksIfWrapper();
        AbstractInsnNode[] firstIfSequences = getFirstRenderBlocksSequences();
        // this sequence occurs more than once
        int ifEndsHandled = 0;

        Pair<InsnList, InsnList> secondIfWrapper = getSecondRenderBlocksIfWrapper();
        AbstractInsnNode[] secondIfSequences = getSecondRenderBlocksSequences();

        boolean secondWrappedIfHandled = false;

        for (MethodNode methodNode : classNode.methods) {
            if (isRenderStandardBlockWithAmbientOcclusion(methodNode)) {
                logger.debug("found renderStandardBlockWithAmbientOcclusion");
                for (AbstractInsnNode node : methodNode.instructions.toArray()) {

                    if (matchesNodeSequence(node, firstIfSequences)) {
                        methodNode.instructions.insert(
                            node.getNext()
                                .getNext()
                                .getNext()
                                .getNext()
                                .getNext(),
                            firstIfWrapper.getLeft());
                        continue;
                    }

                    if (ifEndsHandled == 0 && matchesNodeSequence(node, endIfSequence)) {
                        methodNode.instructions.remove(
                            node.getNext()
                                .getNext()
                                .getNext());
                        methodNode.instructions.insert(
                            node.getNext()
                                .getNext(),
                            firstIfWrapper.getRight());
                        ifEndsHandled++;
                        continue;
                    }

                    if (!secondWrappedIfHandled && matchesNodeSequence(node, secondIfSequences)) {
                        methodNode.instructions.insertBefore(node, secondIfWrapper.getLeft());
                        secondWrappedIfHandled = true;
                        continue;
                    }

                    if (ifEndsHandled == 1 && matchesNodeSequence(node, endIfSequence)) {
                        methodNode.instructions.remove(
                            node.getNext()
                                .getNext()
                                .getNext());
                        methodNode.instructions.insert(
                            node.getNext()
                                .getNext(),
                            secondIfWrapper.getRight());
                        ifEndsHandled++;
                        continue;
                    }
                }
            }
        }
        final ClassWriter classWriter = new ClassWriter(0);
        classNode.accept(classWriter);
        return classWriter.toByteArray();
    }

    private static Pair<InsnList, InsnList> getFirstRenderBlocksIfWrapper() {
        final InsnList ifStart = new InsnList();
        ifStart.add(new VarInsnNode(Opcodes.ALOAD, 0));
        ifStart.add(new VarInsnNode(Opcodes.ALOAD, 1));
        ifStart.add(new VarInsnNode(Opcodes.ALOAD, 0));
        ifStart.add(
            new FieldInsnNode(
                Opcodes.GETFIELD,
                Names.renderBlocks_blockAccess.clas,
                Names.renderBlocks_blockAccess.name,
                Names.renderBlocks_blockAccess.desc));
        ifStart.add(new VarInsnNode(Opcodes.ILOAD, 2));
        ifStart.add(new VarInsnNode(Opcodes.ILOAD, 3));
        ifStart.add(new VarInsnNode(Opcodes.ILOAD, 4));
        ifStart.add(new InsnNode(Opcodes.ICONST_0));
        ifStart.add(new VarInsnNode(Opcodes.FLOAD, 9));
        ifStart.add(new VarInsnNode(Opcodes.FLOAD, 10));
        ifStart.add(new VarInsnNode(Opcodes.FLOAD, 11));
        ifStart.add(new VarInsnNode(Opcodes.FLOAD, 12));
        ifStart.add(
            new MethodInsnNode(
                Opcodes.INVOKESTATIC,
                "com/prupe/mcpatcher/cc/ColorizeBlock",
                "setupBlockSmoothing",
                "(" + Names.renderBlocks_.desc + Names.block_.desc + Names.iBlockAccess_.desc + "IIIIFFFF)Z",
                false));
        Label label85 = new Label();
        ifStart.add(new JumpInsnNode(Opcodes.IFNE, new LabelNode(label85)));
        Label label86 = new Label();
        ifStart.add(new LabelNode(label86));
        ifStart.add(new LineNumberNode(4601, new LabelNode(label86)));

        final InsnList ifEnd = new InsnList();
        ifEnd.add(new LabelNode(label85));
        ifEnd.add(new LineNumberNode(4626, new LabelNode(label85)));
        ifEnd.add(new FrameNode(Opcodes.F_SAME, 0, null, 0, null));

        return Pair.of(ifStart, ifEnd);
    }

    private static AbstractInsnNode[] getFirstRenderBlocksSequences() {

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

        return new AbstractInsnNode[] { searchNode1, searchNode2, searchNode3, searchNode4 };
    }

    private static Pair<InsnList, InsnList> getSecondRenderBlocksIfWrapper() {
        final InsnList ifStart = new InsnList();
        ifStart.add(new VarInsnNode(Opcodes.ALOAD, 0));
        ifStart.add(new VarInsnNode(Opcodes.ALOAD, 1));
        ifStart.add(new VarInsnNode(Opcodes.ALOAD, 0));
        ifStart.add(
            new FieldInsnNode(
                Opcodes.GETFIELD,
                Names.renderBlocks_blockAccess.clas,
                Names.renderBlocks_blockAccess.name,
                Names.renderBlocks_blockAccess.desc));
        ifStart.add(new VarInsnNode(Opcodes.ILOAD, 2));
        ifStart.add(new VarInsnNode(Opcodes.ILOAD, 3));
        ifStart.add(new VarInsnNode(Opcodes.ILOAD, 4));
        ifStart.add(new InsnNode(Opcodes.ICONST_1));
        ifStart.add(new VarInsnNode(Opcodes.FLOAD, 9));
        ifStart.add(new VarInsnNode(Opcodes.FLOAD, 10));
        ifStart.add(new VarInsnNode(Opcodes.FLOAD, 11));
        ifStart.add(new VarInsnNode(Opcodes.FLOAD, 12));
        ifStart.add(
            new MethodInsnNode(
                Opcodes.INVOKESTATIC,
                "com/prupe/mcpatcher/cc/ColorizeBlock",
                "setupBlockSmoothing",
                "(" + Names.renderBlocks_.desc + Names.block_.desc + Names.iBlockAccess_.desc + "IIIIFFFF)Z",
                false));
        Label label176 = new Label();
        ifStart.add(new JumpInsnNode(Opcodes.IFNE, new LabelNode(label176)));
        Label label177 = new Label();
        ifStart.add(new LabelNode(label177));
        ifStart.add(new LineNumberNode(4715, new LabelNode(label177)));

        final InsnList ifEnd = new InsnList();
        ifEnd.add(new LabelNode(label176));
        ifEnd.add(new LineNumberNode(4730, new LabelNode(label176)));
        ifEnd.add(new FrameNode(Opcodes.F_APPEND, 1, new Object[] { Opcodes.FLOAT }, 0, null));

        return Pair.of(ifStart, ifEnd);
    }

    private static AbstractInsnNode[] getSecondRenderBlocksSequences() {
        VarInsnNode searchNode1 = new VarInsnNode(Opcodes.ALOAD, 0);
        VarInsnNode searchNode2 = new VarInsnNode(Opcodes.ALOAD, 0);
        VarInsnNode searchNode3 = new VarInsnNode(Opcodes.ALOAD, 0);
        VarInsnNode searchNode4 = new VarInsnNode(Opcodes.ALOAD, 0);
        VarInsnNode searchNode5 = new VarInsnNode(Opcodes.FLOAD, 5);
        InsnNode searchNode6 = new InsnNode(Opcodes.DUP_X1);

        return new AbstractInsnNode[] { searchNode1, searchNode2, searchNode3, searchNode4, searchNode5, searchNode6 };
    }

    private static AbstractInsnNode[] getEndIfSequence() {
        VarInsnNode searchNode1 = new VarInsnNode(Opcodes.FLOAD, 12);
        InsnNode searchNode2 = new InsnNode(Opcodes.FMUL);
        FieldInsnNode searchNode3 = new FieldInsnNode(
            Opcodes.PUTFIELD,
            Names.renderBlocks_colorBlueTopRight.clas,
            Names.renderBlocks_colorBlueTopRight.name,
            Names.renderBlocks_colorBlueTopRight.desc);
        return new AbstractInsnNode[] { searchNode1, searchNode2, searchNode3 };
    }

    private static boolean isRenderStandardBlockWithAmbientOcclusion(MethodNode methodNode) {
        return Names.renderBlocks_renderStandardBlockWithAmbientOcclusion
            .equalsNameDesc(methodNode.name, methodNode.desc);
    }
}
