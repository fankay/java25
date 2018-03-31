package com.kaishengit.proxy;

public class Mp3 implements Player {

    public void play(String musicName) {
        System.out.println("播放" + musicName);
    }

    public void stop() {
        System.out.println("停止播放");
    }
}
