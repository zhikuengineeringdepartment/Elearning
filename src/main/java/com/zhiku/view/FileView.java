package com.zhiku.view;

import com.zhiku.entity.File;
import com.zhiku.entity.FileKeys;

public class FileView extends File {
    private FileKeys fileKeys;

    public FileKeys getFileKeys() {
        return fileKeys;
    }

    public void setFileKeys(FileKeys fileKeys) {
        this.fileKeys = fileKeys;
    }
}
