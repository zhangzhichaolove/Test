package rxjava.example;

/**
 * 数据基类
 * Created by CJ on 2016/10/27 0027.
 */
public class BaseDataEntity<T> {
    private int result;
    private String content;
    private T data;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
