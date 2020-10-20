package jp.adapter.delipo.screen.activities;

import android.app.Dialog;
import android.content.DialogInterface;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import jp.adapter.delipo.R;
import jp.adapter.delipo.data.PrefManager;
import jp.adapter.delipo.dialog.DialogLocationRequests;
import jp.adapter.delipo.dialog.DialogNotice;
import jp.adapter.delipo.dialog.DialogTutorial;
import jp.adapter.delipo.entity.prefentity.PrefBoolean;
import jp.adapter.delipo.entity.prefentity.PrefFloat;
import jp.adapter.delipo.entity.prefentity.PrefInt;
import jp.adapter.delipo.entity.prefentity.PrefLong;
import jp.adapter.delipo.entity.prefentity.PrefString;
import jp.adapter.delipo.entity.prefentity.PrefValue;

public abstract class BaseActivity extends AppCompatActivity {

    private Dialog dialogProgress;
    private DialogNotice dialogNotice;
    private DialogTutorial dialogTutorial;
    private DialogLocationRequests dialogLocationRequests;
//    private DialogConfirm dialogConfirm;

    public String getClassName() {
        return getClass().getSimpleName();
    }

    public void addOrReplaceFragment(int idContent, Fragment f) {
        try {
            FragmentManager fragmentManager = getSupportFragmentManager();
            Fragment currentFragment = fragmentManager.findFragmentById(idContent);
            if (currentFragment != null) {
                fragmentManager.beginTransaction()
                        .replace(idContent, f)
                        .commitAllowingStateLoss();
            } else {
                fragmentManager.beginTransaction()
                        .add(idContent, f)
                        .commitAllowingStateLoss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showProgress(boolean cancelAble) {
        try {
            if (dialogProgress == null) {
                dialogProgress = new Dialog(this, R.style.dialogNotice);
                dialogProgress.setContentView(R.layout.dialog_progress);
            }

            dialogProgress.setCancelable(cancelAble);
            dialogProgress.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void hideProgress() {
        try {
            if (dialogProgress != null)
                dialogProgress.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showNotice(String message, boolean cancelAble, DialogInterface.OnDismissListener listener) {
        try {
            if (dialogNotice == null) {
                dialogNotice = new DialogNotice(this);
            }

            dialogNotice.show(message, cancelAble, listener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showNotice(int message, boolean cancelAble, DialogInterface.OnDismissListener listener) {
        try {
            if (dialogNotice == null) {
                dialogNotice = new DialogNotice(this);
            }

            dialogNotice.show(message, cancelAble, listener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showLocationRequests(String header, String messageTop, String messageBottom, boolean cancelAble, DialogInterface.OnDismissListener listener) {
        try {
            if (dialogLocationRequests == null) {
                dialogLocationRequests = new DialogLocationRequests(this);
            }

            dialogLocationRequests.show(header, messageTop, messageBottom, cancelAble, listener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showLocationRequests(int header, int messageTop, int messageBottom, boolean cancelAble, DialogInterface.OnDismissListener listener) {
        try {
            if (dialogLocationRequests == null) {
                dialogLocationRequests = new DialogLocationRequests(this);
            }

            dialogLocationRequests.show(header, messageTop, messageBottom, cancelAble, listener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showTutorial(int title, boolean titleHighLight, int message, String keyPref, DialogInterface.OnDismissListener listener) {
        try {
            if (dialogTutorial == null) {
                dialogTutorial = new DialogTutorial(this);
            }

            dialogTutorial.show(title, titleHighLight, message, keyPref, listener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //TODO == pref manager ============================
    private PrefManager prefManager;

    private PrefManager getPrefManager() {
        if (prefManager == null) {
            prefManager = PrefManager.getInstance(this);
        }

        return prefManager;
    }

    public void prefWriteValue(PrefValue prefValue) {
        try {
            if (prefValue instanceof PrefBoolean)
                getPrefManager().writeBoolean(prefValue.key, ((PrefBoolean) prefValue).value);
            else if (prefValue instanceof PrefInt)
                getPrefManager().writeInt(prefValue.key, ((PrefInt) prefValue).value);
            else if (prefValue instanceof PrefLong)
                getPrefManager().writeLong(prefValue.key, ((PrefLong) prefValue).value);
            else if (prefValue instanceof PrefFloat)
                getPrefManager().writeFloat(prefValue.key, ((PrefFloat) prefValue).value);
            else if (prefValue instanceof PrefString)
                getPrefManager().writeString(prefValue.key, ((PrefString) prefValue).value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void prefWriteBoolean(String key, boolean value) {
        try {
            getPrefManager().writeBoolean(key, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void prefWriteString(String key, String value) {
        try {
            getPrefManager().writeString(key, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void prefWriteLong(String key, long value) {
        try {
            getPrefManager().writeLong(key, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void prefWriteFloat(String key, float value) {
        try {
            getPrefManager().writeFloat(key, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void prefWriteInt(String key, int value) {
        try {
            getPrefManager().writeInt(key, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void prefWriteList(List<PrefValue> list) {
        try {
            if (list != null && !list.isEmpty())
                getPrefManager().writeList(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void prefWriteArr(PrefValue... list) {
        try {
            if (list != null && list.length > 0)
                getPrefManager().writeArr(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String prefGetString(String key) {
        try {
            return getPrefManager().getString(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String prefGetString(String key, String keyDefault) {
        try {
            return getPrefManager().getString(key, keyDefault);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return keyDefault;
    }

    public boolean prefGetBoolean(String key) {
        try {
            return getPrefManager().getBoolean(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean prefGetBoolean(String key, boolean defValue) {
        try {
            return getPrefManager().getBoolean(key, defValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defValue;
    }

    public long prefGetLong(String key) {
        try {
            return getPrefManager().getLong(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public long prefGetLong(String key, long defValue) {
        try {
            return getPrefManager().getLong(key, defValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defValue;
    }

    public float prefGetFloat(String key) {
        try {
            return getPrefManager().getFloat(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public float prefGetFloat(String key, float defValue) {
        try {
            return getPrefManager().getFloat(key, defValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defValue;
    }


    public int prefGetInt(String key) {
        try {
            return getPrefManager().getInt(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int prefGetInt(String key, int defaultValues) {
        int value;

        try {
            value = getPrefManager().getInt(key, defaultValues);
        } catch (Exception e) {
            e.printStackTrace();
            value = defaultValues;
        }

        return value;
    }

    public boolean prefIsContainsKey(String key) {
        boolean value;

        try {
            value = getPrefManager().containsKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            value = false;
        }

        return value;
    }
    //=================================================


}
