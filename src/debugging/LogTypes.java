package debugging;

public enum LogTypes{
    INFO(" INFO: "),
    SOFTERROR(" SOFT ERROR: "),
    FATALERROR(" FATAL ERROR:");

    private final String msg;
    LogTypes(final String msg){
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}