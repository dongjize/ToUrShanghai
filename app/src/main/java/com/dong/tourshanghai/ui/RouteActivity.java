package com.dong.tourshanghai.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dong.tourshanghai.R;

/**
 * Intro: 路线查询
 * <p/>
 * Programmer: dong
 * Date: 15/9/19.
 */
public class RouteActivity extends BaseActivity implements View.OnClickListener {

    private EditText etFrom, etTo;
    private TextView tvSearch;
    private Button btnReverse;
    private int searchMode;
    private TextView tvBus, tvCar, tvWalk;
    private ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_search_route);

        etFrom = (EditText) findViewById(R.id.et_from_location);
        etTo = (EditText) findViewById(R.id.et_to_location);
        btnReverse = (Button) findViewById(R.id.btn_reverse_location);
        tvSearch = (TextView) findViewById(R.id.tv_route_search);
        tvBus = (TextView) findViewById(R.id.tv_bus);
        tvCar = (TextView) findViewById(R.id.tv_car);
        tvWalk = (TextView) findViewById(R.id.tv_walk);
        btnBack = (ImageView) findViewById(R.id.btn_backup);

        btnReverse.setOnClickListener(this);
        tvSearch.setOnClickListener(this);
        tvBus.setOnClickListener(this);
        tvCar.setOnClickListener(this);
        tvWalk.setOnClickListener(this);
        btnBack.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_route_search:
                routeSearch();
                break;
            case R.id.btn_reverse_location:
                reverseFromTo();
                break;
            case R.id.tv_bus:
                tvBus.setTextColor(getResources().getColor(R.color.white));
                tvCar.setTextColor(getResources().getColor(R.color.gray));
                tvWalk.setTextColor(getResources().getColor(R.color.gray));
                searchMode = 0;
                break;
            case R.id.tv_car:
                tvCar.setTextColor(getResources().getColor(R.color.white));
                tvBus.setTextColor(getResources().getColor(R.color.gray));
                tvWalk.setTextColor(getResources().getColor(R.color.gray));
                searchMode = 1;
                break;
            case R.id.tv_walk:
                tvWalk.setTextColor(getResources().getColor(R.color.white));
                tvCar.setTextColor(getResources().getColor(R.color.gray));
                tvBus.setTextColor(getResources().getColor(R.color.gray));
                searchMode = 2;
                break;
            case R.id.btn_backup:
                RouteActivity.this.finish();
                break;
        }
    }

    private void reverseFromTo() {
        String fromStr = etFrom.getText().toString().trim();
        String toStr = etTo.getText().toString().trim();
        etFrom.setText(toStr);
        etTo.setText(fromStr);
    }

    private void routeSearch() {
        String fromStr = etFrom.getText().toString().trim();
        String toStr = etTo.getText().toString().trim();
        if ((!TextUtils.isEmpty(fromStr)) && (!TextUtils.isEmpty(toStr))) {
            Intent intent = new Intent(RouteActivity.this, RouteResultActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("start", fromStr);
            bundle.putString("end", toStr);
            bundle.putInt("mode", searchMode);
            intent.putExtras(bundle);
            startActivity(intent);
        } else {
            showToast("输入信息不能为空");
        }


    }
}
