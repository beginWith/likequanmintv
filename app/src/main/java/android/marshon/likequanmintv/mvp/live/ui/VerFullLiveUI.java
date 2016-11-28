package android.marshon.likequanmintv.mvp.live.ui;

import android.content.pm.ActivityInfo;
import android.marshon.likequanmintv.R;
import android.marshon.likequanmintv.bean.PlayBean;
import android.marshon.likequanmintv.controller.LivePlayerHolder;
import android.marshon.likequanmintv.controller.RoomDataController;
import android.marshon.likequanmintv.mvp.live.LivePlayerPresenterImpl;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import org.json.JSONObject;

import javax.inject.Inject;

/**
 * Created by It.Marshon on 2016/11/28 0028 14:30
 */

public class VerFullLiveUI extends BaseLiveUI {


    private SurfaceView mSurfaceView;
    private LivePlayerHolder playerHolder;
    private PlayBean mPlayBean;

    @Inject
    LivePlayerPresenterImpl livePlayerPresenterImpl;
    private int mCodec;
    private RoomDataController mRoomDataController;
    private String mPlayerPath;
    private ImageView bgImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verfulllive);
        mActivityComponent.inject(this);
        livePlayerPresenterImpl.attachView(this);
        initPlayer();
        initControll();
        initData();
    }

    private void initPlayer() {
        mPlayBean = (PlayBean) getIntent().getSerializableExtra("playBean");
        mSurfaceView = (SurfaceView) findViewById(R.id.mSurfaceView);
        bgImg = (ImageView) findViewById(R.id.bgImg);
        mCodec = getIntent().getIntExtra("mediaCodec", 0);
//        mVideoPath = getIntent().getStringExtra("videoPath");
    }

    private void initControll() {

    }

    private void initData() {
        if (mPlayBean!=null){
            livePlayerPresenterImpl.enterRoom(mPlayBean.uid);
        }
        Glide.with(this)
                .load(mPlayBean.love_cover)
                .into(bgImg);
    }


    @Override
    protected void onDestroy() {
        if (playerHolder!=null) {
            playerHolder.release();
            playerHolder=null;
        }
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (playerHolder!=null)
            playerHolder.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (playerHolder!=null)
            playerHolder.onPause();
    }


    @Override
    protected void toPrepare() {
        if (playerHolder!=null)
        playerHolder.prepare();
    }

    @Override
    public void onConnecting() {
        bgImg.setVisibility(View.VISIBLE);

    }

    @Override
    public void onReConnecting() {
        bgImg.setVisibility(View.VISIBLE);

    }

    @Override
    public void onConnectSucces() {

    }

    @Override
    public void onConnectFailed() {

    }

    @Override
    public void onPlayComleted() {

    }

    @Override
    public void onPlayerStart() {
        bgImg.animate().alpha(0).setDuration(1000).start();
//        bgImg.setVisibility(View.GONE);
    }

    @Override
    public void onPlayePause() {


    }

    @Override
    public void onRoomData(JSONObject roomJson) {
        mRoomDataController = new RoomDataController(roomJson);
        mPlayerPath = mRoomDataController.getPlayerPath(0);
        playerHolder = new LivePlayerHolder(this,mSurfaceView,mCodec,mPlayerPath);
        playerHolder.startPlayer();
    }
}
