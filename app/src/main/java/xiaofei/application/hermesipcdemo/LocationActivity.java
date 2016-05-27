/**
 *
 * Copyright 2016 Xiaofei
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package xiaofei.application.hermesipcdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import xiaofei.library.hermes.Hermes;
import xiaofei.library.hermes.HermesListener;
import xiaofei.library.hermes.HermesService;

/**
 * Created by Xiaofei on 16/5/27.
 */
public class LocationActivity extends AppCompatActivity {

    private EditText mCountryEditText;

    private EditText mProvinceEditText;

    private EditText mCityTextView;

    private Button mSaveButton;

    private Button mCancelButton;

    private IUserStorage mUserStorage;

    private UserInfo mUserInfo;

    private Location mLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        mCountryEditText = (EditText) findViewById(R.id.location_country);
        mProvinceEditText = (EditText) findViewById(R.id.location_province);
        mCityTextView = (EditText) findViewById(R.id.location_city);
        mCancelButton = (Button) findViewById(R.id.location_cancel);
        mSaveButton = (Button) findViewById(R.id.location_save);
        //先设为不可点击，在Hermes连接上后再变为可点击
        mCancelButton.setClickable(false);
        mSaveButton.setClickable(false);
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLocation.setCountry(mCountryEditText.getText().toString());
                mLocation.setProvince(mProvinceEditText.getText().toString());
                mLocation.setCity(mCityTextView.getText().toString());
                //下面这个操作咋一看以为是操作本地单例，其实操作的是主进程的单例。
                mUserStorage.setUserInfo(mUserInfo);
                finish();
            }
        });
        //在连接之前给Hermes设置监听器
        Hermes.setHermesListener(new HermesListener() {

            @Override
            public void onHermesConnected(Class<? extends HermesService> service) {
                //连接成功，首先获取单例
                mUserStorage = Hermes.getInstance(IUserStorage.class);
                //将按钮变为可点击
                mSaveButton.setClickable(true);
                mCancelButton.setClickable(true);
                //通过单例获取UserInfo
                mUserInfo = mUserStorage.getUserInfo();
                mLocation = mUserInfo.getLocation();
                //更新界面
                mCountryEditText.setText(mLocation.getCountry());
                mProvinceEditText.setText(mLocation.getProvince());
                mCityTextView.setText(mLocation.getCity());
            }
        });
        //连接Hermes服务
        Hermes.connect(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //断开Hermes服务
        Hermes.disconnect(this);
    }
}
