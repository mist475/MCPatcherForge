package com.prupe.mcpatcher.mal.resource;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.FallbackResourceManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.util.ResourceLocation;

import com.prupe.mcpatcher.MCLogger;
import com.prupe.mcpatcher.MCPatcherUtils;

import mist475.mcpatcherforge.mixins.interfaces.AbstractTextureExpansion;

public class TexturePackAPI {

    private static final MCLogger logger = MCLogger.getLogger("Texture Pack");

    public static final String DEFAULT_NAMESPACE = "minecraft";

    private static final TexturePackAPI instance = new TexturePackAPI();

    public static final String MCPATCHER_SUBDIR = "mcpatcher/";
    public static final ResourceLocation ITEMS_PNG = new ResourceLocation("textures/atlas/items.png");
    public static final ResourceLocation BLOCKS_PNG = new ResourceLocation("textures/atlas/blocks.png");

    private static final String ASSETS = "assets/";

    public static boolean isInitialized() {
        return instance != null && instance.isInitialized_Impl();
    }

    public static void scheduleTexturePackRefresh() {
        Minecraft.getMinecraft()
            .scheduleResourcesRefresh();
    }

    public static List<IResourcePack> getResourcePacks(String namespace) {
        List<IResourcePack> list = new ArrayList<>();
        instance.getResourcePacks_Impl(namespace, list);
        return list;
    }

    public static Set<String> getNamespaces() {
        Set<String> set = new HashSet<>();
        set.add(DEFAULT_NAMESPACE);
        instance.getNamespaces_Impl(set);
        return set;
    }

    public static boolean isDefaultTexturePack() {
        return instance.isDefaultResourcePack_Impl();
    }

    public static InputStream getInputStream(ResourceLocation resource) {
        try {
            if (resource instanceof ResourceLocationWithSource) {
                try {
                    return instance.getInputStream_Impl(((ResourceLocationWithSource) resource).getSource(), resource);
                } catch (IOException e) {}
            }
            return resource == null ? null : instance.getInputStream_Impl(resource);
        } catch (IOException e) {
            return null;
        }
    }

    public static boolean hasResource(ResourceLocation resource) {
        if (resource == null) {
            return false;
        } else if (resource.getResourcePath()
            .endsWith(".png")) {
                return getImage(resource) != null;
            } else if (resource.getResourcePath()
                .endsWith(".properties")) {
                    return getProperties(resource) != null;
                } else {
                    InputStream is = getInputStream(resource);
                    MCPatcherUtils.close(is);
                    return is != null;
                }
    }

    public static boolean hasCustomResource(ResourceLocation resource) {
        InputStream jar = null;
        InputStream pack = null;
        try {
            String path = instance.getFullPath_Impl(resource);
            pack = getInputStream(resource);
            if (pack == null) {
                return false;
            }
            jar = Minecraft.class.getResourceAsStream(path);
            if (jar == null) {
                return true;
            }
            byte[] buffer1 = new byte[4096];
            byte[] buffer2 = new byte[4096];
            int read1;
            int read2;
            while ((read1 = pack.read(buffer1)) > 0) {
                read2 = jar.read(buffer2);
                if (read1 != read2) {
                    return true;
                }
                for (int i = 0; i < read1; i++) {
                    if (buffer1[i] != buffer2[i]) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            MCPatcherUtils.close(jar);
            MCPatcherUtils.close(pack);
        }
        return false;
    }

    public static BufferedImage getImage(ResourceLocation resource) {
        if (resource == null) {
            return null;
        }
        InputStream input = getInputStream(resource);
        BufferedImage image = null;
        if (input != null) {
            try {
                image = ImageIO.read(input);
            } catch (IOException e) {
                logger.error("could not read %s", resource);
                e.printStackTrace();
            } finally {
                MCPatcherUtils.close(input);
            }
        }
        return image;
    }

    public static Properties getProperties(ResourceLocation resource) {
        Properties properties = new Properties();
        if (getProperties(resource, properties)) {
            return properties;
        } else {
            return null;
        }
    }

    public static boolean getProperties(ResourceLocation resource, Properties properties) {
        if (properties != null) {
            InputStream input = getInputStream(resource);
            try {
                if (input != null) {
                    properties.load(input);
                    return true;
                }
            } catch (IOException e) {
                logger.error("could not read %s", resource);
                e.printStackTrace();
            } finally {
                MCPatcherUtils.close(input);
            }
        }
        return false;
    }

    public static ResourceLocation transformResourceLocation(ResourceLocation resource, String oldExt, String newExt) {
        return new ResourceLocation(
            resource.getResourceDomain(),
            resource.getResourcePath()
                .replaceFirst(Pattern.quote(oldExt) + "$", newExt));
    }

    public static ResourceLocation parsePath(String path) {
        return MCPatcherUtils.isNullOrEmpty(path) ? null
            : instance.parsePath_Impl(path.replace(File.separatorChar, '/'));
    }

    public static ResourceLocation parseResourceLocation(String path) {
        return parseResourceLocation(new ResourceLocation(DEFAULT_NAMESPACE, "a"), path);
    }

    public static ResourceLocation parseResourceLocation(ResourceLocation baseResource, String path) {
        return MCPatcherUtils.isNullOrEmpty(path) ? null : instance.parseResourceLocation_Impl(baseResource, path);
    }

    public static ResourceLocation newMCPatcherResourceLocation(String v1Path, String v2Path) {
        return new ResourceLocation(MCPATCHER_SUBDIR + v2Path.replaceFirst("^/+", ""));
    }

    public static ResourceLocation newMCPatcherResourceLocation(String path) {
        return newMCPatcherResourceLocation(path, path);
    }

    public static int getTextureIfLoaded(ResourceLocation resource) {
        return resource == null ? -1 : instance.getTextureIfLoaded_Impl(resource);
    }

    public static boolean isTextureLoaded(ResourceLocation resource) {
        return getTextureIfLoaded(resource) >= 0;
    }

    public static ITextureObject getTextureObject(ResourceLocation resource) {
        return Minecraft.getMinecraft()
            .getTextureManager()
            .getTexture(resource);
    }

    public static void bindTexture(ResourceLocation resource) {
        if (resource != null) {
            instance.bindTexture_Impl(resource);
        }
    }

    public static void unloadTexture(ResourceLocation resource) {
        if (resource != null) {
            instance.unloadTexture_Impl(resource);
        }
    }

    public static void flushUnusedTextures() {
        instance.flushUnusedTextures_Impl();
    };

    private IResourceManager getResourceManager() {
        return Minecraft.getMinecraft()
            .getResourceManager();
    }

    protected boolean isInitialized_Impl() {
        return getResourceManager() != null;
    }

    protected void getResourcePacks_Impl(String namespace, List<IResourcePack> resourcePacks) {
        IResourceManager resourceManager = getResourceManager();
        if (resourceManager instanceof SimpleReloadableResourceManager) {
            Set<Map.Entry<String, FallbackResourceManager>> entrySet = ((SimpleReloadableResourceManager) resourceManager).domainResourceManagers
                .entrySet();
            for (Map.Entry<String, FallbackResourceManager> entry : entrySet) {
                if (namespace == null || namespace.equals(entry.getKey())) {
                    List<IResourcePack> packs = entry.getValue().resourcePacks;
                    if (packs != null) {
                        resourcePacks.removeAll(packs);
                        resourcePacks.addAll(packs);
                    }
                }
            }
        }
    }

    protected void getNamespaces_Impl(Set<String> namespaces) {
        IResourceManager resourceManager = getResourceManager();
        if (resourceManager instanceof SimpleReloadableResourceManager) {
            namespaces.addAll(((SimpleReloadableResourceManager) resourceManager).domainResourceManagers.keySet());
        }
    }

    protected InputStream getInputStream_Impl(ResourceLocation resource) throws IOException {
        return Minecraft.getMinecraft()
            .getResourceManager()
            .getResource(resource)
            .getInputStream();
    }

    protected InputStream getInputStream_Impl(IResourcePack resourcePack, ResourceLocation resource)
        throws IOException {
        return resourcePack.getInputStream(resource);
    }

    protected String getFullPath_Impl(ResourceLocation resource) {
        return ASSETS + resource.getResourceDomain() + "/" + resource.getResourcePath();
    }

    protected ResourceLocation parsePath_Impl(String path) {
        if (path.startsWith(ASSETS)) {
            path = path.substring(ASSETS.length());
            int slash = path.indexOf('/');
            if (slash > 0 && slash + 1 < path.length()) {
                return new ResourceLocation(path.substring(0, slash), path.substring(slash + 1));
            }
        }
        return null;
    }

    protected ResourceLocation parseResourceLocation_Impl(ResourceLocation baseResource, String path) {
        boolean absolute = false;
        if (path.startsWith("%blur%")) {
            path = path.substring(6);
        }
        if (path.startsWith("%clamp%")) {
            path = path.substring(7);
        }
        if (path.startsWith("/")) {
            path = path.substring(1);
            absolute = true;
        }
        if (path.startsWith("assets/minecraft/")) {
            path = path.substring(17);
            absolute = true;
        }
        // Absolute path, including namespace:
        // namespace:path/filename -> assets/namespace/path/filename
        int colon = path.indexOf(':');
        if (colon >= 0) {
            return new ResourceLocation(path.substring(0, colon), path.substring(colon + 1));
        }
        ResourceLocation resource;
        if (path.startsWith("~/")) {
            // Relative to namespace mcpatcher dir:
            // ~/path -> assets/(namespace of base file)/mcpatcher/path
            resource = new ResourceLocation(baseResource.getResourceDomain(), MCPATCHER_SUBDIR + path.substring(2));
        } else if (path.startsWith("./")) {
            // Relative to properties file:
            // ./path -> (dir of base file)/path
            resource = new ResourceLocation(
                baseResource.getResourceDomain(),
                baseResource.getResourcePath()
                    .replaceFirst("[^/]+$", "") + path.substring(2));
        } else if (!absolute && !path.contains("/")) {
            // Relative to properties file:
            // filename -> (dir of base file)/filename
            resource = new ResourceLocation(
                baseResource.getResourceDomain(),
                baseResource.getResourcePath()
                    .replaceFirst("[^/]+$", "") + path);
        } else {
            // Absolute path, w/o namespace:
            // path/filename -> assets/(namespace of base file)/path/filename
            resource = new ResourceLocation(baseResource.getResourceDomain(), path);
        }
        if (baseResource instanceof ResourceLocationWithSource) {
            resource = new ResourceLocationWithSource(
                ((ResourceLocationWithSource) baseResource).getSource(),
                resource);
        }
        return resource;
    }

    protected boolean isDefaultResourcePack_Impl() {
        return getResourcePacks(DEFAULT_NAMESPACE).size() <= 1;
    }

    protected int getTextureIfLoaded_Impl(ResourceLocation resource) {
        ITextureObject texture = Minecraft.getMinecraft()
            .getTextureManager()
            .getTexture(resource);
        return texture instanceof AbstractTexture ? texture.getGlTextureId() : -1;
    }

    protected void bindTexture_Impl(ResourceLocation resource) {
        Minecraft.getMinecraft()
            .getTextureManager()
            .bindTexture(resource);
    }

    protected void unloadTexture_Impl(ResourceLocation resource) {
        TextureManager textureManager = Minecraft.getMinecraft()
            .getTextureManager();
        ITextureObject texture = textureManager.getTexture(resource);
        if (texture != null && !(texture instanceof TextureMap) && !(texture instanceof DynamicTexture)) {
            if (texture instanceof AbstractTexture) {
                ((AbstractTextureExpansion) texture).unloadGLTexture();
            }
            logger.finer("unloading texture %s", resource);
            textureManager.mapTextureObjects.remove(resource);
        }
    }

    protected void flushUnusedTextures_Impl() {
        TextureManager textureManager = Minecraft.getMinecraft()
            .getTextureManager();
        if (textureManager != null) {
            Set<ResourceLocation> texturesToUnload = new HashSet<>();
            Set<Map.Entry<ResourceLocation, ITextureObject>> entrySet = textureManager.mapTextureObjects.entrySet();
            for (Map.Entry<ResourceLocation, ITextureObject> entry : entrySet) {
                ResourceLocation resource = entry.getKey();
                ITextureObject texture = entry.getValue();
                if (texture instanceof SimpleTexture && !(texture instanceof ThreadDownloadImageData)
                    && !TexturePackAPI.hasResource(resource)) {
                    texturesToUnload.add(resource);
                }
            }
            for (ResourceLocation resource : texturesToUnload) {
                unloadTexture(resource);
            }
        }
    }
}
