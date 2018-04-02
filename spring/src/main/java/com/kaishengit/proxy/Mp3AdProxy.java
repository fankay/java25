package com.kaishengit.proxy;

public class Mp3AdProxy implements Player {

    //目标对象
    private Player target;

    public Mp3AdProxy(Player target) {
        this.target = target;
    }

    public void play(String musicName) {
        System.out.println("播放30秒广告。。。。。");
        target.play(musicName);
    }

    public int stop() {
        target.stop();
        return 10;
    }
}
