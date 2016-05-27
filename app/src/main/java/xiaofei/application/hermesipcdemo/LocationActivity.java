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
                mUserStorage.setUserInfo(mUserInfo);
                finish();
            }
        });
        Hermes.setHermesListener(new HermesListener() {
            @Override
            public void onInitSuccess(Class<? extends HermesService> service) {
                mUserStorage = Hermes.getInstance(IUserStorage.class);
                mSaveButton.setClickable(true);
                mCancelButton.setClickable(true);
                mUserInfo = mUserStorage.getUserInfo();
                mLocation = mUserInfo.getLocation();
                mCountryEditText.setText(mLocation.getCountry());
                mProvinceEditText.setText(mLocation.getProvince());
                mCityTextView.setText(mLocation.getCity());
            }
        });
        Hermes.connect(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Hermes.disconnect(this);
    }
}
