package cn.ahyxy.fastvisit.app;

import android.app.Application;
import android.graphics.Color;

import com.baidu.mapapi.SDKInitializer;

import org.xutils.DbManager;
import org.xutils.x;

import cn.ahyxy.fastvisit.R;
import cn.ahyxy.fastvisit.utils.XUtilsImageLoader;
import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ImageLoader;
import cn.finalteam.galleryfinal.ThemeConfig;

/**
 * Created by yexiangyu on 16/4/14.
 */
public class AppContext extends Application
{
    private static AppContext mContext;
    private DbManager.DaoConfig daoConfig;


    public static AppContext getInstance()
    {
        return mContext;
    }

    public DbManager.DaoConfig getDaoConfig()
    {
        return daoConfig;
    }

    public static DbManager getDbmanager()
    {
        return x.getDb(mContext.daoConfig);
    }


    @Override
    public void onCreate()
    {
        super.onCreate();
        mContext = this;
        x.Ext.init(this);
        x.Ext.setDebug(true);
        SDKInitializer.initialize(getApplicationContext());
        daoConfig = new DbManager.DaoConfig()
                .setDbName("blackskirt.db")
                        // 不设置dbDir时, 默认存储在app的私有目录.
//                .setDbDir(new File("/sdcard")) // "sdcard"的写法并非最佳实践, 这里为了简单, 先这样写了.
                .setDbVersion(1)
                .setDbOpenListener(new DbManager.DbOpenListener()
                {
                    @Override
                    public void onDbOpened(DbManager db)
                    {
                        // 开启WAL, 对写入加速提升巨大
                        db.getDatabase().enableWriteAheadLogging();
                    }
                })
                .setDbUpgradeListener(new DbManager.DbUpgradeListener()
                {
                    @Override
                    public void onUpgrade(DbManager db, int oldVersion, int newVersion)
                    {
                        // TODO: ...
                        // db.addColumn(...);
                        // db.dropTable(...);
                        // ...
                        // or
                        // db.dropDb();
                    }
                });

        ThemeConfig theme = new ThemeConfig.Builder()
                .setTitleBarBgColor(Color.BLACK)
                .setTitleBarTextColor(Color.WHITE)
                .setTitleBarIconColor(Color.WHITE)
                .setIconBack(R.mipmap.action_bar_back)
                .setFabNornalColor(Color.RED)
                .setFabPressedColor(Color.BLUE)
                .setCheckNornalColor(Color.WHITE)
                .setCheckSelectedColor(Color.WHITE)
                .build();
//配置功能
        FunctionConfig functionConfig = new FunctionConfig.Builder()

                .setEnableCamera(true)
                .setEnableEdit(true)
                .setEnableCrop(true)
                .setEnableRotate(true)
                .setCropSquare(true)
                .setEnablePreview(true)
                .build();

//配置imageloader
        ImageLoader imageloader = new XUtilsImageLoader();
        CoreConfig coreConfig = new CoreConfig.Builder(this, imageloader, theme)
                .setFunctionConfig(functionConfig)
                .build();
        GalleryFinal.init(coreConfig);
    }

}
