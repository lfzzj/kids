package com.hamitao.kids.model;

/**
 * Created by linjianwen on 2018/2/24.
 *
 * 歌单
 */

public class PlayListModel {

    public final long id;

    /**
     * 歌单名
     */
    public final String name;

    /**
     * 歌曲数
     */
    public final int songCount;

    /**
     * 专辑封面
     */
    public final String albumArt;

    /**
     * 作者
     */
    public final String author;

    public PlayListModel() {
        this.id = -1;
        this.name = "";
        this.songCount = -1;
        this.albumArt = "";
        this.author = "";
    }


    public PlayListModel(long _id, String _name, int _songCount, String _albumArt, String author) {
        this.id = _id;
        this.name = _name;
        this.songCount = _songCount;
        this.albumArt = _albumArt;
        this.author = author;
    }
}
