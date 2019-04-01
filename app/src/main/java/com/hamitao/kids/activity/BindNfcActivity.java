package com.hamitao.kids.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chenenyu.router.Router;
import com.chenenyu.router.annotation.Route;
import com.hamitao.base_module.base.BaseActivity;
import com.hamitao.base_module.customview.RoundCornerImageView;
import com.hamitao.base_module.network.NetWorkCallBack;
import com.hamitao.base_module.network.NetWorkResult;
import com.hamitao.base_module.util.GsonUtil;
import com.hamitao.base_module.util.StringUtil;
import com.hamitao.base_module.util.UserUtil;
import com.hamitao.kids.R;
import com.hamitao.kids.fragment.PlayFragment;
import com.hamitao.kids.model.ContentModel;
import com.hamitao.kids.model.MediaModel;
import com.hamitao.kids.model.RecordModel;
import com.hamitao.kids.model.nfccard.NfcContentModel;
import com.hamitao.kids.model.nfccard.NfcMediaModel;
import com.hamitao.kids.model.nfccard.NfcRecordModel;
import com.hamitao.kids.mvp.CommonPresenter;
import com.hamitao.kids.mvp.CommonView;
import com.hamitao.kids.network.NetworkRequest;
import com.hamitao.kids.player.AudioPlayer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route("bind_nfc")
public class BindNfcActivity extends BaseActivity implements CommonView {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_lable)
    TextView tvState;
    @BindView(R.id.tv_nfcId)
    TextView tvNfcId;
    @BindView(R.id.tv_cover)
    RoundCornerImageView tvCover;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_descrpiption)
    TextView tv_descrpiption;
    @BindView(R.id.tv_bind)
    TextView tvBind;


    private String infotype;
    private String info;
    private String cardid;

    private CommonPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_nfc);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initData() {
        presenter = new CommonPresenter(this);
        info = getIntent().getStringExtra("info");
        infotype = getIntent().getStringExtra("infotype");
        cardid = getIntent().getStringExtra("cardid");
        queryBindContent(cardid, infotype);
    }

    private void initView() {
        title.setText("制卡信息");

        switch (infotype) {
            case "mediaid":
                tvState.setText("已绑定的歌曲");
                break;
            case "contentid":
                tvState.setText("已绑定的专辑");
                break;
            case "recordid":
                tvState.setText("已绑定的录音");
                break;
            default:
                break;
        }
    }

    @OnClick({R.id.back, R.id.more, R.id.rl_content})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.more:
                break;
            case R.id.rl_content:
                //跳转到音乐列表
                switch (infotype) {
                    case "mediaid":
                        presenter.queryMediaById(UserUtil.user() == null ? "" : UserUtil.user().getCustomerid(), info, 0);
                        break;
                    case "contentid":
                        Router.build("music_list").with("contentid", info).go(this);
                        break;
                    case "recordid":
                        presenter.queryRecordById(info);
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
    }


    /**
     * 查询绑定的内容
     *
     * @param cardid
     * @param infotype
     */
    private void queryBindContent(final String cardid, final String infotype) {
        NetworkRequest.queryMyCardByCodeRequest(cardid, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {
                if (infotype.equals("contentid")) {
                    NfcContentModel.ResponseDataObjBean bean = GsonUtil.GsonToBean(result.getResponseDataObj(), NfcContentModel.ResponseDataObjBean.class);
                    tvTitle.setText(StringUtil.deleteNumber(bean.getNFCCards().get(0).getContentDesc().getNameI18List().get(0).getValue()));
                    tv_descrpiption.setText(bean.getNFCCards().get(0).getContentDesc().getDescriptionI18List().get(0).getValue());
                    Glide.with(BindNfcActivity.this).load(bean.getNFCCards().get(0).getImgHttpURL()).error(R.drawable.default_cover).into(tvCover);
                } else if (infotype.equals("recordid")) {
                    NfcRecordModel.ResponseDataObjBean bean = GsonUtil.GsonToBean(result.getResponseDataObj(), NfcRecordModel.ResponseDataObjBean.class);
                    tvTitle.setText(bean.getNFCCards().get(0).getVoiceRecordingDesc().getName());
                } else if (infotype.equals("mediaid")) {
                    NfcMediaModel bean = GsonUtil.GsonToBean(result.getResponseDataObj(), NfcMediaModel.class);
                    tvTitle.setText(StringUtil.deleteNumber(bean.getNFCCards().get(0).getTitle()));
                    Glide.with(BindNfcActivity.this).load(bean.getNFCCards().get(0).getImgHttpURL()).error(R.drawable.default_cover).into(tvCover);
                }
                tvNfcId.setText("" + cardid);
            }

            @Override
            public void onFail(NetWorkResult result, String msg) {

            }

            @Override
            public void onBegin() {

            }

            @Override
            public void onEnd() {

            }
        }));
    }

    @Override
    public void onBegin() {

    }

    @Override
    public void onFinish() {

    }

    @Override
    public void onMessageShow(String msg) {

    }

    @Override
    public void refreshData(Object status, Object data, Object aux) {
        switch ((String) status) {
            case "record":
                RecordModel.ResponseDataObjBean bean = (RecordModel.ResponseDataObjBean) data;
                ContentModel.ResponseDataObjBean.ContentsBean.MediaListBean music = new ContentModel.ResponseDataObjBean.ContentsBean.MediaListBean();
                music.setHttpURL(bean.getVoiceRecordings().get(0).getHttpURL());
                music.setMediaid(bean.getVoiceRecordings().get(0).getId());
                music.setMediatitle(bean.getVoiceRecordings().get(0).getName());
                music.setMediasubtype("record");
                if (AudioPlayer.get().getMusicList().size() > 0)
                    AudioPlayer.get().getMusicList().clear();
                //播放音乐
                AudioPlayer.get().play(music);
                PlayFragment.getInstance().show(getSupportFragmentManager(), "player");
                break;
            case "media":
                MediaModel.ResponseDataObjBean musicbean = (MediaModel.ResponseDataObjBean) data;
                ContentModel.ResponseDataObjBean.ContentsBean.MediaListBean music1 = new ContentModel.ResponseDataObjBean.ContentsBean.MediaListBean();
                if ("audio".equals(musicbean.getContents().get(0).getMediaList().get(0).getMediatype())) {
                    //音频
                    if (musicbean.getContents().get(0).getMyfavouriteid() != null) {
                        music1.setFavourited("yes");
                        music1.setMyfavouriteid(musicbean.getContents().get(0).getMyfavouriteid());
                    }
                    music1.setHttpURL(musicbean.getContents().get(0).getMediaList().get(0).getHttpURL());
                    music1.setMediaid(musicbean.getContents().get(0).getMediaList().get(0).getMediaid());
                    music1.setMediatitle(musicbean.getContents().get(0).getMediaList().get(0).getMediatitle());
                    music1.setHeaderimgurl(musicbean.getContents().get(0).getImgtitleurl());
                    music1.setMediasubtype("mp3"); //这里进行写死处理，为了区分歌曲和录音
                    if (AudioPlayer.get().getMusicList().size() > 0) {
                        AudioPlayer.get().getMusicList().clear();
                    }
                    //播放音乐
                    AudioPlayer.get().play(music1);
                    PlayFragment.getInstance().show(getSupportFragmentManager(), "player");
                } else {
                    Router.build("videoplayer")
                            .with("title", musicbean.getContents().get(0).getMediaList().get(0).getMediatitle())
                            .with("url", musicbean.getContents().get(0).getMediaList().get(0).getHttpURL())
                            .with("info", musicbean.getContents().get(0).getMediaList().get(0).getMediaid())
                            .with("img",musicbean.getContents().get(0).getImgtitleurl())
                            .go(this);
                }
                break;
            default:
                break;

        }
    }
}
