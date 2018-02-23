/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package kr.co.eksys.nativelib;

public class EksysLibrary
{

    /* A native method that is implemented by the
     * 'hello-jni' native library, which is packaged
     * with this application.
     */
    public native String  stringFromJNI(byte[] bytes, String charset);

    /* This is another native method declaration that is *not*
     * implemented by 'hello-jni'. This is simply to show that
     * you can declare as many native methods in your Java code
     * as you want, their implementation is searched in the
     * currently loaded native libraries only the first time
     * you call them.
     *
     * Trying to call this function will result in a
     * java.lang.UnsatisfiedLinkError exception !
     */
    public native int connect(String ip, int port, int timeout);
    public native int connectEx(String ip, int port, int timeout, int connectType);
    public native void setPacketHeader(byte COMP, byte ENCR, byte POS, byte ERR, byte ID, byte TRID, int TRCODE, byte GETTYPE);
    public native void setStatus(String termid, String percode, String deptcode, String jikmucode, byte state_gps, byte auth, byte result);
    public native void setPosition(int date, int time, int lon, int lat, int speed, int elevation, int angle);
    public native int send(int handle, byte COMP, byte ENCR, byte POS, byte ERR, byte ID, byte TRID, int TRCODE, byte GETTYPE, String data);
    public native int sendUDP(String ip, int port, int timeout, String message);
    public native String receive(int handle);

    public native int close(int handle);

    /* this is used to load the 'hello-jni' library on application
     * startup. The library has already been unpacked into
     * /data/data/com.example.hellojni/lib/libhello-jni.so at
     * installation time by the package manager.
     */
    static {
        System.loadLibrary("eksyslibrary");
    }
}
