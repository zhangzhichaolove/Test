package rxjava.example.v;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import rxjava.example.R;
import rxjava.example.p.contract.VoiceContract;
import rxjava.example.view.DialogManager;
import rxjava.example.view.MediaManager;
import rxjava.example.view.VoiceButton;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Created by Administrator on 2017/2/24 0024.
 */

public class VoiceFragment extends Fragment implements VoiceContract.View {
    VoiceContract.Presenter presenter;
    VoiceButton btn;
    ListView lv;
    List<Bean> list;
    DialogManager manager;
    Adapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.voice_fragment, container, false);
        btn = (VoiceButton) view.findViewById(R.id.btn);
        lv = (ListView) view.findViewById(R.id.lv);
        btn.setView(this);
        init();
        return view;
    }

    public static VoiceFragment newInstance() {
        Bundle args = new Bundle();
        VoiceFragment fragment = new VoiceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void init() {
        manager = new DialogManager(getActivity());
        list = new ArrayList<>();
        adapter = new Adapter();
        btn.setListener(new VoiceButton.onFinishListener() {
            @Override
            public void onFinis(float time, String filrPath) {
                Log.e("TAG", time + "----" + filrPath);
                list.add(new Bean(time, filrPath));
                adapter.setList(list);
            }
        });
        lv.setAdapter(adapter);
    }

    class Adapter extends BaseAdapter implements View.OnClickListener {
        private List<Bean> list;

        @Override
        public int getCount() {
            return list == null ? 0 : list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            TextView tv = new TextView(getContext());
            tv.setText(list.get(i).getTime() + ":" + list.get(i).getPath());
            tv.setPadding(20, 20, 20, 20);
            tv.setTag(i);
            tv.setOnClickListener(this);
            return tv;
        }

        public void setList(List<Bean> list) {
            this.list = list;
            notifyDataSetChanged();
        }

        @Override
        public void onClick(View view) {
            int tag = (int) view.getTag();
            MediaManager.play(list.get(tag).getPath());
        }
    }

    class Bean {
        float time;
        String path;

        public Bean(float time, String path) {
            this.time = time;
            this.path = path;
        }

        public float getTime() {
            return time;
        }

        public void setTime(float time) {
            this.time = time;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }

    @Override
    public void showDialog() {
        manager.showStartDialog();
    }

    @Override
    public void showCancelDialog() {
        manager.showCancelDialog();
    }

    @Override
    public void showShortDialog() {
        manager.showShortDialog();
    }

    @Override
    public void dismissDialog() {
        manager.dimissDialog();
    }

    @Override
    public void levelDialog(int level) {
        manager.updateVoiceDialog(level);
    }

//    @Override
//    public void recording() {
//        manager.recording();
//    }

    @Override
    public void setPresenter(@NonNull VoiceContract.Presenter presenter) {
        this.presenter = checkNotNull(presenter);
    }

    @Override
    public void onDestroy() {
        presenter = null;
        manager = null;
        super.onDestroy();
    }
}
