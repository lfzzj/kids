package com.hamitao.kids.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.chenenyu.router.annotation.Route;
import com.hamitao.base_module.base.BaseActivity;
import com.hamitao.kids.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by linjianwen on 2018/3/15.
 */

@Route("location")
public class LocationActivity extends BaseActivity {

    @BindView(R.id.mapView)
    MapView mMapView;
    @BindView(R.id.title)
    TextView title;

    private AMap aMap;
    private CameraUpdate cameraUpdate;

    private double x; //纬度
    private double y; //经度

    private String deviceName = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        ButterKnife.bind(this);
        mMapView.onCreate(savedInstanceState);
        initData();
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
    }


    private void initData() {
        deviceName = getIntent().getStringExtra("devicename");

        //调整偏移
//        double point[] = MapUtil.transform(getIntent().getDoubleExtra("latitude", 0), getIntent().getDoubleExtra("longitude", 0));
//        y = point[1];
//        x = point[0];

        x = getIntent().getDoubleExtra("latitude", 0);
        y = getIntent().getDoubleExtra("longitude", 0);
    }


    private void initView() {
        title.setText("定位");
        if (aMap == null) {
            aMap = mMapView.getMap();
            UiSettings uiSettings = aMap.getUiSettings();
            // 通过UISettings.setZoomControlsEnabled(boolean)来设置缩放按钮是否能显示
            uiSettings.setZoomControlsEnabled(false);
            //可视化区域，将指定位置指定到屏幕中心位置
            cameraUpdate = CameraUpdateFactory.newCameraPosition(new CameraPosition(new LatLng(x, y), 18, 0, 0));
            //设置希望展示的地图缩放级别,级别越高越精细
            aMap.moveCamera(cameraUpdate);
            drawMarkers();//绘制小蓝气泡
        }
    }


    /**
     * 绘制定点
     */
    private void drawMarkers() {
        MarkerOptions markerOptions = new MarkerOptions();
        // 设置Marker的坐标，为我们点击地图的经纬度坐标
        markerOptions.position(new LatLng(x, y));
        // 设置Marker点击之后显示的标题
        markerOptions.title(deviceName);
        // 设置Marker的图标样式
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
        // 设置Marker是否可以被拖拽，
        markerOptions.draggable(false);
        // 设置Marker的可见性
        markerOptions.visible(true);
        //将Marker添加到地图上去
        Marker marker = aMap.addMarker(markerOptions);
        marker.showInfoWindow();
    }


    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }

    @OnClick({R.id.back, R.id.more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.more:
                break;
        }
    }

}
