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
        Hermes.register(UserStorage.class);
        mNameEditText = (EditText) findViewById(R.id.main_name);
        mCompanyEditText = (EditText) findViewById(R.id.main_company);
        mLocationTextView = (TextView) findViewById(R.id.main_location);
        mLocationTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LocationActivity.class));
            }
        });
        mSaveButton = (Button) findViewById(R.id.main_save);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        mUserStorage = UserStorage.getInstance();
        mUserInfo = mUserStorage.getUserInfo();
        if (mUserInfo == null) {
            mUserInfo = new UserInfo();
            mUserStorage.setUserInfo(mUserInfo);
        }
        mNameEditText.setText(mUserInfo.getName());
        mCompanyEditText.setText(mUserInfo.getCompany());
        Location location = mUserInfo.getLocation();
        String locationString = location.getCountry() + location.getProvince() + location.getCity();
        mLocationTextView.setText(locationString.equals("") ? "点击编辑" : locationString);
    }
}
