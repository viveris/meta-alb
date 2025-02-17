require ppa.inc

SRC_URI = "git://github.com/nxp-qoriq/ppa-generic.git;nobranch=1 \
"

SRC_URI += "file://0001-build-disable-parallel-make-to-avoid-compile-time-ra.patch \
"

SRCREV = "e5641434f00d75634a285341d810df4261daf5de"

PPA_PATH:ls1012a = "ls1012"
PPA_PATH:ls1043a = "ls1043"
PPA_PATH:ls1046a = "ls1046"
PPA_PATH:ls2088a = "ls2088"
PPA_PATH:ls1088a = "ls1088"

do_compile () {
    export ARMV8_TOOLS_DIR="${STAGING_BINDIR_TOOLCHAIN}"
    export ARMV8_TOOLS_PREFIX="${TARGET_PREFIX}"
    export CROSS_COMPILE="${WRAP_TARGET_PREFIX}"
    cd ${S}/ppa
    if [ ${MACHINE} = ls1012afrdm ];then
        ./build  frdm-fit ${PPA_PATH}
    else
        ./build  rdb-fit ${PPA_PATH}
    fi
    cd ${S}
}
do_install() {
    install -d ${D}/boot/
    install ${S}/ppa/soc-${PPA_PATH}/build/obj/ppa.itb ${D}/boot/${PPA_NAME}.itb
    ln -sfT ${PPA_NAME}.itb ${D}/boot/${PN}.itb
}

do_deploy(){
    install -d ${DEPLOYDIR}
    install ${S}/ppa/soc-${PPA_PATH}/build/obj/ppa.itb ${DEPLOYDIR}/${PPA_NAME}.itb
    ln -sfT ${PPA_NAME}.itb ${DEPLOYDIR}/${PN}.itb
}

COMPATIBLE_MACHINE = "(ls1043a|ls1046a|ls2088a|ls1012a|ls1088a)"
