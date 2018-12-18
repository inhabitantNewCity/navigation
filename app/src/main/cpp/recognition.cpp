#include <string.h>
#include <jni.h>
#include "Thread_recognition_area.h"


JNIEXPORT jint JNICALL
Java_com_example_vlad_navigation_calculation_machineVisionSystem_RecognitionFacedJNI_recognizeFromJNI(
        JNIEnv *env,
        jobject thiz, jbyteArray data, jint width, jint high) {
#if defined(__arm__)
#if defined(__ARM_ARCH_7A__)
#if defined(__ARM_NEON__)
#if defined(__ARM_PCS_VFP)
#define ABI "armeabi-v7a/NEON (hard-float)"
#else
#define ABI "armeabi-v7a/NEON"
#endif
#else
#if defined(__ARM_PCS_VFP)
#define ABI "armeabi-v7a (hard-float)"
#else
#define ABI "armeabi-v7a"
#endif
#endif
#else
#define ABI "armeabi"
#endif
#elif defined(__i386__)
#define ABI "x86"
#elif defined(__x86_64__)
#define ABI "x86_64"
#elif defined(__mips64)  /* mips64el-* toolchain defines __mips__ too */
#define ABI "mips64"
#elif defined(__mips__)
#define ABI "mips"
#elif defined(__aarch64__)
#define ABI "arm64-v8a"
#else
#define ABI "unknown"
#endif
    jboolean isCopy;
    jbyte *cData = env->GetByteArrayElements(data, &isCopy);
    env->ReleaseByteArrayElements(data, cData, JNI_ABORT);
    unsigned char *img = (unsigned char *) data;
    int c_width = (int) width;
    int c_high = (int) high;
    return 1;//Recognition_area(img, c_width, c_high);
}
