package rxjava.example.v;

/**
 * Class Note:登陆View的接口，实现类也就是登陆的activity
 */
public interface LoginView {

    void showProgress();

    void hideProgress();

    void setUserNameOrPasswordError();

    void navigateToHome();
}