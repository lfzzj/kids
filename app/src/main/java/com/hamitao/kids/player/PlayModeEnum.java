package com.hamitao.kids.player;

/**
 * Created by linjianwen on 2018/3/1.
 */

public enum PlayModeEnum {

   /* LOOP(0),
    SHUFFLE(1),
    SINGLE(2);*/

    LOOP(0),
    /*   SHUFFLE(1),*/
    SINGLE(1);

    private int value;

    PlayModeEnum(int value) {
        this.value = value;
    }

    public static PlayModeEnum valueOf(int value) {
        switch (value) {
/*            case 1:
                return SHUFFLE;*/
            case 1:
                return SINGLE;
            case 0:
            default:
                return LOOP;
        }
    }

    public int value() {
        return value;
    }
}
