package rxjava.example.p;

import rxjava.example.p.contract.VoiceContract;

/**
 * Created by Administrator on 2017/2/24 0024.
 */

public class VoicePresenter implements VoiceContract.Presenter {
    VoiceContract.View view;

    public VoicePresenter(VoiceContract.View view) {
        this.view = view;
        view.setPresenter(this);

    }

    @Override
    public void start() {

    }
}
