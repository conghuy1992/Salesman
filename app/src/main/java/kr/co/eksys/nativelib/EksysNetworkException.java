package kr.co.eksys.nativelib;

/**
 * Created by brian on 2016-07-29.
 */
public class EksysNetworkException extends Exception{

    public static final int RESULT_UNKNOWN = 0;
    public static final int RESULT_ERR_INVALIDSOCKET = 10;
    public static final int RESULT_ERR_CONNECT = 100;
    public static final int RESULT_ERR_PARMETER_INVAID = 150;
    public static final int RESULT_ERR_SEND_TIMEOUT = 200;
    public static final int RESULT_ERR_RECEIVE_TIMEOUT = 300;
    public static final int RESULT_ERR_SEND_UDP = 400;

    private int result = RESULT_UNKNOWN;

    public EksysNetworkException(){

    }

    public EksysNetworkException(int result){
        this.result = result;
    }

    public int getErrorCode(){
        return result;
    }

//    public EksysNetworkException(){
//    }
//
//    @Override
//    public String getMessage(){
//        return super.getMessage();
//    };


}
