/*
 * Copyright (c) 2010-2011, The MiCode Open Source Community (www.micode.net)
 *
 * This file is part of FileExplorer.
 *
 * FileExplorer is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * FileExplorer is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SwiFTP.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.micode.fileexplorer;

import java.io.*;
import java.util.ArrayList;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

public class FileOperationHelper {
    private static final String LOG_TAG = "FileOperation";

    private ArrayList<FileInfo> mCurFileNameList = new ArrayList<FileInfo>();

    private boolean mMoving;

    private IOperationProgressListener mOperationListener;

    private FilenameFilter mFilter;

    public interface IOperationProgressListener {
        void onFinish();

        void onFileChanged(String path);
    }

    public FileOperationHelper(IOperationProgressListener l) {
        mOperationListener = l;
    }

    public void setFilenameFilter(FilenameFilter f) {Log.d("shanlihou", "../../mifile//src/net/micode/fileexplorer/FileOperationHelper.java: setFilenameFilter");
        mFilter = f;
    }

    public boolean CreateFolder(String path, String name) {Log.d("shanlihou", "../../mifile//src/net/micode/fileexplorer/FileOperationHelper.java: CreateFolder");
        Log.v(LOG_TAG, "CreateFolder >>> " + path + "," + name);

        File f = new File(Util.makePath(path, name));
        if (f.exists())
            return false;

        return f.mkdir();
    }

    public void Copy(ArrayList<FileInfo> files) {Log.d("shanlihou", "../../mifile//src/net/micode/fileexplorer/FileOperationHelper.java: Copy");
        copyFileList(files);
    }

    public boolean Paste(String path) {Log.d("shanlihou", "../../mifile//src/net/micode/fileexplorer/FileOperationHelper.java: Paste");
        if (mCurFileNameList.size() == 0)
            return false;

        final String _path = path;
        asnycExecute(new Runnable() {
            @Override
            public void run() {Log.d("shanlihou", "../../mifile//src/net/micode/fileexplorer/FileOperationHelper.java: run");
                for (FileInfo f : mCurFileNameList) {
                    CopyFile(f, _path);
                }

                mOperationListener.onFileChanged(Environment
                        .getExternalStorageDirectory()
                        .getAbsolutePath());

                clear();
            }
        });

        return true;
    }

    public boolean canPaste() {Log.d("shanlihou", "../../mifile//src/net/micode/fileexplorer/FileOperationHelper.java: canPaste");
        return mCurFileNameList.size() != 0;
    }

    public void StartMove(ArrayList<FileInfo> files) {Log.d("shanlihou", "../../mifile//src/net/micode/fileexplorer/FileOperationHelper.java: StartMove");
        if (mMoving)
            return;

        mMoving = true;
        copyFileList(files);
    }

    public boolean isMoveState() {Log.d("shanlihou", "../../mifile//src/net/micode/fileexplorer/FileOperationHelper.java: isMoveState");
        return mMoving;
    }

    public boolean canMove(String path) {Log.d("shanlihou", "../../mifile//src/net/micode/fileexplorer/FileOperationHelper.java: canMove");
        for (FileInfo f : mCurFileNameList) {
            if (!f.IsDir)
                continue;

            if (Util.containsPath(f.filePath, path))
                return false;
        }

        return true;
    }

    public void clear() {Log.d("shanlihou", "../../mifile//src/net/micode/fileexplorer/FileOperationHelper.java: clear");
        synchronized(mCurFileNameList) {
            mCurFileNameList.clear();
        }
    }

    public boolean EndMove(String path) {Log.d("shanlihou", "../../mifile//src/net/micode/fileexplorer/FileOperationHelper.java: EndMove");
        if (!mMoving)
            return false;
        mMoving = false;

        if (TextUtils.isEmpty(path))
            return false;

        final String _path = path;
        asnycExecute(new Runnable() {
            @Override
            public void run() {Log.d("shanlihou", "../../mifile//src/net/micode/fileexplorer/FileOperationHelper.java: run");
                    for (FileInfo f : mCurFileNameList) {
                        MoveFile(f, _path);
                    }

                    mOperationListener.onFileChanged(Environment
                            .getExternalStorageDirectory()
                            .getAbsolutePath());

                    clear();
                }
        });

        return true;
    }

    public ArrayList<FileInfo> getFileList() {
        return mCurFileNameList;
    }

    private void asnycExecute(Runnable r) {Log.d("shanlihou", "../../mifile//src/net/micode/fileexplorer/FileOperationHelper.java: asnycExecute");
        final Runnable _r = r;
        new AsyncTask() {
            @Override
            protected Object doInBackground(Object... params) {
                synchronized(mCurFileNameList) {
                    _r.run();
                }
                if (mOperationListener != null) {
                    mOperationListener.onFinish();
                }

                return null;
            }
        }.execute();
    }

    public boolean isFileSelected(String path) {Log.d("shanlihou", "../../mifile//src/net/micode/fileexplorer/FileOperationHelper.java: isFileSelected");
        synchronized(mCurFileNameList) {
            for (FileInfo f : mCurFileNameList) {
                if (f.filePath.equalsIgnoreCase(path))
                    return true;
            }
        }
        return false;
    }

    public boolean Rename(FileInfo f, String newName) {Log.d("shanlihou", "../../mifile//src/net/micode/fileexplorer/FileOperationHelper.java: Rename");
        if (f == null || newName == null) {
            Log.e(LOG_TAG, "Rename: null parameter");
            return false;
        }

        File file = new File(f.filePath);
        String newPath = Util.makePath(Util.getPathFromFilepath(f.filePath), newName);
        final boolean needScan = file.isFile();
        try {
            boolean ret = file.renameTo(new File(newPath));
            if (ret) {
                if (needScan) {
                    mOperationListener.onFileChanged(f.filePath);
                }
                mOperationListener.onFileChanged(newPath);
            }
            return ret;
        } catch (SecurityException e) {
            Log.e(LOG_TAG, "Fail to rename file," + e.toString());
        }
        return false;
    }

    public boolean Delete(ArrayList<FileInfo> files) {Log.d("shanlihou", "../../mifile//src/net/micode/fileexplorer/FileOperationHelper.java: Delete");
        copyFileList(files);
        asnycExecute(new Runnable() {
            @Override
            public void run() {Log.d("shanlihou", "../../mifile//src/net/micode/fileexplorer/FileOperationHelper.java: run");
                for (FileInfo f : mCurFileNameList) {
                    DeleteFile(f);
                }

                mOperationListener.onFileChanged(Environment
                        .getExternalStorageDirectory()
                        .getAbsolutePath());

                clear();
            }
        });
        return true;
    }

    protected void DeleteFile(FileInfo f) {
        if (f == null) {
            Log.e(LOG_TAG, "DeleteFile: null parameter");
            return;
        }

        File file = new File(f.filePath);
        boolean directory = file.isDirectory();
        if (directory) {
            for (File child : file.listFiles(mFilter)) {
                if (Util.isNormalFile(child.getAbsolutePath())) {
                    DeleteFile(Util.GetFileInfo(child, mFilter, true));
                }
            }
        }

        file.delete();

        Log.v(LOG_TAG, "DeleteFile >>> " + f.filePath);
    }

    private void CopyFile(FileInfo f, String dest) {Log.d("shanlihou", "../../mifile//src/net/micode/fileexplorer/FileOperationHelper.java: CopyFile");
        if (f == null || dest == null) {
            Log.e(LOG_TAG, "CopyFile: null parameter");
            return;
        }

        File file = new File(f.filePath);
        if (file.isDirectory()) {

            // directory exists in destination, rename it
            String destPath = Util.makePath(dest, f.fileName);
            File destFile = new File(destPath);
            int i = 1;
            while (destFile.exists()) {
                destPath = Util.makePath(dest, f.fileName + " " + i++);
                destFile = new File(destPath);
            }

            for (File child : file.listFiles(mFilter)) {
                if (!child.isHidden() && Util.isNormalFile(child.getAbsolutePath())) {
                    CopyFile(Util.GetFileInfo(child, mFilter, Settings.instance().getShowDotAndHiddenFiles()), destPath);
                }
            }
        } else {
            String destFile = Util.copyFile(f.filePath, dest);
        }
        Log.v(LOG_TAG, "CopyFile >>> " + f.filePath + "," + dest);
    }

    private boolean MoveFile(FileInfo f, String dest) {Log.d("shanlihou", "../../mifile//src/net/micode/fileexplorer/FileOperationHelper.java: MoveFile");
        Log.v(LOG_TAG, "MoveFile >>> " + f.filePath + "," + dest);

        if (f == null || dest == null) {
            Log.e(LOG_TAG, "CopyFile: null parameter");
            return false;
        }

        File file = new File(f.filePath);
        String newPath = Util.makePath(dest, f.fileName);
        try {
            return file.renameTo(new File(newPath));
        } catch (SecurityException e) {
            Log.e(LOG_TAG, "Fail to move file," + e.toString());
        }
        return false;
    }

    public boolean EncryptFile(FileInfo f, Context context){Log.d("shanlihou", "../../mifile//src/net/micode/fileexplorer/FileOperationHelper.java: EncryptFile");
        try {
            InputStream  in = new FileInputStream(f.filePath);
            FileOutputStream fs1 = new FileOutputStream(f.filePath + "sh1");
            FileOutputStream fs2 = new FileOutputStream(f.filePath + "sh2");
            byte[] buffer = new byte[1024 * 1024];
            int length;
            int count = 0;
            while((length = in.read(buffer)) != -1){
                if(count % 2 == 0){
                    fs1.write(buffer, 0, length);
                }else{
                    fs2.write(buffer, 0, length);
                }
                count ++;
            }
            in.close();
            File file = new File(f.filePath);
            file.delete();
        }catch (Exception e){
            e.printStackTrace();
        }

        PrivateDatabaseHelper.getInstance(context).insert(f.fileName, f.filePath);
        return true;
    }

    public boolean DecryptFile(FileInfo f, Context context){Log.d("shanlihou", "../../mifile//src/net/micode/fileexplorer/FileOperationHelper.java: DecryptFile");
        Log.d("shanlihou", f.fileName);
        if(!f.fileName.equals("shily")){
            return false;
        }
        Log.d("shanlihou", "shily decrypt");
        Cursor cursor = PrivateDatabaseHelper.getInstance(context).query();
        int count = cursor.getCount();
        Toast.makeText(context, "" + count, Toast.LENGTH_SHORT).show();
        cursor.moveToNext();
        while(cursor.moveToNext()){
            Log.d("shanlihou", "cursor name:" + cursor.getString(2));
            DecryptFile(cursor.getString(2));
            PrivateDatabaseHelper.getInstance(context).delete(cursor.getString(2));
        }
        return true;
    }
    public boolean DecryptFile(String f){Log.d("shanlihou", "../../mifile//src/net/micode/fileexplorer/FileOperationHelper.java: DecryptFile");
        try {
            FileOutputStream fs = new FileOutputStream(f);
            InputStream fs1 = new FileInputStream(f + "sh1");
            InputStream fs2 = new FileInputStream(f + "sh2");
            byte[] buffer = new byte[1024 * 1024];
            int length1 = 0, length2 = 0;
            while(length1 != -1 || length2 != -1){
                length1 = fs1.read(buffer);
                if(length1 != -1){
                    fs.write(buffer, 0, length1);
                }
                length2 = fs2.read(buffer);
                if(length2 != -1){
                    fs.write(buffer, 0, length2);
                }
            }
            fs1.close();
            fs2.close();

            File file1 = new File(f + "sh1");
            File file2 = new File(f + "sh2");
            file1.delete();
            file2.delete();
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }

    private void copyFileList(ArrayList<FileInfo> files) {Log.d("shanlihou", "../../mifile//src/net/micode/fileexplorer/FileOperationHelper.java: copyFileList");
        synchronized(mCurFileNameList) {
            mCurFileNameList.clear();
            for (FileInfo f : files) {
                mCurFileNameList.add(f);
            }
        }
    }

}
