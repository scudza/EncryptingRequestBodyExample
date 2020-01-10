#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_hmacrequestbody_HttpClient_clientSecret(
        JNIEnv *env,
        jobject) {
    std::string key = "my_secret_key";
    return env->NewStringUTF(key.c_str());
}
