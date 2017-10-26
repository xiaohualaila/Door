package ug.door.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by 付博文 on 2017/9/1.
 */
@Entity
public class FileDb {

    @Id
    private String id;
    private String FilePath;
    @Generated(hash = 930236958)
    public FileDb(String id, String FilePath) {
        this.id = id;
        this.FilePath = FilePath;
    }
    @Generated(hash = 73662916)
    public FileDb() {
    }
    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getFilePath() {
        return this.FilePath;
    }
    public void setFilePath(String FilePath) {
        this.FilePath = FilePath;
    }


}