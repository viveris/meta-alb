require recipes-kernel/linux/linux-s32.inc

SRC_URI = "git://source.codeaurora.org/external/autobsps32/linux;protocol=https;branch=alb/master"
SRCREV = "ab652e2446b51e501f6a24a75b90c59943455e85"

DELTA_KERNEL_DEFCONFIG_append_s32v234pcie += " \
    blueboxconfig_s32v234pcie_4.14 \
"
DELTA_KERNEL_DEFCONFIG_append_s32v234pciebcm += " \
    blueboxconfig_s32v234pcie_4.14 \
"
DELTA_KERNEL_DEFCONFIG_append_s32v234bbmini += " \
    blueboxconfig_s32v234pcie_4.14 \
"

# LXC configuration
DELTA_KERNEL_DEFCONFIG_append = " ${@bb.utils.contains('DISTRO_FEATURES', 'lxc', 'containers_4.1.26.config', '', d)}"

# VIRTIO
DELTA_KERNEL_DEFCONFIG_append = " ${@bb.utils.contains('DISTRO_FEATURES', 'virtio', 'virtio', '', d)}"

# Docker configuration
DELTA_KERNEL_DEFCONFIG_append += "${@bb.utils.contains('DISTRO_FEATURES', 'docker', 'docker.config', '', d)}"

# GPU configuration
DELTA_KERNEL_DEFCONFIG_append += "${@bb.utils.contains('DISTRO_FEATURES', 'gpu', 'gpu.config', '', d)}"

SRC_URI += "\
    file://build/blueboxconfig_s32v234pcie_4.14 \
    file://build/containers_4.1.26.config \
    file://build/docker.config \
    file://build/virtio \
    file://build/gpu.config \
"

require vnet-s32.inc

SRC_URI_append_s32v234bbmini += " ${@bb.utils.contains('DISTRO_FEATURES', 'pcie-demos-support', 'file://0001-pcie-s32v-kernel-support-for-pcie-demos-icc-and-user-sp.patch', '', d)}"

# Enable PCIe support for the EVBs also
DELTA_KERNEL_DEFCONFIG_append_s32v234evb += " ${@bb.utils.contains('DISTRO_FEATURES', 'pcie-demos-support', 'blueboxconfig_s32v234pcie_4.14', '', d)}"
DELTA_KERNEL_DEFCONFIG_append_s32v234evb28899 += " ${@bb.utils.contains('DISTRO_FEATURES', 'pcie-demos-support', 'blueboxconfig_s32v234pcie_4.14', '', d)}"
