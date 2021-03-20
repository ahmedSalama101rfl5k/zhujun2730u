package me.anany.weikandian.ui.activity;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.trello.rxlifecycle.ActivityEvent;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import me.anany.bean.HomeTitleDB;
import me.anany.dao.DaoUtil;
import me.anany.weikandian.App;
import me.anany.weikandian.R;
import me.anany.weikandian.adapter.DragGridViewAdapter;
import me.anany.weikandian.base.BaseActivity;
import me.anany.weikandian.model.HomeChannel;
import me.anany.weikandian.retrofit.RxApiThread;
import me.anany.weikandian.utils.ToastUtil;
import me.anany.weikandian.widget.DragGridView;
import rx.Observable;


/**
 * Created by anany on 16/1/21.
 * <p>
 * Email:zhujun2730@gmail.com
 */
public class AddHomeChannelActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    @Bind(R.id.tv_title_text)
    TextView mTextViewTitle;

    @Bind(R.id.gv_channel)
    DragGridView mGridViewChannel;

    private DragGridViewAdapter dragGridViewAdapter;


    @OnClick({R.id.btn_back, R.id.btn_more})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_more:
                break;
        }
    }

    @Override
    protected int inflateLayoutId() {
        return R.layout.activity_home_add_channel;
    }

    @Override
    protected void initViews() {
        mTextViewTitle.setText("频道管理");

        initData();
    }

    void initData() {

        List<HomeTitleDB> homeTitleDataItems = DaoUtil.getHomeTitleList(mContext);

        dragGridViewAdapter =
                new DragGridViewAdapter(this, homeTitleDataItems);
        mGridViewChannel.setAdapter(dragGridViewAdapter);

        mGridViewChannel.setOnItemClickListener(this);

        App.getApi().getHomeChannel()
                .compose(RxApiThread.convert())
                .compose(bindUntilEvent(ActivityEvent.PAUSE))
                .map(HomeChannel::getItems)
                .subscribe(this::handleResponseData,

                        throwable -> {
                            ToastUtil.showToast(this, "网络连接失败");
                        });
    }

    private void handleResponseData(List<HomeChannel.HomeChannelItem> homeChannelItems) {

        List<String> unAddChannelList = new ArrayList<>();

        Observable.from(homeChannelItems).subscribe(homeChannelItem -> {
            if ("0".equals(homeChannelItem.getIs_use())) {
                unAddChannelList.add(homeChannelItem.getIs_use());
            }
        });
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position != 0 && position != 1) {

            final ImageView moveImageView = getView(view);

            if (moveImageView != null) {

                TextView newTextView = (TextView) view.findViewById(R.id.tv_name);
                final int[] startLocation = new int[2];
                newTextView.getLocationInWindow(startLocation);

                final HomeTitleDB channel = ((DragGridViewAdapter) parent.getAdapter()).getItem(position);//获取点击的频道内容


            }
        }
    }

    /**
     * 获取点击的Item的对应View，
     * @param view
     * @return
     */
    private ImageView getView(View view) {
        view.destroyDrawingCache();
        view.setDrawingCacheEnabled(true);
        Bitmap cache = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);
        ImageView iv = new ImageView(this);
        iv.setImageBitmap(cache);
        return iv;
    }
}
