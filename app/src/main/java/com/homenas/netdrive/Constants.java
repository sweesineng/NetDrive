package com.homenas.netdrive;

import android.Manifest;
import android.os.Environment;
import android.os.storage.StorageVolume;
import android.support.v4.provider.DocumentFile;

import java.io.File;
import java.util.Set;

/**
 * Created by engss on 24/10/2017.
 */

public class Constants {

    public static final String KEY_LAYOUT_MANAGER = "layoutManager";

    public enum LayoutManagerType {GRID_LAYOUT_MANAGER, LINEAR_LAYOUT_MANAGER}

    public static final int SPAN_COUNT = 2;

    public static final String permission = Manifest.permission.WRITE_EXTERNAL_STORAGE;

    public static final int PERMISSIONS_REQUEST_CODE = 0;

    public static final DocumentFile LocalRoot = DocumentFile.fromFile(new File(Environment.getExternalStorageDirectory().getAbsolutePath()));

    public static StorageVolume ExtSdVol = null;

    public static DocumentFile ExtStorage = null;

    public static boolean fabExpanded = false;

    public static final String starList = "starrList";

    public static Set<String> starlist;

}
