package kr.co.eksys.nativelib;

import android.util.Log;

/**
 * Created by brian on 2016-07-29.
 */
public class NetworkManager {

    public static final int TCP = 0;
    public static final int UDP = 1;

    private String host = null;
    private int port = 0;

    private int socketHandle = 0;

    private byte COMP = 1;
    private byte GETTYPE = 1;
    private byte ENCR = 1;
    private byte POS = 7;
    private byte ERR = 0;
    private byte ID = 0;
    private byte TRID = 'A';
    private int TRCODE = 1;

    private String termID = "001042579483";
    private String percode = "0000000";
    private String deptcode = "0000000000";
    private String jikmucode = "00000";

    private int date = 20061230, time = 123050, lon, lat, speed = 10, elevation = 30, angle = 15;


    static EksysLibrary lib = new EksysLibrary();

    public NetworkManager(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void setTR(char trid, int trcode) {
        this.TRID = (byte) trid;
        this.TRCODE = trcode;
    }

    public void setTerminalInfo(String termID, String percode, String deptcode, String jikmucode) {
        this.termID = termID;
        this.percode = percode;
        this.deptcode = deptcode;
        this.jikmucode = jikmucode;
    }

    public void setPosition(int date, int time, int lon, int lat, int speed, int elevation, int angle) {
        this.date = date;
        this.time = time;
        this.lon = lon;
        this.lat = lat;
        this.speed = speed;
        this.elevation = elevation;
        this.angle = angle;
    }

    public boolean connect(int timeout) throws EksysNetworkException {
        Log.d("EKSYS", "try to connect.... " + host + ", " + port);

        if (host == null || port == 0) {
            throw new EksysNetworkException(EksysNetworkException.RESULT_ERR_CONNECT);
        }

        if (percode == null || percode.length() != 7)
            throw new EksysNetworkException(EksysNetworkException.RESULT_ERR_PARMETER_INVAID);

        socketHandle = lib.connect(host, port, timeout);

        if (socketHandle == 0) {
            throw new EksysNetworkException(EksysNetworkException.RESULT_ERR_CONNECT);
        }
        Log.d("EKSYS", "connected: " + socketHandle);

        lib.setStatus(termID, percode, deptcode, jikmucode, (byte) 2, (byte) 1, (byte) 0);
        lib.setPosition(date, time, lon, lat, speed, elevation, angle);

        return true;
    }

    public int send(String params) throws EksysNetworkException {
        if (socketHandle == 0) {
            throw new EksysNetworkException(EksysNetworkException.RESULT_ERR_INVALIDSOCKET);
        }
//    public native int send(int socketHandle, byte COMP, byte ENCR, byte POS, byte ERR, byte ID, byte TRID, int TRCODE, byte GETTYPE, String id, String percode);

        String data = params;
        Log.d("NetworkManager", "send param: " + data);


        int ret = lib.send(socketHandle, COMP, ENCR, POS, ERR, ID, TRID, TRCODE, GETTYPE, data);
        if (ret < 0)
            throw new EksysNetworkException(EksysNetworkException.RESULT_ERR_SEND_TIMEOUT);
        return ret;
    }

    public int sendJson(String strJson) throws EksysNetworkException {
        if (socketHandle == 0) {
            throw new EksysNetworkException(EksysNetworkException.RESULT_ERR_INVALIDSOCKET);
        }
//    public native int send(int socketHandle, byte COMP, byte ENCR, byte POS, byte ERR, byte ID, byte TRID, int TRCODE, byte GETTYPE, String id, String percode);


        Log.d("EKSYS", "send param: " + strJson);

        int ret = lib.send(socketHandle, COMP, ENCR, POS, ERR, ID, TRID, TRCODE, GETTYPE, strJson);
        if (ret < 0)
            throw new EksysNetworkException(EksysNetworkException.RESULT_ERR_SEND_TIMEOUT);
        return ret;
    }

    public String receive() throws EksysNetworkException {
        if (socketHandle == 0) {
            throw new EksysNetworkException(EksysNetworkException.RESULT_ERR_INVALIDSOCKET);
        }

        String recv = lib.receive(socketHandle);
        if (recv == null)
            throw new EksysNetworkException(EksysNetworkException.RESULT_ERR_RECEIVE_TIMEOUT);

        return recv;
    }

    public int sendUDP(int timeout, String strJson) throws EksysNetworkException {

        lib.setPacketHeader(COMP, ENCR, POS, ERR, ID, TRID, TRCODE, GETTYPE);
        lib.setStatus(termID, percode, deptcode, jikmucode, (byte) 2, (byte) 1, (byte) 0);
        lib.setPosition(date, time, lon, lat, speed, elevation, angle);

        int ret = lib.sendUDP(host, port, timeout, strJson);

        if (ret < 0)
            throw new EksysNetworkException(EksysNetworkException.RESULT_ERR_SEND_UDP);

        return ret;
    }

    public void close() {
        if (socketHandle > 0)
            lib.close(socketHandle);

    }


}
