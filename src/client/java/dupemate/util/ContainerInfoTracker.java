package dupemate.util;

import net.minecraft.util.math.BlockPos;

public class ContainerInfoTracker {
    private static BlockPos currentContainerPos = null;
    private static String currentContainerId = null;
    private static int currentItemCount = 0;
    private static String currentContainerType = null;
    private static boolean isActive = false;

    public static void setContainerInfo(BlockPos pos, String id, int itemCount, String type) {
        currentContainerPos = pos;
        currentContainerId = id;
        currentItemCount = itemCount;
        currentContainerType = type;
        isActive = true;
    }

    public static void clear() {
        currentContainerPos = null;
        currentContainerId = null;
        currentItemCount = 0;
        currentContainerType = null;
        isActive = false;
    }

    public static boolean isActive() {
        return isActive;
    }

    public static BlockPos getPosition() {
        return currentContainerPos;
    }

    public static String getId() {
        return currentContainerId;
    }

    public static int getItemCount() {
        return currentItemCount;
    }

    public static String getType() {
        return currentContainerType;
    }
}
