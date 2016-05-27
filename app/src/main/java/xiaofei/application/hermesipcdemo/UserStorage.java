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

import xiaofei.library.datastorage.DataStorageFactory;
import xiaofei.library.datastorage.IDataStorage;
import xiaofei.library.hermes.annotation.ClassId;

/**
 * Created by Xiaofei on 16/5/27.
 */
@ClassId("UserStorage")
public class UserStorage implements IUserStorage {

    private static final String KEY = "USER_INFO";

    private static UserStorage sInstance = null;

    private IDataStorage mDataStorage;

    private UserStorage() {
        mDataStorage = DataStorageFactory.getInstance(MyApplication.getInstance(), DataStorageFactory.TYPE_DATABASE);
    }

    public static synchronized UserStorage getInstance() {
        if (sInstance == null) {
            sInstance = new UserStorage();
        }
        return sInstance;
    }

    @Override
    public UserInfo getUserInfo() {
        return mDataStorage.load(UserInfo.class, KEY);
    }

    @Override
    public void setUserInfo(UserInfo userInfo) {
        mDataStorage.storeOrUpdate(userInfo, KEY);
    }
}
