package rxjava.example.base;

public interface BaseView<T extends BasePresenter> {

    void setPresenter(T presenter);
}