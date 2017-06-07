package rxjava.example;

public class HttpStatusEntity {

    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * 是否请求失败
     *
     * @return
     */
    public boolean isValid() {
        if (code == 200) {
            return false;
        } else {
            return true;
        }

    }
}