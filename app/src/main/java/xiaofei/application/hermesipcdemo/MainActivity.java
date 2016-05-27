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

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import xiaofei.library.hermes.Hermes;

/**
 * Created by Xiaofei on 16/5/27.
 */
public class MainActivity extends AppCompatActivity {

    private EditText mNameEditText;

    private EditText mCompanyEditText;

    private TextView mLocationTextView;

    private Button mSaveButton;

    private Button mClearButton;

    private UserStorage mUserStorage;

    private UserInfo mUserInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Hermes注册被子进程共享的类
        Hermes.register(UserStorage.class);
        //初始化各个控件
        mNameEditText = (EditText) findViewById(R.id.main_name);
        mCompanyEditText = (EditText) findViewById(R.id.main_company);
        mLocationTextView = (TextView) findViewById(R.id.main_location);
        mLocationTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //打开收集所在地的Activity
                startActivity(new Intent(getApplicationContext(), LocationActivity.class));
            }
        });
        mSaveButton = (Button) findViewById(R.id.main_save);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //获取对应的数据，并保存。
                mUserInfo.setName(mNameEditText.getText().toString());
                mUserInfo.setCompany(mCompanyEditText.getText().toString());
                mUserStorage.setUserInfo(mUserInfo);
            }
        });
        mClearButton = (Button) findViewById(R.id.main_clear);
        mClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mUserInfo = new UserInfo();
                mUserStorage.setUserInfo(mUserInfo);
            }
        });
        //获取单例
        mUserStorage = UserStorage.getInstance();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //以下操作放在onResume里，这样在子Activity返回这里的时候，信息会及时更新
        //获取UserInfo
        mUserInfo = mUserStorage.getUserInfo();
        if (mUserInfo == null) {
            mUserInfo = new UserInfo();
            mUserStorage.setUserInfo(mUserInfo);
        }
        //更新界面
        mNameEditText.setText(mUserInfo.getName());
        mCompanyEditText.setText(mUserInfo.getCompany());
        Location location = mUserInfo.getLocation();
        String locationString = location.getCountry() + location.getProvince() + location.getCity();
        mLocationTextView.setText(locationString.equals("") ? "点击编辑" : locationString);
    }
}
