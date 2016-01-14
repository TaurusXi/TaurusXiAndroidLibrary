package org.taurusxi.taurusxicommon.keyValueModel.utils;

import android.content.Context;
import android.text.TextUtils;

import org.taurusxi.taurusxicommon.keyValueModel.DaoMaster;
import org.taurusxi.taurusxicommon.keyValueModel.DaoSession;
import org.taurusxi.taurusxicommon.keyValueModel.KeyValueModel;
import org.taurusxi.taurusxicommon.keyValueModel.KeyValueModelDao;
import org.taurusxi.taurusxicommon.manager.AppManager;
import org.taurusxi.taurusxicommon.utils.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SettingHelper {

    private final static String DB_NAME = "KeyValue.db";
    private final static String TRUE = "t";
    private final static String FALSE = "f";

    private static volatile SettingHelper instance;
    private DaoSession daoSession;
    private DaoMaster daoMaster;
    private KeyValueModelDao keyValueModelDao;


    public static SettingHelper getInstance() {
        if (instance == null) {
            synchronized (SettingHelper.class) {
                if (instance == null) {
                    instance = new SettingHelper();

                }
            }
        }
        return instance;
    }

    private SettingHelper() {
        this.daoSession = getDaoSession(AppManager.getInstance().getApplication());
        this.keyValueModelDao = daoSession.getKeyValueModelDao();

    }

    private DaoSession getDaoSession(Context context) {
        if (daoSession == null) {
            if (daoMaster == null) {
                daoMaster = getDaoMaster(context);
            }
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }

    private DaoMaster getDaoMaster(Context context) {
        if (daoMaster == null) {
            DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(context, DB_NAME, null);
            daoMaster = new DaoMaster(helper.getWritableDatabase());
        }
        return daoMaster;
    }

    public void saveInt(String key, int value) {
        if (TextUtils.isEmpty(key)) {
            return;
        }
        KeyValueModel keyValueModel = new KeyValueModel();
        keyValueModel.setKey(key);
        keyValueModel.setValue(String.valueOf(value));
        keyValueModelDao.insertOrReplace(keyValueModel);
    }

    public int getInt(String key, int defaultValue) {
        if (TextUtils.isEmpty(key)) {
            return defaultValue;
        }
        KeyValueModel load = keyValueModelDao.load(key);
        return load == null ? defaultValue : Integer.parseInt(load.getValue());
    }


    public void saveDouble(String key, double value) {
        if (TextUtils.isEmpty(key)) {
            return;
        }
        KeyValueModel keyValueModel = new KeyValueModel();
        keyValueModel.setKey(key);
        keyValueModel.setValue(String.valueOf(value));
        keyValueModelDao.insertOrReplace(keyValueModel);
    }

    public double getDouble(String key, double defaultValue) {
        if (TextUtils.isEmpty(key)) {
            return defaultValue;
        }
        KeyValueModel load = keyValueModelDao.load(key);
        return load == null ? defaultValue : Double.parseDouble(load.getValue());
    }


    public void saveBoolean(String key, boolean value) {
        if (TextUtils.isEmpty(key)) {
            return;
        }
        KeyValueModel keyValueModel = new KeyValueModel();
        keyValueModel.setKey(key);
        keyValueModel.setValue(value ? TRUE : FALSE);
        keyValueModelDao.insertOrReplace(keyValueModel);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        if (TextUtils.isEmpty(key)) {
            return defaultValue;
        }
        KeyValueModel load = keyValueModelDao.load(key);
        if (load == null) {
            return defaultValue;
        } else {
            String value = load.getValue();
            return value.equals(TRUE) || ((!value.equals(FALSE)) && defaultValue);
        }
    }

    public String getString(String key, String defaultValue) {
        if (TextUtils.isEmpty(key)) {
            return defaultValue;
        }
        KeyValueModel load = keyValueModelDao.load(key);
        return load == null ? defaultValue : load.getValue();
    }


    public void saveString(String key, String value) {
        if (TextUtils.isEmpty(key)) {
            return;
        }
        KeyValueModel keyValueModel = new KeyValueModel();
        keyValueModel.setKey(key);
        keyValueModel.setValue(value);
        keyValueModelDao.insertOrReplace(keyValueModel);
    }

    public void saveObj(String key, Object value) {

        if (TextUtils.isEmpty(key)) {
            return;
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(baos);
            oos.writeObject(value);
            // 将对象的转为base64码
            String objBase64 = Base64.encode(baos.toByteArray());
            KeyValueModel keyValueModel = new KeyValueModel();
            keyValueModel.setKey(key);
            keyValueModel.setValue(objBase64);
            keyValueModelDao.insertOrReplace(keyValueModel);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Object getObj(String key) {

        if (TextUtils.isEmpty(key)) {
            return null;
        }

        KeyValueModel keyValueModel = keyValueModelDao.load(key);
        if (keyValueModel == null) {
            return null;
        }
        String objBase64 = keyValueModel.getValue();
        if (TextUtils.isEmpty(objBase64)) {
            return null;
        }
        // 对Base64格式的字符串进行解码
        byte[] base64Bytes = Base64.decode(objBase64);
        ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);

        ObjectInputStream ois;
        Object obj = null;
        try {
            ois = new ObjectInputStream(bais);
            obj = ois.readObject();
            ois.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

}

